package com.cj.dozy.task.dto.addtask.request;

import lombok.Data;

import java.util.List;

@Data
public class AddTaskListRequest {
    private List<AddTaskRequest> tasks;
}
