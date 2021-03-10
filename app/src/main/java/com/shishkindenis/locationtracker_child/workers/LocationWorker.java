package com.shishkindenis.locationtracker_child.workers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.shishkindenis.locationtracker_child.services.ForegroundService;

public class LocationWorker extends Worker {

    Intent serviceIntent;


    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        serviceIntent = new Intent(context, ForegroundService.class);
        ContextCompat.startForegroundService(context, serviceIntent);
    }

    @NonNull
    @Override
    public Result doWork() {

//        socket timeout ~20-30 minutes

//        Handler handler = new Handler(Looper.getMainLooper());
//        handler.post(this::getLocation);



        return Result.success();
    }


}
