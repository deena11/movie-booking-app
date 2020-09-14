package com.example.movieinventoryservice.modules.movies.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Cast;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.movies.repository.CastRepository;
import com.example.movieinventoryservice.modules.movies.service.CastService;

@Service
@Transactional
public class CastServiceImpl implements CastService{
	
	@Autowired
	private CastRepository castRepository;
	
	private String message = "";

	

	@Override
	public String deleteCast(int castId) throws RecordNotDeletedException {
		try {
			castRepository.deleteById(castId);
			message = "Successfully deleted Cast of id - " + castId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updateCast(Cast cast) throws RecordNotUpdatedException {
		try {

			castRepository.save(cast);
			message = "Successfully updated cast of name - " + cast.getName();
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Cast getCastById(int castId) throws RecordNotFoundException {
		try {
			Optional<Cast> cast = castRepository.findById(castId);
			if (cast.isPresent()) {
				return cast.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Cast> getAllCasts() throws EmptyListException {
		try {
			List<Cast> casts = castRepository.findAll();
			if (casts.size()>0) {
				return casts;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public Cast addCast(Cast cast) throws RecordNotAddedException {
		try {
			return castRepository.save(cast);
		} catch (DataAccessException ex) {
			throw new RecordNotAddedException("Failed To Add Cast", ex.getCause());
		}
	}


}
