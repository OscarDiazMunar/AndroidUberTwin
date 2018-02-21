package com.oscar.androidubertwin.domain.model;

/**
 * Created by oscar on 2/20/2018.
 */
public class DataNotification {
    private String title;
    private String detail;
    private int type;

    /**
     * Instantiates a new Data notification.
     *
     * @param title  the title
     * @param detail the detail
     * @param type   the type
     */
    public DataNotification(String title, String detail, int type) {
        this.title = title;
        this.detail = detail;
        this.type = type;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets title.
     *
     * @param title the title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets detail.
     *
     * @param detail the detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * Sets type.
     *
     * @param type the type
     */
    public void setType(int type) {
        this.type = type;
    }
}
