package com.example.task_management.controller;

import com.example.task_management.dto.UserDto;
import com.example.task_management.dto.UserPerfomanceReviewDto;
import com.example.task_management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;


    @GetMapping("/{usename}")
    public ResponseEntity<UserDto> getUserByUsernameOrEmail(@PathVariable("usename") String requesUsernameOrEmail) {

        UserDto userDto = userService.getUserByUsernameOrEmail(requesUsernameOrEmail);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/performanceReview")
    public ResponseEntity<List<UserPerfomanceReviewDto>> getUserPerfomanceReview() {

        List<UserPerfomanceReviewDto> userPerfomanceReviewDtos = userService.getUserPerfomanceReview();

        return ResponseEntity.ok(userPerfomanceReviewDtos);
    }

}
