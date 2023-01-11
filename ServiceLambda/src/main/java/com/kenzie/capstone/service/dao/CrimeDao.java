package com.kenzie.capstone.service.dao;

import com.kenzie.capstone.service.model.CrimeData;
import com.kenzie.capstone.service.model.CrimeDataRecord;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.google.common.collect.ImmutableMap;

import java.time.ZonedDateTime;
import java.util.List;

public class CrimeDao{

    private DynamoDBMapper mapper;

    public CrimeDao(DynamoDBMapper mapper){
        this.mapper = mapper;
    }

    //Gets Closed Cases by borough from Lambda Table
    public List<CrimeDataRecord> getClosedCases(String id) {
        CrimeDataRecord crimeDataRecord = new CrimeDataRecord();
        crimeDataRecord.setId(id);

        DynamoDBQueryExpression<CrimeDataRecord> queryExpression = new DynamoDBQueryExpression<CrimeDataRecord>()
                .withHashKeyValues(crimeDataRecord)
                .withConsistentRead(false);

        return mapper.query(CrimeDataRecord.class, queryExpression);
    }

    //Will add a closed case to our Lambda Table -- Case must be closed
    public CrimeData addClosedCase(CrimeData crimeDataRecord) {


        CrimeDataRecord record = dataToRecord(crimeDataRecord);


        try {
            mapper.save(record, new DynamoDBSaveExpression()
                    .withExpected(ImmutableMap.of(
                            "id",
                            new ExpectedAttributeValue().withExists(false)
                    )));
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }

        return crimeDataRecord;
    }


    private CrimeDataRecord dataToRecord(CrimeData data) {
    CrimeDataRecord record = new CrimeDataRecord();
    record.setDescription(data.getDescription());
    record.setBorough(data.getBorough());
    record.setTime(ZonedDateTime.now());
    record.setCrimeType(data.getCrimeType());
    record.setState(data.getState());
    record.setId(data.getId());
    return record;
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
