package com.shishkindenis.locationtracker_child.examples;

/*public class SendLocationOld {
    public class SendLocationActivity extends MvpAppCompatActivity implements SendLocationView {

        @InjectPresenter
        SendLocationPresenter sendLocationPresenter;

        final static String LONGITUDE_FIELD = "Longitude";
        final static String LATITUDE_FIELD = "Latitude";
        final static String TIME_FIELD = "Time";
        final static String DATE_FIELD = "Date";
        FusedLocationProviderClient mFusedLocationClient;
        int PERMISSION_ID = 1;
        String TAG = "TAG";
        FirebaseFirestore firestoreDataBase = FirebaseFirestore.getInstance();
        Map<String, Object> locationMap = new HashMap<>();
        String time;
        private ActivitySendLocationBinding binding;
        private final String datePattern = "yyyy-MM-dd";

        private final LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                getPosition(locationResult);
                addData();
                Log.d("Location", "TIME:" + DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH).format(new Date()));
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

            OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
            WorkManager.getInstance().enqueue(myWorkRequest);
//
//        sendLocationPresenter.work();

//        getLocation();
//        startService(new Intent(this, LocationService.class));
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            signOut();
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
        public void showToast(String toastMessage) {
            Toast.makeText(getApplicationContext(), toastMessage,
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void showLocationSourceSettings() {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
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
            mLocationRequest.setInterval(30000);
            mLocationRequest.setFastestInterval(30000);
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

       /* public void requestIgnoringBatteryOptimizations() {
            Intent intent = new
                    Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
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

        public void signOut() {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            showToast(R.string.sign_out_successful);
        }

        public void goToAnotherActivity(Class activity) {
            Intent intent = new Intent(this, activity);
            startActivity(intent);
        }

        public void showToast(int toastMessage) {
            Toast.makeText(getApplicationContext(), toastMessage,
                    Toast.LENGTH_LONG).show();
        }


        public void getLocation() {
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

        public boolean checkPermissions() {
            return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        }

        public boolean isLocationEnabled() {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }
    }/*
}*/
