package com.shishkindenis.locationtracker_child.workers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.shishkindenis.locationtracker_child.services.ForegroundService;

public class LocationWorker extends Worker {

    private final Intent serviceIntent;

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        serviceIntent = new Intent(context, ForegroundService.class);
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    @NonNull
    @Override
    public Result doWork() {
        return Result.success();
    }

}
