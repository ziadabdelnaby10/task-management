package com.ziad.task.controller;

import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.UpdateUserRequest;
import com.ziad.task.model.response.UserTasksResponse;
import com.ziad.task.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get All the users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get A Specific User")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable @Valid @NotNull UUID userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(summary = "Get Tasks of a specific user")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{userId}/tasks")
    public ResponseEntity<UserTasksResponse> getUserTasks(@PathVariable @Valid @NotNull UUID userId) {
        return ResponseEntity.ok(userService.getUserTasksById(userId));
    }

    @Operation(summary = "Delete the required user")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable @Valid @NotNull UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.accepted().build();
    }

    @Operation(summary = "Update user data")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable @Valid @NotNull  UUID userId, @RequestBody @Valid UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateUser(userId, request));
    }
}
