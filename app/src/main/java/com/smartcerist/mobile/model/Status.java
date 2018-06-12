package com.smartcerist.mobile.model;

public enum Status {
    on ("ON"),
    off ("OFF");


    private String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
