package com.example.task_management.service.impl;

import com.example.task_management.dto.UserDto;
import com.example.task_management.entity.User;
import com.example.task_management.exception.ResourceNotFoundException;
import com.example.task_management.repository.UserRepository;
import com.example.task_management.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDto getUserByUsernameOrEmail(String requesUsernameOrEmail) {

        User user = userRepository.findByUsernameOrEmail(requesUsernameOrEmail, requesUsernameOrEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return modelMapper.map(user, UserDto.class);
    }
}
