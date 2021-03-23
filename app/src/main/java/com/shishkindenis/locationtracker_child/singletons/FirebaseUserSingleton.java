package com.shishkindenis.locationtracker_child.singletons;

import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;

public class FirebaseUserSingleton {

    @Inject
    public FirebaseUserSingleton(){}

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    public FirebaseAuth getFirebaseAuth(){
        return auth;
    }
}
