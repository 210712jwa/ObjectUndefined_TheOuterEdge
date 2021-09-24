package com.revature.controller;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.revature.dto.LoginDTO;
import com.revature.model.Users;
import com.revature.service.LoginService;

@ExtendWith(MockitoExtension.class)
public class LoginControllerUnitTest {
	
	private static MockMvc mockMvc;

	@Mock
	private LoginService loginService;
	
	
//	private MockHttpServletRequest request;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpSession session;
	
	@InjectMocks
	private LoginController loginController;
	
	private AutoCloseable closeable;

	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
		
	
	}

	@AfterEach
	void tearDown() throws Exception {
		 closeable.close();
	}
	
	@Test
	void testLogin_success() throws Exception {
		LoginDTO loginDto = new LoginDTO("username","password");
		Users user = new Users("first", "last", "email@email.com", "username", "password");
		user.setId(1);
		when(request.getSession(eq(true))).thenReturn(session);
		when(loginService.login(eq(loginDto))).thenReturn(user);
		
		Users expectedUser = new Users("first", "last", "email@email.com", "username", "password");
		expectedUser.setId(1);
		ObjectMapper om = new ObjectMapper();
		String loginDtoBody = om.writeValueAsString(loginDto);
		
		String expectedUserJson = om.writeValueAsString(expectedUser);
		
		mockMvc
		.perform(post("/login")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(loginDtoBody))
				.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(expectedUserJson));
	
	}
	
	@Test
	void testLogin_alreadyloggedin() throws Exception {
		LoginDTO loginDto = new LoginDTO("username","password");
		Users user = new Users("first", "last", "email@email.com", "username", "password");
		user.setId(1);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		when(request.getSession(eq(true))).thenReturn(session);
		when(loginService.login(eq(loginDto))).thenReturn(user);
		Users expectedUser = new Users("first", "last", "email@email.com", "username", "password");
		expectedUser.setId(1);
		ObjectMapper om = new ObjectMapper();
		String loginDtoBody = om.writeValueAsString(loginDto);
		
		mockMvc
		.perform(post("/login")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(loginDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	
	}
	
	@Test
	void testGetCurrentUser_success() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		Users user = new Users("first", "last", "email@email.com", "username", "password");
		user.setId(1);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc
		.perform(get("/currentuser"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	void testGetCurrentUser_notLogin1() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		session = null;
		mockMvc
		.perform(get("/currentuser"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
	
	@Test
	void testGetCurrentUser_notLogin2() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		mockMvc
		.perform(get("/currentuser"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
	
	
	@Test
	void testLogout_success() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		Users user = new Users("first", "last", "email@email.com", "username", "password");
		user.setId(1);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc
		.perform(post("/logout"))
				.andExpect(MockMvcResultMatchers.status().isOk());
		
	}
	
	@Test
	void testLogout_not_login1() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(null);
		mockMvc
		.perform(post("/logout"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
	
	@Test
	void testLogout_not_login2() throws Exception {
		when(request.getSession(eq(false))).thenReturn(session);
		session = null;
		mockMvc
		.perform(post("/logout"))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
}
