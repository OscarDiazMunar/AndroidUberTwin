package com.oscar.androidubertwin.presentation.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.oscar.androidubertwin.presentation.presenter.Presenter;

import java.util.List;

/**
 * Created by oscar on 2/19/2018.
 */

public interface IDriverTrackingView extends Presenter.PView {
    /**
     * Sets camera updapte.
     *
     * @param bounds the bounds
     */
    void setCameraUpdapte(LatLngBounds bounds);

    /**
     * Sets poly line options.
     *
     * @param listPolyline the list polyline
     */
    void setPolyLineOptions(List<LatLng> listPolyline);
    /**
     * Sets toast notification.
     *
     * @param message the message
     */
    void setToastNotification(String message);
}
