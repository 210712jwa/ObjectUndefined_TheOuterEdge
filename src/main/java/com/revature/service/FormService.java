package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.FormDAO;
import com.revature.dto.AddFormDTO;
import com.revature.exception.BadParameterException;
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
			throw new BadParameterException("Name cannot be blank");
		}
		try {
			int uId = Integer.parseInt(userId);
			Form form = formDao.addForm(uId, addFormDto);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("User id is not an integer");
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
			throw new BadParameterException("Name cannot be blank");
		}
		try {
			int fId = Integer.parseInt(formId);
			Form form = formDao.editFormById(fId, editFormDto);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("user id or form id is not an integer");
		}
	}

	public Form editFormStatusAdmin(String formId, String formStatus) throws BadParameterException {
		if (!formStatus.equalsIgnoreCase("verified")) {
			throw new BadParameterException("Not an acceptance form status");
		}
		try {
			int fId = Integer.parseInt(formId);
			Form form = formDao.editFormStatusAdmin(fId, formStatus);
			return form;
		} catch (NumberFormatException e) {
			throw new BadParameterException("form id is not an integer");
		}

	}

	public void deleteForm(String formId) throws BadParameterException {
		try {
			int fId = Integer.parseInt(formId);
			formDao.deleteForm(fId);
		} catch (NumberFormatException e) {
			throw new BadParameterException("user id or form id is not an integer");
		}

	}

}
