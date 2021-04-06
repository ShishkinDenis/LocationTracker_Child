package com.shishkindenis.locationtracker_child;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.SendLocationView$$State;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SendLocationPresenterTest {
    SendLocationPresenter sendLocationPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    SendLocationView$$State sendLocationView$$State;
    @Mock
    FirebaseAuth auth;

    @Before
    public void setUp() {
        sendLocationPresenter = new SendLocationPresenter(firebaseUserSingleton);
        sendLocationPresenter.setViewState(sendLocationView$$State);
    }

    @Test
    public void signOutIsCalled() {
        when(firebaseUserSingleton.getFirebaseAuth()).thenReturn(auth);
        sendLocationPresenter.signOut();
        verify(sendLocationView$$State).showToast(R.string.sign_out_successful);
    }
}
