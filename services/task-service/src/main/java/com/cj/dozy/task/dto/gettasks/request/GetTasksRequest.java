package com.cj.dozy.task.dto.gettasks.request;

import lombok.Data;

import java.util.List;

@Data
public class GetTasksRequest {
    private Long userId;
    private List<Long> localIds;
}
