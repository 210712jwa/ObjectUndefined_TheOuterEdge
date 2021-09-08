package com.revature.aspect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.revature.dto.MessageDTO;
import com.revature.model.Users;

@Aspect
@Component
public class SecurityAspect {

	@Autowired
	private HttpServletRequest request;

	@Around("@annotation(com.revature.annotation.UserProtected)")
	public Object userLoggedInOnlyProtector(ProceedingJoinPoint myProceedingJoinPoint) throws Throwable {

		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("currentUser") == null) {
			return ResponseEntity.status(401)
					.body(new MessageDTO("You are not authorized to access this endpoint. You must be logged in."));
		}

		Object returnValue = myProceedingJoinPoint.proceed();
		return returnValue;
	}

	@Around("@annotation(com.revature.annotation.AdminProtected)")
	public Object adminOnlyProtector(ProceedingJoinPoint myProceedingJoinPoint) throws Throwable {
		HttpSession session = request.getSession(false);
		if(session == null) {
			return ResponseEntity.status(401).body(new MessageDTO("You are not authorized to access this endpoint. You must be login or an admin."));
		}
		if (session != null) {
			Users user = (Users) session.getAttribute("currentUser");
			if (user == null || user.getUserRole().getUserRole().equals("regular user")) {
				return ResponseEntity.status(401).body(new MessageDTO("You are not authorized to access this endpoint. You must be login or an admin."));
			}
		}
		Object returnValue = myProceedingJoinPoint.proceed();
		return returnValue;
	}

}
