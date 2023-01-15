package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;

public class CreateCommentRequest {

    @NotEmpty
    @JsonProperty("crimeId")
    private String crimeId;

    @NotEmpty
    @JsonProperty("comment")
    private String comment;


    @JsonProperty("postDate")
    private String postDate;

    @NotEmpty
    @JsonProperty("username")
    private String username;

    public String getCrimeId() {
        return crimeId;
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
    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
