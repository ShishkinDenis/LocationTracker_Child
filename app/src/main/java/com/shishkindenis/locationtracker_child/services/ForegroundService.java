package com.shishkindenis.locationtracker_child.services;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.singletons.IdSingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

public class ForegroundService extends Service {

    private final static String LONGITUDE_FIELD = "Longitude";
    private final static String LATITUDE_FIELD = "Latitude";
    private final static String TIME_FIELD = "Time";
    private final static String DATE_FIELD = "Date";
    private final static String datePattern = "yyyy-MM-dd";
    private final static String CHANNEL_ID = "ForegroundServiceChannel";
    private final static String TAG = "TAG";
    @Inject
    FirebaseAuth auth;
    @Inject
    IdSingleton idSingleton;
    private FusedLocationProviderClient mFusedLocationClient;
    private String userId;
    private final FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
    private final Map<String, Object> locationMap = new HashMap<>();
    private String time;
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            getPosition(locationResult);
            addData();
            Log.d("Location", "TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
        }
    };
    private FirebaseUser user;

    @Override
    public void onCreate() {
        super.onCreate();
        isGpsEnabled();
        MyApplication.appComponent.inject(this);
        user = auth.getCurrentUser();
        if (user != null) {
            idSingleton.setUserId(user.getUid());
            userId = idSingleton.getUserId();
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, SendLocationActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.location_tracker))
                .setSmallIcon(R.drawable.map)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setVibrate(null)
                .setContentIntent(pendingIntent);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            if (isNetworkConnected() || isGpsEnabled()) {
                getLocation();
                notification.setContentText(getString(R.string.location_determination_in_progress));
            } else {
                notification.setContentText(getString(R.string.location_determination_is_impossible));
            }
            startForeground(1, notification.build());
        });
        return START_NOT_STICKY;
    }

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
        firestoreDataBase.collection(userId)
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
        mLocationRequest.setInterval(15000);
        mLocationRequest.setFastestInterval(15000);
//        TODO установить в окончательном коммите
//        mLocationRequest.setSmallestDisplacement(60);
//        mLocationRequest.setInterval(60000*10);
//        mLocationRequest.setFastestInterval(60000*10);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    public boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}