package com.kenzie.capstone.service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CrimeDataResponse {

    @JsonProperty("caseId")
    private String caseId;

    @JsonProperty("borough")
    private String borough;

    @JsonProperty("state")
    private String state;

    @JsonProperty("crimeType")
    private String crimeType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("dateClosed")
    private String dateClosed;

    @JsonProperty("status")
    private String status;

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

    public String getDateClosed() {
        return dateClosed;
    }

    public void setDateClosed(String zonedDateTime) {
        this.dateClosed = zonedDateTime;
    }

    public String getStatus(){return status;}

    public void setStatus(String status){this.status = status;}
}
