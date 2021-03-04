package com.shishkindenis.locationtracker_child.daggerUtils;

import android.app.Application;

public class MyApplication extends Application {

    public static ApplicationComponent appComponent = DaggerApplicationComponent.create();

}

