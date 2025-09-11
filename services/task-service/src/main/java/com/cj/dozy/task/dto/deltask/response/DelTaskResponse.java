package com.cj.dozy.task.dto.deltask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DelTaskResponse {
    private String uuid;
    private Long taskId;
    private String message;
}
