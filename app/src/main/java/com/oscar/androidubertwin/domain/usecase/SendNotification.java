package com.oscar.androidubertwin.domain.usecase;

import com.oscar.androidubertwin.data.repository.Repository;
import com.oscar.androidubertwin.data.repository.RepositoryNotification;
import com.oscar.androidubertwin.domain.model.SenderFCM;

import io.reactivex.Observable;

/**
 * Created by oscar on 2/13/2018.
 */
public class SendNotification extends UseCaseSendNotification {
    private final RepositoryNotification repository;

    /**
     * Instantiates a new Send notification.
     *
     * @param repository the repository
     */
    public SendNotification(RepositoryNotification repository) {
        this.repository = repository;
    }


    @Override
    Observable buildUseCaseObservable(SenderFCM senderFCM) {
        return repository.sendNotificationMessage(senderFCM);
    }
}
