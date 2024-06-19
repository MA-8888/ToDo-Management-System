package com.dmm.task.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;

import com.dmm.task.TaskForm;
import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository repo;

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date, AccountUserDetails userDetails) {
		// 月初と月末を計算
		YearMonth currentYearMonth = YearMonth.from(date);
		LocalDate startOfMonth = currentYearMonth.atDay(1);
		LocalDate endOfMonth = currentYearMonth.atEndOfMonth();

		LocalDateTime startDateTime = startOfMonth.atStartOfDay();
		LocalDateTime endDateTime = endOfMonth.plusDays(1).atStartOfDay();

		List<Tasks> tasks;
		if ("user".equals(userDetails.getUser().getRoleName())) {
			tasks = repo.findByDateBetween(startDateTime, endDateTime);
		} else {
			tasks = repo.findByDateBetween(startDateTime, endDateTime, userDetails.getUsername());
		}

		// タスクを日付ごとにマップに変換
		LinkedMultiValueMap<LocalDate, Tasks> tasksMap = new LinkedMultiValueMap<>();
		tasks.forEach(task -> tasksMap.add(task.getDate().toLocalDate(), task));

		return tasksMap;
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
