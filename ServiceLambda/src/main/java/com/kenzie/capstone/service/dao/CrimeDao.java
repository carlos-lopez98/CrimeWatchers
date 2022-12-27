package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.CrimeDataRecord;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;
import java.util.List;

public class CrimeDao{

    private DynamoDBMapper mapper;

    public CrimeDao(DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    //Gets Closed Case by ID from Lambda Table
    public List<CrimeDataRecord> getClosedCase(String id) {
        CrimeDataRecord crimeDataRecord = new CrimeDataRecord();
        crimeDataRecord.setId(id);

        DynamoDBQueryExpression<CrimeDataRecord> queryExpression = new DynamoDBQueryExpression<CrimeDataRecord>()
                .withHashKeyValues(crimeDataRecord)
                .withConsistentRead(false);

        return mapper.query(CrimeDataRecord.class, queryExpression);
    }

    //Will add a closed case to our Lambda Table -- Case must be closed
    public CrimeDataRecord addClosedCase(CrimeDataRecord crimeDataRecord) {

        try {
            mapper.save(crimeDataRecord, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return crimeDataRecord;
    }




    //Don't need this
    /*    public ExampleData storeExampleData(ExampleData exampleData) {

        try {
            mapper.save(exampleData, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id has already been used");
        }

        return exampleData;
    }*/

}
