package com.shishkindenis.locationtracker_child.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shishkindenis.locationtracker_child.databinding.ActivitySendLocationBinding;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.views.SendLocationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class SendLocationActivity extends MvpAppCompatActivity implements SendLocationView {

    @InjectPresenter
    SendLocationPresenter sendLocationPresenter;

    private ActivitySendLocationBinding activitySendLocationBinding;


    FusedLocationProviderClient mFusedLocationClient;

    int PERMISSION_ID = 44;


    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase;
    Map<String, Object> locationMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activitySendLocationBinding = ActivitySendLocationBinding.inflate(getLayoutInflater());
        View view = activitySendLocationBinding.getRoot();
        setContentView(view);

        Log.d("Location","onCreate");

        firestoreDataBase = FirebaseFirestore.getInstance();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();

        locationMap = new HashMap<>();
        locationMap.put("geo", "location");

        activitySendLocationBinding.btnSendLocation.setOnClickListener(v -> addData());

    }

    public void addData(){
        firestoreDataBase.collection(EmailAuthActivity.userID)
                .add(locationMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {


                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();

                    } else {
                        locationMap.put("Latitude",location.getLatitude());
                        locationMap.put("Longitude",location.getLongitude());

                        //Вынести в метод
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        locationMap.put("Date",dateFormat.format(new Date()));

                        Log.d("Location","Else: " + String.valueOf(location.getLatitude()));
//                        addData();

                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
//        ПРИМЕНИТЬ ИНТЕРВАЛ
//        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(6000*10);
//        mLocationRequest.setFastestInterval(6000*10);



//        УДАЛИТЬ
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());

    }

    private LocationCallback mLocationCallback = new LocationCallback() {


        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            Log.d("Location","Callback: " + mLastLocation.getLatitude());

            locationMap.put("Latitude",mLastLocation.getLatitude());
            locationMap.put("Longitude",mLastLocation.getLongitude());

         DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                locationMap.put("Date",dateFormat.format(new Date()));

                    addData();
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
            Log.d("Location","onResume");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Location","onStart");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Location","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Location","onDestroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Location","onPause");

    }

}