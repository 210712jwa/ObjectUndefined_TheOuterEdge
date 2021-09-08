package com.revature.service;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.model.Users;

@Service
public class LoginService {
	
	private UserDAO userDao;
	
	@Autowired
	public LoginService(UserDAO userDao) {
		this.userDao = userDao;
	}

	public Users login(String username, String password) throws LoginException {
		
		Users user = userDao.getUserByUsernameAndPassword(username, password);
		
		if (user == null) {
			throw new LoginException("Incorrect credentials provided");
		}
		
		return user;
	}

}
