package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.dto.LoginDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;
import com.revature.util.PasswordHashing;

@Service
public class LoginService {
	
	private UserDAO userDao;
	

	
	@Autowired
	public LoginService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Users login(LoginDTO loginDto) throws LoginException, BadParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
	
		if (loginDto.getUsername().equals("") && loginDto.getPassword().equals("")) {
			throw new BadParameterException("Username and password cannot be blank.");
		}
		if (loginDto.getUsername().equals("")) {
			throw new BadParameterException("Username cannot be blank.");
		}
		if (loginDto.getPassword().equals("")) {
			throw new BadParameterException("Password cannot be blank.");
		}

		Users user = userDao.getUserByUsernameAndPassword(loginDto.getUsername(), loginDto.getPassword());
		
		if (user == null) {
			throw new LoginException("Incorrect credentials provided");
		}
		
		return user;
	}

}
