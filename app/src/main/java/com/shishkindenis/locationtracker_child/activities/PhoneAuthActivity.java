package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityPhoneAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class PhoneAuthActivity extends BaseActivity implements PhoneAuthView {

    @InjectPresenter
    PhoneAuthPresenter phoneAuthPresenter;

//    @Inject
//    FirebaseAuth auth;
FirebaseAuth auth;

@Inject
FirebaseUserSingleton firebaseUserSingleton;
    private ActivityPhoneAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

       MyApplication.appComponent.inject(this);
        auth = firebaseUserSingleton.getFirebaseAuth();

        binding.btnRequestCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (phoneNumberIsValid()) {
                startPhoneNumberVerification(binding.etPhoneNumber.getText().toString());
            } else {
                setErrorIfInvalid();
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });
        binding.btnVerifyCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (codeIsValid()) {
                phoneAuthPresenter.verifyPhoneNumberWithCode(
//                        auth, binding.etVerificationCode.getText().toString());
                        auth, binding.etVerificationCode.getText().toString());
            } else {
                setErrorIfInvalid();
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });
//        phoneAuthPresenter.phoneVerificationCallback(auth);
        phoneAuthPresenter.phoneVerificationCallback(auth);
    }

    public void goToSendLocationActivity() {
        Intent intent = new Intent(this, SendLocationActivity.class);
        finish();
        startActivity(intent);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(auth)
                PhoneAuthOptions.newBuilder(firebaseUserSingleton.getFirebaseAuth())
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
//                        .setCallbacks(phoneAuthPresenter.phoneVerificationCallback(auth))
                        .setCallbacks(phoneAuthPresenter.phoneVerificationCallback(auth))
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    public boolean phoneNumberIsValid() {
        return !binding.etPhoneNumber.getText().toString().isEmpty();
    }

    public boolean codeIsValid() {
        return !binding.etVerificationCode.getText().toString().isEmpty();
    }

    @Override
    public void enableVerifyButton() {
        binding.btnVerifyCode.setEnabled(true);
    }

    @Override
    public void showInvalidPhoneNumberError() {
        binding.etPhoneNumber.setError(getString(R.string.invalid_phone_number));
    }

    @Override
    public void showInvalidCodeError() {
        binding.etVerificationCode.setError(getString(R.string.invalid_code));
    }

    public void setErrorIfInvalid() {
        if (!phoneNumberIsValid()) {
            showInvalidPhoneNumberError();
        }
        if (!codeIsValid()) {
            showInvalidCodeError();
        }
    }
}