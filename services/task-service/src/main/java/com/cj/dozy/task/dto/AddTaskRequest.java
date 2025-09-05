package com.cj.dozy.task.dto;

import lombok.Data;

@Data
public class AddTaskRequest {
    private String title;
    private String description;
    private String date;
    private String category;
    private String color;
    private String status;
}
