package com.oscar.androidubertwin.domain.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by oscar on 1/24/2018.
 */
public class SenderFCM {
    @SerializedName("notification")
    private Notification notification;

    @SerializedName("to")
    private String to;

    @SerializedName("data")
    private DataNotification data;

    /**
     * Instantiates a new Sender fcm.
     *
     * @param dataFCM the DataNotification fcm
     * @param to      the to
     * @param data    the data
     */
    public SenderFCM(Notification dataFCM, String to, DataNotification data) {
        this.notification = dataFCM;
        this.to = to;
        this.data = data;

    }

    /**
     * Gets notification.
     *
     * @return the notification
     */
    public Notification getNotification() {
        return notification;
    }

    /**
     * Sets notification.
     *
     * @param notification the notification
     */
    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    /**
     * Gets to.
     *
     * @return the to
     */
    public String getTo() {
        return to;
    }

    /**
     * Sets to.
     *
     * @param to the to
     */
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "SenderFCM{" +
                "notification=" + notification +
                ", to='" + to + '\'' +
                ", DataNotification=" + data +
                '}';
    }
}
