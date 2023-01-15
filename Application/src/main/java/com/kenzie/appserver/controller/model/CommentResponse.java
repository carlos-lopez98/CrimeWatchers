package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponse {


    @JsonProperty("crimeId")
    private String crimeId;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("commentId")
    private String commentId;

    @JsonProperty("postDate")
    private String postDate;

    @JsonProperty("username")
    private String username;

    public String getCrimeId() {
        return crimeId;
    }
    public String getCommentId() {
        return commentId;
    }
    public String getComment() {
        return comment;
    }
    public String getPostDate() {
        return postDate;
    }
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
}
