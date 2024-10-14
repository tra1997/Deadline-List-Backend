package com.example.task_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserPerfomanceReviewDto {
    private String username;
    private int onTimeTask;
    private int overdueTask;
    private int ongoingTask;


}
