package com.dmm.task.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dmm.task.service.CalendarService;

@Controller
public class CalendarController {
	@Autowired
	private CalendarService calendarS;

	@GetMapping("/home")
	public String showCalendar(Model model) {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int month = now.getMonthValue();

		List<List<LocalDate>> calendar = calendarS.generateCalendar(year, month);
		model.addAttribute("calendar", calendar);
		model.addAttribute("currentMonth", now.getMonth());
		model.addAttribute("urrentYear", year);
		
		return "home";
	}
}
