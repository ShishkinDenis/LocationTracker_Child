package com.shishkindenis.locationtracker_child.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ForegroundService extends Service {

    final static String LONGITUDE_FIELD = "Longitude";
    final static String LATITUDE_FIELD = "Latitude";
    final static String TIME_FIELD = "Time";
    final static String DATE_FIELD = "Date";
    private final String datePattern = "yyyy-MM-dd";
    FusedLocationProviderClient mFusedLocationClient;
    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
    Map<String, Object> locationMap = new HashMap<>();
    String time;


//    private static final String TAG2 = ForegroundService.class.getName();
//    зачем public
    public static final String CHANNEL_ID = "ForegroundServiceChannel";


    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
//        Intent notificationIntent = new Intent(this, MainActivity.class);
        Intent notificationIntent = new Intent(this, SendLocationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Foreground Service")
//                нужен?
                .setContentText("")
                //                нужен?
                .setSmallIcon(R.drawable.map)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                //                нужен?
                .setVibrate(null) // Passing null here silently fails
                .setContentIntent(pendingIntent);

//как быть со стоп?
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            getLocation();
            notification.setContentText("Location determination in progress");
            startForeground(1, notification.build());
        });
//        это что?
        return START_NOT_STICKY;
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            serviceChannel.enableVibration(false);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            getPosition(locationResult);
            addData();
            Log.d("Location", "TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
        }
    };

    public void getPosition(LocationResult locationResult) {
        Location mLastLocation = locationResult.getLastLocation();
        DateFormat dateFormat = new SimpleDateFormat(datePattern);
        time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date());
        locationMap.put(LATITUDE_FIELD, mLastLocation.getLatitude());
        locationMap.put(LONGITUDE_FIELD, mLastLocation.getLongitude());
        locationMap.put(DATE_FIELD, dateFormat.format(new Date()));
        locationMap.put(TIME_FIELD, time);
    }

    public void addData() {
        firestoreDataBase.collection(MainActivity.getUserID())
                .add(locationMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    public void getLocation() {
        requestNewLocationData(mFusedLocationClient);
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(30000);
        mLocationRequest.setFastestInterval(30000);
//        TODO установить в оконччательном коммите
//        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(60000*10);
//        mLocationRequest.setFastestInterval(60000*10);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

}