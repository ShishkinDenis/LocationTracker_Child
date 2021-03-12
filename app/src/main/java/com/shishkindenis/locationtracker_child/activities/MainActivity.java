package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_child.presenters.MainPresenter;
import com.shishkindenis.locationtracker_child.views.MainView;

import moxy.presenter.InjectPresenter;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        MyApplication.appComponent.inject(this);

        if (savedInstanceState == null) {
            mainPresenter.checkIfUserLoggedIn();
        }

        binding.btnEmail.setOnClickListener(v -> goToEmailAuthActivity());
        binding.btnPhone.setOnClickListener(v -> goToPhoneAuthActivity());
    }

    @Override
    public void goToSendLocationActivityForResult() {
        Intent intent = new Intent(this, SendLocationActivity.class);
        startActivityForResult(intent, 3);
        finish();
    }

    public void goToEmailAuthActivity() {
        Intent intent = new Intent(this, EmailAuthActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToPhoneAuthActivity() {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        startActivity(intent);
        finish();
    }
}