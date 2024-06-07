package com.dmm.task.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dmm.task.entity.Create;
import com.dmm.task.repository.CreateRepository;

@Service
public class CreateService {
	@Autowired
	private CreateRepository repo;

	public void save(Create create) {
		repo.save(create);
	}

//	public Map<LocalDate, List<Create>> getTasksForCalendar(LocalDate date) {
//		LocalDate start = date.withDayOfMonth(1);
//		LocalDate end = date.withDayOfMonth(date.lengthOfMonth());
//		List<Create> create = repo.findByDateBetween(start, end);
//		return create.stream().collect(Collectors.groupingBy(Create::getDate));
//	}
}