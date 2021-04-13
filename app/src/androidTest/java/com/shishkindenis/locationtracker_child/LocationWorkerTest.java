package com.shishkindenis.locationtracker_child;

import android.content.Context;
import android.util.Log;

import androidx.test.InstrumentationRegistry;
import androidx.work.Configuration;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.impl.utils.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import com.shishkindenis.locationtracker_child.presenters.SendLocationPresenter;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.SendLocationView$$State;
import com.shishkindenis.locationtracker_child.workers.LocationWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocationWorkerTest {
    SendLocationPresenter sendLocationPresenter;
    @Mock
    FirebaseUserSingleton firebaseUserSingleton;
    @Mock
    SendLocationView$$State sendLocationView$$State;

    @Before
    public void setUp() {
        sendLocationPresenter = new SendLocationPresenter(firebaseUserSingleton);
        sendLocationPresenter.setViewState(sendLocationView$$State);

        Context context = InstrumentationRegistry.getTargetContext();
        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(new SynchronousExecutor())
                .build();

        WorkManagerTestInitHelper.initializeTestWorkManager(
                context, config);
    }

    @Test
    public void startLocationWorkerIsCalled() {
        sendLocationPresenter.startLocationWorker();
    }

    @Test
    public void workManagerStateIsSucceeded() throws ExecutionException, InterruptedException {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager workManager = WorkManager.getInstance();
        workManager.enqueue(myWorkRequest).getResult().get();
        WorkInfo workInfo = workManager.getWorkInfoById(myWorkRequest.getId()).get();
        assertThat(workInfo.getState(), is(WorkInfo.State.SUCCEEDED));
    }
}
