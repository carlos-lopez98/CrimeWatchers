package com.kenzie.appserver.repositories;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
//import com.kenzie.appserver.repositories.model.CrimeId;
import com.kenzie.appserver.repositories.model.CrimeRecord;

import com.kenzie.appserver.service.model.Crime;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableScan
@EnableScanCount
@Repository
public class CrimeRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public CrimeRecord findById(String id){
        return dynamoDBMapper.load(CrimeRecord.class, id);
    }

    public List<CrimeRecord> findByBorough(String borough){

        CrimeRecord hashKey = new CrimeRecord();
        hashKey.setBorough(borough);

        DynamoDBQueryExpression<CrimeRecord> queryExpression = new DynamoDBQueryExpression<CrimeRecord>();
        queryExpression.setHashKeyValues(hashKey);

        return dynamoDBMapper.query(CrimeRecord.class, queryExpression);
    }

    public void save(CrimeRecord record){
        dynamoDBMapper.save(record);
    }

    public List<CrimeRecord> findAll(){

        DynamoDBScanExpression scan = new DynamoDBScanExpression();

        List<CrimeRecord> allRecords = dynamoDBMapper.scan(CrimeRecord.class, scan);

            return allRecords;
    }
}
