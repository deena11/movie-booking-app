package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.CommentRepository;
import com.example.movieinventoryservice.modules.movies.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	private String message = "";

	

	@Override
	public String deleteComment(int commentId) throws RecordNotDeletedException {
		try {
			commentRepository.deleteById(commentId);
			message = "Successfully deleted Comment of id - " + commentId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updateComment(Comment comment) throws RecordNotUpdatedException {
		try {

			commentRepository.save(comment);
			message = "Successfully updated comment";
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Comment getCommentById(int commentId) throws RecordNotFoundException {
		try {
			Optional<Comment> comment = commentRepository.findById(commentId);
			if (comment.isPresent()) {
				return comment.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Comment> getAllComments() throws EmptyListException {
		try {
			List<Comment> comments = commentRepository.findAll();
			if (comments.size()>0) {
				return comments;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public Comment addComment(Comment comment) throws RecordNotAddedException {
		try {
			return commentRepository.save(comment);
		} catch (DataAccessException ex) {
			throw new RecordNotAddedException("Failed To Add Comment", ex.getCause());
		}
	}

}
