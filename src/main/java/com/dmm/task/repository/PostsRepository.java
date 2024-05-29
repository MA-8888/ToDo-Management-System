package com.dmm.task.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dmm.task.entity.Posts;

public interface PostsRepository extends JpaRepository<Posts, Long>{
	List<Posts> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
