package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.movieinventoryservice.entity.Comment;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.CommentRepository;
import com.example.movieinventoryservice.modules.movies.service.CommentService;

@RunWith(SpringRunner.class)
@WebMvcTest({ CommentServiceImpl.class })
public class CommentServiceImplTest {


	@Autowired
	private CommentService commentService;

	@MockBean
	private CommentRepository commentRepository;

	@Test
	public void testGetComment() throws Exception {
		Mockito.when(commentRepository.getOne(Mockito.anyInt())).thenReturn(getComment());
		Mockito.when(commentRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getComment()));
		Comment comment = commentService.getCommentById(1);
		assertTrue(comment.toString().contains("test"));
	}
	@Test(expected=RecordNotFoundException.class)
	public void testGetCommentException() throws Exception {
		Mockito.when(commentRepository.findById(Mockito.anyInt()))
		.thenThrow(Mockito.mock(DataAccessException.class));
		commentService.getCommentById(1);

	}


	@Test
	public void testGetAllComment() throws Exception {
		Mockito.when(commentRepository.findAll()).thenReturn(getCommentList());
		List<Comment> comments = commentService.getAllComments();
		assertTrue(comments.toString().contains("test"));
	}
	
	@Test(expected=EmptyListException.class)
	public void testGetAllCommentException() throws Exception {
		Mockito.when(commentRepository.findAll())
		.thenThrow(Mockito.mock(DataAccessException.class));
		commentService.getAllComments();

	}


	@Test
	public void testAddComment() throws Exception {
		Mockito.when(commentRepository.save(Mockito.any(Comment.class))).thenReturn(getComment());
		Comment comment = commentService.addComment(getComment());
		assertTrue(comment.toString().contains("test"));

	}
	@Test(expected=RecordNotAddedException.class)
	public void testAddCommentException() throws Exception {
		Mockito.when(commentRepository.save(Mockito.any(Comment.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		commentService.addComment(getComment());

	}



	@Test
	public void testUpdateComment() throws Exception {
		String response = commentService.updateComment(getComment());
		assertTrue(response.contains("Successfully updated"));

	}
	
	@Test(expected=RecordNotUpdatedException.class)
	public void testUpdateCommentException() throws Exception {
		Mockito.when(commentRepository.save(Mockito.any(Comment.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		commentService.updateComment(getComment());

	}



	@Test
	public void testDeleteComment() throws Exception {
		String response = commentService.deleteComment(1);
		assertTrue(response.contains("Successfully deleted"));

	}




	public Comment getComment() {

		Comment comment = new Comment();

		comment.setComments("test");

		return comment;
	}


	public List<Comment> getCommentList() {

		

		List<Comment> comments = new ArrayList<>();
		comments.add(getComment());
		return comments;
	}
}


