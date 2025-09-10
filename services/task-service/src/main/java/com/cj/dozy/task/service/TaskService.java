package com.cj.dozy.task.service;

import com.cj.dozy.task.dto.addtask.request.AddTaskListRequest;
import com.cj.dozy.task.dto.addtask.request.AddTaskRequest;
import com.cj.dozy.task.dto.addtask.response.AddTaskListResponse;
import com.cj.dozy.task.dto.addtask.response.AddTaskResponse;
import com.cj.dozy.task.dto.deltask.request.DelTaskListRequest;
import com.cj.dozy.task.dto.deltask.response.DelTaskListResponse;
import com.cj.dozy.task.dto.deltask.request.DelTaskRequest;
import com.cj.dozy.task.dto.deltask.response.DelTaskResponse;
import com.cj.dozy.task.dto.modtask.request.ModTaskListRequest;
import com.cj.dozy.task.dto.modtask.request.ModTaskRequest;
import com.cj.dozy.task.dto.modtask.response.ModTaskListResponse;
import com.cj.dozy.task.dto.modtask.response.ModTaskResponse;
import com.cj.dozy.task.model.Task;
import com.cj.dozy.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public AddTaskListResponse addTask(AddTaskListRequest addTaskReq) {
        List<AddTaskRequest> validTasks = new ArrayList<>();
        List<AddTaskResponse> responses = new ArrayList<>();

        for (AddTaskRequest task : addTaskReq.getTasks()) {
            if (validateRequiredFields(task.getTitle(), task.getDate(), task.getStatus())) {
                validTasks.add(task);
            } else {
                responses.add(new AddTaskResponse(task.getUuid(), null, "The task to be added has empty fields: Title, Date, or Status"));
            }
        }

        if (!validTasks.isEmpty()) {
            List<Task> tasks = validTasks.stream()
                    .map(taskReq ->
                            Task.builder()
                                    .title(taskReq.getTitle())
                                    .description(taskReq.getDescription())
                                    .date(taskReq.getDate())
                                    .category(taskReq.getCategory())
                                    .color(taskReq.getColor())
                                    .status(taskReq.getStatus())
                                    .userId(taskReq.getUserId())
                                    .build())
                    .toList();

            List<Task> savedTasks = taskRepository.saveAll(tasks);

            IntStream.range(0, validTasks.size()).forEach(i -> {
                responses.add(new AddTaskResponse(validTasks.get(i).getUuid(), savedTasks.get(i).getId(), "Task added successfully"));
            });
        }

        return new AddTaskListResponse(responses);
    }

    public ModTaskListResponse updateTask(ModTaskListRequest modTaskReq) {
        List<Long> tasksIds = modTaskReq.getTasks().stream().map(ModTaskRequest::getId).toList();
        Set<Long> existingIds = taskRepository.findAllById(tasksIds).stream().map(Task::getId).collect(Collectors.toSet());

        List<ModTaskRequest> validTasks = new ArrayList<>();
        List<ModTaskResponse> responses = new ArrayList<>();

        IntStream.range(0, tasksIds.size()).forEach(i -> {
            ModTaskRequest taskReq = modTaskReq.getTasks().get(i);

            if (existingIds.contains(taskReq.getId())
                    && validateRequiredFields(taskReq.getTitle(), taskReq.getDate(), taskReq.getStatus())) {
                validTasks.add(taskReq);
            } else {
                responses.add(new ModTaskResponse(taskReq.getUuid(), taskReq.getId(), "Task not found in DB, cannot be updated"));
            }
        });

        if (!validTasks.isEmpty()) {
            List<Task> tasks = validTasks.stream()
                    .map(taskReq ->
                            Task.builder()
                                    .id(taskReq.getId())
                                    .title(taskReq.getTitle())
                                    .description(taskReq.getDescription())
                                    .date(taskReq.getDate())
                                    .category(taskReq.getCategory())
                                    .color(taskReq.getColor())
                                    .status(taskReq.getStatus())
                                    .userId(taskReq.getUserId())
                                    .build())
                    .toList();

            List<Task> updatedTasks = taskRepository.saveAll(tasks);

            IntStream.range(0, validTasks.size()).forEach(i -> {
                responses.add(new ModTaskResponse(validTasks.get(i).getUuid(), updatedTasks.get(i).getId(), "Task updated successfully"));
            });
        }

        return new ModTaskListResponse(responses);
    }

    public DelTaskListResponse deleteTask(DelTaskListRequest delTaskReq) {
        List<Long> tasksIds = delTaskReq.getTasks().stream().map(DelTaskRequest::getId).toList();
        Set<Long> existingIds = taskRepository.findAllById(tasksIds).stream().map(Task::getId).collect(Collectors.toSet());

        List<Long> validIds = new ArrayList<>();
        List<DelTaskResponse> responses = new ArrayList<>();

        IntStream.range(0, tasksIds.size()).forEach(i -> {
            if (existingIds.contains(tasksIds.get(i))) {
                validIds.add(tasksIds.get(i));
            } else {
                responses.add(new DelTaskResponse(
                        delTaskReq.getTasks().get(i).getUuid(),
                        tasksIds.get(i),
                        "Task not found in DB, cannot be deleted"
                ));
            }
        });

        if (!validIds.isEmpty()) {
            taskRepository.deleteAllById(tasksIds);

            IntStream.range(0, validIds.size()).forEach(i -> {
                responses.add(new DelTaskResponse(delTaskReq.getTasks().get(i).getUuid(), validIds.get(i), "Task deleted successfully"));
            });
        }

        return new DelTaskListResponse(responses);
    }

    private boolean validateRequiredFields(String title, String date, String status) {
        return isNotBlank(title) && isNotBlank(date) && isNotBlank(status);
    }
}
