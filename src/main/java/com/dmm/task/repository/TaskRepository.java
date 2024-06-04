package com.dmm.task.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
	List<Task> findByDate(LocalDate date);
}
