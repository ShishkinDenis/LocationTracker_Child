package com.shishkindenis.locationtracker_child.examples;

/*public class ConnectivityCallback extends ConnectivityManager.NetworkCallback {
//   SendLocationActivity sendLocationActivity = new SendLocationActivity(this);

    @Override
    public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        boolean connected = networkCapabilities.hasCapability(NET_CAPABILITY_INTERNET);
        Log.d("Net", Boolean.toString(connected));
//        Toast.makeText(getApplicationContext(), "f",
//                Toast.LENGTH_LONG).show();
        SendLocationActivity.networkStatusOn = true;

    }

    @Override
    public void onLost(@NonNull Network network) {
        super.onLost(network);
        Log.d("Net", "Связь потеряна");
//        sendLocationActivity.showToast("Отключено");
        SendLocationActivity.networkStatusOn = false;
        if(!SendLocationActivity.gpsStatusOn){
            Log.d("Net", "Detection is impossible");
        }
    }
}*/
