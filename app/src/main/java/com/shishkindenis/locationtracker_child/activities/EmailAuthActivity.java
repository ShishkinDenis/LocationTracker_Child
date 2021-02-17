package com.shishkindenis.locationtracker_child.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;

public class EmailAuthActivity extends MvpAppCompatActivity implements EmailAuthView {


    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    private static final String TAG = "EmailPassword";

    private ActivityEmailAuthBinding activityEmailAuthBinding;

    private FirebaseAuth mAuth;

    public static String email;
    private String password;
    public static String userID;

//    boolean networkIsStopped;
//    boolean gpsIsStopped;

    private static final int REQUEST_PERMISSIONS_LOCATION_SETTINGS_REQUEST_CODE = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmailAuthBinding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        View view = activityEmailAuthBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        email = activityEmailAuthBinding.etEmail.getText().toString();
        password = activityEmailAuthBinding.etPassword.getText().toString();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        // API 24+
        connectivityManager.registerDefaultNetworkCallback(new ConnectivityCallback());

        IntentFilter filter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        this.registerReceiver(locationSwitchStateReceiver, filter);



//        LocationManager manager =
//                (LocationManager) getSystemService(LOCATION_SERVICE);
//
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//             TODO: Consider calling
//                ActivityCompat#requestPermissions
//             here to request the missing permissions, and then overriding
//               public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                                      int[] grantResults)
//             to handle the case where the user grants the permission. See the documentation
//             for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        manager.registerGnssStatusCallback(gnssStatusListener);


//        isOnline();
//        ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

//mobile


//        if(checkInternetPermissions()){
//            Toast.makeText(getApplicationContext(), "Permission granted",
//            Toast.LENGTH_SHORT).show();
//        }
//        else{
//            requestInternetPermissions();
//        }
//
//        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
//        requestInternetPermissions();
//        requestPermissions();

//    if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
//
//   Toast.makeText(getApplicationContext(), "Не включено определение локации",
//            Toast.LENGTH_SHORT).show();
//}

        activityEmailAuthBinding.btnLogin.setOnClickListener(v -> {

            Toast toast = Toast.makeText(getApplicationContext(), "Действие",
                    Toast.LENGTH_SHORT);
            toast.show();

            checkEmail();
            checkPassword();
            activityEmailAuthBinding.etEmail.setError("f");
            showError();
        });
        activityEmailAuthBinding.btnRegister.setOnClickListener(v -> {
            createAccount(activityEmailAuthBinding.etEmail.getText().toString(), activityEmailAuthBinding.etPassword.getText().toString());
        });

        activityEmailAuthBinding.btnLogin.setOnClickListener(v -> signIn(activityEmailAuthBinding.etEmail.getText().toString(),
                activityEmailAuthBinding.etPassword.getText().toString()));

        activityEmailAuthBinding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(EmailAuthActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                    }

                    // [START_EXCLUDE]
//                        hideProgressBar();
                    // [END_EXCLUDE]
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressBar();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
//                        FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        FirebaseUser user = mAuth.getCurrentUser();
                      userID = user.getUid();
                        activityEmailAuthBinding.status.setText("Online");
                        //вынести в отдельный метод -goToMapActivity
                        Intent intent = new Intent(this, SendLocationActivity.class);
                        intent.putExtra("abc3", "abc3");
                        startActivity(intent);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(EmailAuthActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        // [START_EXCLUDE]
//                        checkForMultiFactorFailure(task.getException());
                        // [END_EXCLUDE]
                    }

