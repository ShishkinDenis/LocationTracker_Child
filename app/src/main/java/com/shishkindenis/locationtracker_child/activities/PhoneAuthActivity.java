package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivityPhoneAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView;

import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class PhoneAuthActivity extends MvpAppCompatActivity implements PhoneAuthView {

    @InjectPresenter
    PhoneAuthPresenter phoneAuthPresenter;

    private ActivityPhoneAuthBinding binding;
    private FirebaseAuth auth;
    //    нужен этот булин?
    private boolean mVerificationInProgress = false;
    private boolean codeSent;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        auth = FirebaseAuth.getInstance();

        binding.btnRequestCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (validatePhoneNumber()) {
                startPhoneNumberVerification(binding.etPhoneNumber.getText().toString());
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });

        binding.btnVerifyCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (validateCode()) {
                verifyPhoneNumberWithCode(
                        mVerificationId, binding.etVerificationCode.getText().toString());
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                mVerificationInProgress = false;
                signInWithPhoneAuthCredential(credential);
            }
            @Override
            public void onVerificationFailed(FirebaseException e) {
                mVerificationInProgress = false;
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    binding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    showToast(R.string.quota_exceeded);
                }
            }
            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                mVerificationId = verificationId;
                mResendToken = token;
//                codeSent = true;
                binding.btnVerifyCode.setEnabled(true);
            }
        };
    }

    //        Почему onStart?
//    @Override
//    public void onStart() {
//        super.onStart();
//current user
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if (mVerificationInProgress && validatePhoneNumber()) {
//            startPhoneNumberVerification(activityPhoneAuthBinding.etPhoneNumber.getText().toString());
//        }
//    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        mVerificationInProgress = true;


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        showToast(R.string.authentication_successful);
//                        getViewState().showToast(R.string.authentication_successful);
                        goToAnotherActivity(SendLocationActivity.class);
                    } else {
                        showToast((R.string.authentication_failed));
//                        getViewState().showToast((R.string.authentication_failed));
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            binding.etVerificationCode.setError(getString(R.string.invalid_code));
                        }
                    }
                });
    }

    private boolean validatePhoneNumber() {
        if (binding.etPhoneNumber.getText().toString().isEmpty()) {
            binding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
            showToast(R.string.invalid_phone_number);
            return false;
        }
        return true;
    }

    private boolean validateCode() {
        if (binding.etVerificationCode.getText().toString().isEmpty()) {
            binding.etVerificationCode.setError(getString(R.string.cannot_be_empty));
            showToast(R.string.cannot_be_empty);
            return false;
        }
        return true;
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {
//        if (validatePhoneNumber()) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
//        }
//        else {
//            showToast(R.string.verification_failed);
//             getViewState().showToast(getString(R.string.verification_failed));
//        }
    }

//    private void signOut(){
//
//        if(auth.getCurrentUser() != null) {
//            auth.signOut();
//            showToast(R.string.sign_out_successful);
////            getViewState().showToast(R.string.sign_out_successful);
//        }
//        else {
//          showToast(R.string.you_havent_sign_in);
////            getViewState().showToast(R.string.you_havent_sign_in);
//        }
//    }

    public void goToAnotherActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(int toastMessage){
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

//    TODO Resending Code
//    private void resendVerificationCode(String phoneNumber,
//                                        PhoneAuthProvider.ForceResendingToken token) {
//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)       // Phone number to verify
//                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                        .setActivity(this)                 // Activity (for callback binding)
//                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
//                        .setForceResendingToken(token)     // ForceResendingToken from callbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
//    }
}