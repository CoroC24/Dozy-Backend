package com.cj.dozy.task.dto.modtask.request;

import lombok.Data;

import java.util.List;

@Data
public class ModTaskListRequest {
    private List<ModTaskRequest> tasks;
}
