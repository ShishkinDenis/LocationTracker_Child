package com.shishkindenis.locationtracker_child.presenters;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.shishkindenis.locationtracker_child.R;
import com.shishkindenis.locationtracker_child.singletons.FirebaseUserSingleton;
import com.shishkindenis.locationtracker_child.views.SendLocationView;
import com.shishkindenis.locationtracker_child.workers.LocationWorker;

import javax.inject.Inject;

import moxy.InjectViewState;
import moxy.MvpPresenter;

@InjectViewState
public class SendLocationPresenter extends MvpPresenter<SendLocationView> {

//    @Inject
//    FirebaseAuth auth;
//    @Inject
//    FirebaseUserSingleton firebaseUserSingleton;
    FirebaseUserSingleton firebaseUserSingleton;


//    public SendLocationPresenter() {
//        MyApplication.appComponent.inject(this);
//    }
        @Inject
        public SendLocationPresenter(FirebaseUserSingleton firebaseUserSingleton) {
            this.firebaseUserSingleton = firebaseUserSingleton;
    
    }


    public void startLocationWorker() {
        OneTimeWorkRequest myWorkRequest = new OneTimeWorkRequest.Builder(LocationWorker.class).build();
        WorkManager.getInstance().enqueue(myWorkRequest);
    }

    public void signOut() {
        getViewState().stopService();
//        auth.signOut();
        firebaseUserSingleton.getFirebaseAuth().signOut();
        getViewState().showToast(R.string.sign_out_successful);
    }

}
