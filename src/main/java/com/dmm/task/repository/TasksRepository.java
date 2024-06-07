package com.dmm.task.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dmm.task.entity.Tasks;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {
    List<Tasks> findByDateBetween(LocalDate start, LocalDate end);
}