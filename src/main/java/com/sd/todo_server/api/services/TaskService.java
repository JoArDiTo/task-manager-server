package com.sd.todo_server.api.services;

import com.sd.todo_server.api.entities.Task;
import com.sd.todo_server.api.repositories.jpa.TaskRepository;
import com.sd.todo_server.api.schemas.TaskRequest;
import com.sd.todo_server.api.schemas.TaskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskRequest taskRequest) {
        Task task = new Task();
        task.setId(UUID.randomUUID().toString());
        task.setTitle(taskRequest.getTitle());
        task.setDescription(taskRequest.getDescription());
        task.setCreatedAt(getCurrentTime());
        return taskRepository.save(task);
    }

    public List<TaskResponse> getTasks() {
        List<Task> tasks = taskRepository.findAll();
        return tasks.stream().map(this::mapToTaskResponse).toList();
    }

    public TaskResponse getTask(String id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return mapToTaskResponse(task);
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .build();
    }

    private LocalDateTime getCurrentTime() {
        return LocalDateTime.now();
    }

}
