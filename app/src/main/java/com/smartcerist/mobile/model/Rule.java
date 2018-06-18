package com.smartcerist.mobile.model;

public class Rule {
    private Boolean status;
    private String description;

    public Rule(Boolean status, String description) {
        this.status = status;
        this.description = description;
    }


    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
