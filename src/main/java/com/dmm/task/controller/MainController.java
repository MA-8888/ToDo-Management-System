package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.service.AccountUserDetails;
import com.dmm.task.service.TaskService;

@Controller
public class MainController {

	@Autowired
	private TaskService service;

	@GetMapping("/main")
	public String getCalendar(Model model, @AuthenticationPrincipal AccountUserDetails user,
			@RequestParam(required = false) String date) {
		// 指定された日付または現在の日付を基準日とする
		LocalDate currentDate = date != null ? LocalDate.parse(date) : LocalDate.now();
		YearMonth currentYearMonth = YearMonth.from(currentDate);
		LocalDate firstOfMonth = currentYearMonth.atDay(1);
		LocalDate lastOfMonth = currentYearMonth.atEndOfMonth();

		// 先月分の日付を含むように調整
		LocalDate startDate = firstOfMonth.with(DayOfWeek.SUNDAY);
		if (startDate.isAfter(firstOfMonth)) {
			startDate = startDate.minusWeeks(1);
		}

		// 来月分の日付を含むように調整
		LocalDate endDate = lastOfMonth.with(DayOfWeek.SATURDAY);
		if (endDate.isBefore(lastOfMonth)) {
			endDate = endDate.plusWeeks(1);
		}

		List<List<LocalDate>> month = new ArrayList<>();
		List<LocalDate> week = new ArrayList<>();

		LocalDate current = startDate;
		while (!current.isAfter(endDate)) {
			week.add(current);
			if (week.size() == 7) {
				month.add(new ArrayList<>(week));
				week.clear();
			}
			current = current.plusDays(1);
		}

		// カレンダーとタスクデータをモデルに追加
		model.addAttribute("matrix", month);
		model.addAttribute("month", currentDate.getYear() + "年" + " - " + currentDate.getMonthValue() + "月");
		model.addAttribute("prev", currentDate.minusMonths(1));
		model.addAttribute("next", currentDate.plusMonths(1));
		model.addAttribute("tasks", service.getTasksForCalendar(currentDate, user));
		return "main";
	}
}
