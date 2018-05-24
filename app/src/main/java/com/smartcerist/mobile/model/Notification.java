package com.smartcerist.mobile.model;

/**
 * Created by root on 18/05/18.
 */

public class Notification {
    private String message;
    private String date;
    private String seen;
    private String type;
    private String category;

    public Notification(String message, String date, String type) {
        this.message = message;
        this.date = date;
        this.type = type;
    }

    public Notification(String message, String date, String type, String category) {
        this.message = message;
        this.date = date;
        this.type = type;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
