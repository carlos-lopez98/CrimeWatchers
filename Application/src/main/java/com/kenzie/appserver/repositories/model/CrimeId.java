package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

import java.io.Serializable;

public class CrimeId implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String borough;

    @DynamoDBRangeKey
    public String getId(){
        return this.id;
    }

    @DynamoDBHashKey
    public String getBorough(){
        return this.borough;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBorough(String borough) {
        this.borough = borough;
    }
}
