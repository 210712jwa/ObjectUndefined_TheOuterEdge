package com.revature.dto;

import java.sql.Timestamp;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class AddOrEditCommentDTO {

	private String content;

	public AddOrEditCommentDTO(String content) {
		super();
		this.content = content;
	}
	
}
