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
import com.revature.util.PasswordHashing;

@ExtendWith(SpringExtension.class)
@ContextConfiguration("classpath:applicationContext.xml")
@WebAppConfiguration
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestPropertySource("classpath:springorm-test.properties")
//@Sql(scripts={"classpath:data.sql"})
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@Sql({"/schema.sql", "/data.sql"})
public class UserDaoTest {	
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private PasswordHashing passhash;
	
	@Test
	@Order(0)
	@Commit
	void testAddUser_success() throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] salt = passhash.getSalt();
		String passwordHashed = passhash.generateStorngPasswordHash("password", salt);
		AddUserDTO addUserDto = new AddUserDTO("first", "last", "email@email.com", "username", passwordHashed);
		Users user = userDao.addUser(addUserDto, salt);
		assertEquals("username", user.getUsername());
	}
	
	@Test
	@Order(1)
	void testlogin_success() throws NoSuchAlgorithmException, InvalidKeySpecException {
		LoginDTO dto = new LoginDTO("username", "password");
		Users user = userDao.getUserByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		assertEquals("username", user.getUsername());
	}
	
	@Test
	@Order(2)
	void testlogin_fail() throws NoSuchAlgorithmException, InvalidKeySpecException {
		LoginDTO dto = new LoginDTO("username", "wrongPassword");
		Users user = userDao.getUserByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		assertEquals(null, user);
	}

}
