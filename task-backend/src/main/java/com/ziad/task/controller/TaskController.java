package com.ziad.task.controller;

import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.response.TaskUsersResponse;
import com.ziad.task.service.TaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public Page<TaskDto> getAllTasks(Pageable pageable
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String description
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String priority
    ) {
        return taskService.getAllTasks(pageable , title , description , status , priority);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Valid @NotNull UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }


    @GetMapping("/{taskId}/users")
    public ResponseEntity<TaskUsersResponse> getTaskUsersById(@PathVariable @Valid @NotNull UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskUsersById(taskId));
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid AddTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(request));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Valid @NotNull UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.accepted().build();
    }

    @DeleteMapping("/{taskId}/unassign/{userId}")
    public ResponseEntity<Void> unassignTask(@PathVariable @Valid @NotNull UUID taskId, @PathVariable @Valid @NotNull UUID userId) {
        taskService.unAssignTask(taskId, userId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Valid @NotNull UUID taskId, @RequestBody @Valid UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    @PutMapping("/{taskId}/assign-user/{userId}")
    public ResponseEntity<TaskDto> assignUser(@PathVariable @Valid @NotNull UUID taskId, @PathVariable @Valid @NotNull UUID userId) {
        return ResponseEntity.ok(taskService.assignTaskToUser(taskId, userId));
    }

    @PutMapping("/{taskId}/assign-users")
    public ResponseEntity<TaskDto> assignUsers(@PathVariable @Valid @NotNull UUID taskId, @RequestBody @Valid List<UUID> userIds) {
        return ResponseEntity.ok(taskService.assignTaskToUsers(taskId, userIds));
    }
}
