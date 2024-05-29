package com.dmm.task.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CalendarService {
	public List<List<LocalDate>> generateCalendar(int year, int month) {
		List<List<LocalDate>> calendar = new ArrayList<>();

		// その月の初日を取得
		LocalDate firstDayOfMonth = YearMonth.of(year, month).atDay(1);

		// その月の最後の日を取得
		LocalDate lastDayOfMonth = YearMonth.of(year, month).atEndOfMonth();

		// 初日の曜日を取得
		DayOfWeek firstDayOfWeek = firstDayOfMonth.getDayOfWeek();

		// カレンダーの開始日を計算
		LocalDate current = firstDayOfMonth.minusDays(firstDayOfWeek.getValue() % 7);

		// カレンダーの日付を週ごとにListへ格納
		while (current.isBefore(lastDayOfMonth) || current.getDayOfWeek() != DayOfWeek.MONDAY) {
			List<LocalDate> week = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				week.add(current);
				current = current.plusDays(1);
			}
			calendar.add(week);
		}

		return calendar;
	}
}
