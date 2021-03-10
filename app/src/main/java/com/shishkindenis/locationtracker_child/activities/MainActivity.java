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

        binding.btnEmail.setOnClickListener(v -> goToAnotherActivity(EmailAuthActivity.class));
        binding.btnPhone.setOnClickListener(v -> goToAnotherActivity(PhoneAuthActivity.class));
    }


    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    @Override
    public void goToAnotherActivityForResult(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, 2);
    }


}