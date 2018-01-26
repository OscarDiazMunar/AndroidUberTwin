package com.oscar.androidubertwin.presentation.presenter.CustomerCallPresenter;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by oscar on 1/25/2018.
 */
public interface ICustomerCallPresenter {
    /**
     * Gets direction.
     *
     * @param destination     the destination
     * @param currentPosition the current position
     */
    void getDirection(String destination, LatLng currentPosition);
}
