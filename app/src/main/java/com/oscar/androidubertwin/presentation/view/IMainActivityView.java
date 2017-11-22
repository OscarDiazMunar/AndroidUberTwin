package com.oscar.androidubertwin.presentation.view;


import com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter.IMainActivityPresenter;
import com.oscar.androidubertwin.presentation.presenter.MainActivityPresenter.MainActivityPresenter;
import com.oscar.androidubertwin.presentation.presenter.Presenter;

/**
 * Created by oscar on 11/10/2017.
 */
public interface IMainActivityView extends Presenter.PView {
    /**
     * Sets message snack bar.
     *
     * @param message the message
     */
    void setMessageSnackBar(String message);

    /**
     * Navigate to welcome.
     */
    void navigateToWelcome();

    /**
     * Show progress.
     */
    void showProgress();

    /**
     * Dismiss progress.
     */
    void dismissProgress();

    /**
     * Enable buttons.
     *
     * @param enable the enable
     */
    void enableButtons(boolean enable);
}
