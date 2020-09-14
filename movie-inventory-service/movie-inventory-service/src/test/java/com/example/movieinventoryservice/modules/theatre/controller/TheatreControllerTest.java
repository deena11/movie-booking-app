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
import static org.junit.Assert.assertTrue;
import org.junit.Test;
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

import com.example.movieinventoryservice.entity.Theatre;
import com.example.movieinventoryservice.modules.theatre.repository.TheatreRepository;
import com.example.movieinventoryservice.modules.theatre.service.TheatreService;
import com.example.movieinventoryservice.modules.theatre.service.serviceImpl.TheatreServiceImpl;
import com.example.movieinventoryservice.restApiConfig.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest({ TheatreController.class, TheatreServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class TheatreControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public ApiSuccessResponse apiResponse;

	@Autowired
	private TheatreService theatreService;

	@MockBean
	private TheatreRepository theatreRepository;

	@Before
	public void setUp() throws Exception {
		List<Theatre> theatre = new ArrayList<>();
		theatre.add(getTheatre());
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class))).thenReturn(getTheatre());
//		Mockito.verify(theatreRepository).deleteById(Mockito.anyInt());
		Mockito.when(theatreRepository.getOne(Mockito.anyInt())).thenReturn(getTheatre());
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getTheatre()));
		Mockito.when(theatreRepository.findAll()).thenReturn(theatre);

	}

	@Test
	public void testGetTheatre() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/theatre/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));
	}

	@Test
	public void testGetTheatreException() throws Exception {
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/theatre/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetTheatreException1() throws Exception {
		Mockito.when(theatreRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/theatre/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllTheatre() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/theatre/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));
	}

	@Test
	public void testGetAllTheatreException() throws Exception {
		Mockito.when(theatreRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/theatre/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testGetAllTheatreException1() throws Exception {
		Mockito.when(theatreRepository.findAll()).thenReturn(new ArrayList<Theatre>());
		MvcResult result = this.mockMvc.perform(get("/theatre/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddTheatre() throws Exception {
		MvcResult result = mockMvc.perform(post("/theatre/").contentType(MediaType.APPLICATION_JSON)
				.content(getTheatresAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("sampleText"));

	}

	@Test
	public void testAddTheatreException() throws Exception {

		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/theatre/").contentType(MediaType.APPLICATION_JSON)
				.content(getTheatresAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdateTheatre() throws Exception {
		MvcResult result = mockMvc.perform(put("/theatre/").contentType(MediaType.APPLICATION_JSON)
				.content(getTheatresAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully updated"));

	}

	@Test
	public void testUpdateTheatreException() throws Exception {
		Mockito.when(theatreRepository.save(Mockito.any(Theatre.class)))
				.thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/theatre/").contentType(MediaType.APPLICATION_JSON)
				.content(getTheatresAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeleteTheatre() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/theatre/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getTheatresAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getTheatre());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Theatre getTheatre() {

		Theatre theatre = new Theatre();

		theatre.setName("sampleText");
		return theatre;
	}

}
