package com.shishkindenis.locationtracker_child.activities;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityPhoneAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class PhoneAuthActivity extends BaseActivity implements PhoneAuthView {

    @InjectPresenter
    PhoneAuthPresenter phoneAuthPresenter;

    @Inject
    FirebaseAuth auth;

    private boolean phoneNumberValid;
    private boolean codeValid;
    private ActivityPhoneAuthBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneAuthBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MyApplication.appComponent.inject(this);

        binding.btnRequestCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (validatePhoneNumber()) {
                startPhoneNumberVerification(binding.etPhoneNumber.getText().toString());
            } else {
                setErrorIfInvalid();
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });
        binding.btnVerifyCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (validateCode()) {
                phoneAuthPresenter.verifyPhoneNumberWithCode(
                        auth, binding.etVerificationCode.getText().toString());
            } else {
                setErrorIfInvalid();
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });
        phoneAuthPresenter.phoneVerificationCallback(auth);
    }

    @Override
    public void showToast(int toastMessage) {
        super.showToast(toastMessage);
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(auth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(phoneAuthPresenter.phoneVerificationCallback(auth))
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }


    public boolean validatePhoneNumber() {
        phoneNumberValid = !binding.etPhoneNumber.getText().toString().isEmpty();
        return phoneNumberValid;
    }

    public boolean validateCode() {
        codeValid = !binding.etVerificationCode.getText().toString().isEmpty();
        return codeValid;
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
        if (!phoneNumberValid) {
            showInvalidPhoneNumberError();
        }
        if (!codeValid) {
            showInvalidCodeError();
        }
    }
}