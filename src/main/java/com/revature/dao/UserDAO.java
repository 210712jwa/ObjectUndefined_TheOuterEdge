package com.revature.dao;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dto.AddUserDTO;
import com.revature.model.UserRole;
import com.revature.model.Users;

@Repository
public class UserDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Users addUser(AddUserDTO addUserDto) {
		Session session = sessionFactory.getCurrentSession();
		Users user = new Users(addUserDto.getFirstName(), addUserDto.getLastName(), addUserDto.getEmail(),
				addUserDto.getUsername(), addUserDto.getPassword());
		String userRole_hql = "FROM UserRole ur WHERE ur.id = 2";
		UserRole userRole = (UserRole) session.createQuery(userRole_hql).getSingleResult();
		user.setUserRole(userRole);
		session.persist(user);
		return user;
	}

	@Transactional
	public Users getUserByUsernameAndPassword(String username, String password) {
		Session session = sessionFactory.getCurrentSession();

		try {
			String user_hql = "FROM Users u WHERE u.username=:username AND u.password=:password";
			Users user = (Users) session.createQuery(user_hql)
					.setParameter("username", username).setParameter("password", password).getSingleResult();
			return user;
		} catch (NoResultException e) {
			return null;
		}

	}
}
