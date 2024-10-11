package com.example.task_management.service;

import com.example.task_management.dto.UserDto;

public interface UserService {
    UserDto getUserByUsernameOrEmail(String requesUsernameOrEmail);
}
