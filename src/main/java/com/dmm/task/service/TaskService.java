package com.dmm.task.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Task;
import com.dmm.task.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;

	public Map<LocalDate, List<Task>> getTasksForCalendar(LocalDate date) {
		// 月初と月末を計算
		LocalDate start = date.withDayOfMonth(1);
		LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

		// 月のすべてのタスクを取得
		List<Task> tasks = taskRepository.findByDateBetween(start, end);

		// タスクを日付ごとにマップに変換
		return tasks.stream().collect(Collectors.groupingBy(Task::getDate));
	}
}
