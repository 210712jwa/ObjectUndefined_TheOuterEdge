package com.revature.dto;

import com.revature.model.FormStatus;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class AddUserDTO {

	private String firstName;
	
	private String LastName;
	
	private String email;
	
	private String username;
	
	private String Password;

	public AddUserDTO(String firstName, String lastName, String email, String username, String password) {
		super();
		this.firstName = firstName;
		LastName = lastName;
		this.email = email;
		this.username = username;
		Password = password;
	}
	
	
}
