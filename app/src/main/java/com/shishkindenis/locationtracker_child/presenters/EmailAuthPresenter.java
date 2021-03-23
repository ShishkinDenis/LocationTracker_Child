package com.shishkindenis.locationtracker_child.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {
    FirebaseUserSingleton firebaseUserSingleton;
    private String userId;

    @Inject
    public EmailAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public void createAccount(FirebaseAuth auth, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                    } else {
                        getViewState().showToast(R.string.signing_up_failed);
                    }
                });
    }

    public void signIn(FirebaseAuth auth, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        userId = user.getUid();
                        firebaseUserSingleton.setUserId(userId);
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToSendLocationActivity();
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
