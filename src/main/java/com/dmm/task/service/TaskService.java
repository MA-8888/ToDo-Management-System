package com.dmm.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository repo;

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		boolean isAdmin = authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
		// 月初と月末を計算
		LocalDate start = date.withDayOfMonth(1);
		LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

		List<Tasks> tasks;
		if (isAdmin) {
			// Admin: Get all tasks between the start and end dates
			tasks = repo.findByDateBetween(start, end);
		} else {
			// User: Get tasks for the specific user between the start and end dates
			LocalDateTime startDateTime = start.atStartOfDay();
			LocalDateTime endDateTime = end.atTime(23, 59, 59);
			tasks = repo.findByDateBetween(startDateTime, endDateTime, username);
		}

		// 月のすべてのタスクを取得
		//		List<Tasks> tasks = repo.findByDateBetween(start, end);

		// タスクを日付ごとにマップに変換
		return tasks.stream()
				.collect(Collectors.groupingBy(task -> task.getDate().toLocalDate()));
	}

	public Tasks createTask(String title, String name, String text, LocalDateTime date, boolean done) {
		Tasks task = new Tasks();
		task.setTitle(title);
		task.setName(name);
		task.setText(text);
		task.setDate(date);
		task.setDone(done);
		return repo.save(task);
	}
}
