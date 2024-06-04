package com.dmm.task.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dmm.task.service.TaskService;

@Controller
public class TaskController {

	@Autowired
	private TaskService Service;

	@GetMapping("/main")
	public String getCalendar(Model model, @RequestParam(required = false) String date) {
		// 指定された日付または現在の日付を基準日とする
		LocalDate currentDate = (date == null) ? LocalDate.now() : LocalDate.parse(date);

		// その月の1日を取得
		LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
		// その月の1日の曜日を取得
		DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

		// 前月分の日付を取得するために最初の表示日を計算
		LocalDate startDate = firstDayOfMonth.minusDays(firstDayOfWeek.getValue());

		// 週と日を格納する二次元配列を用意する
		List<List<LocalDate>> month = new ArrayList<>();

		// 1週間分の日付を格納するリスト
		List<LocalDate> week = new ArrayList<>();
		for (int i = 0; i < 42; i++) { // 6週間分の42日間をカバー
			// 日付をリストに追加
			week.add(startDate.plusDays(i));

			// 1週間分の日付を追加したら週リストを月リストに追加し、新しい週リストを作成
			if (week.size() == 7) {
				month.add(new ArrayList<>(week));
				week.clear();
			}
		}

		// カレンダーとタスクデータをモデルに追加
		model.addAttribute("matrix", month);
		model.addAttribute("month", currentDate.getYear() + "-" + currentDate.getMonthValue());
		model.addAttribute("prev", currentDate.minusMonths(1));
		model.addAttribute("next", currentDate.plusMonths(1));
		model.addAttribute("tasks", Service.getTasksForCalendar(currentDate));

		return "main";
	}
}
