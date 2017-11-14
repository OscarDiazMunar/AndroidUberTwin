package com.oscar.androidubertwin.presentation.view;


/**
 * Created by oscar on 11/10/2017.
 */

public interface IMainActivityView {
    void setMessageSnackBar(String message);
    void navigateToWelcome();
    void showProgress();
    void dismissProgress();
    void enableButtons(boolean enable);
}
