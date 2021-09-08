package com.revature.service;

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
		if(addFormDto.getTitle().trim().equals("")) {
			throw new BadParameterException("Name cannot be blank");
		}
		int uId = Integer.parseInt(userId);
		Form form = formDao.addForm(uId, addFormDto);
		return form;
		
	}


	public Form editFormById(String userId, String formId, AddFormDTO editFormDto) throws BadParameterException {
		if(editFormDto.getTitle().trim().equals("")) {
			throw new BadParameterException("Name cannot be blank");
		}
		int uId = Integer.parseInt(userId);
		int fId = Integer.parseInt(formId);
		Form form = formDao.editFormById(uId, fId, editFormDto);
		return null;
	}
}
