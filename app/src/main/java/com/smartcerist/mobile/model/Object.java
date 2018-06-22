package com.smartcerist.mobile.model;

public class Object {

    private String _id;
    private String name;
    private String path;
    private ObjectsTypes type;
    private String ipv6;
    private String server_ipv6;
    private String server_lipv6;
    private String server_ipv4;
    private String server_lipv4;
    private String measure;
    private String max_threshold;
    private String min_threshold;

    public Object() {
    }

    public Object(String _id, String name, String path, ObjectsTypes type, String min_threshold, String max_threshold) {
        this._id = _id;
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public Object(String _id, String name, String path, ObjectsTypes type, String ipv6, String server_ipv6, String server_lipv6, String server_ipv4, String server_lipv4, String max_threshold, String min_threshold) {
        this._id = _id;
        this.name = name;
        this.path = path;
        this.type = type;
        this.ipv6 = ipv6;
        this.server_ipv6 = server_ipv6;
        this.server_lipv6 = server_lipv6;
        this.server_ipv4 = server_ipv4;
        this.server_lipv4 = server_lipv4;
        this.max_threshold = max_threshold;
        this.min_threshold = min_threshold;
    }

    public Object(String name, String path, ObjectsTypes type, String ipv6, String server_ipv6, String server_ipv4) {
        this.name = name;
        this.path = path;
        this.type = type;
        this.ipv6 = ipv6;
        this.server_ipv6 = server_ipv6;
        this.server_ipv4 = server_ipv4;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ObjectsTypes getType() {
        return type;
    }

    public void setType(ObjectsTypes type) {
        this.type = type;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getServer_ipv6() {
        return server_ipv6;
    }

    public void setServer_ipv6(String server_ipv6) {
        this.server_ipv6 = server_ipv6;
    }

    public String getServer_lipv6() {
        return server_lipv6;
    }

    public void setServer_lipv6(String server_lipv6) {
        this.server_lipv6 = server_lipv6;
    }

    public String getServer_ipv4() {
        return server_ipv4;
    }

    public void setServer_ipv4(String server_ipv4) {
        this.server_ipv4 = server_ipv4;
    }

    public String getServer_lipv4() {
        return server_lipv4;
    }

    public void setServer_lipv4(String server_lipv4) {
        this.server_lipv4 = server_lipv4;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getMax_threshold() {
        return max_threshold;
    }

    public void setMax_threshold(String max_threshold) {
        this.max_threshold = max_threshold;
    }

    public String getMin_threshold() {
        return min_threshold;
    }

    public void setMin_threshold(String min_threshold) {
        this.min_threshold = min_threshold;
    }
}
