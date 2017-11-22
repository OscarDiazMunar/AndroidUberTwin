package com.oscar.androidubertwin.presentation.presenter.MapsActivityPresenter;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.oscar.androidubertwin.domain.model.RequestGoogleApi;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;
import com.oscar.androidubertwin.domain.usecase.UseCaseObserver;
import com.oscar.androidubertwin.presentation.presenter.Presenter;
import com.oscar.androidubertwin.presentation.ui.MapsActivity;
import com.oscar.androidubertwin.presentation.view.IMapsActivityView;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by oscar on 11/11/2017.
 */
public class MapsActivityPresenter extends Presenter<IMapsActivityView> implements IMapsActivityPresenter{
    private MapsActivity mapsActivity;
    private Context context;

    /**
     * The Get request api.
     */
    GetRequestApi getRequestApi;

    /**
     * Instantiates a new Maps activity presenter.
     *
     * @param mapsActivity  the maps activity
     * @param getRequestApi the get request api
     */
    public MapsActivityPresenter(MapsActivity mapsActivity, GetRequestApi getRequestApi) {
        this.mapsActivity = mapsActivity;
        this.getRequestApi = getRequestApi;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void getDirection(String destination, LatLng currentPOsition) {
        this.getRequestApi.execute(new GetRequestObserver(), destination, currentPOsition);
    }

    private class GetRequestObserver extends UseCaseObserver<RequestGoogleApi>{

        @Override
        public void onNext(RequestGoogleApi value) {
            super.onNext(value);
            Log.e("value",value.toString());
            Log.e("points" ,value.getRoutes().get(0).getOverview_polyline().getPoints());
            adjustBounds(decodePoly(value.getRoutes().get(0).getOverview_polyline().getPoints()));
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.e("error request ", e.getMessage());
        }

        @Override
        public void onComplete() {
            super.onComplete();
        }
    }

    private void adjustBounds(List<LatLng> listPolyline) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng:listPolyline) {
            builder.include(latLng);
        }
        LatLngBounds bounds = builder.build();
        getView().setCameraUpdapte(bounds);
        getView().setPolyLineOptions(listPolyline);
    }

    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }
}
