package com.shishkindenis.locationtracker_child.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class EmailAuthActivity extends MvpAppCompatActivity implements EmailAuthView {

    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    private static final String TAG = "EmailPassword";

    private ActivityEmailAuthBinding activityEmailAuthBinding;

    private FirebaseAuth mAuth;

    public static String email;
    private String password;
    public static String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEmailAuthBinding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        View view = activityEmailAuthBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        email = activityEmailAuthBinding.etEmail.getText().toString();
        password = activityEmailAuthBinding.etPassword.getText().toString();


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



}