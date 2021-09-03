package com.revature.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.LoginDTO;
import com.revature.model.Users;


@RestController
@CrossOrigin("http://localhost:4201")
public class LoginController {
	
	@PostMapping(path = "/login", consumes = "application/json" )
	public Users login(@RequestBody LoginDTO loginDto) {
		return null;	
	}

}
