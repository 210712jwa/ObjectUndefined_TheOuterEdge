package com.revature.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "forms")
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
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "time_submitted", nullable = false)
	private Timestamp submitted;
	
	@Column(name = "event_time")
	private Timestamp eventTime;
	
	@Column(name = "image", columnDefinition = "LONGBlob")
	@Lob
	private byte[] image;
	
	@Column(name = "latitude")
	private double latitude;
	
	@Column(name = " longitude")
	private double longitude;
	
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
	
	@ManyToOne
	@JoinColumn(name = "form_status", nullable = false)
	private FormStatus formStatus;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "comments")
	private List<Comment> comments;
	

	@Transient
	@JsonIgnore
	private long now = System.currentTimeMillis();
	
	@Transient
	@JsonIgnore
	private Timestamp submitTimestamp = new Timestamp(now);

	public Form(String title, String description) {
		super();
		this.title = title;
		this.description = description;
		this.submitted = submitTimestamp;
	}

	public Form(String title, String description, Timestamp eventTime, double latitude, double longitude) {
		super();
		this.title = title;
		this.description = description;
		this.eventTime = eventTime;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	


	
	
}
