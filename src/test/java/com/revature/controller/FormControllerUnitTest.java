package com.revature.controller;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dto.AddFormDTO;
import com.revature.dto.AddOrEditCommentDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Comment;
import com.revature.model.Form;
import com.revature.model.FormStatus;
import com.revature.model.Users;
import com.revature.service.FormService;

@ExtendWith(MockitoExtension.class)
public class FormControllerUnitTest {
	
	private static MockMvc mockMvc;
	
	@Mock
	private FormService formService;
	
	@InjectMocks
	private FormController formController;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpSession session;
	
	private Users user;
	
	private AutoCloseable closeable;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		closeable = MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(formController).build();
		user = new Users("first", "last", "email@email.com", "username", "password");
		user.setId(1);
	
	}

	@AfterEach
	void tearDown() throws Exception {
		 closeable.close();
	}

	@Test
	void testAddForm_success() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		Form form = new Form(addFormDto.getTitle(), addFormDto.getDescription(), null, addFormDto.getLatitude(), addFormDto.getLongitude());
		form.setId(10);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		when(formService.addForm(eq("1"), eq(addFormDto))).thenReturn(form);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);
		String expectedForm = om.writeValueAsString(form);
		mockMvc.perform(post("/user/1/form")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedForm));
	}
	
	@Test
	void testAddForm_not_auth() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);

		mockMvc.perform(post("/user/2/form")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testAddForm_Bad_Param() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		Form form = new Form(addFormDto.getTitle(), addFormDto.getDescription(), null, addFormDto.getLatitude(), addFormDto.getLongitude());
		form.setId(10);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);
		mockMvc.perform(post("/user/w/form")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testGEtForm() throws Exception  {
		List<Form> forms = new ArrayList<>();
		Form form1 = new Form("title", "description", null, 100, 101);
		Form form2 = new Form("title form", "description form", null, 102, 103);
		forms.add(form1);
		forms.add(form2);
		when(formService.getAllForm()).thenReturn(forms);
		ObjectMapper om = new ObjectMapper();
		String formsBody = om.writeValueAsString(forms);
		mockMvc.perform(get("/form"))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(formsBody));
	}
	
	@Test
	void testEditForm() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		Form form = new Form(addFormDto.getTitle(), addFormDto.getDescription(), null, addFormDto.getLatitude(), addFormDto.getLongitude());
		form.setId(10);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		when(formService.editFormById(eq("10"), eq(addFormDto))).thenReturn(form);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);
		String expectedForm = om.writeValueAsString(form);
		mockMvc.perform(patch("/user/1/form/10")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedForm));
	}
	
	@Test
	void testEditForm_not_auth() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);

		mockMvc.perform(patch("/user/2/form/10")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testEditForm_Bad_Param() throws Exception {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", null, 100, 101);
		Form form = new Form(addFormDto.getTitle(), addFormDto.getDescription(), null, addFormDto.getLatitude(), addFormDto.getLongitude());
		form.setId(10);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addFormDtoBody = om.writeValueAsString(addFormDto);
		when(formService.editFormById(eq("w"), eq(addFormDto))).thenThrow(BadParameterException.class);
		mockMvc.perform(patch("/user/1/form/w")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testAddComment_success() throws Exception {
		AddOrEditCommentDTO addCommentDto = new AddOrEditCommentDTO("content");
		Comment newComment = new Comment(addCommentDto.getContent());
		Form form = new Form("title", "description", null, 100, 101);
		form.setId(10);
		List<Comment> comments = new ArrayList<>();
		comments.add(newComment);
		form.setComments(comments);
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		when(formService.addComment(eq("1"), eq("10"), eq(addCommentDto))).thenReturn(form);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(addCommentDto);
		String expectedForm = om.writeValueAsString(form);
		mockMvc.perform(patch("/user/1/form/10/comment")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedForm));
	}
	
	@Test
	void testAddComment_fail_Not_Auth() throws Exception {
		AddOrEditCommentDTO addCommentDto = new AddOrEditCommentDTO("content");
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(addCommentDto);
		mockMvc.perform(patch("/user/2/form/10/comment")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testAddComment_fail_BadParam() throws Exception {
		AddOrEditCommentDTO addCommentDto = new AddOrEditCommentDTO("content");
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(addCommentDto);
		//when(formService.addComment(eq("1"), eq("w"), eq(addCommentDto))).thenThrow(BadParameterException.class);
		mockMvc.perform(patch("/user/2/form/w/comment")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testEditComment_success() throws Exception {
		AddOrEditCommentDTO editCommentDto = new AddOrEditCommentDTO("content");
		Comment editComment = new Comment(editCommentDto.getContent());
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		when(formService.editComment(eq("10"), eq(editCommentDto))).thenReturn(editComment);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(editCommentDto);
		String expectedComment = om.writeValueAsString(editComment);
		mockMvc.perform(patch("/user/1/form/10/comment/10")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedComment));
	}
	

	@Test
	void testEditComment_fail_Not_auth() throws Exception {
		AddOrEditCommentDTO editCommentDto = new AddOrEditCommentDTO("content");
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(editCommentDto);
		mockMvc.perform(patch("/user/2/form/10/comment/10")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	

	@Test
	void testEditComment_fail_bad_param() throws Exception {
		AddOrEditCommentDTO editCommentDto = new AddOrEditCommentDTO("content");
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		ObjectMapper om = new ObjectMapper();
		String addCommentDtoBody = om.writeValueAsString(editCommentDto);
		when(formService.editComment(eq("w"), eq(editCommentDto))).thenThrow(BadParameterException.class);
		mockMvc.perform(patch("/user/1/form/2/comment/w")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(addCommentDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testEditFormStatus_success() throws Exception {
		EditFormStatusDTO editFormDto = new EditFormStatusDTO("verified");
		Form form = new Form("title", "description", null, 100, 101);
		FormStatus formStatus = new FormStatus(editFormDto.getStatus());
		form.setFormStatus(formStatus);
		when(formService.editFormStatusAdmin(eq("10"), eq(editFormDto))).thenReturn(form);
		ObjectMapper om = new ObjectMapper();
		String editFormDtoBody = om.writeValueAsString(editFormDto);
		String expectedForm = om.writeValueAsString(form);
		mockMvc.perform(patch("/admin/1/form/10")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(editFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
				.andExpect(MockMvcResultMatchers.content().json(expectedForm));
	}
	
	@Test
	void testEditFormStatus_fail_BadParam() throws Exception {
		EditFormStatusDTO editFormDto = new EditFormStatusDTO("verified");
		
		ObjectMapper om = new ObjectMapper();
		String editFormDtoBody = om.writeValueAsString(editFormDto);
		when(formService.editFormStatusAdmin(eq("w"), eq(editFormDto))).thenThrow(BadParameterException.class);
		mockMvc.perform(patch("/admin/1/form/w")
				.accept(MediaType.APPLICATION_JSON_VALUE)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(editFormDtoBody))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}
	
	@Test
	void testDeleteComment_Success() throws Exception {
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc.perform(delete("/user/1/form/10/comment/11"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
	}
	
	@Test
	void testDeleteComment_Fail_Not_Auth() throws Exception {
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc.perform(delete("/user/2/form/10/comment/11"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
	
	
	@Test
	void testDeleteForm_Success() throws Exception {
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc.perform(delete("/user/1/form/10"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
	}
	
	@Test
	void testDeleteForm_Fail_Not_Auth() throws Exception {
		
		when(request.getSession(eq(false))).thenReturn(session);
		when(session.getAttribute(eq("currentUser"))).thenReturn(user);
		mockMvc.perform(delete("/user/2/form/10"))
		.andExpect(MockMvcResultMatchers.status().is4xxClientError());
		
	}
	

	
	@Test
	void testDeleteCommentAdmin_Success() throws Exception {
		
		mockMvc.perform(delete("/admin/1/form/10/comment/11"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
	}
	
	@Test
	void testDeleteFormAdmin_Success() throws Exception {
		
		mockMvc.perform(delete("/admin/1/form/10"))
		.andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
		
	}

	

	
	
	
	
}
