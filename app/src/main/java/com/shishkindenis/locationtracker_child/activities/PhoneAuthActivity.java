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
                phoneAuthPresenter.verifyPhoneNumberWithCode(

                        phoneAuthPresenter.mVerificationId, binding.etVerificationCode.getText().toString());

            }
            binding.pbPhoneAuth.setVisibility(View.INVISIBLE);
        });


        phoneAuthPresenter.callback();
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
    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void showToast(int toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
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