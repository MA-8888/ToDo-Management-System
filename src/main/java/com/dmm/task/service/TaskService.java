package com.dmm.task.service;

import java.time.DayOfWeek;
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
import com.dmm.task.entity.Users;
import com.dmm.task.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository repo;

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date, AccountUserDetails user) {
		// 月初と月末を計算
		YearMonth currentYearMonth = YearMonth.from(date);
		LocalDate firstOfMonth = currentYearMonth.atDay(1);
		LocalDate lastOfMonth = currentYearMonth.atEndOfMonth();

		// 先月分の日付を含むように調整
		LocalDate adjustedStartDate = firstOfMonth.with(DayOfWeek.SUNDAY);
		if (adjustedStartDate.isAfter(firstOfMonth)) {
			adjustedStartDate = adjustedStartDate.minusWeeks(1);
		}

		// 来月分の日付を含むように調整
		LocalDate adjustedEndDate = lastOfMonth.with(DayOfWeek.SATURDAY);
		if (adjustedEndDate.isBefore(lastOfMonth)) {
			adjustedEndDate = adjustedEndDate.plusWeeks(1);
		}

		LocalDateTime startDate = adjustedStartDate.atStartOfDay();
		LocalDateTime endDate = adjustedEndDate.plusDays(1).atStartOfDay();

		// ログインユーザの権限情報を取得

		List<Tasks> tasks;
		Users currentUser = user.getUser();
		String roleName = "ROLE_" + currentUser.getRoleName();

		if (roleName.equals("ROLE_USER")) {
			// userの場合の処
			tasks = repo.findByDateBetween(startDate, endDate, currentUser.getName());
		} else {
			// adminの場合の処理
			tasks = repo.findByDateBetween(startDate, endDate);
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
//List<Tasks> tasks;
//if (user == null) {
//	tasks = repo.findByDateBetween(startDateTime, endDateTime);
//} else {
//	tasks = repo.findByDateBetween(startDateTime, endDateTime, user);
//}
