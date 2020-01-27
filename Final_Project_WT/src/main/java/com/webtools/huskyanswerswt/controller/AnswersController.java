package com.webtools.huskyanswerswt.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webtools.huskyanswerswt.dao.AnswersDAO;
import com.webtools.huskyanswerswt.dao.PostsDAO;
import com.webtools.huskyanswerswt.exception.AnswerException;
import com.webtools.huskyanswerswt.exception.PostException;
import com.webtools.huskyanswerswt.pojo.Answer;
import com.webtools.huskyanswerswt.pojo.Post;


@RestController
public class AnswersController {
	@Autowired
	AnswersDAO answersDao;
	
	@Autowired
	PostsDAO postDao;
	
	// Method to test the angular fetch call.
    @CrossOrigin(origins="http://localhost:4200")                           // @CrossOrigin is used to handle the request from a difference origin.
    @RequestMapping(value= "/posts/{postId}/answers", method= RequestMethod.GET) 
    public List<Answer> getAllAnswersByPostId(@PathVariable("postId") long postId) throws AnswerException{
    	return answersDao.getAnswersByPostId(postId);
    }
    
    @CrossOrigin(origins="http://localhost:4200") 
    @RequestMapping(value= "/posts/{postId}/answer", method = RequestMethod.POST)
    public Answer createAnswer(@PathVariable("postId") long postId, @RequestBody Answer answer) throws PostException, AnswerException {
        Post post = postDao.findById(postId);
        answer.setPost(post);
        return answersDao.saveAnswer(answer);
    }

//    @PutMapping("/posts/{postId}/comments/{commentId}")
//    public Comment updateComment(@PathVariable (value = "postId") Long postId,
//                                 @PathVariable (value = "commentId") Long commentId,
//                                 @Valid @RequestBody Comment commentRequest) {
//        if(!postRepository.existsById(postId)) {
//            throw new ResourceNotFoundException("PostId " + postId + " not found");
//        }
//
//        return commentRepository.findById(commentId).map(comment -> {
//            comment.setText(commentRequest.getText());
//            return commentRepository.save(comment);
//        }).orElseThrow(() -> new ResourceNotFoundException("CommentId " + commentId + "not found"));
//    }
//
//    @DeleteMapping("/posts/{postId}/comments/{commentId}")
//    public ResponseEntity<?> deleteComment(@PathVariable (value = "postId") Long postId,
//                              @PathVariable (value = "commentId") Long commentId) {
//        return commentRepository.findByIdAndPostId(commentId, postId).map(comment -> {
//            commentRepository.delete(comment);
//            return ResponseEntity.ok().build();
//        }).orElseThrow(() -> new ResourceNotFoundException("Comment not found with id " + commentId + " and postId " + postId));
//    }
    
}
