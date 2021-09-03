package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

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
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "message", nullable = false, length = 150)
	private String message;
	
	@Column(name = "submitted_time", nullable = false)
	private Timestamp submitted;
	
	@ManyToOne
	@JoinColumn(name = "author", nullable = false)
	private Users author;
	
	@ManyToOne
	@JoinColumn(name = "form", nullable = false)
	private Form form;
	
	
	@Transient
	private long now = System.currentTimeMillis();
	@Transient
	private Timestamp submitTimestamp = new Timestamp(now);

	public Comment(String message) {
		super();
		this.message = message;
		this.submitted = submitTimestamp;
	}
	
	
	
	

}
