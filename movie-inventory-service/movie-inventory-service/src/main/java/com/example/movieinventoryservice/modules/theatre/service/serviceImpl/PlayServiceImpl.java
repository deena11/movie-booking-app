package com.example.movieinventoryservice.modules.theatre.service.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;

@Service
@Transactional
public class PlayServiceImpl implements PlayService{
	
	
	@Autowired
	private PlayRepository playRepository;
	
	@Autowired
	private ScreenService screenService;
	
	private Logger logger = LoggerFactory.getLogger(PlayService.class);
	
	private String message="";

	@Override
	public Play addPlay(Play play) throws RecordNotAddedException {
		try {
			Screen screen = screenService.getScreenById(play.getScreen().getId());

			logger.info(screen.toString());
			play.setSeatsAvailable(screen.getTotalSeatsPerScreen());
			logger.info(play.toString());
			return playRepository.save(play);
		} catch (DataAccessException ex) {
			throw new RecordNotAddedException("Failed To Add Play", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotAddedException("Failed To Add Play", ex.getCause());
		}
	}

	@Override
	public String deletePlay(int playId) throws RecordNotDeletedException {
		try {
			playRepository.deleteById(playId);
			message = "Successfully deleted Play of id - " + playId;
			return message;
		} catch (DataAccessException ex) {
			throw new RecordNotDeletedException("Failed to Delete", ex.getCause());
		}
	}

	@Override
	public String updatePlay(Play play) throws RecordNotUpdatedException {
		try {
			playRepository.save(play);
			message = "Successfully updated theatre";
			return message;
		}

		catch (DataAccessException ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
		catch(Exception ex) {
			throw new RecordNotUpdatedException("Failed to Update", ex.getCause());
		}
	}

	@Override
	public Play getPlayById(int playId) throws RecordNotFoundException {
		try {
			logger.info("Entered into Play Service - getByid "+playId);
			logger.info(playRepository.findAll().toString());
			Optional<Play> play = playRepository.findById(playId);
			if (play.isPresent()) {
				logger.info(play.get().toString());
				return play.get();
			} else {
				throw new RecordNotFoundException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new RecordNotFoundException("Failed to Fetch", ex.getCause());
		}
	}

	@Override
	public List<Play> getAllPlays() throws EmptyListException {
		try {
			List<Play> plays = playRepository.findAll();
			if (plays.size()>0) {
				return plays;
			} else {
				throw new EmptyListException("No Record to Fetch");
			}
		} catch (DataAccessException ex) {
			throw new EmptyListException("Failed to Fetch", ex.getCause());
		}
	}

	
}
