package com.cj.dozy.task.dto.deltask.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelTaskListRequest {
    private List<DelTaskRequest> tasks;
}
