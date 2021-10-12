package com.revature.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.LoginDTO;
import com.revature.dto.MessageDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Users;
import com.revature.service.LoginService;


@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private HttpServletRequest request;
	
	public LoginController(LoginService loginService) {
		this.loginService = loginService;
	}
	
	

	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json" )
	public ResponseEntity<Object> login(@RequestBody LoginDTO loginDto) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {
			Users user = this.loginService.login(loginDto);
			
			
			HttpSession session = request.getSession(true);
			
			if (session.getAttribute("currentUser") != null) {
				return ResponseEntity.status(400).body(new MessageDTO("You are already logged in!"));
			}
	
			session.setAttribute("currentUser", user);

			
			return ResponseEntity.status(200).body(user);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (LoginException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} 
	}
	
	@GetMapping(path = "/currentuser")
	public ResponseEntity<Object> getCurrentUser() {
		
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("currentUser") == null) {
			return ResponseEntity.status(400).body(new MessageDTO("You are not logged in!"));
		}
		
		Users user = (Users) session.getAttribute("currentUser");
		return ResponseEntity.status(200).body(user);	
	}
	
	@PostMapping(path = "/logout")
	public ResponseEntity<Object> logout() {
		HttpSession session = request.getSession(false);
		
		if (session == null || session.getAttribute("currentUser") == null) {
			return ResponseEntity.status(400).body(new MessageDTO("You are not logged in!"));
		}
		session.setAttribute("currentUser", null);
		return ResponseEntity.status(200).body(new MessageDTO("Successfully logged out"));
	}
		

}
