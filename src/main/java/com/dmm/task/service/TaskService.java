package com.dmm.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository repo;

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date) {

		// 月初と月末を計算
		LocalDateTime start = date.atStartOfDay();
		LocalDateTime end = date.atTime(0, 0);

		List<Tasks> tasks = repo.findByDateBetween(start, end);

		// タスクを日付ごとにマップに変換
		return tasks.stream()
				.collect(Collectors.groupingBy(task -> task.getDate().toLocalDate()));
	}

	public Tasks createTask(TaskForm form) {
		Tasks task = new Tasks();
		task.setTitle(form.getTitle());
		task.setName(form.getName());
		task.setText(form.getText());
		task.setDone(form.isDone());
		LocalDateTime dateTime = form.getDate().atStartOfDay();
		task.setDate(dateTime);
		return repo.save(task);
	}
}

//@Service
//public class TaskService {
//
//	@Autowired
//	private TasksRepository repo;
//	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date) {
//
//		// 月初と月末を計算
//		LocalDateTime start = date.atStartOfDay();
//		LocalDateTime end = date.atTime(0,0);
//
//		List<Tasks> tasks = repo.findByDateBetween(start, end);
//
//		// タスクを日付ごとにマップに変換
//		return tasks.stream()
//				.collect(Collectors.groupingBy(task -> task.getDate().toLocalDate()));
//	}
//
//	public Tasks createTask(TaskForm form) {
//		Tasks task = new Tasks();
//		task.setTitle(form.getTitle());
//		task.setName(form.getName());
//		task.setText(form.getText());
//		task.setDone(form.isDone());
//		LocalDateTime dateTime = form.getDate().atStartOfDay();
//		task.setDate(dateTime);
//		return repo.save(task);
//	}
//}
