package com.shishkindenis.locationtracker_child.services;

//Удалить класс
/*public class LocationService extends Service {
   /* FusedLocationProviderClient mFusedLocationClient;
    String TAG = "TAG";
    FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
    Map<String, Object> locationMap = new HashMap<>();
    String time;

    public LocationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        SendLocationActivity sendLocationActivity = new SendLocationActivity();
//        sendLocationActivity.someTask();
//        В ОТДЕЛЬНЫЙ ПОТОК
      getLocation();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void getLocation(){
//        if (checkPermissions()) {
//            if (isLocationEnabled()) {
//                sendLocationPresenter.requestNewLocationData(mFusedLocationClient);
//            }
//            else {
//                showToast("Please turn on detection of location");
//                showLocationSourceSettings();
//            }
//        }
//        else {
//            requestPermissions();
//        }

//        sendLocationPresenter.someTask();
//        someTask();
        requestNewLocationData(mFusedLocationClient);
    }

    public void addData() {
        firestoreDataBase.collection(EmailAuthActivity.userID)
                .add(locationMap)
                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
    }

    @SuppressLint("MissingPermission")
    public void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(60000);
        mLocationRequest.setSmallestDisplacement(60);
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
}*/