package com.smartcerist.mobile.model;

public enum ObjectsTypes {
    led ("LED"),
    temperature("TEMPERATURE"),
    ventilator("VENTILATOR"),
    power("POWER"),
    light("LIGHT"),
    presence("PRESENCE");

    private String name;

    ObjectsTypes(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
