package com.dmm.task.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.service.CalendarService;

@Controller
public class CalendarController {
	@Autowired
	private CalendarService calendarService;

	@GetMapping("/main")
	public String getCalendar(Model model, @RequestParam(required = false) String date) {
		LocalDate currentDate = (date == null) ? LocalDate.now() : LocalDate.parse(date);
		model.addAttribute("matrix", calendarService.generateCalendar(currentDate));
		model.addAttribute("month", currentDate.getYear() + "-" + currentDate.getMonthValue());
		model.addAttribute("prev", currentDate.minusMonths(1));
		model.addAttribute("next", currentDate.plusMonths(1));
		// タスクを取得するコードを追加
		model.addAttribute("tasks", calendarService.getTasksForCalendar(currentDate));
		return "main";
	}
}
