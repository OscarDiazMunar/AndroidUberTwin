package com.oscar.androidubertwin.presentation.presenter.MapsActivityPresenter;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by oscar on 11/11/2017.
 */
public interface IMapsActivityPresenter {
    /**
     * On create.
     */
    void onCreate();

    /**
     * Gets direction.
     *
     * @param destination     the destination
     * @param currentPosition the current position
     */
    void getDirection(String destination, LatLng currentPosition);
}
