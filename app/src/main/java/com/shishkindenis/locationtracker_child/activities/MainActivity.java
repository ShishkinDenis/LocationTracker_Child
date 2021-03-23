package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_child.presenters.MainPresenter;
import com.shishkindenis.locationtracker_child.views.MainView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;

public class MainActivity extends BaseActivity implements MainView {

//    @InjectPresenter
//    MainPresenter mainPresenter;


    @Inject
    @InjectPresenter
    MainPresenter mainPresenter;

    @ProvidePresenter
    MainPresenter provideMainPresenter(){return mainPresenter;}

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MyApplication.appComponent.inject(this);
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if (savedInstanceState == null) {
            mainPresenter.checkIfUserLoggedIn();
        }

        binding.btnEmail.setOnClickListener(v -> goToEmailAuthActivity());
        binding.btnPhone.setOnClickListener(v -> goToPhoneAuthActivity());
    }

    @Override
    public void goToSendLocationActivityForResult() {
        Intent intent = new Intent(this, SendLocationActivity.class);
        finish();
        startActivityForResult(intent, 3);
    }

    public void goToEmailAuthActivity() {
        Intent intent = new Intent(this, EmailAuthActivity.class);
        finish();
        startActivity(intent);
    }

    public void goToPhoneAuthActivity() {
        Intent intent = new Intent(this, PhoneAuthActivity.class);
        finish();
        startActivity(intent);
    }
}