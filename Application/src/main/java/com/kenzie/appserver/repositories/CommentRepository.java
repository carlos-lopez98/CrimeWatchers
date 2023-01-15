package com.kenzie.appserver.repositories;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.kenzie.appserver.repositories.model.CommentRecord;
import com.kenzie.appserver.repositories.model.CrimeRecord;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.xml.stream.events.Comment;
import java.util.List;

@EnableScan
@EnableScanCount
@Repository
public class CommentRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;



    public void saveComment(CommentRecord commentRecord){
        dynamoDBMapper.save(commentRecord);
    }

    public List<CommentRecord> findCommentByCrimeId(String crimeId){
        CommentRecord hashKey = new CommentRecord();
        hashKey.setCrimeId(crimeId);

        DynamoDBQueryExpression<CommentRecord> queryExpression = new DynamoDBQueryExpression<CommentRecord>();
        queryExpression.setHashKeyValues(hashKey);

        return dynamoDBMapper.query(CommentRecord.class, queryExpression);
    }

}
