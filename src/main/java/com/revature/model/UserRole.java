package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "user_role")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class UserRole {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "user_role", nullable = false, length = 20)
	private String userRole;

	public UserRole(String userRole) {
		super();
		this.userRole = userRole;
	}
	
}
