package com.shishkindenis.locationtracker_child.daggerUtils;

import com.shishkindenis.locationtracker_child.activities.EmailAuthActivity;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.PhoneAuthActivity;
import com.shishkindenis.locationtracker_child.presenters.EmailAuthPresenter;
import com.shishkindenis.locationtracker_child.presenters.MainPresenter;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.services.ForegroundService;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FirebaseAuthModule.class,IdSingletonModule.class})
public interface ApplicationComponent {
    void inject(EmailAuthActivity emailAuthActivity);
    void inject(PhoneAuthActivity phoneAuthActivity);
    void inject(MainActivity mainActivity);

    void inject(MainPresenter mainPresenter);
    void inject(EmailAuthPresenter emailAuthPresenter);
    void inject(PhoneAuthPresenter phoneAuthPresenter);
    void inject(SendLocationPresenter sendLocationPresenter);

    void inject(ForegroundService foregroundService);
}
