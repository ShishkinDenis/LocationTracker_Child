package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.databinding.ActivityMainBinding;
import com.shishkindenis.locationtracker_child.presenters.MainPresenter;
import com.shishkindenis.locationtracker_child.views.MainView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    @InjectPresenter
    MainPresenter mainPresenter;

// TODO:getter
    public static String userID;
    public static FirebaseUser user;
    private ActivityMainBinding activityMainBinding;
    private FirebaseAuth mAuth;

    public static String getUserID() {
        return userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            userID = user.getUid();
            goToAnotherActivity(SendLocationActivity.class);
        }

        activityMainBinding.btnEmail.setOnClickListener(v -> goToAnotherActivity(EmailAuthActivity.class));
        activityMainBinding.btnPhone.setOnClickListener(v -> goToAnotherActivity(PhoneAuthActivity.class));
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}