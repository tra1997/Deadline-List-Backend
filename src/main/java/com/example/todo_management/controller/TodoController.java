package com.example.todo_management.controller;

import com.example.todo_management.dto.TodoDto;
import com.example.todo_management.service.TodoService;
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
public class TodoController {

   private final TodoService todoService;

   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> create(@RequestBody TodoDto requestTodoDto) {
        TodoDto todoDto =  todoService.addTodo(requestTodoDto);

        return new ResponseEntity<>(todoDto, HttpStatus.CREATED);
    }

 @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<TodoDto>> getAll() {
        List<TodoDto> allTodoDto = todoService.getAll();
        return ResponseEntity.ok(allTodoDto);
    }

   @PreAuthorize("hasAnyRole('ADMIN','USER')")  // NẾU LÀ USER CẦN THÊM 1 LƯỢT CHECK ID CÓ GIỐNG ID TRONG BẢNG TODO KHÔNG ?
    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long requestId) {

        TodoDto todoDto = todoService.getById(requestId);

        return ResponseEntity.ok(todoDto);
    }
    @PreAuthorize("hasRole('ADMIN')")
  @PutMapping("{id}")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto requestTodoDto, @PathVariable("id") Long requestId) {
        TodoDto todoDto = todoService.updateTodo(requestTodoDto,requestId);
        return ResponseEntity.ok(todoDto);

  }
    @PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("{id}")
    public  ResponseEntity<String> deleteTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(todoService.deleteTodo(requestId));
}
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
@PatchMapping("{id}/complete")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(todoService.completeTodo(requestId));

}
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @PatchMapping("{id}/in-complete")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable("id") Long requestId) {
        return ResponseEntity.ok(todoService.inCompleteTodo(requestId));

    }


}
