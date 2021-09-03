package com.revature.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"password"})
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;
	
	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;
	
	@Column(name = "email", nullable = false, length = 150)
	private String email;
	
	@Column(name = "username", nullable = false, length = 50)
	private String username;
	
	@Column(name = "password", nullable = false, length = 50)
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	@ManyToOne
	@JoinColumn(name = "user_role", nullable = false)
	private UserRole userRole;

	public Users(String firstName, String lastName, String email, String username, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
	}
	
}
