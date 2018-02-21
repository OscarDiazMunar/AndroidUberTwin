package com.oscar.androidubertwin.di.DriverTracking;

import com.oscar.androidubertwin.presentation.ui.DriverTracking;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oscar on 2/19/2018.
 */
@Singleton
@Component(modules = DriverTrackingModule.class)
public interface DriverTrackingComponent {
    /**
     * Inject.
     *
     * @param driverTracking the driver tracking
     */
    void inject(DriverTracking driverTracking);
}
