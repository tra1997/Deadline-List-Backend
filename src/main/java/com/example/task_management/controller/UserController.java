package com.example.task_management.controller;

import com.example.task_management.dto.UserDto;
import com.example.task_management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @GetMapping("/{usename}")
    public ResponseEntity<UserDto> getUserByUsernameOrEmail(@PathVariable("usename") String requesUsernameOrEmail) {
        System.out.println(requesUsernameOrEmail);
        UserDto userDto = userService.getUserByUsernameOrEmail(requesUsernameOrEmail);

        return ResponseEntity.ok(userDto);
    }
}
