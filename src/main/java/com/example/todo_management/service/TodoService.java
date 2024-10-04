package com.example.todo_management.service;

import com.example.todo_management.dto.TodoDto;

import java.util.List;


public interface TodoService {
    TodoDto addTodo(TodoDto requestTodo);

    List<TodoDto> getAll();
    TodoDto getById(Long id);

    TodoDto updateTodo(TodoDto requestTodoDto,Long requestId);

   String deleteTodo(Long requestId);

    TodoDto completeTodo(Long requestId);

    TodoDto inCompleteTodo(Long requestId);
}
