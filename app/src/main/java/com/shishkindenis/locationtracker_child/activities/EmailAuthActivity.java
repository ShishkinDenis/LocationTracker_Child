package com.shishkindenis.locationtracker_child.activities;

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
    private boolean emailValid;
    private boolean passwordValid;
    private ActivityEmailAuthBinding binding;

    @Override
    public void showToast(int toastMessage) {
        super.showToast(toastMessage);
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
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
        emailValid = !binding.etEmail.getText().toString().isEmpty();
        return emailValid;
    }

    public boolean validatePassword() {
        passwordValid = !binding.etPassword.getText().toString().isEmpty();
        return passwordValid;
    }

    public void setErrorIfInvalid() {
        if (!emailValid) {
            binding.etEmail.setError(getString(R.string.required_email));
        }
        if (!passwordValid) {
            binding.etPassword.setError(getString(R.string.required_password));
        }
    }

    public void logInIfValid(){
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.signIn(auth, binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

    public void registerIfValid(){
        binding.pbEmailAuth.setVisibility(View.VISIBLE);
        emailAuthPresenter.createAccount(auth, binding.etEmail.getText().toString(),
                binding.etPassword.getText().toString());
        binding.pbEmailAuth.setVisibility(View.INVISIBLE);
    }

}