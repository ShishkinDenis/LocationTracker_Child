package com.shishkindenis.locationtracker_child.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.shishkindenis.locationtracker_child.databinding.ActivitySendLocationBinding;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.views.SendLocationView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class SendLocationActivity extends MvpAppCompatActivity implements SendLocationView {

    @InjectPresenter
    SendLocationPresenter sendLocationPresenter;

    private ActivitySendLocationBinding activitySendLocationBinding;
    FusedLocationProviderClient mFusedLocationClient;
//    44?
    int PERMISSION_ID = 44;
//    String TAG = "TAG";
//    FirebaseFirestore firestoreDataBase;
//    Map<String, Object> locationMap;
//    String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySendLocationBinding = ActivitySendLocationBinding.inflate(getLayoutInflater());
        View view = activitySendLocationBinding.getRoot();
        setContentView(view);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }

    public void getLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                sendLocationPresenter.requestNewLocationData(mFusedLocationClient);
            }
            else {
                showToast("Please turn on detection of location");
                showLocationSourceSettings();
            }
        }
        else {
            requestPermissions();
        }
    }
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    @Override
    public void showToast(String toastMessage){
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }
    @Override
    public void showLocationSourceSettings(){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }


    //    public void addData() {
//        firestoreDataBase.collection(EmailAuthActivity.userID)
//                .add(locationMap)
//                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
//                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
//    }
//    public void getLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                requestNewLocationData();
//            }
//            else {
//                Log.d("Location","Please turn on" + " your location...");
//                Toast.makeText(getApplicationContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//            }
//        }
//        else {
//            requestPermissions();
//        }
//    }
    //    Зачем SupressLint?
 /*   @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        //Вынести в метод
                        locationMap.put("Latitude",location.getLatitude());
                        locationMap.put("Longitude",location.getLongitude());
                        //Вынести в метод
                        time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date());
                        locationMap.put("Time",time);

                        //Вынести в метод
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        locationMap.put("Date",dateFormat.format(new Date()));
                        Log.d("Location","Else: " + location.getLatitude());
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }*/

//    @SuppressLint("MissingPermission")
//    private void requestNewLocationData() {
//        LocationRequest mLocationRequest = new LocationRequest();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(15000);
//        mLocationRequest.setFastestInterval(15000);
////        mLocationRequest.setSmallestDisplacement(60);
////        mLocationRequest.setInterval(6000*10);
////        mLocationRequest.setFastestInterval(6000*10);
////        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
////            // TODO: Consider calling
////            //    ActivityCompat#requestPermissions
////            // here to request the missing permissions, and then overriding
////            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////            //                                          int[] grantResults)
////            // to handle the case where the user grants the permission. See the documentation
////            // for ActivityCompat#requestPermissions for more details.
////            return;
////        }
//        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//    }
//    private LocationCallback mLocationCallback = new LocationCallback() {
//        @Override
//        public void onLocationResult(LocationResult locationResult) {
//            Вынести в метод
//            Location mLastLocation = locationResult.getLastLocation();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date());
//
//            locationMap.put("Latitude",mLastLocation.getLatitude());
//            locationMap.put("Longitude",mLastLocation.getLongitude());
//            locationMap.put("Date",dateFormat.format(new Date()));
//            locationMap.put("Time",time);
//            addData();
//            Log.d("Location","TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
//        }
//    };
}