package com.example.movieinventoryservice.modules.movies.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.entity.Genre;
import com.example.movieinventoryservice.entity.Movie;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.repository.GenreRepository;
import com.example.movieinventoryservice.modules.movies.repository.MovieRepository;
import com.example.movieinventoryservice.modules.movies.service.MovieService;
import com.example.movieinventoryservice.modules.movies.service.serviceImpl.MovieServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ MovieController.class, MovieServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class MovieControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public ApiSuccessResponse apiResponse;

	@Autowired
	private MovieService movieService;

	@MockBean
	private MovieRepository movieRepository;

	@MockBean
	private GenreRepository genreRepository;

	@MockBean
	private CastRepository castRepository;

	@Before
	public void setUp() throws Exception {
		List<Movie> movies = new ArrayList<>();
		movies.add(getMovie());
		Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenReturn(getMovie());
//		Mockito.verify(movieRepository).deleteById(Mockito.anyInt());
		Mockito.when(movieRepository.getOne(Mockito.anyInt())).thenReturn(getMovie());
		Mockito.when(movieRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getMovie()));
		Mockito.when(movieRepository.findAll()).thenReturn(movies);
		Mockito.when(genreRepository.getIdByName(Mockito.anyString())).thenReturn(Optional.of(1));

		Mockito.when(genreRepository.save(Mockito.any(Genre.class))).thenReturn(getGenre());
		Mockito.when(castRepository.save(Mockito.any(Cast.class))).thenReturn(getCast());
	}

	@WithMockUser
	@Test
	public void testGetMovie() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/movie/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Aladdin"));
	}

	@Test
	public void testGetMovieException() throws Exception {
		Mockito.when(movieRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/movie/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetMovieException1() throws Exception {
		Mockito.when(movieRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/movie/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllMovie() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/movie/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Aladdin"));
	}

	@Test
	public void testGetAllMovieException() throws Exception {
		Mockito.when(movieRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/movie/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetAllMovieException1() throws Exception {
		Mockito.when(movieRepository.findAll()).thenReturn(new ArrayList<Movie>());
		MvcResult result = this.mockMvc.perform(get("/movie/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddMovie() throws Exception {
		MvcResult result = mockMvc.perform(post("/movie/").contentType(MediaType.APPLICATION_JSON)
				.content(getMoviesAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Aladdin"));

	}

	@Test
	public void testAddMovieException() throws Exception {

		Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/movie/").contentType(MediaType.APPLICATION_JSON)
				.content(getMoviesAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdateMovie() throws Exception {
		MvcResult result = mockMvc.perform(put("/movie/").contentType(MediaType.APPLICATION_JSON)
				.content(getMoviesAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdateMovieException() throws Exception {
		Mockito.when(movieRepository.save(Mockito.any(Movie.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/movie/").contentType(MediaType.APPLICATION_JSON)
				.content(getMoviesAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeleteMovie() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/movie/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getMoviesAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getMovie());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Movie getMovie() {

		Movie movie = new Movie();

		movie.setTitle("Aladdin");
		movie.setGenre(getGenreList());
		movie.setCast(getCastList());

		return movie;
	}

	public Genre getGenre() {

		Genre genre = new Genre();

		genre.setName("Action");

		return genre;
	}

	public Cast getCast() {

		Cast cast = new Cast();

		cast.setName("sampleCast");

		return cast;
	}

	public List<Genre> getGenreList() {

		Genre genre = new Genre();

		genre.setName("Action");

		List<Genre> genres = new ArrayList<>();
		genres.add(genre);
		return genres;
	}

	public List<Cast> getCastList() {

		Cast cast = new Cast();

		cast.setName("sampleCast");

		List<Cast> casts = new ArrayList<>();
		casts.add(cast);
		return casts;
	}
}
