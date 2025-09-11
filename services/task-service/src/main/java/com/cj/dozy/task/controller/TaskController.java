package com.cj.dozy.task.controller;

import com.cj.dozy.task.dto.addtask.request.AddTaskListRequest;
import com.cj.dozy.task.dto.deltask.request.DelTaskListRequest;
import com.cj.dozy.task.dto.gettasks.request.GetTasksRequest;
import com.cj.dozy.task.dto.gettasks.response.GetTasksListResponse;
import com.cj.dozy.task.dto.modtask.request.ModTaskListRequest;
import com.cj.dozy.task.dto.addtask.response.AddTaskListResponse;
import com.cj.dozy.task.dto.deltask.response.DelTaskListResponse;
import com.cj.dozy.task.dto.modtask.response.ModTaskListResponse;
import com.cj.dozy.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task-management")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<AddTaskListResponse> addTask(@RequestBody AddTaskListRequest taskRequest) {
        return new ResponseEntity<>(taskService.addTask(taskRequest), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<ModTaskListResponse> updateTask(@RequestBody ModTaskListRequest taskRequest) {
        return new ResponseEntity<>(taskService.updateTask(taskRequest), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DelTaskListResponse> deleteTask(@RequestBody DelTaskListRequest taskRequest) {
        return new ResponseEntity<>(taskService.deleteTask(taskRequest), HttpStatus.OK);
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<GetTasksListResponse> getTasks(@RequestBody GetTasksRequest taskRequest) {
        return new ResponseEntity<>(taskService.getTasksByUserId(taskRequest), HttpStatus.OK);
    }
}
