package com.oscar.androidubertwin.data.repository;

import com.google.android.gms.maps.model.LatLng;
import com.oscar.androidubertwin.domain.model.OverviewPolyline;
import com.oscar.androidubertwin.domain.model.RequestGoogleApi;
import com.oscar.androidubertwin.domain.model.ResponseFCM;
import com.oscar.androidubertwin.domain.model.SenderFCM;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;

import io.reactivex.Observable;

/**
 * Created by oscar on 11/16/2017.
 */
public interface Repository {
    /**
     * Gets request api.
     *
     * @param destination     the destination
     * @param currentPosition the current position
     * @return the request api
     */
    Observable<RequestGoogleApi> getRequestApi(String destination, LatLng currentPosition);
}
