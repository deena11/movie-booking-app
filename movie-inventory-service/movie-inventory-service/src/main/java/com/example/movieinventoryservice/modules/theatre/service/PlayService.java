package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface PlayService {

	public Play addPlay(Play play) throws RecordNotAddedException;

	public String deletePlay(int playId) throws RecordNotDeletedException;

	public String updatePlay(Play play) throws RecordNotUpdatedException;

	public Play getPlayById(int playId) throws RecordNotFoundException;

	public List<Play> getAllPlays() throws EmptyListException;

}
