package com.shishkindenis.locationtracker_child.presenters;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.daggerUtils.MyApplication;
import com.shishkindenis.locationtracker_child.views.SendLocationView;
import com.shishkindenis.locationtracker_child.workers.LocationWorker;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SendLocationPresenter extends MvpPresenter<SendLocationView> {

    @Inject
    FirebaseAuth auth;

    public SendLocationPresenter() { }

    public void doWork() {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager.getInstance().enqueue(myWorkRequest);

//        Код для периодичности в 15 мин
//        PeriodicWorkRequest locationWork = new PeriodicWorkRequest.Builder(
//                LocationWork.class, 15, TimeUnit.MINUTES).addTag(LocationWork.TAG).build();
//        WorkManager.getInstance().enqueue(locationWork);
    }

    public void signOut() {
        MyApplication.appComponent.inject(this);
        auth.signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
