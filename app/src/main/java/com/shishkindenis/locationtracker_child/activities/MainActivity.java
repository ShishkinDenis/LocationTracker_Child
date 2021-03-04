package com.shishkindenis.locationtracker_child.activities;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_child.presenters.MainPresenter;
import com.shishkindenis.locationtracker_child.views.MainView;

import javax.inject.Inject;

import moxy.presenter.InjectPresenter;

public class MainActivity extends BaseActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

    @Inject
    FirebaseAuth auth;

// TODO:shared preferences
    public static String userID;
    public static FirebaseUser user;
    private ActivityMainBinding binding;

    public static String getUserID() {
        return userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MyApplication.appComponent.inject(this);
        user = auth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();
            goToAnotherActivity(SendLocationActivity.class);
            showButtonBackToCalendar();
        }
//        else

        binding.btnEmail.setOnClickListener(v -> goToAnotherActivity(EmailAuthActivity.class));
        binding.btnPhone.setOnClickListener(v -> goToAnotherActivity(PhoneAuthActivity.class));
        binding.btnBackToCalendar.setOnClickListener(v -> goToAnotherActivity(SendLocationActivity.class));
    }

    @Override
    public void goToAnotherActivity(Class activity) {
        super.goToAnotherActivity(activity);
    }

    public void showButtonBackToCalendar(){
        binding.btnBackToCalendar.setVisibility(View.VISIBLE);
        binding.ivCalendar.setVisibility(View.VISIBLE);
    }

}