package com.ziad.task.service;

import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.UpdateUserRequest;
import com.ziad.task.model.response.UserTasksResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserDto addUser(AddUserRequest user);

    UserDto updateUser(UUID id, UpdateUserRequest user);

    void deleteUser(UUID userId);

    UserDto getUserById(UUID userId);

    List<UserDto> getAllUsers();

    UserTasksResponse getUserTasksById(UUID userId);
}
