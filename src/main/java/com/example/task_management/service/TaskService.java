package com.example.task_management.service;

import com.example.task_management.dto.TaskDto;

import java.util.List;


public interface TaskService {
    TaskDto addTodo(TaskDto requestTodo);

    List<TaskDto> getAll();

    TaskDto getById(Long id);

    TaskDto updateTodo(TaskDto requestTaskDto, Long requestId);

    String deleteTodo(Long requestId);

    TaskDto completeTodo(Long requestId);

    TaskDto inCompleteTodo(Long requestId);

    List<TaskDto> getAllTodoByUsername(String requestUsername);


}
