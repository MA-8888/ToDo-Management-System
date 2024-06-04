package com.dmm.task.entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.config.Task;

public class Calendar {
	public interface TaskRepository extends JpaRepository<Task, Long> {
	    List<Task> findByUserAndDate(Users user, LocalDate date);
	    List<Task> findByDate(LocalDate date);
	}
}
