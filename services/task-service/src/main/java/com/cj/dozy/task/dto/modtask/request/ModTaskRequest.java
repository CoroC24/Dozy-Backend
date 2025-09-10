package com.cj.dozy.task.dto.modtask.request;

import lombok.Data;

@Data
public class ModTaskRequest {
    private Long id;
    private String uuid;
    private String title;
    private String description;
    private String date;
    private String category;
    private String color;
    private String status;
    private Long userId;
}
