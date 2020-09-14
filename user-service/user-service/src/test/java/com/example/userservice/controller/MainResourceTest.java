package com.example.userservice.controller;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.test.context.support.WithMockUser;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.userservice.config.AuthorizationServerConfiguration;
import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.restApiConfig.ApiSuccessResponse;
import com.example.userservice.service.UserDetailServiceImpl;
import com.example.userservice.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@WebMvcTest({ MainResource.class, UserServiceImpl.class,UserDetailServiceImpl.class,AuthorizationServerConfiguration.class })
@AutoConfigureMockMvc(addFilters=false)
class MainResourceTest {

	@Autowired
	private MockMvc mockMvc;

	public ApiSuccessResponse apiResponse;

	@Autowired
	private UserServiceImpl userService;

	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private DataSource dataSource;
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@BeforeEach
	public void setUp() throws Exception {
		List<User> users = new ArrayList<>();
		users.add(getUser());
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(getUser());
//		Mockito.verify(userRepository).deleteById(Mockito.anyInt());
		Mockito.when(userRepository.getOne(Mockito.anyInt())).thenReturn(getUser());
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(getUser()));
		Mockito.when(userRepository.findAll()).thenReturn(users);
	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetUser() throws Exception {

		MvcResult result = this.mockMvc.perform(get("/user/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetUserException() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/user/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetUserException1() throws Exception {
		Mockito.when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
		MvcResult result = this.mockMvc.perform(get("/user/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetAllUser() throws Exception {
		MvcResult result = this.mockMvc.perform(get("/user/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("test"));
	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetAllUserException() throws Exception {
		Mockito.when(userRepository.findAll()).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = this.mockMvc.perform(get("/user/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}
	
	@Test
	@WithMockUser(username="user",roles="admin")
	public void testGetAllUserException1() throws Exception {
		Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<User>());
		MvcResult result = this.mockMvc.perform(get("/user/")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	public void testAddUser() throws Exception {
		MvcResult result = mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON)
				.content(getUsersAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully Added"));

	}

	@Test
	public void testAddUserException() throws Exception {

		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(post("/user/add").contentType(MediaType.APPLICATION_JSON)
				.content(getUsersAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));

	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testUpdateUser() throws Exception {
		MvcResult result = mockMvc.perform(put("/user/").contentType(MediaType.APPLICATION_JSON)
				.content(getUsersAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully Updated"));

	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testUpdateUserException() throws Exception {
		Mockito.when(userRepository.save(Mockito.any(User.class))).thenThrow(Mockito.mock(DataAccessException.class));
		MvcResult result = mockMvc.perform(put("/user/").contentType(MediaType.APPLICATION_JSON)
				.content(getUsersAsJson()).characterEncoding("utf-8")).andExpect(status().isOk()).andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("\"error\":true"));
	}

	@Test
	@WithMockUser(username="user",roles="admin")
	public void testDeleteUser() throws Exception {
		MvcResult result = this.mockMvc.perform(delete("/user/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		System.out.println(result.getResponse().getContentAsString());
		assertTrue(result.getResponse().getContentAsString().contains("Successfully Deleted"));

	}

	public String getUsersAsJson() {
		try {
			return new ObjectMapper().writeValueAsString(getUser());
		} catch (JsonProcessingException e) {

			throw new RuntimeException(e.getLocalizedMessage());
		}

	}

	public User getUser() {

		com.example.userservice.entity.User user = new User();

		user.setEmail("test@gmail.com");
		user.setUsername("test");
		user.setPassword("test");

		return user;
	}

	



	
}
