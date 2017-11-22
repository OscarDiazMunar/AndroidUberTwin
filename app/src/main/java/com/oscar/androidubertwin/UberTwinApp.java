package com.oscar.androidubertwin;

import android.app.Application;

import com.oscar.androidubertwin.di.MapsActivity.DaggerMapsComponent;
import com.oscar.androidubertwin.di.MapsActivity.MapsComponent;
import com.oscar.androidubertwin.di.MapsActivity.MapsModule;

/**
 * Created by oscar on 11/21/2017.
 */
public class UberTwinApp extends Application {
    private MapsComponent mapsComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mapsComponent = DaggerMapsComponent.builder().mapsModule(new MapsModule()).build();
    }

    /**
     * Gets maps component.
     *
     * @return the maps component
     */
    public MapsComponent getMapsComponent() {
        return mapsComponent;
    }
}
