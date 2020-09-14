package com.example.movieinventoryservice.modules.theatre.controller;

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
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.movieinventoryservice.entity.Play;
import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.modules.theatre.repository.PlayRepository;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.service.PlayService;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.PlayServiceImpl;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.ScreenServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ PlayController.class, PlayServiceImpl.class, ScreenServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class PlayControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ScreenService screenService;
	
	public ApiSuccessResponse apiResponse;

	@Autowired
	private PlayService playService;
	
	@MockBean
	private ScreenRepository screenRepository;

	@MockBean
	private PlayRepository playRepository;

	@Before
	public void setUp() throws Exception {
		List<Play> play = new ArrayList<>();
		play.add(getPlay());
		Mockito.when(playRepository.save(Mockito.any(Play.class))).thenReturn(getPlay());
//		Mockito.verify(playRepository).deleteById(Mockito.anyInt());
		Mockito.when(playRepository.getOne(Mockito.anyInt())).thenReturn(getPlay());
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getPlay()));
		Mockito.when(playRepository.findAll()).thenReturn(play);
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getScreen()));
	}

	@Test
	public void testGetPlay() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));
	}

	@Test
	public void testGetPlayException() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetPlayException1() throws Exception {
		Mockito.when(playRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/play/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllPlay() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));
	}

	@Test
	public void testGetAllPlayException() throws Exception {
		Mockito.when(playRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllPlayException1() throws Exception {
		Mockito.when(playRepository.findAll()).thenReturn(new ArrayList<Play>());
		MvcResult result = this.mockMvc.perform(get("/play/all")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddPlay() throws Exception {
		MvcResult result = mockMvc.perform(post("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1234"));

	}

	@Test
	public void testAddPlayException() throws Exception {

		Mockito.when(playRepository.save(Mockito.any(Play.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdatePlay() throws Exception {
		MvcResult result = mockMvc.perform(put("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdatePlayException() throws Exception {
		Mockito.when(playRepository.save(Mockito.any(Play.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/play/").contentType(MediaType.APPLICATION_JSON)
				.content(getPlaysAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeletePlay() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/play/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getPlaysAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getPlay());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Play getPlay() {

		Play play = new Play();

		play.setId(1234);
		play.setScreen(getScreen());
		return play;
	}
	
	public Screen getScreen() {

		Screen screen = new Screen();
		screen.setTotalSeatsPerScreen(110);
		screen.setName("sampleText");
		return screen;
	}

}

