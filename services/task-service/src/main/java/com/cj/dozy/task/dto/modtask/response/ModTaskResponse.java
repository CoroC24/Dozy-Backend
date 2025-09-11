package com.cj.dozy.task.dto.modtask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModTaskResponse {
    private String uuid;
    private Long taskId;
    private String message;
}
