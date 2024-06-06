package com.dmm.task.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Posts;
import com.dmm.task.repository.PostsRepository;

@Service
public class PostService {

	@Autowired
	private PostsRepository taskRepository;

	public Map<LocalDate, List<Posts>> getTasksForCalendar(LocalDate date) {
		// 月初と月末を計算
		LocalDate start = date.withDayOfMonth(1);
		LocalDate end = date.withDayOfMonth(date.lengthOfMonth());

		// 月のすべてのタスクを取得
		List<Posts> tasks = taskRepository.findByDateBetween(start, end);

		// タスクを日付ごとにマップに変換
		return tasks.stream().collect(Collectors.groupingBy(Posts::getDate));
	}
}
