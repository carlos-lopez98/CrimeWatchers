package com.kenzie.capstone.service.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.kenzie.capstone.service.converter.ZonedDateTimeConverter;
import com.kenzie.capstone.service.model.ClosedCrimeData;
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
    public List<CrimeDataRecord> getClosedCases(String borough) {
        CrimeDataRecord crimeDataRecord = new CrimeDataRecord();
        crimeDataRecord.setBorough(borough);

        DynamoDBQueryExpression<CrimeDataRecord> queryExpression = new DynamoDBQueryExpression<CrimeDataRecord>()
                .withHashKeyValues(crimeDataRecord)
                .withConsistentRead(false);

        return mapper.query(CrimeDataRecord.class, queryExpression);
    }

    //Will add a closed case to our Lambda Table -- Case must be closed
    public ClosedCrimeData addClosedCase(CrimeDataRecord crimeDataRecord) {

        try {
            mapper.save(crimeDataRecord);
        } catch (ConditionalCheckFailedException e) {
            throw new IllegalArgumentException("id already exists");
        }


        return recordToData(crimeDataRecord);
    }



    public List<CrimeDataRecord> getAllClosedCases() {
        return mapper.scan(CrimeDataRecord.class, new DynamoDBScanExpression());
    }



private ClosedCrimeData recordToData(CrimeDataRecord record){
        ClosedCrimeData data = new ClosedCrimeData(record.getId(), record.getBorough(), record.getState(),
                record.getCrimeType(), record.getDescription(),record.getDateClosed(), record.getStatus());

        return data;
}
}
