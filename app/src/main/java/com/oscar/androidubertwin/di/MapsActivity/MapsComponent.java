package com.oscar.androidubertwin.di.MapsActivity;

import com.oscar.androidubertwin.presentation.ui.MapsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oscar on 11/21/2017.
 */
@Singleton
@Component(modules = MapsModule.class)
public interface MapsComponent {
    /**
     * Inject.
     *
     * @param mapsActivity the maps activity
     */
    void inject(MapsActivity mapsActivity);
}
