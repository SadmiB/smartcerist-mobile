package com.smartcerist.mobile.model;

import java.util.List;

public class Server {

    private String name;
    private String ipv6;
    private String lipv6;
    private String ipv4;
    private String lipv4;
    private List<Beacon> beacons;
    private List<Camera> cameras;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getLipv6() {
        return lipv6;
    }

    public void setLipv6(String lipv6) {
        this.lipv6 = lipv6;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getLipv4() {
        return lipv4;
    }

    public void setLipv4(String lipv4) {
        this.lipv4 = lipv4;
    }

    public List<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(List<Beacon> beacons) {
        this.beacons = beacons;
    }

    public List<Camera> getCameras() {
        return cameras;
    }

    public void setCameras(List<Camera> cameras) {
        this.cameras = cameras;
    }
}
