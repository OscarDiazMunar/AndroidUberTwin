package com.oscar.androidubertwin.data.repository;

import com.oscar.androidubertwin.domain.model.ResponseFCM;
import com.oscar.androidubertwin.domain.model.SenderFCM;

import io.reactivex.Observable;

/**
 * Created by oscar on 2/13/2018.
 */

public interface RepositoryNotification {
    /**
     * Send notification message observable.
     *
     * @param senderFCM the sender fcm
     * @return the observable
     */
    Observable<ResponseFCM> sendNotificationMessage(SenderFCM senderFCM);
}
