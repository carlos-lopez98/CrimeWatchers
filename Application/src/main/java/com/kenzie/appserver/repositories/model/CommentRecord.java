package com.kenzie.appserver.repositories.model;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "CommentTable")
public class CommentRecord {

    private String crimeId;
    private String comment;
    private String commentId;
    private String postDate;
    private String username;

    @DynamoDBHashKey(attributeName = "crimeId")
    public String getCrimeId() {
        return crimeId;
    }
    @DynamoDBRangeKey(attributeName = "commentId")
    public String getCommentId() {
        return commentId;
    }
    @DynamoDBAttribute(attributeName = "comment")
    public String getComment() {
        return comment;
    }
    @DynamoDBAttribute(attributeName = "postDate")
    public String getPostDate() {
        return postDate;
    }
    @DynamoDBAttribute(attributeName = "username")
    public String getUsername() {
        return username;
    }


    public void setCrimeId(String crimeId) {
        this.crimeId = crimeId;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommentRecord)) return false;
        CommentRecord that = (CommentRecord) o;
        return getCrimeId().equals(that.getCrimeId()) && getCommentId().equals(that.getCommentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCrimeId(), getCommentId());
    }
}
