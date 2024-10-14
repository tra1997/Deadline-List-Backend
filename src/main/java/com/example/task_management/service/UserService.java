package com.example.task_management.service;

import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserPerfomanceReviewDto;

import java.util.List;

public interface UserService {
    UserDto getUserByUsernameOrEmail(String requesUsernameOrEmail);

    List<UserPerfomanceReviewDto> getUserPerfomanceReview();
}
