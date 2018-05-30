package com.smartcerist.mobile.model;

import java.io.Serializable;

public class Room implements Serializable {
    private String _id;
    private String name;
    private String type;
    private String[] objects;
    private String[] users;
    private String[] cameras;
    private ObjectEvent[] events;

    public String getName() {
        return name;
    }

    public ObjectEvent[] getEvents() {
        return events;
    }

    public void setEvents(ObjectEvent[] events) {
        this.events = events;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String[] getObjects() {
        return objects;
    }

    public void setObjects(String[] objects) {
        this.objects = objects;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public String[] getCameras() {
        return cameras;
    }

    public void setCameras(String[] cameras) {
        this.cameras = cameras;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
