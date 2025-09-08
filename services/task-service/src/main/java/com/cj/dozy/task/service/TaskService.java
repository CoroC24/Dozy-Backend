package com.cj.dozy.task.service;

import com.cj.dozy.task.dto.AddTaskRequest;
import com.cj.dozy.task.dto.DelTaskRequest;
import com.cj.dozy.task.dto.ModTaskRequest;
import com.cj.dozy.task.model.Task;
import com.cj.dozy.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Task addTask(AddTaskRequest addTask) {
        Task task = Task
                .builder()
                .title(addTask.getTitle())
                .description(addTask.getDescription())
                .date(addTask.getDate())
                .category(addTask.getCategory())
                .color(addTask.getColor())
                .status(addTask.getStatus())
                .build();

        return taskRepository.save(task);
    }

    public void modifyTask(ModTaskRequest modTask) {
        if (!taskRepository.existsById(modTask.getId())) throw new NoSuchElementException("Task to modify doesn't exists in DB");

        Task taskToMod = taskRepository.findTaskById(modTask.getId());
        taskToMod.setTitle(modTask.getTitle());
        taskToMod.setDescription(modTask.getDescription());
        taskToMod.setDate(modTask.getDate());
        taskToMod.setCategory(modTask.getCategory());
        taskToMod.setColor(modTask.getColor());
        taskToMod.setStatus(modTask.getStatus());

        taskRepository.save(taskToMod);
    }

    public void deleteTask(DelTaskRequest delTask) {
        if (!taskRepository.existsById(delTask.getId())) throw new NoSuchElementException("Task to modify doesn't exists in DB");

        Task taskToDel = taskRepository.findTaskById(delTask.getId());
        taskToDel.setId(delTask.getId());

        taskRepository.delete(taskToDel);
    }
}
