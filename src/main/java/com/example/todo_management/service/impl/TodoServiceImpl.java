package com.example.todo_management.service.impl;

import com.example.todo_management.dto.TodoDto;
import com.example.todo_management.entity.Todo;
import com.example.todo_management.exception.ResourceNotFoundException;
import com.example.todo_management.repository.TodoReponsitory;
import com.example.todo_management.service.TodoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TodoServiceImpl implements TodoService {
    TodoReponsitory todoReponsitory;
 private ModelMapper modelMapper;
    @Override
    public TodoDto addTodo(TodoDto requestTodoDto) {
    Todo todo = modelMapper.map(requestTodoDto, Todo.class);
    Todo saveTodo = todoReponsitory.save(todo);
// TodoDto savedTodoDto = modelMapper.map(saveTodo,TodoDto.class);
// return savedTodoDto;
//    }

        return modelMapper.map(saveTodo,TodoDto.class);

    }


    @Override
    public List<TodoDto> getAll() {

        List<Todo> todos = todoReponsitory.findAll();


        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .toList(); // chuyển stream thành list
    }

    @Override
    public TodoDto getById(Long id) {
Todo todo = todoReponsitory.findById(id).orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+id));

return  modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public TodoDto updateTodo(TodoDto requestTodoDto, Long requestId) {
        Todo todo = todoReponsitory.findById(requestId)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+requestId));
        todo.setTitle(requestTodoDto.getTitle());
        todo.setDescription(requestTodoDto.getDescription());
        todo.setCompleted(requestTodoDto.isCompleted());
        todoReponsitory.save(todo);
        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public String deleteTodo(Long requestId) {
        Todo todo =todoReponsitory.findById(requestId)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+requestId));

        todoReponsitory.delete(todo);
        return "Todo deleted successfully !!";
    }

    @Override
    public TodoDto completeTodo(Long requestId) {
        Todo todo =todoReponsitory.findById(requestId)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+requestId));
todo.setCompleted(Boolean.TRUE);
todoReponsitory.save(todo);

        return modelMapper.map(todo,TodoDto.class);
    }

    @Override
    public TodoDto inCompleteTodo(Long requestId) {
        Todo todo =todoReponsitory.findById(requestId)
                .orElseThrow(()-> new ResourceNotFoundException("Todo not found with id : "+requestId));
        todo.setCompleted(Boolean.FALSE);
        todoReponsitory.save(todo);

        return modelMapper.map(todo,TodoDto.class);
    }
    }




