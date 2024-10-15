package com.example.task_management.controller;

import com.example.task_management.dto.TaskDto;
import com.example.task_management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/todos")
public class TaskController {


    private final TaskService taskService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskDto> create(@RequestBody TaskDto requestTaskDto) {

        TaskDto taskDto = taskService.addTodo(requestTaskDto);

        return new ResponseEntity<>(taskDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TaskDto>> getAll() {
        List<TaskDto> allTaskDto = taskService.getAll();
        return ResponseEntity.ok(allTaskDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/getAllByUsername/{username}")
    public ResponseEntity<List<TaskDto>> getAllTodoByUsername(@PathVariable("username") String username) {

        List<TaskDto> allTaskDto = taskService.getAllTodoByUsername(username);
        return ResponseEntity.ok(allTaskDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTodo(@PathVariable("id") Long requestId) {

        TaskDto taskDto = taskService.getById(requestId);

        return ResponseEntity.ok(taskDto);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TaskDto> updateTodo(@RequestBody TaskDto requestTaskDto, @PathVariable("id") Long requestId) {
        TaskDto taskDto = taskService.updateTodo(requestTaskDto, requestId);
        return ResponseEntity.ok(taskDto);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(taskService.deleteTodo(requestId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/complete")
    public ResponseEntity<TaskDto> completeTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(taskService.completeTodo(requestId));
    }

    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TaskDto> inCompleteTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(taskService.inCompleteTodo(requestId));

    }


}
