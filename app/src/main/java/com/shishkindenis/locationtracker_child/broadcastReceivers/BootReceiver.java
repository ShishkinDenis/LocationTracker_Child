package com.shishkindenis.locationtracker_child.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.shishkindenis.locationtracker_child.services.ForegroundService;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        if (intent.getAction() == Intent.ACTION_BOOT_COMPLETED) {
//         intent.setClass(context, ForegroundService.class);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            если все включено и все права получены..
//            context.startForegroundService(intent);
//        }
//        else {
//            context.startService(intent);
//        }
//    }

        //            если все включено и все права получены и пользователь зашел и автоСТАРТ включен

//        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
//            intent.setClass(context, ForegroundService.class);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                context.startForegroundService(intent);
//            }
//            else{
//                context.startService(intent);
//            }

//        }
        Intent service = new Intent(context, ForegroundService.class);
        if ("android.intent.action.BOOT_COMPLETED".equals(service.getAction())) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(service);
            } else {
                context.startService(service);
            }
        }
    }
}

