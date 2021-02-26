package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.databinding.ActivityEmailAuthBinding;
import com.shishkindenis.locationtracker_child.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class EmailAuthActivity extends MvpAppCompatActivity implements EmailAuthView {

    @InjectPresenter
    EmailAuthPresenter emailAuthPresenter;

    private ActivityEmailAuthBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmailAuthBinding.inflate(getLayoutInflater());
        auth = FirebaseAuth.getInstance();
        View view = binding.getRoot();
        setContentView(view);

        binding.btnRegister.setOnClickListener(v -> {
            if (validateForm()) {
                binding.pbEmailAuth.setVisibility(View.VISIBLE);
                emailAuthPresenter.createAccount(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                binding.pbEmailAuth.setVisibility(View.INVISIBLE);
            }
        });
        binding.btnLogin.setOnClickListener(v -> {
            if (validateForm()) {
                binding.pbEmailAuth.setVisibility(View.VISIBLE);
                emailAuthPresenter.signIn(auth, binding.etEmail.getText().toString(),
                        binding.etPassword.getText().toString());
                binding.pbEmailAuth.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public void showToast(int toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public void showToastWithEmail(String toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public boolean validateForm() {
        boolean valid = true;
        if (binding.etEmail.getText().toString().isEmpty()) {
            binding.etEmail.setError(getString(R.string.required_email));
            valid = false;
        }
        if (binding.etPassword.getText().toString().isEmpty()) {
            binding.etPassword.setError(getString(R.string.required_password));
            valid = false;
        }
        return valid;
    }
}