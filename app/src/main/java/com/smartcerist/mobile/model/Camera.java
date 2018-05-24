package com.smartcerist.mobile.model;

import android.widget.VideoView;

import java.util.List;

public class Camera {

    private String _id;
    private String name;
    private String ipv4;
    private String ipv6;
    private Integer port;
    private String username;
    private String password;
    private String server_ip4;
    private String server_ip6;
    private String mainStream;
    private String subStream;
    private String ddns;
    private List<CameraHistory> history;

    public Camera(String name, String subStream) {
        this.name = name;
        this.subStream = subStream;
    }


    public String getSubStream() {
        return subStream;
    }

    public void setSubStream(String subStream) {
        this.subStream = subStream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
