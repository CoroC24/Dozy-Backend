package com.cj.dozy.task.controller;

import com.cj.dozy.task.dto.AddTaskRequest;
import com.cj.dozy.task.dto.DelTaskRequest;
import com.cj.dozy.task.dto.ModTaskRequest;
import com.cj.dozy.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody AddTaskRequest addTask) {
        taskService.addTask(addTask);
        return ResponseEntity.ok().body("Task added successfully");
    }

    @PostMapping("/modifyTask")
    public ResponseEntity<String> modifyTask(@RequestBody ModTaskRequest modTask) {
        taskService.modifyTask(modTask);
        return ResponseEntity.ok().body("Task modified successfully " + modTask.getId());
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<String> deleteTask(@RequestBody DelTaskRequest delTask) {
        taskService.deleteTask(delTask);
        return ResponseEntity.ok().body("Task deleted successfully " + delTask.getId());
    }
}
