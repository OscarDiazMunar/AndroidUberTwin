package com.oscar.androidubertwin.data.rest;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.oscar.androidubertwin.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by oscar on 1/24/2018.
 */
public class FCMClient {
    private static FCMClient instance;
    private IFCMService service;

    /**
     * Instantiates a new Fcm client.
     */
    public FCMClient() {
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
        final Retrofit retrofit = new  Retrofit.Builder()
                .baseUrl(Constants.UrlServices.FCM_URL_API)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(IFCMService.class);
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static FCMClient getInstance() {
        if (instance == null){
            instance = new FCMClient();
        }
        return instance;
    }


}
