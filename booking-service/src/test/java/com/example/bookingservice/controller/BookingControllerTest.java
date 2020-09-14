package com.example.bookingservice.controller;

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
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.bookingservice.dto.Play;
import com.example.bookingservice.model.Booking;
import com.example.bookingservice.repository.BookingRepository;
import com.example.bookingservice.response.APISuccessResponse;
import com.example.bookingservice.service.BookingService;
import com.example.bookingservice.service.serviceimpl.BookingServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest({ BookingController.class, BookingServiceImpl.class })
@AutoConfigureMockMvc(secure=false)
public class BookingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	public APISuccessResponse apiResponse;

	@MockBean
	private WebClient.Builder webclient;
	
	@Autowired
	private BookingService bookingService;

	@MockBean
	private BookingRepository bookingRepository;

	@MockBean
	private RestTemplate restTemplate;
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp() throws Exception {
		List<Booking> bookings = new ArrayList<>();
		bookings.add(getBooking());
		Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenReturn(getBooking());
//		Mockito.verify(bookingRepository).deleteById(Mockito.anyInt());
		Mockito.when(bookingRepository.getOne(Mockito.anyInt())).thenReturn(getBooking());
		Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getBooking()));
		Mockito.when(bookingRepository.findAll()).thenReturn(bookings);
		
		Mockito.when(restTemplate.exchange(
	            Matchers.anyString(),
	            Matchers.eq(HttpMethod.GET),
	            Matchers.<HttpEntity> any(),
	            Matchers.<Class<APISuccessResponse>>any())
	        ).thenReturn(getApiSuccessResponse());
		
		Mockito.when(restTemplate.exchange(
	            Matchers.anyString(),
	            Matchers.eq(HttpMethod.PUT),
	            Matchers.<HttpEntity<?>> any(),
	            Matchers.<Class<APISuccessResponse>>any())
	        ).thenReturn(getApiSuccessResponse());
		
		//Mockito.when(webclient.build().get().uri(Mockito.anyString()).retrieve().bodyToMono(String.class)).thenReturn(Mono.just("Success"));
		
		
	}


	@Test
	public void testGetBooking() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/booking/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1007"));
	}

	@Test
	public void testGetBookingException() throws Exception {
		Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/booking/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetBookingException1() throws Exception {
		Mockito.when(bookingRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/booking/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@WithMockUser
	@Test
	public void testGetAllBooking() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/booking/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1007"));
	}

	@Test
	public void testGetAllBookingException() throws Exception {
		Mockito.when(bookingRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/booking/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	public void testGetAllBookingException1() throws Exception {
		Mockito.when(bookingRepository.findAll()).thenReturn(new ArrayList<Booking>());
		MvcResult result = this.mockMvc.perform(get("/booking/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddBooking() throws Exception {
		MvcResult result = mockMvc.perform(post("/booking/").contentType(MediaType.APPLICATION_JSON)
				.content(getBookingsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("1007"));

	}

	@Test
	public void testAddBookingException() throws Exception {

		Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/booking/").contentType(MediaType.APPLICATION_JSON)
				.content(getBookingsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	public void testUpdateBooking() throws Exception {
		MvcResult result = mockMvc.perform(put("/booking/").contentType(MediaType.APPLICATION_JSON)
				.content(getBookingsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfull"));

	}

	@Test
	public void testUpdateBookingException() throws Exception {
		Mockito.when(bookingRepository.save(Mockito.any(Booking.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/booking/").contentType(MediaType.APPLICATION_JSON)
				.content(getBookingsAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testDeleteBooking() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/booking/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully deleted"));

	}

	public String getBookingsAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getBooking());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public Booking getBooking() {

		com.example.bookingservice.model.Booking booking = new Booking();

		booking.setId(1007);
		booking.setSeatCount(5);
		return booking;
	}

	
	
	public Play getPlay() {

		Play play = new Play();

		play.setDate("testDate");
		play.setSeatsAvailable(120);
		
		return play;
	}
	
	public ResponseEntity<APISuccessResponse> getApiSuccessResponse() {
		APISuccessResponse response = new APISuccessResponse();
		response.setBody(getPlay());
		
		response.setMessage("success");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
