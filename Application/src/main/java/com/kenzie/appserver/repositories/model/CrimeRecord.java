package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "CrimeTable")
public class CrimeRecord {

    private String id;
    private String borough;
    private String state;
    private String crimeType;
    private String description;
    private String zonedDateTime;

    @DynamoDBHashKey(attributeName = "borough")
    public String getBorough() {
        return this.borough;
    }

    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return this.id;
    }

    public void setId(String caseId) {

        this.id=caseId;
    }

    public void setBorough(String borough) {
//        if(crimeId == null){
//            crimeId = new CrimeId();
//        }
       this.borough = borough;
    }

    @DynamoDBAttribute(attributeName = "state")
    public String getState() {
        return state;
    }

    @DynamoDBAttribute(attributeName = "crimeType")
    public String getCrimeType() {
        return crimeType;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    @DynamoDBAttribute(attributeName = "zonedDateTime")
    public String getZonedDateTime() {
        return zonedDateTime;
    }



    public void setState(String state) {
        this.state = state;
    }

    public void setCrimeType(String crimeType) {
        this.crimeType = crimeType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setZonedDateTime(String zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CrimeRecord crimeRecord = (CrimeRecord) o;
        return Objects.equals(id, crimeRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
