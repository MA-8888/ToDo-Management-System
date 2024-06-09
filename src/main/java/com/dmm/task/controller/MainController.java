package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.service.TaskService;

@Controller
public class MainController {

	@Autowired
	private TaskService Service;

	@GetMapping("/main")
	public String getCalendar(Model model, @RequestParam(required = false) String date) {
		// 指定された日付または現在の日付を基準日とする
		LocalDate currentDate = date != null ? LocalDate.parse(date) : LocalDate.now();
		YearMonth currentYearMonth = YearMonth.from(currentDate);
		// その月の1日を取得
		LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
		LocalDate lastOfMonth = currentYearMonth.atEndOfMonth();
		// その月の1日の曜日を取得
		DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

		LocalDate startDate = firstDayOfMonth.minusDays(firstDayOfWeek.getValue());
		LocalDate endDate = lastOfMonth.with(DayOfWeek.SATURDAY);

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
		if (!week.isEmpty()) {
			month.add(new ArrayList<>(week));
		}

		// カレンダーとタスクデータをモデルに追加
		model.addAttribute("matrix", month);
		model.addAttribute("month", currentDate.getYear() + " - " + currentDate.getMonthValue());
		model.addAttribute("prev", currentDate.minusMonths(1));
		model.addAttribute("next", currentDate.plusMonths(1));
		model.addAttribute("tasks", Service.getTasksForCalendar(currentDate));

		return "main";
	}
}
