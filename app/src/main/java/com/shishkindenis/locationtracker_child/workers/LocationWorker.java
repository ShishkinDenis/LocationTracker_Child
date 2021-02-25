package com.shishkindenis.locationtracker_child.workers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shishkindenis.locationtracker_child.activities.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class LocationWorker extends Worker {

    FusedLocationProviderClient mFusedLocationClient;
    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
    Map<String, Object> locationMap = new HashMap<>();
    String time;
    int PERMISSION_ID = 1;

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    @NonNull
    @Override
    public Result doWork() {
//        заменить на RX
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                LocationWorker.this.getLocation();
            }
        });
//        someTask();
        return Result.success();
    }

    public void getLocation(){
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//
//        requestNewLocationData(mFusedLocationClient);
//            }
//            else {
//                showToast("Please turn on detection of location");
//                showLocationSourceSettings();
//            }
//        }
//        else {
//            requestPermissions();
//        }


        requestNewLocationData(mFusedLocationClient);
    }

    public void addData() {
        firestoreDataBase.collection(MainActivity.getUserID())
                .add(locationMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(6000);
        mLocationRequest.setFastestInterval(6000);
//        TODO установить в оконччательном коммите
//        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(60000*10);
//        mLocationRequest.setFastestInterval(60000*10);

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

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

//    public boolean checkPermissions() {
//        return ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
//    }
//    public boolean isLocationEnabled() {
//        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }
//void someTask() {
//    new Thread(() -> {
//        for (int i = 1; i<=5; i++) {
//            Log.d(TAG, "i = " + i);
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }).start();
//}
}
