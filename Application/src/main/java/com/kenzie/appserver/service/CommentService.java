package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CommentRepository;
import com.kenzie.appserver.repositories.model.CommentRecord;
import com.kenzie.appserver.service.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {


    @Autowired
    private CommentRepository commentRepo;


    public List<Comment> findAllCommentsByCrimeId(String crimeId){

        // Example getting data from the local repository
        List<CommentRecord> commentsFromDB = commentRepo.findCommentByCrimeId(crimeId);

        List<Comment> comments = new ArrayList<>();
        //Returns a list of crimes from the ActiveCrimeRepository
        for(CommentRecord record : commentsFromDB){

            comments.add(new Comment(record.getCrimeId(),record.getComment(),record.getCommentId(),
                    record.getPostDate(), record.getUsername()));
        }
        return comments;
    }

    public Comment addNewComment(Comment comment) {

        List<Comment> allCrimes = this.findAllCommentsByCrimeId(comment.getCrimeId());

        String commentId = String.valueOf (allCrimes.size() + 1);

        // Example sending data to the local repository
        CommentRecord commentRecord = new CommentRecord();
        commentRecord.setCommentId(commentId);
        commentRecord.setComment(comment.getComment());
        commentRecord.setPostDate(comment.getPostDate());
        commentRecord.setUsername(comment.getUsername());
        commentRecord.setCrimeId(comment.getCrimeId());
        commentRepo.saveComment(commentRecord);

        return new Comment(commentRecord.getCrimeId(), commentRecord.getComment(), commentId, commentRecord.getPostDate(),
                commentRecord.getUsername());
    }
}