                    // [START_EXCLUDE]
                    if (!task.isSuccessful()) {
//                        mBinding.status.setText(R.string.auth_failed);
                    }
//                        hideProgressBar();
                    // [END_EXCLUDE]
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        activityEmailAuthBinding.status.setText("OFFline");
    }

    private boolean validateForm() {
        boolean valid = true;

//        if (TextUtils.isEmpty(email)) {
        if (activityEmailAuthBinding.etEmail.getText().toString().isEmpty()) {
            activityEmailAuthBinding.etEmail.setError("Required email.");
            valid = false;
        } else {
            activityEmailAuthBinding.etEmail.setError(null);
        }

//        if (TextUtils.isEmpty(password)) {
        if (activityEmailAuthBinding.etPassword.getText().toString().isEmpty()) {
            activityEmailAuthBinding.etPassword.setError("Required.");
            valid = false;
        } else {
            activityEmailAuthBinding.etPassword.setError(null);
        }

        return valid;
    }

   /* private void updateUI(FirebaseUser user) {
        hideProgressBar();
        if (user != null) {
            mBinding.status.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            mBinding.emailPasswordButtons.setVisibility(View.GONE);
            mBinding.emailPasswordFields.setVisibility(View.GONE);
            mBinding.signedInButtons.setVisibility(View.VISIBLE);

            if (user.isEmailVerified()) {
                mBinding.verifyEmailButton.setVisibility(View.GONE);
            } else {
                mBinding.verifyEmailButton.setVisibility(View.VISIBLE);
            }
        } else {
            mBinding.status.setText(R.string.signed_out);
            mBinding.detail.setText(null);

            mBinding.emailPasswordButtons.setVisibility(View.VISIBLE);
            mBinding.emailPasswordFields.setVisibility(View.VISIBLE);
            mBinding.signedInButtons.setVisibility(View.GONE);
        }
    }*/

    private void showLoginFailed(){
//        TODO
        Toast.makeText(getApplicationContext(),"LogIn failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setMessage("Что-то")
//                .setOnCancelListener(dialog -> mRepositoriesPresenter.onErrorCancel())
                .show();
    }
    @Override
    public void checkEmail(){
        if (activityEmailAuthBinding.etEmail.getText().toString().isEmpty()){
            activityEmailAuthBinding.etEmail.setError("f");
        }

//        if else проверка на валидность
    }

    @Override
    public void checkPassword() {
        if (activityEmailAuthBinding.etPassword.getText().toString().isEmpty()){
            activityEmailAuthBinding.etPassword.setError("a");
        }
        //        if else проверка на валидность
    }


public class ConnectivityCallback extends ConnectivityManager.NetworkCallback{

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        boolean connected = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET);
        Log.d("Net", Boolean.toString(connected));
        Toast.makeText(getApplicationContext(), "Online",
                Toast.LENGTH_LONG).show();


    }

    @Override
    public void onLost(@NonNull Network network) {
        Log.d("Net", "Связь потеряна");
        Toast.makeText(getApplicationContext(), "Check internet connection",
                Toast.LENGTH_LONG).show();
//        networkIsStopped = true;
    }
}
    private BroadcastReceiver locationSwitchStateReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {

                LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isGpsEnabled /* isNetworkEnabled*/) {
                    //location is enabled
                     Log.d("Connection","On");
                    Toast.makeText(getApplicationContext(), "GPS turned on",
                            Toast.LENGTH_LONG).show();

                } else {
                    //location is disabled
                    Log.d("Connection","Off");
                    Toast.makeText(getApplicationContext(), "GPS turned off",
                            Toast.LENGTH_LONG).show();
//                    gpsIsStopped = true;
//                    if(networkIsStopped && gpsIsStopped){
//                        Toast.makeText(getApplicationContext(), "impossible",
//                                Toast.LENGTH_LONG).show();
//                        Log.d("Connection","impossible");
//                    }
                }
            }
        }
    };
//    public final GnssStatus.Callback gnssStatusListener = new GnssStatus.Callback() {
//        @Override
//        public void onStarted() {
//            Log.d("GPS","GPS started");
//        }
//
//        @Override
//        public void onStopped() {
//            Log.d("GPS","GPS stopped");
//        }
//
//        @Override
//        public void onSatelliteStatusChanged(GnssStatus status) {
//            Log.d("GPS","GPS started"+status.toString());
//        }
//    };

}