package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.kenzie.appserver.service.model.Crime;
import org.springframework.data.annotation.Id;

import java.util.Objects;

@DynamoDBTable(tableName = "CrimeTable")
public class CrimeRecord {

    @Id
    private CrimeId crimeId;

    private String state;
    private String crimeType;
    private String description;
    private String zonedDateTime;

    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return crimeId != null ? crimeId.getId() : null;
    }

    @DynamoDBHashKey(attributeName = "borough")
    public String getBorough() {
        return crimeId != null ? crimeId.getBorough() : null;
    }

    public void setId(String caseId) {
        if(crimeId == null){
            crimeId = new CrimeId();
        }

        crimeId.setId(caseId);
    }

    public void setBorough(String borough) {
        if(crimeId == null){
            crimeId = new CrimeId();
        }

        crimeId.setBorough(borough);
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
        return Objects.equals(crimeId, crimeRecord.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(crimeId);
    }
}
