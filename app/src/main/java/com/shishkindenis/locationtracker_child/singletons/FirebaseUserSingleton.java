package com.shishkindenis.locationtracker_child.singletons;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class FirebaseUserSingleton {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private String userId;

    @Inject
    public FirebaseUserSingleton() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public FirebaseAuth getFirebaseAuth() {
        return auth;
    }
}
