package com.sd.todo_server.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.sd.todo_server.api.entities.Task;
import com.sd.todo_server.api.schemas.TaskRequest;
import com.sd.todo_server.api.schemas.TaskResponse;
import com.sd.todo_server.api.services.TaskService;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskResponse createTask(@RequestBody TaskRequest taskRequest) {
        Task task = taskService.createTask(taskRequest);
        TaskResponse taskResponse = new TaskResponse();
        taskResponse.setId(task.getId());
        taskResponse.setTitle(task.getTitle());
        taskResponse.setDescription(task.getDescription());
        taskResponse.setCreatedAt(task.getCreatedAt());
        return taskResponse;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskResponse> getTasks() {
        return taskService.getTasks();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskResponse getTask(@PathVariable String id) {
        return taskService.getTask(id);
    }
}





