package com.revature.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.revature.dto.LoginDTO;

public class LoginController {
	
	@PostMapping(path = "/login", consumes = "application/json" )
	public @ResponseBody String login(@RequestBody LoginDTO loginDto) {
		return null;	
	}

}
