package com.revature.dao;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dto.AddUserDTO;
import com.revature.model.UserRole;
import com.revature.model.Users;
import com.revature.util.PasswordHashing;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private PasswordHashing passwordHash;

	@Transactional
	public Users addUser(AddUserDTO addUserDto, byte[] salt) {
		Session session = sessionFactory.getCurrentSession();
		Users user = new Users(addUserDto.getFirstName(), addUserDto.getLastName(), addUserDto.getEmail(),
				addUserDto.getUsername(), addUserDto.getPassword());
		String userRole_hql = "FROM UserRole ur WHERE ur.id = 2";
		UserRole userRole = (UserRole) session.createQuery(userRole_hql).getSingleResult();
		user.setUserRole(userRole);
		user.setSalt(salt);
		session.persist(user);
		return user;
	}

	@Transactional
	public Users getUserByUsernameAndPassword(String username, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		Session session = sessionFactory.getCurrentSession();

		try {
			String usernamehql = "FROM Users u WHERE u.username=:username";
			Users userTemp = (Users) session.createQuery(usernamehql).setParameter("username", username).getSingleResult();
			String realPassword = passwordHash.generateStorngPasswordHash(password, userTemp.getSalt());
			String userhql = "FROM Users u WHERE u.username=:username AND u.password=:password";
			Users user = (Users) session.createQuery(userhql)
					.setParameter("username", username).setParameter("password", realPassword).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}

	}
}
