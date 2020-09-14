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

import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.service.GenreService;

@RunWith(SpringRunner.class)
@WebMvcTest({ GenreServiceImpl.class })
public class GenreServiceImplTest {

	@Autowired
	private GenreService genreService;

	@MockBean
	private GenreRepository genreRepository;

	@Test
	public void testGetGenre() throws Exception {
		Mockito.when(genreRepository.getOne(Mockito.anyInt())).thenReturn(getGenre());
		Mockito.when(genreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getGenre()));
		Genre genre = genreService.getGenreById(1);
		assertTrue(genre.toString().contains("test"));
	}

	@Test(expected = RecordNotFoundException.class)
	public void testGetGenreException() throws Exception {
		Mockito.when(genreRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		genreService.getGenreById(1);

	}

	@Test
	public void testGetAllGenre() throws Exception {
		Mockito.when(genreRepository.findAll()).thenReturn(getGenreList());
		List<Genre> genres = genreService.getAllGenres();
		assertTrue(genres.toString().contains("test"));
	}

	@Test(expected = EmptyListException.class)
	public void testGetAllGenreException() throws Exception {
		Mockito.when(genreRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		genreService.getAllGenres();

	}

	@Test
	public void testAddGenre() throws Exception {
		Mockito.when(genreRepository.save(Mockito.any(Genre.class))).thenReturn(getGenre());
		Genre genre = genreService.addGenre(getGenre());
		assertTrue(genre.toString().contains("test"));

	}

	@Test(expected = RecordNotAddedException.class)
	public void testAddGenreException() throws Exception {
		Mockito.when(genreRepository.save(Mockito.any(Genre.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		genreService.addGenre(getGenre());

	}

	@Test
	public void testUpdateGenre() throws Exception {
		String response = genreService.updateGenre(getGenre());
		assertTrue(response.contains("Successfully updated"));

	}

	@Test(expected = RecordNotUpdatedException.class)
	public void testUpdateGenreException() throws Exception {
		Mockito.when(genreRepository.save(Mockito.any(Genre.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		genreService.updateGenre(getGenre());

	}

	@Test
	public void testDeleteGenre() throws Exception {
		String response = genreService.deleteGenre(1);
		assertTrue(response.contains("Successfully deleted"));

	}

	public Genre getGenre() {

		Genre genre = new Genre();

		genre.setName("test");

		return genre;
	}

	public List<Genre> getGenreList() {

		List<Genre> genres = new ArrayList<>();
		genres.add(getGenre());
		return genres;
	}
}
