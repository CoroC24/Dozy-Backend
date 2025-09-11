package com.cj.dozy.task.service;

import com.cj.dozy.task.dto.addtask.request.AddTaskListRequest;
import com.cj.dozy.task.dto.addtask.request.AddTaskRequest;
import com.cj.dozy.task.dto.addtask.response.AddTaskListResponse;
import com.cj.dozy.task.dto.addtask.response.AddTaskResponse;
import com.cj.dozy.task.dto.deltask.request.DelTaskListRequest;
import com.cj.dozy.task.dto.deltask.request.DelTaskRequest;
import com.cj.dozy.task.dto.deltask.response.DelTaskListResponse;
import com.cj.dozy.task.dto.deltask.response.DelTaskResponse;
import com.cj.dozy.task.dto.gettasks.request.GetTasksRequest;
import com.cj.dozy.task.dto.gettasks.response.GetTasksListResponse;
import com.cj.dozy.task.dto.modtask.request.ModTaskListRequest;
import com.cj.dozy.task.dto.modtask.request.ModTaskRequest;
import com.cj.dozy.task.dto.modtask.response.ModTaskListResponse;
import com.cj.dozy.task.dto.modtask.response.ModTaskResponse;
import com.cj.dozy.task.model.Task;
import com.cj.dozy.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
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
            if (validateRequiredFields(task.getTitle(), task.getDate(), task.getStatus(), task.getUserId())) {
                validTasks.add(task);
            } else {
                responses.add(new AddTaskResponse(task.getUuid(), null, "The task to be added has empty fields: Title, Date, Status or UserId"));
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

            IntStream.range(0, validTasks.size()).forEach(i ->
                    responses.add(new AddTaskResponse(validTasks.get(i).getUuid(), savedTasks.get(i).getId(), "Task added successfully")));
        }

        return new AddTaskListResponse(responses);
    }

    public ModTaskListResponse updateTask(ModTaskListRequest modTaskReq) {
        List<Long> tasksIds = modTaskReq.getTasks().stream().map(ModTaskRequest::getId).toList();
        Set<Long> existingIds = taskRepository.findAllById(tasksIds).stream().map(Task::getId).collect(Collectors.toSet());

        List<ModTaskRequest> validTasks = new ArrayList<>();
        List<ModTaskResponse> responses = new ArrayList<>();

        IntStream.range(0, tasksIds.size()).forEach(i -> {
            ModTaskRequest task = modTaskReq.getTasks().get(i);

            if (existingIds.contains(task.getId())
                    && validateRequiredFields(task.getTitle(), task.getDate(), task.getStatus(), task.getUserId())) {
                validTasks.add(task);
            } else {
                responses.add(new ModTaskResponse(task.getUuid(), task.getId(), "Task not found in DB, cannot be updated"));
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

            IntStream.range(0, validTasks.size()).forEach(i ->
                    responses.add(new ModTaskResponse(validTasks.get(i).getUuid(), updatedTasks.get(i).getId(), "Task updated successfully")));
        }

        return new ModTaskListResponse(responses);
    }

    public DelTaskListResponse deleteTask(DelTaskListRequest delTaskReq) {
        List<Long> tasksIds = delTaskReq.getTasks().stream().map(DelTaskRequest::getId).toList();
        Set<Long> existingIds = taskRepository.findAllById(tasksIds).stream().map(Task::getId).collect(Collectors.toSet());

        List<DelTaskResponse> responses = new ArrayList<>();
        List<Long> idsToDelete = new ArrayList<>();

        for (DelTaskRequest taskReq : delTaskReq.getTasks()) {
            Long id = taskReq.getId();
            String uuid = taskReq.getUuid();

            if (existingIds.contains(id)) {
                idsToDelete.add(id);
                responses.add(new DelTaskResponse(uuid, id, "Task deleted successfully"));
            } else {
                responses.add(new DelTaskResponse(uuid, id, "Task not found in DB, cannot be deleted"));
            }
        }

        if (!idsToDelete.isEmpty()) taskRepository.deleteAllById(idsToDelete);

        return new DelTaskListResponse(responses);
    }

    public GetTasksListResponse getTasksByUserId(GetTasksRequest getTaskReq) {
        Long userId = getTaskReq.getUserId();

        List<Long> clientIds = getTaskReq.getLocalIds();
        List<Long> dbIds = taskRepository.findTasksIdsByUserId(userId);

        if (dbIds.isEmpty()) throw new NoSuchElementException("There are no tasks with the specified user ID in the database");

        Set<Long> clientSet = new HashSet<>(clientIds);
        List<Long> missingInClient = dbIds.stream().filter(id -> !clientSet.contains(id)).toList();

        List<Task> tasksResponses = missingInClient.isEmpty() ? List.of() : taskRepository.findAllById(missingInClient);

        return new GetTasksListResponse(tasksResponses);
    }

    private boolean validateRequiredFields(String title, String date, String status, Long userId) {
        return isNotBlank(title) && isNotBlank(date) && isNotBlank(status) && isNotBlank(userId.toString());
    }
}
