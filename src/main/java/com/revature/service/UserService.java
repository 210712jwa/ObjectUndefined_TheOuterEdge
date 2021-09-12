package com.revature.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.dto.AddUserDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;
import com.revature.util.PasswordHashing;

@Service
public class UserService {
	
	private UserDAO userDao;
	
	@Autowired
	private PasswordHashing passwordHash;
	
	@Autowired
	public UserService(UserDAO userDao) {
		this.userDao = userDao;
	}
	
	public Users addUser(AddUserDTO addUserDto) throws BadParameterException, NoSuchAlgorithmException, InvalidKeySpecException {
		if(addUserDto.getFirstName().trim().equals("") || addUserDto.getLastName().trim().equals("")) {
			throw new BadParameterException("Name cannot be blank");
		}
		if(!addUserDto.getFirstName().matches("[a-zA-Z]+") || !addUserDto.getLastName().matches("[a-zA-Z]+")) {
			throw new BadParameterException("Name cannot contain number");
		}
		if(addUserDto.getEmail().trim().equals("")) {
			throw new BadParameterException("Email cannot be blank");
		}
		if(addUserDto.getUsername().trim().equals("")) {
			throw new BadParameterException("Username cannot be blank");
		}
		if(addUserDto.getPassword().trim().equals("")) {
			throw new BadParameterException("Password cannot be blank");
		}
		
		byte[] userSalt = passwordHash.getSalt();
		String hashedPassword = passwordHash.generateStorngPasswordHash(addUserDto.getPassword(), userSalt);
		
		addUserDto.setPassword(hashedPassword);
		Users user = userDao.addUser(addUserDto, userSalt);
		return user;
	}

}
