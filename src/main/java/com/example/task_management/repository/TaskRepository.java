package com.example.task_management.repository;

import com.example.task_management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {


    Optional<List<Task>> findAllByUsername(String username); // Sửa tên phương thức
}