package com.revature.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(* com.revature.dao.*.*(..))")
	public void logDaoMethodsBefore(JoinPoint myJointPoint) {
		MethodSignature methodSignature = (MethodSignature) myJointPoint.getSignature();
		String methodName = methodSignature.getName();
		logger.info("DAO method " + methodName + " is about to be executed");
	}
	
	@AfterReturning(pointcut = "execution(* com.revature.dao.*.*(..))", returning = "myObject")
	public void logDaoMethodAfterReturning(JoinPoint myJointPoint, Object myObject) {
		MethodSignature methodSignature = (MethodSignature) myJointPoint.getSignature();
		String methodName = methodSignature.getName();
		logger.info("DAO method " + methodName + " successfully returned" + myObject);
	}
}
