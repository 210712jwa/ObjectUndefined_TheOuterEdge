package com.revature.dao;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dto.AddUserDTO;
import com.revature.dto.LoginDTO;
import com.revature.model.UserRole;
import com.revature.model.Users;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@WebAppConfiguration
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:springorm-test.properties")
@Sql({"/schema.sql"})
@Sql(scripts={"classpath:data.sql"})
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
public class UserDaoTest {
	
	@Autowired
	private UserDAO userDao;
	

	@Test
	@Transactional
	@Order(0)
	@Commit
	void testAddUser_success() {
		byte[] salt = "hash".getBytes();
		AddUserDTO addUserDto = new AddUserDTO("first", "last", "email@email.com", "username", "password");
		Users user = userDao.addUser(addUserDto, salt);
		assertEquals(1, user.getId());
	}
	
	@Test
	@Transactional
	@Order(1)
	@Commit
	void testlogin_() throws NoSuchAlgorithmException, InvalidKeySpecException {
		LoginDTO dto = new LoginDTO("username", "password");
		
		Users user = userDao.getUserByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		
		assertEquals(1, user.getId());
	}

}
