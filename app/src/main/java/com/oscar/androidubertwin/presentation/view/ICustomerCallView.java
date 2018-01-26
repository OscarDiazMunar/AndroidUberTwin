package com.oscar.androidubertwin.presentation.view;

import com.oscar.androidubertwin.presentation.presenter.Presenter;

/**
 * Created by oscar on 1/25/2018.
 */
public interface ICustomerCallView extends Presenter.PView {
    /**
     * Sets information notification.
     *
     * @param distance   the distance
     * @param duration   the duration
     * @param endAddress the end address
     */
    void setInformationNotification(String distance, String duration, String endAddress);
}
