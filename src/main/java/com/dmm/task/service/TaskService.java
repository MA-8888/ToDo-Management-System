package com.dmm.task.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Tasks;
import com.dmm.task.repository.TasksRepository;

@Service
public class TaskService {

	@Autowired
	private TasksRepository repo;

	public Map<LocalDate, List<Tasks>> getTasksForCalendar(LocalDate date) {
		// 月初と月末を計算
		LocalDate start = date.withDayOfMonth(1);
		LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

		// 月のすべてのタスクを取得
		List<Tasks> tasks = repo.findByDateBetween(start, end);

		// タスクを日付ごとにマップに変換
		return tasks.stream().collect(Collectors.groupingBy(Tasks::getDate));
	}
	
	   public Tasks createTask(String title, String name, String text, LocalDate date, boolean done) {
	        Tasks task = new Tasks();
	        task.setTitle(title);
	        task.setName(name);
	        task.setText(text);
	        task.setDate(date);
	        task.setDone(done);
	        return repo.save(task);
	    }

//	public void save(Tasks task) {
//		repo.save(task);
//	}
	
}
