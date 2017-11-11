package com.oscar.androidubertwin.presentation.view;

import android.widget.EditText;

/**
 * Created by oscar on 11/10/2017.
 */

public interface IMainActivityView {
    void setMessageError(int type, String mesage);
    void setMessageSnackBar(String message);
    void navigateToWelcome();
    void showProgress();
    void dismissProgress();
    void enableButtons(boolean enable);
}
