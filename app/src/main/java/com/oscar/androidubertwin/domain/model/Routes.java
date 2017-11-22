package com.oscar.androidubertwin.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by oscar on 11/17/2017.
 */
public class Routes implements Serializable{
    @SerializedName("overview_polyline")
    private OverviewPolyline overview_polyline;

    /**
     * Gets overview polyline.
     *
     * @return the overview polyline
     */
    public OverviewPolyline getOverview_polyline() {
        return overview_polyline;
    }

    /**
     * Sets overview polyline.
     *
     * @param overview_polyline the overview polyline
     */
    public void setOverview_polyline(OverviewPolyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "overview_polyline=" + overview_polyline +
                '}';
    }
}
