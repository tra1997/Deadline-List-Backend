package com.example.task_management.service;

import com.example.task_management.dto.JwtAuthResponse;
import com.example.task_management.dto.LoginDto;
import com.example.task_management.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);

    JwtAuthResponse login(LoginDto loginDto);

}
