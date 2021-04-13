package com.shishkindenis.locationtracker_child.presenters;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class PhoneAuthPresenter extends MvpPresenter<PhoneAuthView> {
    FirebaseUserSingleton firebaseUserSingleton;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String phoneVerificationId = "phoneVerificationId";
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private String userId;

    @Inject
    public PhoneAuthPresenter(FirebaseUserSingleton firebaseUserSingleton) {
        this.firebaseUserSingleton = firebaseUserSingleton;
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks phoneVerificationCallback() {
        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    getViewState().showInvalidPhoneNumberError();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    getViewState().showToast(R.string.quota_exceeded);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                phoneVerificationId = verificationId;
                forceResendingToken = token;
                getViewState().enableVerifyButton();
            }
        };
        return callbacks;
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseUserSingleton.getFirebaseAuth().signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        userId = user.getUid();
                        firebaseUserSingleton.setUserId(userId);
                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToSendLocationActivity();
                    } else {
                        getViewState().showToast((R.string.authentication_failed));

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            getViewState().showInvalidCodeError();
                        }
                    }
                });
    }

    public void verifyPhoneNumberWithCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(phoneVerificationId, code);
        signInWithPhoneAuthCredential(credential);
    }

}


