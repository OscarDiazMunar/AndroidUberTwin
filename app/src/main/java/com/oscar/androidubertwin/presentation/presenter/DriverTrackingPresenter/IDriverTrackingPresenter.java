package com.oscar.androidubertwin.presentation.presenter.DriverTrackingPresenter;

import com.google.android.gms.maps.model.LatLng;
import com.oscar.androidubertwin.domain.model.SenderFCM;

/**
 * Created by oscar on 2/19/2018.
 */
public interface IDriverTrackingPresenter {
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

    /**
     * Send message notification.
     *
     * @param senderFCM the sender fcm
     */
    void sendMessageNotification(SenderFCM senderFCM);
}
