package com.smartcerist.mobile.model;

import java.io.Serializable;

/**
 * Created by root on 24/05/18.
 */

public class Permission implements Serializable{
    private String homeId;
    private String roomId;
    private String permission;

    public Permission(String homeId, String roomId, String permission) {
        this.homeId = homeId;
        this.roomId = roomId;
        this.permission = permission;
    }

    public String getHomeId() {
        return homeId;
    }

    public void setHomeId(String homeId) {
        this.homeId = homeId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
