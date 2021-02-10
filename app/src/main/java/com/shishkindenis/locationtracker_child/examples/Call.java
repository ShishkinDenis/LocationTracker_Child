package com.shishkindenis.locationtracker_child.examples;

import android.util.Log;

public class Call {

    private void launch(){
        log("Creating callback");
        ReadyCallback readyCallback = new ReadyCallback() {
            @Override
            public void onReady() {
                Call.this.log("Ready do to anything");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log("Выполнил операцию в две секунды");
            }
        };
        log("callback created, launching thread");

        new Thread(() -> {
            try {
                Thread.sleep(5000);
                readyCallback.onReady();
//                log("Ready do to anything");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        log("Thread launched");

    }
    public void log (String message){
        System.out.println(message);

    }
    private interface ReadyCallback{
        void onReady();
    }

    public static void main(String[] args) {
        Call call = new Call();
        call.launch();
    }
}
