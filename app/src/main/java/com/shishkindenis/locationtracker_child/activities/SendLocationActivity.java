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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivitySendLocationBinding;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.services.ForegroundService;
import com.shishkindenis.locationtracker_child.views.SendLocationView;

import moxy.presenter.InjectPresenter;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;

public class SendLocationActivity extends BaseActivity implements SendLocationView {

    @InjectPresenter
    SendLocationPresenter sendLocationPresenter;

    private static final int PERMISSION_ID = 1;
    private FusedLocationProviderClient fusedLocationClient;
    private Intent intent;
    private boolean networkStatusOn;
    private boolean gpsStatusOn;
    private ActivitySendLocationBinding binding;
    private final BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                if (isGpsEnabled) {
                    startService();
                    binding.tvGpsStatus.setText(R.string.gps_status_on);
                    binding.tvProgress.setText(R.string.location_determination_in_progress);
                    gpsStatusOn = true;
                } else {
                    binding.tvGpsStatus.setText(R.string.gps_status_off);
                    gpsStatusOn = false;

                    if (!isNetworkConnected()) {
                        stopService();
                        showAlertDialog();
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

        intent = new Intent();
        setSupportActionBar(binding.toolbar);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (!checkIgnoringBatteryOptimizationsPermission()) {
            requestIgnoringBatteryOptimizations();
        }

        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(locationSwitchStateReceiver, filter);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityCallback());

        checkGpsStatus();
        checkNetworkStatus();

        if (!gpsStatusOn && !isNetworkConnected()) {
            showAlertDialog();
        } else {
            sendLocationToFirebase();
        }
    }

    public void startService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        ContextCompat.startForegroundService(this, serviceIntent);
    }

    @Override
    public void showToast(int toastMessage) {
        super.showToast(toastMessage);
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        sendLocationPresenter.signOut();
        setResult(RESULT_OK, intent);
        finish();
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

    public void showNetworkSettings() {
        Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
        startActivity(intent);
    }

    public void showAlertDialog() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.location_determination_is_impossible)
                .setPositiveButton(R.string.network, (dialog, which) -> showNetworkSettings())
                .setNegativeButton(R.string.gps, (dialog, which) -> showLocationSourceSettings())
                .show();
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

    public boolean checkIgnoringBatteryOptimizationsPermission() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS) == PackageManager.PERMISSION_GRANTED;
    }

    public boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
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
                sendLocationPresenter.startLocationWorker();
            } else {
                showToast(R.string.turn_on_determination_of_location);
                showLocationSourceSettings();
            }
        } else {
            requestPermissions();
        }
    }

    public void stopService() {
        Intent serviceIntent = new Intent(this, ForegroundService.class);
        stopService(serviceIntent);
    }

    public class ConnectivityCallback extends ConnectivityManager.NetworkCallback {

        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            boolean connected = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET);
            networkStatusOn = true;
            startService();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> {
                binding.tvNetworkStatus.setText(R.string.network_status_on);
                binding.tvProgress.setText(R.string.location_determination_in_progress);
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
                    stopService();
                    showAlertDialog();
                    binding.tvProgress.setText(R.string.location_determination_off);
                }
            });
        }
    }
    
}
