package com.oscar.androidubertwin.presentation.view;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.oscar.androidubertwin.presentation.presenter.Presenter;

import java.util.List;

/**
 * Created by oscar on 11/11/2017.
 */
public interface IMapsActivityView extends Presenter.PView {
    /**
     * Show progress.
     */
    void showProgress();

    /**
     * Dismiss progress.
     */
    void dismissProgress();

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
}
