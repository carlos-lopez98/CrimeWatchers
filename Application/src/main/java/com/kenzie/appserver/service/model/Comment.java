package com.kenzie.appserver.service.model;

public class Comment {

    private String crimeId;
    private String comment;
    private String commentId;
    private String postDate;
    private String username;


    public Comment(String crimeId, String comment, String commentId, String postDate, String username) {
        this.crimeId = crimeId;
        this.comment = comment;
        this.commentId = commentId;
        this.postDate = postDate;
        this.username = username;
    }


    public String getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(String crimeId) {
        this.crimeId = crimeId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
