package com.example.todo_management.repository;

import com.example.todo_management.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoReponsitory extends JpaRepository<Todo, Long> {


}
