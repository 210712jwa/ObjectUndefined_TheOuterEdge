package com.revature.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
public class Form {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title", nullable = false, length = 100)
	private String title;
	
	@Column(name = "description", length = 500)
	private String description;
	
	@Column(name = "time_submitted", nullable = false)
	private Timestamp submitted;
	
	@Column(name = "event_time")
	private Timestamp eventTime;
	
	@Column(name = "image", columnDefinition = "LONGBlob")
	@Lob
	private byte[] image;
	
	@ManyToOne
	@JoinColumn(name = "author", nullable = false)
	private Users author;
	
	@Column(name = "likes")
	private int likes;
	
	@Column(name = "dislikes")
	private int dislikes;
	
	@ManyToOne
	@JoinColumn(name = "verifier")
	private Users verifier;
	
	@Column(name = "verify_staus", nullable = false)
	private FormStatus verifyStatus;
	
//	@OneToMany
//	@JoinColumn(name = "comments")
//	private List<Comment> comments;
	
	
	@Transient
	private long now = System.currentTimeMillis();
	@Transient
	private Timestamp submitTimestamp = new Timestamp(now);

	public Form(String title, String description, Timestamp eventTime, int likes, int dislikes) {
		super();
		this.title = title;
		this.description = description;
		this.submitted = submitTimestamp;
		this.eventTime = eventTime;
		this.likes = likes;
		this.dislikes = dislikes;
	}
	
}
