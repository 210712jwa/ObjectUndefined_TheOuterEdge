package com.revature.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dao.FormDAO;
import com.revature.dto.AddFormDTO;
import com.revature.model.Form;

@Service
public class FormService {

	private FormDAO formDao;
	
	@Autowired
	public FormService(FormDAO formDao) {
		this.formDao = formDao;
	}
	
	
	public Form addForm(AddFormDTO addFormDto) {
		return null;
		
	}
}
