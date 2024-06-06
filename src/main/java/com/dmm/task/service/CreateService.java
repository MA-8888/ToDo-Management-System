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
}
