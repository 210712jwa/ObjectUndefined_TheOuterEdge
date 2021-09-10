package com.revature.config;

import javax.servlet.annotation.MultipartConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@MultipartConfig
@EnableWebMvc
public class FileUploadConfig {
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multi(){
	     CommonsMultipartResolver resolver=new CommonsMultipartResolver();
	        resolver.setDefaultEncoding("utf-8");
	        return resolver;
	}
}
