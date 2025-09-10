package com.cj.dozy.task.dto.addtask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AddTaskListResponse {
    private List<AddTaskResponse> addedTasks;
}
