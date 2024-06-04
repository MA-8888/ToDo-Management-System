package com.dmm.task.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Task;
import com.dmm.task.repository.TaskRepository;

@Service
public class CalendarService {

	@Autowired
	private TaskRepository taskRepository;

	public List<List<LocalDate>> generateCalendar(LocalDate date) {
		List<List<LocalDate>> calendar = new ArrayList<>();
		List<LocalDate> week = new ArrayList<>();

		LocalDate firstDayOfMonth = date.withDayOfMonth(1);
		int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();
		LocalDate startDate = firstDayOfMonth.minusDays(dayOfWeekValue);

		LocalDate day = startDate;
		while (day.isBefore(firstDayOfMonth.plusMonths(1))) {
			week.add(day);
			if (week.size() == 7) {
				calendar.add(new ArrayList<>(week));
				week.clear();
			}
			day = day.plusDays(1);
		}

		if (!week.isEmpty()) {
			calendar.add(new ArrayList<>(week));
		}

		return calendar;
	}

	public Map<LocalDate, List<Task>> getTasksForCalendar(LocalDate date) {
		Map<LocalDate, List<Task>> tasks = new HashMap<>();
		List<List<LocalDate>> calendar = generateCalendar(date);

		for (List<LocalDate> week : calendar) {
			for (LocalDate day : week) {
				List<Task> tasksForDay = taskRepository.findByDate(day);
				tasks.put(day, tasksForDay);
			}
		}

		return tasks;
	}
}
