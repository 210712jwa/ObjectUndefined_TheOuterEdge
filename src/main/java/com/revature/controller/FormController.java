package com.revature.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotation.UserProtected;
import com.revature.dto.AddFormDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.dto.MessageDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Form;
import com.revature.model.Users;
import com.revature.service.FormService;

@RestController
@CrossOrigin("http://localhost:4200")
public class FormController {

	@Autowired
	private FormService formService;

	@Autowired
	private HttpServletRequest request;

	public FormController(FormService formService) {
		this.formService = formService;
	}

	@PostMapping(path = "user/{userId}/form", consumes = "application/json", produces = "application/json")
	@UserProtected
	public ResponseEntity<Object> addForm(@PathVariable String userId, @RequestBody AddFormDTO addFormDTO) {
		try {

			Form form = formService.addForm(userId, addFormDTO);
			return ResponseEntity.status(201).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@PatchMapping(path = "user/{userId}/form/{formId}", consumes = "application/json", produces = "application/json")
	@UserProtected
	public ResponseEntity<Object> editForm(@PathVariable String userId, @PathVariable String formId,
			@RequestBody AddFormDTO editFormDTO) {
		try {
			HttpSession session = request.getSession(false);
			Users user = (Users) session.getAttribute("currentUser");
			String currentUserId = Integer.toString(user.getId());
			if (!userId.equals(currentUserId)) {
				return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
			}
			Form form = formService.editFormById(formId, editFormDTO);
			return ResponseEntity.status(200).body(form);

		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@PatchMapping(path = "admin/{userId}/form/{formId}", consumes = "text/plain", produces = "application/json")
	@AdminProtected
	public ResponseEntity<Object> editFormStatusAdmin(@PathVariable String formId, @RequestBody EditFormStatusDTO formStatusDto) {
		try {
			Form form = formService.editFormStatusAdmin(formId, formStatusDto);
			return ResponseEntity.status(200).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}
  
	public ResponseEntity<Object> editForm(@PathVariable String userId, @PathVariable String formId, @RequestBody AddFormDTO editFormDTO) {
		try {

			Form form = formService.editFormById(userId, formId, editFormDTO);
			return ResponseEntity.status(201).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}
}
