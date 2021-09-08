package com.revature.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "comment")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "content", nullable = false, length = 200)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "author", nullable = false)
	private Users author;

	@Column(name = "likes")
	private int likes;
	
	@Column(name = "dislikes")
	private int dislikes;

	public Comment(String content) {
		super();
		this.content = content;
	}
	
}
