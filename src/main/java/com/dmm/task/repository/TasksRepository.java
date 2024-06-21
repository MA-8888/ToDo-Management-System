package com.dmm.task.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.dmm.task.entity.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Integer> {
//	List<Tasks> findByDateBetween(LocalDateTime start, LocalDateTime end);
	@Query("select a from Tasks a where a.date between :from and :to")
    List<Tasks> findByDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);


	@Query("select a from Tasks a where a.date between :from and :to and name = :name")
	List<Tasks> findByDateBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to,
			@Param("name") String name);
}