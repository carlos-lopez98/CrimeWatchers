package com.kenzie.capstone.service.model;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;

import java.time.ZonedDateTime;
import java.util.Objects;

@DynamoDBTable(tableName = "LambdaTable")
public class CrimeDataRecord {

    String id;
    String borough;
    String state;
    String crimeType;
    String description;
    ZonedDateTime time;


    @DynamoDBRangeKey(attributeName = "id")
    public String getId() {
        return id;
    }
    @DynamoDBHashKey(attributeName = "borough")
    public String getBorough() {
        return borough;
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
    @DynamoDBAttribute(attributeName = "description")
    @DynamoDBTypeConverted(converter = ZonedDateTimeConverter.class)
    public ZonedDateTime getTime() {
        return time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBorough(String borough) {
        this.borough = borough;
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

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CrimeDataRecord)) return false;
        CrimeDataRecord that = (CrimeDataRecord) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
