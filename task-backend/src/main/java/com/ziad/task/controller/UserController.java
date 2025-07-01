package com.ziad.task.controller;

import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.UpdateUserRequest;
import com.ziad.task.model.response.UserTasksResponse;
import com.ziad.task.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable @Valid @NotNull @NotEmpty UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @GetMapping("/{userId}/tasks")
    public ResponseEntity<UserTasksResponse> getUserTasks(@PathVariable @Valid @NotNull @NotEmpty  UUID userId) {
        return ResponseEntity.ok(userService.getUserTasksById(userId));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid AddUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addUser(request));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid @NotNull @NotEmpty UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @Valid @NotNull @NotEmpty  UUID userId, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }

    //TODO Implement To assign a task
}
