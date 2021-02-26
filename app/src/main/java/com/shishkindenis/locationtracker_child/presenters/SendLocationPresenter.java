package com.shishkindenis.locationtracker_child.presenters;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.views.SendLocationView;
import com.shishkindenis.locationtracker_child.workers.LocationWorker;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SendLocationPresenter extends MvpPresenter<SendLocationView> {

    public SendLocationPresenter() { }

    public void doWork() {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    public void signOut() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
