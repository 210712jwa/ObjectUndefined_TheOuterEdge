package com.revature.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.annotation.AdminProtected;
import com.revature.annotation.UserProtected;
import com.revature.dto.AddFormDTO;
import com.revature.dto.AddOrEditCommentDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.dto.MessageDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Comment;
import com.revature.model.Form;
import com.revature.model.Users;
import com.revature.service.FormService;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class FormController {

	@Autowired
	private FormService formService;

	@Autowired
	private HttpServletRequest request;

	public FormController(FormService formService) {
		this.formService = formService;
	}

	@PostMapping(path = "/user/{userId}/form", consumes = "application/json", produces = "application/json")
	@UserProtected
	public ResponseEntity<Object> addForm(@PathVariable String userId, @RequestBody AddFormDTO addFormDTO) {
		try {
			System.out.println(userId);
			System.out.println(addFormDTO.getTitle());
			HttpSession session = request.getSession(false);
			Users user = (Users) session.getAttribute("currentUser");
			String currentUserId = Integer.toString(user.getId());
			if (!userId.equals(currentUserId)) {
				return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
			}
			Form form = formService.addForm(userId, addFormDTO);
			return ResponseEntity.status(201).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@GetMapping(path = "/form", produces = "application/json")
	public ResponseEntity<Object> getForm() throws BadParameterException {
//		if (!search.trim().equals("")) {
//			List<Form> form = formService.getFormByTitle(search);
//			return ResponseEntity.status(200).body(form);
//		} else {
			List<Form> form = formService.getAllForm();
			return ResponseEntity.status(200).body(form);
//		}

	}
	


	@PatchMapping(path = "/user/{userId}/form/{formId}/upload", consumes = "multipart/form-data")
	@UserProtected
	public ResponseEntity<Object> addImage(@PathVariable String userId, @PathVariable String formId,
			@RequestParam("file") MultipartFile file) {
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("currentUser");
		String currentUserId = Integer.toString(user.getId());
		if (!userId.equals(currentUserId)) {
			return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
		}
		try {
			Form form = formService.addImage(formId, file);
			return ResponseEntity.status(200).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		} catch (IOException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@PatchMapping(path = "/user/{userId}/form/{formId}", consumes = "application/json", produces = "application/json")
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


	@PatchMapping(path = "/user/{userId}/form/{formId}/comment")
	@UserProtected
	public ResponseEntity<Object> addComment(@PathVariable String userId, @PathVariable String formId,
			@RequestBody AddOrEditCommentDTO commentDTO) {
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("currentUser");
		String currentUserId = Integer.toString(user.getId());
		if (!userId.equals(currentUserId)) {
			return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
		}
		try {
			Form form = formService.addComment(userId, formId, commentDTO);
			return ResponseEntity.status(200).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}

	}

	@PatchMapping(path = "/user/{userId}/form/{formId}/comment/{commentId}")
	@UserProtected
	public ResponseEntity<Object> editComment(@PathVariable String userId, @PathVariable String commentId,
			@RequestBody AddOrEditCommentDTO commentDTO) {

		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("currentUser");
		String currentUserId = Integer.toString(user.getId());
		if (!userId.equals(currentUserId)) {
			return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
		}
		try {
			Comment comment = formService.editComment(commentId, commentDTO);
			return ResponseEntity.status(200).body(comment);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@DeleteMapping(path = "/user/{userId}/form/{formId}/comment/{commentId}")
	@UserProtected
	public ResponseEntity<Object> deleteComment(@PathVariable String userId, @PathVariable String commentId) {
		HttpSession session = request.getSession(false);
		Users user = (Users) session.getAttribute("currentUser");
		String currentUserId = Integer.toString(user.getId());
		if (!userId.equals(currentUserId)) {
			return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
		}
		try {
			formService.deleteComment(commentId);
			return ResponseEntity.status(200).body(new MessageDTO("Delete comment successfully"));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}

	}


	@PatchMapping(path = "/admin/{userId}/form/{formId}", consumes = "application/json", produces = "application/json")
	@AdminProtected
	public ResponseEntity<Object> editFormStatusAdmin(@PathVariable String formId,
			@RequestBody EditFormStatusDTO formStatusDto) {
		try {
			Form form = formService.editFormStatusAdmin(formId, formStatusDto);
			return ResponseEntity.status(200).body(form);
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@DeleteMapping(path = "/user/{userId}/form/{formId}")
	@UserProtected
	public ResponseEntity<Object> deleteFormById(@PathVariable String userId, @PathVariable String formId) {
		try {
			HttpSession session = request.getSession(false);
			Users user = (Users) session.getAttribute("currentUser");
			String currentUserId = Integer.toString(user.getId());
			if (!userId.equals(currentUserId)) {
				return ResponseEntity.status(401).body(new MessageDTO("unauthorized action."));
			}
			formService.deleteForm(formId);
			return ResponseEntity.status(200).body(new MessageDTO("Delete successfully"));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@DeleteMapping(path = "/admin/{userId}/form/{formId}/comment/{commentId}")
	@AdminProtected
	public ResponseEntity<Object> deleteCommentAdmin(@PathVariable String commentId) {
		try {
			formService.deleteForm(commentId);
			return ResponseEntity.status(200).body(new MessageDTO("Delete successfully"));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}

	@DeleteMapping(path = "/admin/{userId}/form/{formId}")
	@AdminProtected
	public ResponseEntity<Object> deleteFormAdmin(@PathVariable String formId) {
		try {
			formService.deleteForm(formId);
			return ResponseEntity.status(200).body(new MessageDTO("Delete successfully"));
		} catch (BadParameterException e) {
			return ResponseEntity.status(400).body(new MessageDTO(e.getMessage()));
		}
	}
}
