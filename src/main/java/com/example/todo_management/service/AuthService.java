package com.example.todo_management.service;

import com.example.todo_management.dto.LoginDto;
import com.example.todo_management.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
   String login(LoginDto loginDto);

}
