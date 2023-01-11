package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrimeResponse {

    @JsonProperty("id")
    private String id;

    @JsonProperty("borough")
    private String borough;

    @JsonProperty("state")
    private String state;

    @JsonProperty("crimeType")
    private String crimeType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("zonedDateTime")
    private String zonedDateTime;

    public String getCaseId() {
        return id;
    }

    public void setCaseId(String caseId) {
        this.id = caseId;
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
