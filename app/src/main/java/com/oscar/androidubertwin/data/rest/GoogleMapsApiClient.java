package com.oscar.androidubertwin.data.rest;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.oscar.androidubertwin.data.repository.Repository;
import com.oscar.androidubertwin.domain.model.RequestGoogleApi;
import com.oscar.androidubertwin.utils.Constants;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by oscar on 11/16/2017.
 */
public class GoogleMapsApiClient implements Repository{
    private static GoogleMapsApiClient instance;
    private GoogleMapsApi services;

    /**
     * Instantiates a new Google maps api client.
     */
    @Inject
    public GoogleMapsApiClient() {
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.UrlServices.BASE_URL_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        services = retrofit.create(GoogleMapsApi.class);
    }

    /**
     * Get instance google maps api client.
     *
     * @return the google maps api client
     */
    public static GoogleMapsApiClient getInstance(){
        if (instance == null){
            instance = new GoogleMapsApiClient();
        }
        return instance;
    }


    @Override
    public Observable<RequestGoogleApi> getRequestApi(String destination, LatLng currentPosition) {
        String origin = Double.toString(currentPosition.latitude) + "," + Double.toString(currentPosition.longitude);
        return services.getPath("driving", "less_driving", origin, destination, "AIzaSyCd2NTGuFbyE3EeaK5zK3OgXTWDSEQi95Q");
    }
}
