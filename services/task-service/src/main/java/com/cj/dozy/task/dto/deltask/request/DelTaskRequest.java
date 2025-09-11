package com.cj.dozy.task.dto.deltask.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DelTaskRequest {
    private Long id;
    private String uuid;
}
