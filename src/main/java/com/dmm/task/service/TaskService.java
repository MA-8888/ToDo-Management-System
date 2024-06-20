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

	private Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date, String user) {
		// 月初と月末を計算
		YearMonth currentYearMonth = YearMonth.from(date);
		LocalDate startOfMonth = currentYearMonth.atDay(1);
		LocalDate endOfMonth = currentYearMonth.atEndOfMonth();

		LocalDateTime startDateTime = startOfMonth.atStartOfDay();
		LocalDateTime endDateTime = endOfMonth.plusDays(1).atStartOfDay();

		List<Tasks> tasks;
		if (user == null) {
			tasks = repo.findByDateBetween(startDateTime, endDateTime);
		} else {
			tasks = repo.findByDateBetween(startDateTime, endDateTime, user);
		}

		// タスクを日付ごとにマップに変換
		LinkedMultiValueMap<LocalDate, Tasks> tasksMap = new LinkedMultiValueMap<>();
		tasks.forEach(task -> tasksMap.add(task.getDate().toLocalDate(), task));

		return tasksMap;
	}

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date) {
		return getTasksForCalendar(date, null);
	}

	public Map<LocalDate, List<Tasks>> getLimitedTasksForCalendar(LocalDate date, String user) {
		return getTasksForCalendar(date, user);
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
