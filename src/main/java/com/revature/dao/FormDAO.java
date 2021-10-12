package com.revature.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dto.AddFormDTO;
import com.revature.dto.AddOrEditCommentDTO;
import com.revature.dto.EditFormStatusDTO;
import com.revature.model.Comment;
import com.revature.model.Form;
import com.revature.model.FormStatus;
import com.revature.model.Users;

@Repository
public class FormDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Transactional
	public Form addForm(int userId, AddFormDTO addFormDto) {
		Session session = sessionFactory.getCurrentSession();
		Form form = new Form(addFormDto.getTitle(), addFormDto.getDescription());
		
		Users user = session.get(Users.class, userId);
		FormStatus formStatus = session.get(FormStatus.class, 1);
		form.setAuthor(user);
		form.setFormStatus(formStatus);
		session.persist(form);
		return form;
	}
	
	@Transactional
	public Form addFormImage(int formId, byte[] storedimage) {
		Session session = sessionFactory.getCurrentSession();
		Form form = session.get(Form.class, formId);
		form.setImage(storedimage);
		session.saveOrUpdate(form);
		return form;
	}

	@Transactional
	public List<Form> getAllForm() {
		Session session = sessionFactory.getCurrentSession();

		List<Form> form = session.createQuery("FROM Form f ORDER BY f.submitted desc").getResultList();

		return form;
	}

	@Transactional
	public List<Form> getFormByTitle(String title) {
		Session session = sessionFactory.getCurrentSession();
		String formhql = "From Form f WHERE lower(f.title) like lower(:title) ORDER BY f.submitted desc";
		List<Form> form = session.createQuery(formhql).setParameter("title", "%" + title + "%").getResultList();
		return form;	
	}
	
	@Transactional
	public Form editFormById(int formId, AddFormDTO formDto) {
		Session session = sessionFactory.getCurrentSession();
		String formHql = "FROM Form f WHERE f.id = :id";
		Form form = (Form) session.createQuery(formHql).setParameter("id", formId).getSingleResult();
		form.setTitle(formDto.getTitle());
		form.setDescription(formDto.getDescription());
		//form.setImage(formDto.getImage());
		form.setEventTime(formDto.getEventTime());
		session.saveOrUpdate(form);
		return form;
	}
	
	@Transactional
	public Form editFormStatusAdmin(int formId, EditFormStatusDTO formStatusDto) {
		Session session = sessionFactory.getCurrentSession();
		String formHql = "FROM Form f WHERE f.id = :id";
		Form form = (Form) session.createQuery(formHql).setParameter("id", formId).getSingleResult();
		String statusHql = "FROM FromStatus fs WHERE fs.status = :formStatus";
		FormStatus newFormStatus = (FormStatus) session.createQuery(statusHql).setParameter("formStatus", formStatusDto.getStatus()).getSingleResult();
		form.setFormStatus(newFormStatus);
		session.saveOrUpdate(form);
		return form;
	}
	
	@Transactional
	public Form addComment(int userId, int formId, AddOrEditCommentDTO commentDto) {
		Session session = sessionFactory.getCurrentSession();
		String formHql = "FROM Form f WHERE f.id = :id";
		Form form = (Form) session.createQuery(formHql).setParameter("id", formId).getSingleResult();
		Comment comment = new Comment(commentDto.getContent());
		Users user = session.get(Users.class, userId);
		comment.setAuthor(user);
		List<Comment> formCommentList = form.getComments();
		formCommentList.add(comment);
		form.setComments(formCommentList);
		session.persist(form);
		return form;
	}
	
	@Transactional
	public Comment editComment(int commentId, AddOrEditCommentDTO commentDto) {
		Session session = sessionFactory.getCurrentSession();
		String commentHql = "FROM Comment c WHERE c.id = :id";
		Comment comment = (Comment) session.createQuery(commentHql).setParameter("id", commentId).getSingleResult();
		session.saveOrUpdate(comment);
		return comment;
	}


	@Transactional
	public void deleteComment(int commentId) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "DELETE FROM Comment c WHERE c.id = :id";
		int recordUpdate = session.createQuery(hql).setParameter("id", commentId).executeUpdate();
		if(recordUpdate != 1) {
			throw new HibernateException("Fail to delete comment");
		}
	}
	
	public void deleteAllCommentOfAForm(int formId) {
		Session session = sessionFactory.getCurrentSession();
		int recordUpdate = session.createSQLQuery("DELETE FROM comment WHERE comments = :formId").setParameter("formId", formId).executeUpdate();
	}
	
	@Transactional
	public void deleteForm(int formId) {
		Session session = sessionFactory.getCurrentSession();
		deleteAllCommentOfAForm(formId);
		int recordUpdate = session.createQuery("DELETE FROM Form f WHERE f.id = :id").setParameter("id", formId).executeUpdate();
		if(recordUpdate != 1) {
			throw new HibernateException("Fail to delete form");
		}
	}
	
	
	

}
