package com.ziad.task.controller;

import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.request.LoginRequest;
import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.response.TaskUsersResponse;
import com.ziad.task.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    @Operation(summary = "Get Paginated List Of Tasks")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TaskDto> getAllTasks(Pageable pageable
            , @RequestParam(required = false) String search
            , @RequestParam(required = false) String status
            , @RequestParam(required = false) String priority
    ) {
        return taskService.getAllTasks(pageable , search , status , priority);
    }

    @Operation(summary = "Get The Required Task")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable @Valid @NotNull UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @Operation(summary = "Get The Required Task with it's users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{taskId}/users")
    public ResponseEntity<TaskUsersResponse> getTaskUsersById(@PathVariable @Valid @NotNull UUID taskId) {
        return ResponseEntity.ok(taskService.getTaskUsersById(taskId));
    }

    @Operation(summary = "Create A New Task")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody @Valid AddTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTask(request));
    }

    @Operation(summary = "Delete Required Task")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable @Valid @NotNull UUID taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Unassign Required Task for a user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{taskId}/unassign/{userId}")
    public ResponseEntity<Void> unassignTask(@PathVariable @Valid @NotNull UUID taskId, @PathVariable @Valid @NotNull UUID userId) {
        taskService.unAssignTask(taskId, userId);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Update a Required Task")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable @Valid @NotNull UUID taskId, @RequestBody @Valid UpdateTaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    @Operation(summary = "Assign Required Task for a user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{taskId}/assign-user/{userId}")
    public ResponseEntity<TaskDto> assignUser(@PathVariable @Valid @NotNull UUID taskId, @PathVariable @Valid @NotNull UUID userId) {
        return ResponseEntity.ok(taskService.assignTaskToUser(taskId, userId));
    }

    @Operation(summary = "Assign Required Task for a list of users")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{taskId}/assign-users")
    public ResponseEntity<TaskDto> assignUsers(@PathVariable @Valid @NotNull UUID taskId, @RequestBody @Valid List<UUID> userIds) {
        return ResponseEntity.ok(taskService.assignTaskToUsers(taskId, userIds));
    }
}
