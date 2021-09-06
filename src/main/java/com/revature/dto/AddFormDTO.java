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

	public AddFormDTO(String title, String description, Timestamp eventTime, byte[] image) {
		super();
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
		this.image = image;
	}
	
	

}
