package com.example.movieinventoryservice.modules.theatre.service;

import java.util.List;

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.exception.EmptyListException;
import com.example.movieinventoryservice.exception.RecordNotAddedException;
import com.example.movieinventoryservice.exception.RecordNotDeletedException;
import com.example.movieinventoryservice.exception.RecordNotFoundException;
import com.example.movieinventoryservice.exception.RecordNotUpdatedException;

public interface ScreenService {
	public Screen addScreen(Screen screen) throws RecordNotAddedException;

	public String deleteScreen(int screenId) throws RecordNotDeletedException;

	public String updateScreen(Screen screen) throws RecordNotUpdatedException;

	public Screen getScreenById(int screenId) throws RecordNotFoundException;

	public List<Screen> getAllScreens() throws EmptyListException;

}
