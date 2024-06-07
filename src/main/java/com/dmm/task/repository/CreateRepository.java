package com.dmm.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dmm.task.entity.Create;

@Repository
public interface CreateRepository extends JpaRepository<Create, Long> {
//	List<Create> findByDateBetween(LocalDate start, LocalDate end);
}
