package com.shishkindenis.locationtracker_child.examples;

public class WorkerOld {
//    public class LocationWorker extends Worker {
//
//        final static String LONGITUDE_FIELD = "Longitude";
//        final static String LATITUDE_FIELD = "Latitude";
//        final static String TIME_FIELD = "Time";
//        final static String DATE_FIELD = "Date";
//        private final String datePattern = "yyyy-MM-dd";
//        FusedLocationProviderClient mFusedLocationClient;
//        String TAG = "TAG";
//        FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
//        Map<String, Object> locationMap = new HashMap<>();
//        String time;
//
//        private final LocationCallback mLocationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                getPosition(locationResult);
//                addData();
//                Log.d("Location", "TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
//            }
//        };
//
//        public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//            super(context, workerParams);
//            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
//        }
//
//        @NonNull
//        @Override
//        public Result doWork() {
////        заменить на RX
////        Observable.create(o -> getLocation())
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe();
//
////        socket timeout ~20-30 minutes
//
//            Handler handler = new Handler(Looper.getMainLooper());
//            handler.post(this::getLocation);
//
//            return Result.success();
//        }
//
//        public void getPosition(LocationResult locationResult) {
//            Location mLastLocation = locationResult.getLastLocation();
//            DateFormat dateFormat = new SimpleDateFormat(datePattern);
//            time = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date());
//            locationMap.put(LATITUDE_FIELD, mLastLocation.getLatitude());
//            locationMap.put(LONGITUDE_FIELD, mLastLocation.getLongitude());
//            locationMap.put(DATE_FIELD, dateFormat.format(new Date()));
//            locationMap.put(TIME_FIELD, time);
//        }
//
//        public void addData() {
////        firestoreDataBase.collection(MainActivity.getUserID())
////                .add(locationMap)
////                .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
////                .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));
//        }
//
//        public void getLocation() {
//            requestNewLocationData(mFusedLocationClient);
//        }
//
//        @SuppressLint("MissingPermission")
//        public void requestNewLocationData(FusedLocationProviderClient mFusedLocationClient) {
//            LocationRequest mLocationRequest = new LocationRequest();
//            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//            mLocationRequest.setInterval(60000);
//            mLocationRequest.setFastestInterval(60000);
////        TODO установить в оконччательном коммите
////        mLocationRequest.setSmallestDisplacement(60);
////        mLocationRequest.setInterval(60000*10);
////        mLocationRequest.setFastestInterval(60000*10);
//            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
//        }
//
//    }

}
