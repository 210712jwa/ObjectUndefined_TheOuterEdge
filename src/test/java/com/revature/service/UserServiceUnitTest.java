package com.revature.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dao.UserDAO;
import com.revature.dto.AddUserDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;
import com.revature.util.PasswordHashing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.Test;


@ExtendWith(MockitoExtension.class)
public class UserServiceUnitTest {

	@Mock
	private UserDAO userDao;
	
	@Mock
	private PasswordHashing passwordHash;
	
	@InjectMocks
	private UserService userService;
	
	private Users user;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		user = new Users("first", "last", "email", "username", "password");
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Test
	void testAddUser_success() throws NoSuchAlgorithmException, InvalidKeySpecException, BadParameterException {
		AddUserDTO addUserDto = new AddUserDTO("first", "last","email@email.com", "username", "password");
		byte[] salt = "mySalt".getBytes();
		when(passwordHash.getSalt()).thenReturn(salt);
		when(passwordHash.generateStorngPasswordHash(addUserDto.getPassword(), passwordHash.getSalt())).thenReturn("passwordHashed");
		addUserDto.setPassword(passwordHash.generateStorngPasswordHash(addUserDto.getPassword(), passwordHash.getSalt()));
		Users newUser = new Users(addUserDto.getFirstName(), addUserDto.getLastName(), addUserDto.getEmail(), addUserDto.getUsername(), addUserDto.getPassword());
		newUser.setId(10);
		when(userDao.addUser(addUserDto, salt)).thenReturn(newUser);
		Users actualUser = userService.addUser(addUserDto);
		Users expectedUser = new Users("first", "last", "email@email.com", "username", "passwordHashed");
		expectedUser.setId(10);
		assertEquals(actualUser, expectedUser);
	}
	
	@Test
	void testAddUser_firstName_null() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
		AddUserDTO addUserDto = new AddUserDTO("", "last","email@email.com", "username", "password");
		userService.addUser(addUserDto);
		fail();
		}catch(BadParameterException e) {
			assertEquals("Name cannot be blank", e.getMessage());
		}
	}
	
	@Test
	void testAddUser_lastName_null() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
		AddUserDTO addUserDto = new AddUserDTO("first", "","email@email.com", "username", "password");
		userService.addUser(addUserDto);
		fail();
		}catch(BadParameterException e) {
			assertEquals("Name cannot be blank", e.getMessage());
		}
	}
	
	@Test
	void testAddUser_email_null() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
		AddUserDTO addUserDto = new AddUserDTO("first", "last","", "username", "password");
		userService.addUser(addUserDto);
		fail();
		}catch(BadParameterException e) {
			assertEquals("Email cannot be blank", e.getMessage());
		}
	}
	
	@Test
	void testAddUser_username_null() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
		AddUserDTO addUserDto = new AddUserDTO("first", "last","email@email.com", "", "password");
		userService.addUser(addUserDto);
		fail();
		}catch(BadParameterException e) {
			assertEquals("Username cannot be blank", e.getMessage());
		}
	}
	
	@Test
	void testAddUser_password_null() throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
		AddUserDTO addUserDto = new AddUserDTO("first", "last","email@email.com", "username", "");
		userService.addUser(addUserDto);
		fail();
		}catch(BadParameterException e) {
			assertEquals("Password cannot be blank", e.getMessage());
		}
	}
}
