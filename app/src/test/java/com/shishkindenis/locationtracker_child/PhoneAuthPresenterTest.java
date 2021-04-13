package com.shishkindenis.locationtracker_child;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PhoneAuthPresenterTest {
    PhoneAuthPresenter phoneAuthPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    FirebaseAuth auth;
    @Mock
    PhoneAuthCredential credential;
    @Mock
    Task task;
    @Mock
    PhoneAuthView$$State phoneAuthView$$State;

    @Before
    public void setUp() {
        phoneAuthPresenter = new PhoneAuthPresenter(firebaseUserSingleton);
        phoneAuthPresenter.setViewState(phoneAuthView$$State);
    }

    @Test
    public void signInWithCredentialIsCalled() {
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(auth.signInWithCredential(credential)).thenReturn(task);
        phoneAuthPresenter.signInWithPhoneAuthCredential(credential);
        verify(auth).signInWithCredential(credential);
    }

    @Test
    public void authenticationFailedToastIsCalled() {
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(auth.signInWithCredential(credential)).thenReturn(task);
        phoneAuthPresenter.signInWithPhoneAuthCredential(credential);
        ArgumentCaptor<OnCompleteListener<AuthResult>> listenerCaptor =
                ArgumentCaptor.forClass(OnCompleteListener.class);
        verify(task).addOnCompleteListener(listenerCaptor.capture());
        OnCompleteListener<AuthResult> onCompleteListener = listenerCaptor.getValue();
        onCompleteListener.onComplete(task);
        verify(phoneAuthView$$State).showToast((R.string.authentication_failed));
    }

    @Test
    public void verifyPhoneNumberWithCodeIsCalled(){
        String code = "777";
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        when(task.addOnCanceledListener(any())).thenReturn(task);
        when(auth.signInWithCredential(any())).thenReturn(task);
        phoneAuthPresenter.verifyPhoneNumberWithCode(code);
        verify(auth).signInWithCredential(any());
    }
}
