package com.shishkindenis.locationtracker_child.daggerUtils;

import com.shishkindenis.locationtracker_child.activities.EmailAuthActivity;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.PhoneAuthActivity;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;

import dagger.Component;

@Component(modules = {CustomModule.class})
public interface ApplicationComponent {
    void inject(EmailAuthActivity emailAuthActivity);
    void inject(PhoneAuthActivity phoneAuthActivity);
    void inject(MainActivity mainActivity);
    void inject(SendLocationPresenter sendLocationPresenter);
}
