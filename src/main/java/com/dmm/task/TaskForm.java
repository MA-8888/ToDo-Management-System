package com.dmm.task;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TaskForm {
	@Size(min = 1, max = 50)
	private String title;

	@Size(min = 1, max = 500)
	private String text;
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;
	private boolean done;
}
