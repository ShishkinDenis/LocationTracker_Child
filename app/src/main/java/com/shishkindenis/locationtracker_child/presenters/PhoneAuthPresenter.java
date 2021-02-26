package com.shishkindenis.locationtracker_child.presenters;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.activities.MainActivity;
import com.shishkindenis.locationtracker_child.activities.SendLocationActivity;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class PhoneAuthPresenter extends MvpPresenter<PhoneAuthView> {

    public String mVerificationId;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    public PhoneAuthPresenter() {
    }

    public PhoneAuthProvider.OnVerificationStateChangedCallbacks callback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
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
                mVerificationId = verificationId;
                mResendToken = token;
                getViewState().enableVerifyButton();
            }
        };
        return mCallbacks;
    }

    public void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        MainActivity.user = task.getResult().getUser();
                        MainActivity.userID = MainActivity.user.getUid();

                        getViewState().showToast(R.string.authentication_successful);
                        getViewState().goToAnotherActivity(SendLocationActivity.class);
                    } else {
                        getViewState().showToast((R.string.authentication_failed));

                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            getViewState().showInvalidCodeError();
                        }
                    }
                });
    }

    public void verifyPhoneNumberWithCode(String verificationId, String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithPhoneAuthCredential(credential);
    }


}

