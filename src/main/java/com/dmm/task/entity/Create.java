package com.dmm.task.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Create {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	private String title;

	@Column(length = 1000)
	private String text;

	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private LocalDate date;

//	private boolean done;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private Users user;
}
