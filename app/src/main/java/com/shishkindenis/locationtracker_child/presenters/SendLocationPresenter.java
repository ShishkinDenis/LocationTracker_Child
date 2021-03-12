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

    public SendLocationPresenter() {
        MyApplication.appComponent.inject(this);
    }

    public void startLocationWorker() {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    public void signOut() {
        getViewState().stopService();
        auth.signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
