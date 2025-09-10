package com.cj.dozy.task.dto.deltask.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DelTaskListResponse {
    private List<DelTaskResponse> deletedTasks;
}
