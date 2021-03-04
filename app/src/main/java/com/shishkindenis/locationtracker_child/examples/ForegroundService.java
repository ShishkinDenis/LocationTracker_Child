package com.shishkindenis.locationtracker_child.examples;

/*public class ForegroundService extends Service {
    MediaPlayer myPlayer;
    @Override
    public void onCreate() {
        super.onCreate();

        myPlayer = MediaPlayer.create(this, R.raw.track);
        myPlayer.setLooping(false); // Set looping
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        myPlayer.start();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        //Build a notification
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Forground Service")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
        //A notifcation HAS to be passed for the foreground service to be started.
        startForeground(1, notification);;

        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        myPlayer.stop();
    }

    //Used for Bound service,At this point let's keep it null
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}*/
