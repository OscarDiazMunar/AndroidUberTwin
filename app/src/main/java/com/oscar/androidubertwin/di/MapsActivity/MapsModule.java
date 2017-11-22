package com.oscar.androidubertwin.di.MapsActivity;

import com.oscar.androidubertwin.data.rest.GoogleMapsApiClient;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;
import com.oscar.androidubertwin.presentation.presenter.MapsActivityPresenter.MapsActivityPresenter;
import com.oscar.androidubertwin.presentation.ui.MapsActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oscar on 11/21/2017.
 */
@Module
public class MapsModule {

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
     * Provides maps activity maps activity.
     *
     * @return the maps activity
     */
    @Provides
    public  MapsActivity providesMapsActivity(){
        return new MapsActivity();
    }


    /**
     * Provide maps presenter maps activity presenter.
     *
     * @param mapsActivity  the maps activity
     * @param getRequestApi the get request api
     * @return the maps activity presenter
     */
    @Provides
    public MapsActivityPresenter provideMapsPresenter(MapsActivity mapsActivity,GetRequestApi getRequestApi){

        return new MapsActivityPresenter(mapsActivity, getRequestApi);
    }
}
