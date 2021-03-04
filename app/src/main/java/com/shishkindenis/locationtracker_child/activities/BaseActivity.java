package com.shishkindenis.locationtracker_child.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.shishkindenis.locationtracker_child.R;

import moxy.MvpAppCompatActivity;

public class BaseActivity extends MvpAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    public void showToast(int toastMessage) {
        Toast.makeText(getApplicationContext(), toastMessage,
                Toast.LENGTH_LONG).show();
    }

    public void goToAnotherActivity(Class activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}