package com.oscar.androidubertwin.di.CustomerCallActivity;

import com.oscar.androidubertwin.data.rest.FCMClient;
import com.oscar.androidubertwin.data.rest.GoogleMapsApiClient;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;
import com.oscar.androidubertwin.domain.usecase.SendNotification;
import com.oscar.androidubertwin.presentation.presenter.CustomerCallPresenter.CustomerCallPresenter;
import com.oscar.androidubertwin.presentation.ui.CustomerCallActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oscar on 1/25/2018.
 */
@Module
public class CustomerCallModule {
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
     * Provide customer call activity customer call activity.
     *
     * @return the customer call activity
     */
    @Provides
    public CustomerCallActivity provideCustomerCallActivity(){
        return new CustomerCallActivity();
    }

    /**
     * Provides customer call presenter customer call presenter.
     *
     * @param customerCallActivity the customer call activity
     * @param getRequestApi        the get request api
     * @param sendNotification     the send notification
     * @return the customer call presenter
     */
    @Provides
    public CustomerCallPresenter providesCustomerCallPresenter(CustomerCallActivity customerCallActivity,
                                                               GetRequestApi getRequestApi,
                                                               SendNotification sendNotification){
        return new CustomerCallPresenter(customerCallActivity, getRequestApi, sendNotification);
    }
}
