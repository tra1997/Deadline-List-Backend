package com.example.task_management.service.impl;

import com.example.task_management.dto.TaskDto;
import com.example.task_management.entity.Task;
import com.example.task_management.exception.ResourceNotFoundException;
import com.example.task_management.repository.TaskReponsitory;
import com.example.task_management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service

public class TaskServiceImpl implements TaskService {
    TaskReponsitory taskReponsitory;


    private Task todoDtoToTodo(TaskDto taskDto) {
        Task task = new Task();
        task.setId(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setUsername(taskDto.getUsername());
        task.setCompleted(task.isCompleted());
        // Chuyển đổi số giờ (int) từ TodoDTO sang LocalTime
        LocalDateTime deadlineTime = LocalDateTime.now().plusHours(taskDto.getDeadlineTime()); // Đặt phút mặc định là 00
        task.setDeadlineTime(deadlineTime);

        return task;
    }

    private TaskDto todoToTodoDto(Task task) {

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setUsername(task.getUsername());
        taskDto.setCompleted(task.isCompleted());

        LocalDateTime localDateTime = LocalDateTime.now();

        if (task.getDeadlineTime().isEqual(localDateTime)) {
            int deadlineTime = task.getDeadlineTime().getHour() - localDateTime.getHour();
            taskDto.setDeadlineTime(deadlineTime);
        } else if (task.getDeadlineTime().isBefore(localDateTime)) {
            taskDto.setDeadlineTime(0);// gia tri 0 the hien cho het time de lam viec
        } else {
            int deadlineTime = (task.getDeadlineTime().getDayOfMonth() - localDateTime.getDayOfMonth()) * 24 + (task.getDeadlineTime().getHour() - localDateTime.getHour());
            taskDto.setDeadlineTime(deadlineTime);
        }


        return taskDto;
    }

    private LocalDateTime localDateTimeToInt(int deadlineTime) {

        LocalDateTime localDateTime = LocalDateTime.now().plusHours(deadlineTime);
        return localDateTime;

    }


    @Override
    public TaskDto addTodo(TaskDto requestTaskDto) {

        Task task = todoDtoToTodo(requestTaskDto);

        Task saveTask = taskReponsitory.save(task);

        //   TodoDto todoDto= modelMapper.map(saveTodo,TodoDto.class);


        return todoToTodoDto(saveTask);
    }

    @Override
    public List<TaskDto> getAll() {

        List<Task> tasks = taskReponsitory.findAll();

        return tasks.stream()
                .map(task -> todoToTodoDto(task))
                .toList(); // chuyển stream thành list
    }

    @Override
    public List<TaskDto> getAllTodoByUsername(String requestUsername) {

        List<Task> tasks = taskReponsitory.findAllByUsername(requestUsername);

        return tasks.stream()
                .map(task -> todoToTodoDto(task))
                .toList();
    }


    @Override
    public TaskDto getById(Long id) {
        Task task = taskReponsitory.findById(id).orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + id));

//return  modelMapper.map(todo,TodoDto.class);
        return todoToTodoDto(task);
    }

    @Override
    public TaskDto updateTodo(TaskDto requestTaskDto, Long requestId) {

        Task task = taskReponsitory.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + requestId));
        task.setTitle(requestTaskDto.getTitle());
        task.setDescription(requestTaskDto.getDescription());
        task.setCompleted(requestTaskDto.isCompleted());

        task.setDeadlineTime(localDateTimeToInt(requestTaskDto.getDeadlineTime()));

        task.setUsername(requestTaskDto.getUsername());
        taskReponsitory.save(task);
        return todoToTodoDto(task);
    }

    @Override
    public String deleteTodo(Long requestId) {
        Task task = taskReponsitory.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + requestId));

        taskReponsitory.delete(task);
        return "Todo deleted successfully !!";
    }

    @Override
    public TaskDto completeTodo(Long requestId) {
        Task task = taskReponsitory.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + requestId));
        task.setCompleted(Boolean.TRUE);
        taskReponsitory.save(task);

        return todoToTodoDto(task);
    }

    @Override
    public TaskDto inCompleteTodo(Long requestId) {
        Task task = taskReponsitory.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id : " + requestId));
        task.setCompleted(Boolean.FALSE);
        taskReponsitory.save(task);

        return todoToTodoDto(task);
    }


}




