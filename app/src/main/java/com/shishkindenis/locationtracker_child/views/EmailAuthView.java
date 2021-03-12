package com.shishkindenis.locationtracker_child.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface EmailAuthView extends MvpView {

    void showToast(int toastMessage);

    void showToastWithEmail(String toastMessage);

    void goToSendLocationActivity();

}
