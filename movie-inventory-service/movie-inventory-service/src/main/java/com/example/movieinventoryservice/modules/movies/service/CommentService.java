package com.example.movieinventoryservice.modules.movies.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface CommentService {

	public Comment addComment(Comment comment) throws RecordNotAddedException;
	
	public String deleteComment(int commentId) throws RecordNotDeletedException;
	
	public String updateComment(Comment comment) throws RecordNotUpdatedException;
	
	public Comment getCommentById(int commentId) throws RecordNotFoundException;
	
	public List<Comment> getAllComments() throws EmptyListException;


}
