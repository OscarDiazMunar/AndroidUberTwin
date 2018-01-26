package com.oscar.androidubertwin.di.CustomerCallActivity;

import com.oscar.androidubertwin.presentation.ui.CustomerCallActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by oscar on 1/25/2018.
 */
@Singleton
@Component(modules = CustomerCallModule.class)
public interface CustomerCallComponent {
    /**
     * Inject.
     *
     * @param customerCallActivity the customer call activity
     */
    void inject(CustomerCallActivity customerCallActivity);
}
