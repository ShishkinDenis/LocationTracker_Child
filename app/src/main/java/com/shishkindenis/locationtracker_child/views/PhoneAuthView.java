package com.shishkindenis.locationtracker_child.views;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface PhoneAuthView extends MvpView {

    void showToast(int toastMessage);

    void enableVerifyButton();

    void showInvalidPhoneNumberError();

    void showInvalidCodeError();

    void goToSendLocationActivity();
}
