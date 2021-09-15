package com.revature.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.dao.FormDAO;
import com.revature.dto.AddFormDTO;
import com.revature.dto.AddOrEditCommentDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.exception.BadParameterException;
import com.revature.model.Comment;
import com.revature.model.Form;

@Service
public class FormService {

	private FormDAO formDao;

	@Autowired
	public FormService(FormDAO formDao) {
		this.formDao = formDao;
	}

	public Form addForm(String userId, AddFormDTO addFormDto) throws BadParameterException {
		if (addFormDto.getTitle().trim().equals("")) {
			throw new BadParameterException("title cannot be blank");
		}
		try {
			int uId = Integer.parseInt(userId);
			Form form = formDao.addForm(uId, addFormDto);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("User id is not an integer");
		}

	}
	
	public Form addImage(String formId, MultipartFile file) throws IOException, BadParameterException {
		InputStream fileContent = file.getInputStream();
		
		byte[] fileByte = new byte[(int) file.getSize()];
		int nRead;
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			int reimbId = Integer.parseInt(formId);
			while ((nRead = fileContent.read(fileByte, 0, fileByte.length)) != -1) {
				buffer.write(fileByte, 0, nRead);
			}
			byte[] storeImageByte = buffer.toByteArray(); //file.getBytes();
			Form form = formDao.addFormImage(reimbId, storeImageByte);
			return form;
		}catch(NumberFormatException e) {
			throw new BadParameterException("Form id is not valid integer");
		}
	}

	public List<Form> getAllForm() {
		List<Form> form = formDao.getAllForm();
		return form;
	}

	public List<Form> getFormByTitle(String title) throws BadParameterException {
		if (title.trim().equals("")) {
			throw new BadParameterException("Search cannot be blank");
		}
		List<Form> form = formDao.getFormByTitle(title);
		return form;
	}

	public Form editFormById(String formId, AddFormDTO editFormDto) throws BadParameterException {
		if (editFormDto.getTitle().trim().equals("")) {
			throw new BadParameterException("title cannot be blank");
		}
		try {
			int fId = Integer.parseInt(formId);
			Form form = formDao.editFormById(fId, editFormDto);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("Form id is not an integer");
		}
	}

	public Form editFormStatusAdmin(String formId, EditFormStatusDTO formStatusDto) throws BadParameterException {
		if (!formStatusDto.getStatus().equalsIgnoreCase("verified")) {
			throw new BadParameterException("Not an acceptance form status");
		}
		try {
			int fId = Integer.parseInt(formId);
			Form form = formDao.editFormStatusAdmin(fId, formStatusDto);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("form id is not an integer");
		}

	}

	public Form addComment(String userId, String formId, AddOrEditCommentDTO commentDto) throws BadParameterException {
		if(commentDto.getContent().trim().equals("")) {
			throw new BadParameterException("Comment cannot be blank");
		}
		try {
			int uId = Integer.parseInt(userId);
			int fId = Integer.parseInt(formId);
			Form form = formDao.addComment(uId,fId, commentDto);
			return form;
		}catch(NumberFormatException e) {
			throw new BadParameterException("Form id or user id is not an valid integer");
		}
		
	}
	
	public Comment editComment(String commentId, AddOrEditCommentDTO commentDto) throws BadParameterException {
		if(commentDto.getContent().trim().equals("")) {
			throw new BadParameterException("Comment cannot be blank");
		}
		try {
			int cId= Integer.parseInt(commentId);
			Comment comment = formDao.editComment(cId, commentDto);
			return comment;
		}catch(NumberFormatException e) {
			throw new BadParameterException("Comment id is not an valid integer");
		}
	}
	
	public void deleteComment(String commentId) throws BadParameterException {
		try {
			int cId= Integer.parseInt(commentId);
			formDao.deleteComment(cId);
		}catch(NumberFormatException e) {
			throw new BadParameterException("Comment id is not an valid integer");
		}
	}
	
	public void deleteForm(String formId) throws BadParameterException {
		try {
			int fId = Integer.parseInt(formId);
			formDao.deleteForm(fId);
		} catch (NumberFormatException e) {
			throw new BadParameterException("Form id is not an integer");
		}

	}
	

}
