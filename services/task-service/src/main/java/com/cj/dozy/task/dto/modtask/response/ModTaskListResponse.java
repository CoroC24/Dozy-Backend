package com.cj.dozy.task.dto.modtask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ModTaskListResponse {
    private List<ModTaskResponse> moddedTasks;
}
