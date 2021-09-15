package com.revature.service;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.security.auth.login.LoginException;

import com.revature.dao.UserDAO;
import com.revature.dto.LoginDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;

@ExtendWith(MockitoExtension.class)
public class LoginServiceUnitTest {

	@Mock
	private UserDAO userDao;
	
	@InjectMocks
	private LoginService loginService;
	
	private Users user;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		user = new Users("first", "last", "email@email.com", "username", "password");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	public void testLogin_success() throws NoSuchAlgorithmException, InvalidKeySpecException, LoginException, BadParameterException {
		LoginDTO loginDto = new LoginDTO("username","password");
		when(userDao.getUserByUsernameAndPassword(eq("username"), eq("password"))).thenReturn(user);
		Users loginUser = loginService.login(loginDto);
		assertEquals(loginUser, user);
	}
	
	@Test
	void testLogin_username_null() throws LoginException, NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			LoginDTO loginDto = new LoginDTO("","password");
			loginService.login(loginDto);
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Username cannot be blank.");
		}
	}
	
	@Test
	void testLogin_username_and_password_null() throws LoginException, NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			LoginDTO loginDto = new LoginDTO("","");
			loginService.login(loginDto);
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Username and password cannot be blank.");
		}
	}
	
	@Test
	void testLogin_password_null() throws LoginException, NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			LoginDTO loginDto = new LoginDTO("username","");
			loginService.login(loginDto);
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Password cannot be blank.");
		}
	}
	
	@Test
	void testLogin_negative() {
		assertThrows(LoginException.class,
	            ()->{
	            	LoginDTO loginDto = new LoginDTO("wrong username","wrong password");
	            	when(userDao.getUserByUsernameAndPassword(any(), any())).thenReturn(null);
	            	loginService.login(loginDto);
	            });
	}

}
