package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;

@Service
@Transactional
public class TheatreServiceImpl implements TheatreService {

	@Autowired
	private TheatreRepository theatreRepository;
	
	private String message = "";

	

	@Override
	public String deleteTheatre(int theatreId) throws RecordNotDeletedException {
		try {
			theatreRepository.deleteById(theatreId);
			message = "Successfully deleted Theatre of id - " + theatreId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updateTheatre(Theatre theatre) throws RecordNotUpdatedException {
		try {

			theatreRepository.save(theatre);
			message = "Successfully updated theatre";
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Theatre getTheatreById(int theatreId) throws RecordNotFoundException {
		try {
			Optional<Theatre> theatre = theatreRepository.findById(theatreId);
			if (theatre.isPresent()) {
				return theatre.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Theatre> getAllTheatres() throws EmptyListException {
		try {
			List<Theatre> theatres = theatreRepository.findAll();
			if (theatres.size()>0) {
				return theatres;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public Theatre addTheatre(Theatre theatre) throws RecordNotAddedException {
		try {
			return theatreRepository.save(theatre);
		} catch (DataAccessException ex) {
			throw new RecordNotAddedException("Failed To Add Theatre", ex.getCause());
		}
	}
}
