package com.smartcerist.mobile.model;

import java.io.Serializable;
import java.util.List;

public class Rule implements Serializable{
    private String _id;
    private String name;
    private String description;
    private List<String> actions;
    private List<String> conditions;
    private Boolean state;

    public Rule(String description, Boolean state) {
        this.description = description;
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}
