package com.revature.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.model.Form;

@Repository
public class FormDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public Form addForm() {
		return null;
	}
	

}
