package com.shishkindenis.locationtracker_child.presenters;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;
import com.shishkindenis.locationtracker_child.views.EmailAuthView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class EmailAuthPresenter extends MvpPresenter<EmailAuthView> {

    public EmailAuthPresenter() {
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
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToAnotherActivity(SendLocationActivity.class);
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

//    public void signOut(FirebaseAuth auth) {
//        if(auth.getCurrentUser() != null) {
//            auth.signOut();
//            getViewState().showToast(R.string.sign_out_successful);
//        }
//        else {
//            getViewState().showToast(R.string.you_havent_sign_in);
//        }
//    }

}
