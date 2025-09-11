package com.cj.dozy.task.dto.gettasks.response;

import com.cj.dozy.task.model.Task;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetTasksListResponse {
    private List<Task> pendingTasks;
}
