package com.oscar.androidubertwin.presentation.presenter.CustomerCallPresenter;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.oscar.androidubertwin.domain.model.RequestGoogleApi;
import com.oscar.androidubertwin.domain.model.ResponseFCM;
import com.oscar.androidubertwin.domain.model.SenderFCM;
import com.oscar.androidubertwin.domain.usecase.GetRequestApi;
import com.oscar.androidubertwin.domain.usecase.SendNotification;
import com.oscar.androidubertwin.domain.usecase.UseCaseObserver;
import com.oscar.androidubertwin.presentation.presenter.Presenter;
import com.oscar.androidubertwin.presentation.ui.CustomerCallActivity;
import com.oscar.androidubertwin.presentation.view.ICustomerCallView;

/**
 * Created by oscar on 1/25/2018.
 */
public class CustomerCallPresenter extends Presenter<ICustomerCallView> implements ICustomerCallPresenter{
    private CustomerCallActivity customerCallActivity;
    private GetRequestApi getRequestApi;
    private SendNotification sendNotification;

    /**
     * Instantiates a new Customer call presenter.
     *
     * @param customerCallActivity the customer call activity
     * @param getRequestApi        the get request api
     * @param sendNotification     the send notification
     */
    public CustomerCallPresenter(CustomerCallActivity customerCallActivity,
                                 GetRequestApi getRequestApi,
                                 SendNotification sendNotification) {
        this.customerCallActivity = customerCallActivity;
        this.getRequestApi = getRequestApi;
        this.sendNotification = sendNotification;
    }

    @Override
    public void getDirection(String destination, LatLng currentPosition) {
        getRequestApi.execute(new GetRequestObserver(), destination, currentPosition);
    }

    @Override
    public void sendMessageNotification(SenderFCM senderFCM) {
        sendNotification.execute(new SendNotificationObserver(), senderFCM);
    }

    private class GetRequestObserver extends UseCaseObserver<RequestGoogleApi> {
        /**
         * The Value aux.
         */
        RequestGoogleApi valueAux;
        @Override
        public void onNext(RequestGoogleApi value) {
            super.onNext(value);
            Log.e("valueAux",value.toString());
            Log.e("distance" ,value.getRoutes().get(0).getLegs().get(0).getDistance().getText());
            Log.e("duration" ,value.getRoutes().get(0).getLegs().get(0).getDuration().getText());
            Log.e("end address" ,value.getRoutes().get(0).getLegs().get(0).getEndAddress());
            valueAux = value;
            /*customerCallActivity.setInformationNotification(value.getRoutes().get(0).getLegs().get(0).getDistance().getText(),\
                    value.getRoutes().get(0).getLegs().get(0).getDuration().getText(),
                    value.getRoutes().get(0).getLegs().get(0).getEndAddress());*/
           // adjustBounds(decodePoly(valueAux.getRoutes().get(0).getOverview_polyline().getPoints()));
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.e("error request ", e.getMessage());
        }

        @Override
        public void onComplete() {
            super.onComplete();
            if (valueAux != null){
                getView().setInformationNotification(valueAux.getRoutes().get(0).getLegs().get(0).getDistance().getText(),
                        valueAux.getRoutes().get(0).getLegs().get(0).getDuration().getText(),
                        valueAux.getRoutes().get(0).getLegs().get(0).getEndAddress());
            }

        }
    }

    private class SendNotificationObserver extends UseCaseObserver<ResponseFCM>{
        private ResponseFCM responseFCM;
        @Override
        public void onNext(ResponseFCM value) {
            super.onNext(value);
            responseFCM = value;
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
        }

        @Override
        public void onComplete() {
            super.onComplete();
            if (responseFCM != null){
                if (responseFCM.getSuccess() == 1){
                    getView().setToastNotification("Cancelled");
                }
            }
        }
    }
}
