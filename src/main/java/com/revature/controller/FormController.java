package com.revature.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.AddFormDTO;
import com.revature.service.FormService;


@RestController
@CrossOrigin("http://localhost:4200")
public class FormController {

	@Autowired
	private FormService formService;
	
	public FormController(FormService formService) {
		this.formService = formService;
	}

	@PostMapping(path = "/form")
	public ResponseEntity<Object> addForm(@RequestBody AddFormDTO addFormDTO) {
		return null;
	}
}
