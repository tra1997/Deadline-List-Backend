package com.example.task_management.repository;

import com.example.task_management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskReponsitory extends JpaRepository<Task, Long> {
    List<Task> findAllByUsername(String username);

}
