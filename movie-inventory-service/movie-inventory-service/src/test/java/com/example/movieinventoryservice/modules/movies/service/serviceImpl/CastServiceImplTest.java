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

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.service.CastService;

@RunWith(SpringRunner.class)
@WebMvcTest({ CastServiceImpl.class })
public class CastServiceImplTest {


	@Autowired
	private CastService castService;

	@MockBean
	private CastRepository castRepository;

	@Test
	public void testGetCast() throws Exception {
		Mockito.when(castRepository.getOne(Mockito.anyInt())).thenReturn(getCast());
		Mockito.when(castRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getCast()));
		Cast cast = castService.getCastById(1);
		assertTrue(cast.toString().contains("test"));
	}
	@Test(expected=RecordNotFoundException.class)
	public void testGetCastException() throws Exception {
		Mockito.when(castRepository.findById(Mockito.anyInt()))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.getCastById(1);

	}


	@Test
	public void testGetAllCast() throws Exception {
		Mockito.when(castRepository.findAll()).thenReturn(getCastList());
		List<Cast> casts = castService.getAllCasts();
		assertTrue(casts.toString().contains("test"));
	}
	
	@Test(expected=EmptyListException.class)
	public void testGetAllCastException() throws Exception {
		Mockito.when(castRepository.findAll())
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.getAllCasts();

	}


	@Test
	public void testAddCast() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class))).thenReturn(getCast());
		Cast cast = castService.addCast(getCast());
		assertTrue(cast.toString().contains("test"));

	}
	@Test(expected=RecordNotAddedException.class)
	public void testAddCastException() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.addCast(getCast());

	}



	@Test
	public void testUpdateCast() throws Exception {
		String response = castService.updateCast(getCast());
		assertTrue(response.contains("Successfully updated"));

	}
	
	@Test(expected=RecordNotUpdatedException.class)
	public void testUpdateCastException() throws Exception {
		Mockito.when(castRepository.save(Mockito.any(Cast.class)))
		.thenThrow(Mockito.mock(DataAccessException.class));
		castService.updateCast(getCast());

	}



	@Test
	public void testDeleteCast() throws Exception {
		String response = castService.deleteCast(1);
		assertTrue(response.contains("Successfully deleted"));

	}




	public Cast getCast() {

		Cast cast = new Cast();

		cast.setName("test");

		return cast;
	}


	public List<Cast> getCastList() {

		Cast cast = new Cast();

		cast.setName("test");

		List<Cast> casts = new ArrayList<>();
		casts.add(cast);
		return casts;
	}
}


