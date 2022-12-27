package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class CreateCrimeRequest {

    @NotEmpty
    @JsonProperty("caseId")
    private String caseId;

    @NotEmpty
    @JsonProperty("borough")
    private String borough;

    @NotEmpty
    @JsonProperty("state")
    private String state;

    @NotEmpty
    @JsonProperty("crimeType")
    private String crimeType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("zonedDateTime")
    private String zonedDateTime;

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getBorough() {
        return borough;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCrimeType() {
        return crimeType;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getZonedDateTime() {
        return zonedDateTime;
    }

    public void setZonedDateTime(String zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

}
