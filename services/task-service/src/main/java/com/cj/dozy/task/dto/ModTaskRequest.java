package com.cj.dozy.task.dto;

import lombok.Data;

@Data
public class ModTaskRequest {
    private Long id;
    private String title;
    private String description;
    private String date;
    private String category;
    private String color;
    private String status;
}
