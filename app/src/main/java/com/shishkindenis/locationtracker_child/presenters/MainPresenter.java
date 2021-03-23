package com.shishkindenis.locationtracker_child.presenters;

import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.MainView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    FirebaseUserSingleton firebaseUserSingleton;
    private String userID;
    private FirebaseUser user;

    @Inject
    public MainPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public void checkIfUserLoggedIn() {
        user = firebaseUserSingleton.getFirebaseAuth().getCurrentUser();
        if (user != null) {
            userID = user.getUid();
            firebaseUserSingleton.setUserId(userID);
            getViewState().goToSendLocationActivityForResult();
        }
    }
}
