package com.shishkindenis.locationtracker_child.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SendLocationView extends MvpView {
    public void showToast(String toastMessage);
    public void showLocationSourceSettings();
    public void requestPermissions();
}
