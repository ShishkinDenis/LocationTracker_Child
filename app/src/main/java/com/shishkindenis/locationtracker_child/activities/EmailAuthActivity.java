package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class EmailAuthActivity extends BaseActivity implements EmailAuthView {

    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    @Inject
    FirebaseAuth auth;
    private ActivityEmailAuthBinding binding;

    public void goToSendLocationActivity() {
        Intent intent = new Intent(this, SendLocationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        MyApplication.appComponent.inject(this);
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(v -> {
            if (validateEmail() & validatePassword()) {
                registerIfValid();
            } else {
                setErrorIfInvalid();
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (validateEmail() & validatePassword()) {
                logInIfValid();
            } else {
                setErrorIfInvalid();
            }
        });
    }

    public void showToastWithEmail(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public boolean validateEmail() {
        return !binding.etEmail.getText().toString().isEmpty();
    }

    public boolean validatePassword() {
        return !binding.etPassword.getText().toString().isEmpty();
    }

    public void setErrorIfInvalid() {
        if (!validateEmail()) {
            binding.etEmail.setError(getString(R.string.required_email));
        }
        if (!validatePassword()) {
            binding.etPassword.setError(getString(R.string.required_password));
        }
    }

    public void logInIfValid() {
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.signIn(auth, binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

    public void registerIfValid() {
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.createAccount(auth, binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

}