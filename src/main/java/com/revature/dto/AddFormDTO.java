package com.revature.dto;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class AddFormDTO {
	
	private String title;
	
	private String description;
	
	private Timestamp eventTime;
	
	private byte[] image;
	

}
