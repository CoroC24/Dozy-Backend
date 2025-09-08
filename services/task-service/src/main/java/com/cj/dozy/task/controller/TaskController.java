package com.cj.dozy.task.controller;

import com.cj.dozy.task.dto.AddTaskRequest;
import com.cj.dozy.task.dto.DelTaskRequest;
import com.cj.dozy.task.dto.ModTaskRequest;
import com.cj.dozy.task.model.Task;
import com.cj.dozy.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/addTask")
    public ResponseEntity<Map<String, Object>> addTask(@RequestBody AddTaskRequest addTask) {
        Task addedTask = taskService.addTask(addTask);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Task added successfully");
        body.put("task-added", addedTask.getId());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @PostMapping("/modifyTask")
    public ResponseEntity<Map<String, Object>> modifyTask(@RequestBody ModTaskRequest modTask) {
        taskService.modifyTask(modTask);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Task modified successfully");
        body.put("task-modified", modTask.getId());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask")
    public ResponseEntity<Map<String, Object>> deleteTask(@RequestBody DelTaskRequest delTask) {
        taskService.deleteTask(delTask);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", "Task deleted successfully");
        body.put("task-deleted", delTask.getId());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
