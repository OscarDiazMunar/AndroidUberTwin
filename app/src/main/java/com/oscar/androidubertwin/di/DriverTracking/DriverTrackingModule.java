package com.oscar.androidubertwin.di.DriverTracking;

import com.oscar.androidubertwin.data.rest.FCMClient;
import com.oscar.androidubertwin.data.rest.GoogleMapsApiClient;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;
import com.oscar.androidubertwin.domain.usecase.SendNotification;
import com.oscar.androidubertwin.presentation.presenter.DriverTrackingPresenter.DriverTrackingPresenter;
import com.oscar.androidubertwin.presentation.ui.DriverTracking;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oscar on 2/19/2018.
 */
@Module
public class DriverTrackingModule {
    /**
     * Provide get request api get request api.
     *
     * @return the get request api
     */
    @Provides
    public GetRequestApi provideGetRequestApi(){
        return new GetRequestApi(GoogleMapsApiClient.getInstance());
    }
    /**
     * Provide send notification send notification.
     *
     * @return the send notification
     */
    @Provides
    public SendNotification provideSendNotification(){
        return new SendNotification(FCMClient.getInstance());
    }

    /**
     * Provode driver tracking driver tracking.
     *
     * @return the driver tracking
     */
    @Provides
    public DriverTracking provodeDriverTracking(){
        return new DriverTracking();
    }

    /**
     * Provode driver tracking presenter driver tracking presenter.
     *
     * @param driverTracking the driver tracking
     * @param getRequestApi  the get request api
     * @return the driver tracking presenter
     */
    @Provides
    public DriverTrackingPresenter provodeDriverTrackingPresenter(DriverTracking driverTracking,
                                                                  GetRequestApi getRequestApi,
                                                                  SendNotification sendNotification){
        return new DriverTrackingPresenter(driverTracking, getRequestApi, sendNotification);
    }
}
