package com.shishkindenis.locationtracker_child.activities;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivitySendLocationBinding;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.views.SendLocationView;

import java.util.Observable;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;

public class SendLocationActivity extends MvpAppCompatActivity implements SendLocationView {

    @InjectPresenter
    SendLocationPresenter sendLocationPresenter;
    FusedLocationProviderClient mFusedLocationClient;

    public static boolean networkStatusOn;
    public static boolean gpsStatusOn;
    int PERMISSION_ID = 1;
    private ActivitySendLocationBinding binding;
    private final BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGpsEnabled) {
                    binding.tvGpsStatus.setText(R.string.gps_status_on);
                    binding.tvProgress.setText(R.string.location_determination_in_progress);
                    gpsStatusOn = true;
                } else {
                    binding.tvGpsStatus.setText(R.string.gps_status_off);
                    gpsStatusOn = false;

                    if (!isNetworkConnected()) {
                        showToast(R.string.location_determination_is_impossible);
                        binding.tvProgress.setText(R.string.location_determination_off);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySendLocationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        setSupportActionBar(binding.toolbar);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        requestIgnoringBatteryOptimizations();

        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(locationSwitchStateReceiver, filter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityCallback());

        sendLocationToFirebase();
        checkGpsStatus();
        checkNetworkStatus();

        if (!gpsStatusOn && !isNetworkConnected()) {
            showToast(R.string.location_determination_is_impossible);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        sendLocationPresenter.signOut();
        goToAnotherActivity(MainActivity.class);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    @Override
    public void showLocationSourceSettings() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    public void requestIgnoringBatteryOptimizations() {
        Intent intent = new
                Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(int toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void checkGpsStatus() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGpsEnabled) {
            binding.tvGpsStatus.setText(R.string.gps_status_on);
            binding.tvProgress.setText(R.string.location_determination_in_progress);
            gpsStatusOn = true;
        } else {
            binding.tvGpsStatus.setText(R.string.gps_status_off);
            gpsStatusOn = false;
        }
    }

    public void checkNetworkStatus() {
        if (isNetworkConnected()) {
            binding.tvNetworkStatus.setText(R.string.network_status_on);
            binding.tvProgress.setText(R.string.location_determination_in_progress);
        } else {
            binding.tvNetworkStatus.setText(R.string.network_status_off);
        }
    }

    public void sendLocationToFirebase() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                sendLocationPresenter.doWork();
            } else {
                showToast(R.string.turn_on_detection_of_location);
                showLocationSourceSettings();
            }
        } else {
            requestPermissions();
        }
    }

    public class ConnectivityCallback extends ConnectivityManager.NetworkCallback {

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            boolean connected = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET);
            networkStatusOn = true;

            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                binding.tvNetworkStatus.setText(R.string.network_status_on);
                binding.tvProgress.setText(R.string.location_determination);
            });



        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
            networkStatusOn = false;
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                binding.tvNetworkStatus.setText(R.string.network_status_off);
                if (!gpsStatusOn) {
                    showToast(R.string.location_determination_is_impossible);
                    binding.tvProgress.setText(R.string.location_determination_off);
                }
            });
        }
    }
}
