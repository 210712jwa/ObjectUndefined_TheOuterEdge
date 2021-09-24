package com.revature.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.AddUserDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;
import com.revature.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerUnitTest {
	
	private static MockMvc mockMvc;
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private UserController userController;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {	
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	

	@AfterEach
	void tearDown() throws Exception {

	}

	@Test
	void testAddUser_success() throws Exception {
		AddUserDTO addUserDto = new AddUserDTO("first", "last", "email@email.com", "username", "password");
		Users addedUser = new Users(addUserDto.getFirstName(), addUserDto.getLastName(), addUserDto.getEmail(), addUserDto.getUsername(), addUserDto.getPassword());
		addedUser.setId(1);
		when(userService.addUser(addUserDto)).thenReturn(addedUser);
		
		Users expectedUser = new Users("first", "last", "email@email.com", "username", "password");
		expectedUser.setId(1);
		ObjectMapper om = new ObjectMapper();
		String addUserDtoBody = om.writeValueAsString(addUserDto);
		
		String expectedUserJson = om.writeValueAsString(expectedUser);
		mockMvc
		.perform(post("/addUser")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addUserDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
			.andExpect(MockMvcResultMatchers.content().json(expectedUserJson));
	}
	
	@Test
	void testAddUser_badParam() throws Exception {
		AddUserDTO addUserDto = new AddUserDTO("", "last", "email@email.com", "username", "password");

		when(userService.addUser(addUserDto)).thenThrow(BadParameterException.class);
		
		ObjectMapper om = new ObjectMapper();
		String addUserDtoBody = om.writeValueAsString(addUserDto);
		mockMvc
		.perform(post("/addUser")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addUserDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
}
