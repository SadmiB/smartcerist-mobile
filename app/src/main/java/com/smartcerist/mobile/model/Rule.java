package com.smartcerist.mobile.model;

import java.io.Serializable;
import java.util.List;

public class Rule implements Serializable{
    private String _id;
    private String name;
    private String description;
    private List<Action> actions;
    private List<Condition> conditions;
    private Boolean state;

    public Rule(String description, Boolean state) {
        this.description = description;
        this.state = state;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
