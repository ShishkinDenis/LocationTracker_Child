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
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });

        binding.btnVerifyCode.setOnClickListener(v -> {
            binding.pbPhoneAuth.setVisibility(View.VISIBLE);
            if (validateCode()) {
                phoneAuthPresenter.verifyPhoneNumberWithCode(
                        binding.etVerificationCode.getText().toString());
            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });
        //поставить в качестве параметра auth?
        phoneAuthPresenter.callback();
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
//                        .setCallbacks(mCallbacks)
                        .setCallbacks(phoneAuthPresenter.callback())
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
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

}