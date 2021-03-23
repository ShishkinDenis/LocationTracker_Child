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

//    @Inject
//    IdSingleton idSingleton;
//@Inject
//FirebaseUserSingleton firebaseUserSingleton;
FirebaseUserSingleton firebaseUserSingleton;
private String userId;




//    public EmailAuthPresenter() {
//
//        MyApplication.appComponent.inject(this);
//        Log.d("EmailAuthPresenter", "This is FirebaseUserSingleton injected");
//    }

    @Inject
    public EmailAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

//    FirebaseAuth auth = firebaseUserSingleton.getFirebaseAuth();

    public void createAccount(FirebaseAuth auth, String email, String password) {
//    public void createAccount(String email, String password) {
//    public void createAccount(String email, String password) {
//    public void createAccount(FirebaseUserSingleton firebaseUserSingleton, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
//        firebaseUserSingleton.getFirebaseAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        getViewState().showToastWithEmail("User with email: " + email + " was signed up ");
                    } else {
                        getViewState().showToast(R.string.signing_up_failed);
                    }
                });
    }

    public void signIn(FirebaseAuth auth, String email, String password) {
//    public void signIn(String email, String password) {
//    public void signIn(String email, String password) {
//    public void signIn(FirebaseUserSingleton firebaseUserSingleton, String email, String password) {
        auth.signInWithEmailAndPassword(email, password)
//        firebaseUserSingleton.getFirebaseAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
//                        FirebaseUser user = auth.getCurrentUser();
//                        FirebaseUser user = firebaseUserSingleton.getFirebaseAuth().getCurrentUser();
                        FirebaseUser user = auth.getCurrentUser();
                        userId = user.getUid();
//                        idSingleton.setUserId(userId);
                        firebaseUserSingleton.setUserId(userId);
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToSendLocationActivity();
                    } else {
                        getViewState().showToast((R.string.authentication_failed));
                    }
                });
    }

}
