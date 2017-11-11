package com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter;

import android.widget.Button;

/**
 * Created by oscar on 11/10/2017.
 */

public interface IMainActivityPresenter {
    void onCreate();
    void checkValidationDataEntry(String email, String password, String name, String phone);
    void showRegisterDialog();
    void showLoginDialog();
}
