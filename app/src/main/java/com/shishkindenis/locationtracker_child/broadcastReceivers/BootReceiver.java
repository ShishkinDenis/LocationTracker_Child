package com.shishkindenis.locationtracker_child.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.workers.LocationWorker;

public class BootReceiver extends BroadcastReceiver {
    private FirebaseUser user;

    @Override
    public void onReceive(Context context, Intent intent) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user != null) {
            OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
            WorkManager.getInstance().enqueue(myWorkRequest);
        }
    }
}

