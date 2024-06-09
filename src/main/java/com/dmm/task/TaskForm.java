package com.dmm.task;

import java.time.LocalDate;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class TaskForm {
	@Size(min = 1, max = 50)
	private String title;
	
	@Size(min  = 1, max = 500)
	private String text;
	
	private LocalDate date;
}
