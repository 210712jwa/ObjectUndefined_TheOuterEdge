package com.revature.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;


import com.revature.dao.FormDAO;
import com.revature.dto.AddFormDTO;
import com.revature.dto.AddOrEditCommentDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Comment;
import com.revature.model.Form;
import com.revature.model.FormStatus;
import com.revature.model.UserRole;
import com.revature.model.Users;

@ExtendWith(MockitoExtension.class)
public class FormServiceUnitTest {
	
	
	@Mock
	private FormDAO formDao;
	
	@InjectMocks
	private FormService formService;
	
	private Timestamp eventTimestamp;
	
	private Timestamp submitted;
	
	private Users user;
	
	private Users admin;
	private UserRole regularUser;
	private UserRole adminUser;
	
	private FormStatus unverified;
	private FormStatus verified;
	
	private List<Form> mockFormList;
	private List<Form> expectedFormList;
	
	private List<Comment> mockCommentList;
	
	private List<Comment> expectedCommentList;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		eventTimestamp = Timestamp.valueOf("2020-10-22 19:30:23.168");
		submitted = Timestamp.valueOf("2020-09-22 19:30:23.168");
		regularUser = new UserRole("Regular user");
		adminUser = new UserRole("");
		user = new Users("first", "last", "email@email.com", "username", "password");
		user.setUserRole(regularUser);
		user.setId(2);
		admin = new Users("adminFirst", "adminLast", "admin@email.com", "Adminusername", "Adminpassword");
		admin.setUserRole(adminUser);
		verified = new FormStatus("verified");
		unverified = new FormStatus("unverified");
		mockFormList = new ArrayList<>();
		expectedFormList = new ArrayList<>();
		mockCommentList = new ArrayList<>();
		expectedCommentList = new ArrayList<>();
	}

	@AfterEach
	void tearDown() throws Exception {
		mockFormList.clear();
		expectedFormList.clear();
	}
	
	@Test
	void testAddFormPositive_success() throws BadParameterException {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", eventTimestamp, 100, 101);
		Form formMock = new Form(addFormDto.getTitle(), addFormDto.getDescription());
		formMock.setAuthor(user);
		formMock.setEventTime(addFormDto.getEventTime());
		formMock.setFormStatus(unverified);
		formMock.setLatitude(addFormDto.getLatitude());
		formMock.setLongitude(addFormDto.getLongitude());
		formMock.setId(10);
		formMock.setSubmitted(submitted);
		formMock.setNow(0);
		formMock.setSubmitTimestamp(null);
		when(formDao.addForm(eq(10), eq(addFormDto))).thenReturn(formMock);
		Form formActual = formService.addForm("10", addFormDto);
		Form expectForm = new Form("title", "description");
		expectForm.setAuthor(user);
		expectForm.setId(10);
		expectForm.setEventTime(eventTimestamp);
		expectForm.setFormStatus(unverified);
		expectForm.setLatitude(100);
		expectForm.setLongitude(101);
		expectForm.setSubmitted(submitted);
		expectForm.setNow(0);
		expectForm.setSubmitTimestamp(null);
		assertEquals(formActual, expectForm);
	}
	
	@Test
	void testAddForm_title_null() {
		AddFormDTO addFormDto = new AddFormDTO("", "description", eventTimestamp, 100, 101);
		try {
			formService.addForm("10", addFormDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "title cannot be blank");
		}
	}
	
	@Test
	void testAddForm_id_null_unvalid() {
		AddFormDTO addFormDto = new AddFormDTO("title", "description", eventTimestamp, 100, 101);
		try {
			formService.addForm("w", addFormDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "User id is not an integer");
		}
	}
	
	/*
	 * upload image test
	 */
	
	
	@Test
	void testGetAllForm_success() {
		Form formMock1 = new Form("title1", "description1");
		formMock1.setId(10);
		formMock1.setAuthor(user);
		formMock1.setSubmitted(submitted);
		formMock1.setEventTime(eventTimestamp);
		formMock1.setNow(0);
		formMock1.setSubmitTimestamp(null);
		formMock1.setFormStatus(unverified);
		Form formMock2 = new Form("title2", "description2");
		formMock2.setId(11);
		formMock2.setAuthor(user);
		formMock2.setSubmitted(submitted);
		formMock2.setEventTime(eventTimestamp);
		formMock2.setNow(0);
		formMock2.setSubmitTimestamp(null);
		formMock2.setFormStatus(unverified);
		Form formMock3 = new Form("title3", "description3");
		formMock3.setId(12);
		formMock3.setAuthor(user);
		formMock3.setSubmitted(submitted);
		formMock3.setNow(0);
		formMock3.setSubmitTimestamp(null);
		formMock3.setEventTime(eventTimestamp);
		formMock3.setFormStatus(unverified);
		mockFormList.add(formMock1);
		mockFormList.add(formMock2);
		mockFormList.add(formMock3);
		when(formDao.getAllForm()).thenReturn(mockFormList);
		List<Form> mockForms = formService.getAllForm();
		
		Form expected1 = new Form("title1", "description1");
		expected1.setId(10);
		expected1.setAuthor(user);
		expected1.setSubmitted(submitted);
		expected1.setNow(0);
		expected1.setSubmitTimestamp(null);
		expected1.setFormStatus(unverified);
		expected1.setEventTime(eventTimestamp);
		Form expected2 = new Form("title2", "description2");
		expected2.setId(11);
		expected2.setAuthor(user);
		expected2.setSubmitted(submitted);
		expected2.setNow(0);
		expected2.setSubmitTimestamp(null);
		expected2.setFormStatus(unverified);
		expected2.setEventTime(eventTimestamp);
		Form expected3 = new Form("title3", "description3");
		expected3.setId(12);
		expected3.setAuthor(user);
		expected3.setSubmitted(submitted);
		expected3.setNow(0);
		expected3.setSubmitTimestamp(null);
		expected3.setFormStatus(unverified);
		expected3.setEventTime(eventTimestamp);
		expectedFormList.add(expected1);
		expectedFormList.add(expected2);
		expectedFormList.add(expected3);
		assertEquals(mockForms, expectedFormList);
		
	}
	
	@Test
	void testGetFormByTitle_success() throws BadParameterException {
		Form formMock1 = new Form("title1", "description1");
		formMock1.setId(10);
		formMock1.setAuthor(user);
		formMock1.setSubmitted(submitted);
		formMock1.setEventTime(eventTimestamp);
		formMock1.setNow(0);
		formMock1.setSubmitTimestamp(null);
		formMock1.setFormStatus(unverified);
		mockFormList.add(formMock1);
		when(formDao.getFormByTitle(eq("title1"))).thenReturn(mockFormList);
		List<Form> mockForms = formService.getFormByTitle("title1");
		Form formExpected = new Form("title1", "description1");
		formExpected.setId(10);
		formExpected.setAuthor(user);
		formExpected.setSubmitted(submitted);
		formExpected.setNow(0);
		formExpected.setSubmitTimestamp(null);
		formExpected.setFormStatus(unverified);
		formExpected.setEventTime(eventTimestamp);
		expectedFormList.add(formExpected);
		assertEquals(mockForms, expectedFormList);		
	}
	
	@Test
	void testGetFormByTitle_title_null() {
		try {
			formService.getFormByTitle("");
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Search cannot be blank");
		}
	}
	
	@Test
	void testEditForm_success() throws BadParameterException {
		AddFormDTO editFormDto = new AddFormDTO("title", "description", eventTimestamp, 100, 101);
		Form formMock = new Form(editFormDto.getTitle(), editFormDto.getDescription());
		formMock.setAuthor(user);
		formMock.setEventTime(editFormDto.getEventTime());
		formMock.setFormStatus(unverified);
		formMock.setLatitude(editFormDto.getLatitude());
		formMock.setLongitude(editFormDto.getLongitude());
		formMock.setId(10);
		formMock.setSubmitted(submitted);
		formMock.setNow(0);
		formMock.setSubmitTimestamp(null);
		when(formDao.editFormById(eq(10), eq(editFormDto))).thenReturn(formMock);
		Form formActual = formService.editFormById("10", editFormDto);
		Form expectForm = new Form("title", "description");
		expectForm.setAuthor(user);
		expectForm.setId(10);
		expectForm.setEventTime(eventTimestamp);
		expectForm.setFormStatus(unverified);
		expectForm.setLatitude(100);
		expectForm.setLongitude(101);
		expectForm.setSubmitted(submitted);
		expectForm.setNow(0);
		expectForm.setSubmitTimestamp(null);
		assertEquals(formActual, expectForm);	
	}
	
	@Test
	void testEditForm_title_null() {
		try {
			AddFormDTO editFormDto = new AddFormDTO("", "description", eventTimestamp, 100, 101);
			formService.editFormById("10", editFormDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "title cannot be blank");
		}
	}
	
	@Test
	void testEditForm_id_not_valid() {
		try {
			AddFormDTO editFormDto = new AddFormDTO("title", "description", eventTimestamp, 100, 101);
			formService.editFormById("d", editFormDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Form id is not an integer");
		}
	}
	
	@Test
	void testEditFormStatusAdmin_success() throws BadParameterException {
		EditFormStatusDTO editStatusDto = new EditFormStatusDTO("verified");
		FormStatus verified1 = new FormStatus(editStatusDto.getStatus());
		Form formMock1 = new Form("title1", "description1");
		formMock1.setId(10);
		formMock1.setAuthor(user);
		formMock1.setSubmitted(submitted);
		formMock1.setEventTime(eventTimestamp);
		formMock1.setNow(0);
		formMock1.setSubmitTimestamp(null);
		formMock1.setFormStatus(verified1);
		formMock1.setVerifier(admin);
		when(formDao.editFormStatusAdmin(eq(10), eq(editStatusDto))).thenReturn(formMock1);
		Form formActual = formService.editFormStatusAdmin("10", editStatusDto);
		Form formExpected1 = new Form("title1", "description1");
		formExpected1.setId(10);
		formExpected1.setAuthor(user);
		formExpected1.setSubmitted(submitted);
		formExpected1.setEventTime(eventTimestamp);
		formExpected1.setNow(0);
		formExpected1.setSubmitTimestamp(null);
		formExpected1.setFormStatus(verified);
		formExpected1.setVerifier(admin);
		assertEquals(formActual, formExpected1);
		
	}
	
	@Test
	void testEditFormStatusAdmin_not_status_type() {
		try {
			EditFormStatusDTO editStatusDto = new EditFormStatusDTO("random");
			formService.editFormStatusAdmin("10", editStatusDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Not an acceptance form status");
		}
	}
	
	@Test
	void testEditFormStatusAdmin_id_not_valid() {
		try {
			EditFormStatusDTO editStatusDto = new EditFormStatusDTO("verified");
			formService.editFormStatusAdmin("", editStatusDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "form id is not an integer");
		}
	}
	
	@Test
	void testAddComment_success() throws BadParameterException {
		AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("content");
		Comment newMockComment = new Comment(commentDto.getContent()); 
		newMockComment.setAuthor(user);
		newMockComment.setId(10);
		mockCommentList.add(newMockComment);
		Form formMock1 = new Form("title1", "description1");
		formMock1.setId(10);
		formMock1.setAuthor(user);
		formMock1.setSubmitted(submitted);
		formMock1.setEventTime(eventTimestamp);
		formMock1.setNow(0);
		formMock1.setSubmitTimestamp(null);
		formMock1.setFormStatus(unverified);
		formMock1.setComments(mockCommentList);
		when(formDao.addComment(eq(10), eq(11), eq(commentDto))).thenReturn(formMock1);
		Form actualForm = formService.addComment("10", "11", commentDto);
		Comment newExpectedComment = new Comment("content");
		newExpectedComment.setAuthor(user);
		newExpectedComment.setId(10);
		expectedCommentList.add(newExpectedComment);
		Form expectedForm = new Form("title1", "description1");
		expectedForm.setId(10);
		expectedForm.setAuthor(user);
		expectedForm.setSubmitted(submitted);
		expectedForm.setNow(0);
		expectedForm.setSubmitTimestamp(null);
		expectedForm.setFormStatus(unverified);
		expectedForm.setEventTime(eventTimestamp);
		expectedForm.setComments(expectedCommentList);
		assertEquals(actualForm, expectedForm);
	}
	
	@Test
	void testAddComment_title_null() {
		try {
			AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("");
			formService.addComment("10", "11", commentDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Comment cannot be blank");
		}
	}
	
	@Test
	void testAddComment_form_id_not_valid() {
		try {
			AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("content");
			formService.addComment("10", "", commentDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Form id or user id is not an valid integer");
		}
	}
	
	@Test
	void testEditComment_success() throws BadParameterException {
		AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("new content");
		Comment newMockComment = new Comment("content"); 
		newMockComment.setId(10);
		newMockComment.setAuthor(user);
		newMockComment.setContent(commentDto.getContent());
		when(formDao.editComment(10, commentDto)).thenReturn(newMockComment);
		Comment actualComment = formService.editComment("10", commentDto);
		Comment expectedComment = new Comment("new content"); 
		expectedComment.setId(10);
		expectedComment.setAuthor(user);
		assertEquals(actualComment, expectedComment);
	}
	
	@Test
	void testEditomment_title_null() {
		try {
			AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("");
			formService.editComment("10", commentDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Comment cannot be blank");
		}
	}
	
	@Test
	void testEditComment_id_not_valid() {
		try {
			AddOrEditCommentDTO commentDto = new AddOrEditCommentDTO("new content");
			formService.editComment("daw", commentDto);
			fail();
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Comment id is not an valid integer");
		}
	}
	
	@Test
	void testDeleteComment_success() throws BadParameterException {
		Mockito.doNothing().when(formDao).deleteComment(eq(10));
		formService.deleteComment("10");
		Mockito.verify(formDao).deleteComment(eq(10));
	}
	
	@Test
	void testDeleteComment_id_not_valid() {
		try {
			formService.deleteComment("da");
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Comment id is not an valid integer");
		}
	}
	
	@Test
	void testDeleteForm_success() throws BadParameterException {
		Mockito.doNothing().when(formDao).deleteForm(eq(10));
		formService.deleteForm("10");
		Mockito.verify(formDao).deleteForm(eq(10));;
	}
	
	@Test
	void testDeleteForm_id_not_valid() {
		try {
			formService.deleteForm("da");
		}catch(BadParameterException e) {
			assertEquals(e.getMessage(), "Form id is not an integer");
		}
	}
}
