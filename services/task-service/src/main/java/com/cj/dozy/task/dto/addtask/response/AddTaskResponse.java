package com.cj.dozy.task.dto.addtask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddTaskResponse {
    private String uuid;
    private Long taskId;
    private String message;
}
