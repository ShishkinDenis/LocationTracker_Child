package com.shishkindenis.locationtracker_child.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SendLocationView extends MvpView {

    void showLocationSourceSettings();

    void requestPermissions();

    void showToast(int toastMessage);
}
