package com.example.movieinventoryservice.modules.theatre.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bouncycastle.util.Times;
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

import com.example.movieinventoryservice.entity.Screen;
import com.example.movieinventoryservice.modules.theatre.repository.ScreenRepository;
import com.example.movieinventoryservice.modules.theatre.service.ScreenService;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.ScreenServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ ScreenController.class, ScreenServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class ScreenControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public ApiSuccessResponse apiResponse;

	@Autowired
	private ScreenService screenService;

	@MockBean
	private ScreenRepository screenRepository;

	@Before
	public void setUp() throws Exception {
		List<Screen> screen = new ArrayList<>();
		screen.add(getScreen());
		Mockito.when(screenRepository.save(Mockito.any(Screen.class))).thenReturn(getScreen());
//		Mockito.verify(screenRepository).deleteById(Mockito.anyInt());
		Mockito.when(screenRepository.getOne(Mockito.anyInt())).thenReturn(getScreen());
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getScreen()));
		Mockito.when(screenRepository.findAll()).thenReturn(screen);

	}

	@Test
	public void testGetScreen() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/screen/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));
	}

	@Test
	public void testGetScreenException() throws Exception {
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/screen/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetScreenException1() throws Exception {
		Mockito.when(screenRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/screen/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllScreen() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/screen/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));
	}

	@Test
	public void testGetAllScreenException() throws Exception {
		Mockito.when(screenRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/screen/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllScreenException1() throws Exception {
		Mockito.when(screenRepository.findAll()).thenReturn(new ArrayList<Screen>());
		MvcResult result = this.mockMvc.perform(get("/screen/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddScreen() throws Exception {
		MvcResult result = mockMvc.perform(post("/screen/").contentType(MediaType.APPLICATION_JSON)
				.content(getScreensAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));

	}

	@Test
	public void testAddScreenException() throws Exception {

		Mockito.when(screenRepository.save(Mockito.any(Screen.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/screen/").contentType(MediaType.APPLICATION_JSON)
				.content(getScreensAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdateScreen() throws Exception {
		MvcResult result = mockMvc.perform(put("/screen/").contentType(MediaType.APPLICATION_JSON)
				.content(getScreensAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdateScreenException() throws Exception {
		Mockito.when(screenRepository.save(Mockito.any(Screen.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/screen/").contentType(MediaType.APPLICATION_JSON)
				.content(getScreensAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeleteScreen() throws Exception {
		this.mockMvc.perform(delete("/screen/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(screenRepository).deleteById(Mockito.anyInt());
	}

	public String getScreensAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getScreen());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Screen getScreen() {

		Screen screen = new Screen();

		screen.setName("sampleText");
		return screen;
	}

}
