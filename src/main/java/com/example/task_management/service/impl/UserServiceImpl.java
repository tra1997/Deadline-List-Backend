package com.example.task_management.service.impl;

import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserPerfomanceReviewDto;
import com.example.task_management.entity.Task;
import com.example.task_management.entity.User;
import com.example.task_management.exception.ResourceNotFoundException;
import com.example.task_management.repository.TaskReponsitory;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final TaskReponsitory taskReponsitory;
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto getUserByUsernameOrEmail(String requesUsernameOrEmail) {

        User user = userRepository.findByUsernameOrEmail(requesUsernameOrEmail, requesUsernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(user, UserDto.class);
    }

    private UserPerfomanceReviewDto userToUserPerfomanceDto(User user) {
        UserPerfomanceReviewDto userPerfomanceReviewDto = new UserPerfomanceReviewDto();

        int countOnTimeTask = 0;
        int countOverdueTime = 0;
        int counOngoingTask = 0;

        LocalDateTime localDateTime = LocalDateTime.now();
        List<Task> tasks = taskReponsitory.findAll();
        for (Task task : tasks) {
            if (task.getUsername().equals(user.getUsername())) {
                if (task.isCompleted()) {
                    countOnTimeTask++;
                }
                if (task.isCompleted() == false && task.getDeadlineTime().isBefore(localDateTime)) {
                    countOverdueTime++;
                }
                if (task.isCompleted() == false && task.getDeadlineTime().isAfter(localDateTime)) {
                    counOngoingTask++;
                }
            }

        }
        userPerfomanceReviewDto.setUsername(user.getUsername());
        userPerfomanceReviewDto.setOngoingTask(counOngoingTask);
        userPerfomanceReviewDto.setOverdueTask(countOverdueTime);
        userPerfomanceReviewDto.setOnTimeTask(countOnTimeTask);

        return userPerfomanceReviewDto;
    }

    @Override
    public List<UserPerfomanceReviewDto> getUserPerfomanceReview() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> userToUserPerfomanceDto(user))
                .toList();

    }
}
