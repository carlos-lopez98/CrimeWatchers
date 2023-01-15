package com.kenzie.appserver.controller;


import com.kenzie.appserver.controller.model.CommentResponse;
import com.kenzie.appserver.controller.model.CreateCommentRequest;
import com.kenzie.appserver.service.CommentService;
import com.kenzie.appserver.service.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @GetMapping("/byCrimeId/{crimeId}")
    public ResponseEntity<List<CommentResponse>> getCommentsByCrimeId(@PathVariable("crimeId") String crimeId) {
        //TODO return all borough crimes
        List<Comment> comments = commentService.findAllCommentsByCrimeId(crimeId);

        if (comments == null) {
            return ResponseEntity.notFound().build();
        }

        List<CommentResponse> commentResponses = comments.stream().map(this::commentToResponse).collect(Collectors.toList());

        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping
    public ResponseEntity<CommentResponse> addCrime(@RequestBody CreateCommentRequest createCommentRequest) {
        Comment comment = commentService.addNewComment(requestToComment(createCommentRequest));

        List<Comment> comments = commentService.findAllCommentsByCrimeId(createCommentRequest.getCrimeId());

        CommentResponse commentResponse = new CommentResponse();
        commentResponse.setCrimeId(comment.getCrimeId());
        commentResponse.setComment(comment.getComment());
        commentResponse.setCommentId(String.valueOf(comments.size() + 1));
        commentResponse.setUsername(comment.getUsername());
        commentResponse.setPostDate(comment.getPostDate());

        return ResponseEntity.ok(commentResponse);
    }

    private CommentResponse commentToResponse (Comment comment){

        CommentResponse response = new CommentResponse();
        response.setComment(comment.getComment());
        response.setCrimeId(comment.getCrimeId());
        response.setPostDate(comment.getPostDate());
        response.setUsername(comment.getUsername());
        response.setCommentId(comment.getCommentId());

        return response;
    }

    private Comment requestToComment(CreateCommentRequest request){

        List<Comment> comments = commentService.findAllCommentsByCrimeId(request.getCrimeId());

        return new Comment(request.getCrimeId(), request.getComment(),
                String.valueOf(comments.size() + 1),request.getPostDate(), request.getUsername());
    }
}
