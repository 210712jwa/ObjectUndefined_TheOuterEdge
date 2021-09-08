package com.revature.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode @ToString
public class EditFormStatusDTO {
	
	private String Status;

	public EditFormStatusDTO(String status) {
		super();
		Status = status;
	}
	
	

}
