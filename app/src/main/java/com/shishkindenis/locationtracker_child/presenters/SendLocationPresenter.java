package com.shishkindenis.locationtracker_child.presenters;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shishkindenis.locationtracker_child.activities.EmailAuthActivity;
import com.shishkindenis.locationtracker_child.views.SendLocationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SendLocationPresenter extends MvpPresenter<SendLocationView> {

    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
    Map<String, Object> locationMap = new HashMap<>();
    String time;

    public void addData() {
        firestoreDataBase.collection(EmailAuthActivity.userID)
                .add(locationMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

//    public void getLocation() {
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                requestNewLocationData();
//            }
//            else {
//                Log.d("Location","Please turn on" + " your location...");
//                Toast.makeText(getApplicationContext(), "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
//                getViewState().showToast("Please turn on" + " your location...");
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(intent);
//                getViewState().showLocationSourceSettings();
//            }
//        }
//        else {
//            getViewState().requestPermissions();
//        }
//    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(15000);
//        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(6000*10);
//        mLocationRequest.setFastestInterval(6000*10);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

//    private boolean checkPermissions() {
//        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }

//    private void requestPermissions() {
//        ActivityCompat.requestPermissions(this, new String[]{
//                Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
//    }

//    private boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            //Вынести в метод
            Location mLastLocation = locationResult.getLastLocation();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date());

            locationMap.put("Latitude",mLastLocation.getLatitude());
            locationMap.put("Longitude",mLastLocation.getLongitude());
            locationMap.put("Date",dateFormat.format(new Date()));
            locationMap.put("Time",time);
            addData();
            Log.d("Location","TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
        }
    };
}
