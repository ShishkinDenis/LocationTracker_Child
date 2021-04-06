package com.shishkindenis.locationtracker_child;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.shishkindenis.locationtracker_child.presenters.PhoneAuthPresenter;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.PhoneAuthView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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
        when(auth.signInWithCredential(credential)).thenReturn(task);
        phoneAuthPresenter.signInWithPhoneAuthCredential(auth, credential);
        verify(auth).signInWithCredential(credential);
    }
}
