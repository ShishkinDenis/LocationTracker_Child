package com.shishkindenis.locationtracker_child.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.singletons.IdSingleton;
import com.shishkindenis.locationtracker_child.views.MainView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    FirebaseAuth auth;

    @Inject
    IdSingleton idSingleton;

    private String userID;
    private FirebaseUser user;

    public MainPresenter() {
    }

    public void checkIfUserLoggedIn() {
//        куда правильно инжектить эту строчку в презентер?
        MyApplication.appComponent.inject(this);

        user = auth.getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            idSingleton.setUserId(userID);
            getViewState().goToAnotherActivityForResult(SendLocationActivity.class);
        }
        //        else what?
    }
}
