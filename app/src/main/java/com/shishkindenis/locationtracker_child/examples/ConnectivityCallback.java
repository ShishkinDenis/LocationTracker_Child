package com.shishkindenis.locationtracker_child.examples;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.util.Log;

import androidx.annotation.NonNull;

import static android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET;

public class ConnectivityCallback extends ConnectivityManager.NetworkCallback{
//   SendLocationActivity sendLocationActivity = new SendLocationActivity(this);

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        boolean connected = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET);
        Log.d("Net", Boolean.toString(connected));
//        Toast.makeText(getApplicationContext(), "f",
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d("Net", "Связь потеряна");
//        sendLocationActivity.showToast("Отключено");
    }
}
