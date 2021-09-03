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
@Table(name = "form_status")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class FormStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "form_status", nullable = false, length = 20)
	private String formStatus;

	public FormStatus(String formStatus) {
		super();
		this.formStatus = formStatus;
	}
	
}
