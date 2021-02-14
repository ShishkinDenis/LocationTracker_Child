package com.shishkindenis.locationtracker_child;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SignInView extends MvpView {

    void showError();

    void checkEmail();
    void checkPassword();
}
