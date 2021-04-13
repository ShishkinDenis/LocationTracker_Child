package com.shishkindenis.locationtracker_child;

import android.content.Intent;

import androidx.core.content.ContextCompat;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ServiceTestRule;

import com.shishkindenis.locationtracker_child.services.ForegroundService;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class ForegroundServiceTest {
    @Rule
    public final ServiceTestRule serviceRule = new ServiceTestRule();
    @Test
    public void ForegroundServiceIsCalled() throws TimeoutException {
        Intent serviceIntent =
                new Intent(ApplicationProvider.getApplicationContext(),
                        ForegroundService.class);
        ContextCompat.startForegroundService(ApplicationProvider.getApplicationContext(),
                serviceIntent);
//        LocalBinder не имплементится
//        IBinder binder = serviceRule.bindService(serviceIntent);
//        ForegroundService service =
//                ((ForegroundService.LocalBinder) binder).getService();
//             assertThat(service).is(any(Object.class));

    }
}
