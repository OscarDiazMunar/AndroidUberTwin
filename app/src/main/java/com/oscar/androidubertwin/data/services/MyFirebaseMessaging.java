package com.oscar.androidubertwin.data.services;

import android.content.Intent;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.oscar.androidubertwin.presentation.ui.CustomerCallActivity;

/**
 * Created by oscar on 1/24/2018.
 */
public class MyFirebaseMessaging extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        LatLng customerLocation = new Gson().fromJson(remoteMessage.getNotification().getBody(), LatLng.class);

        Intent intent = new Intent(getBaseContext(), CustomerCallActivity.class);
        intent.putExtra("lat", customerLocation.latitude);
        intent.putExtra("lng", customerLocation.longitude);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
    }
}
