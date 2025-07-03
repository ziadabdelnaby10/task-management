package com.ziad.task.service;

import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.response.TaskUsersResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ITaskService {

    TaskDto addTask(AddTaskRequest task);

    TaskDto updateTask(UUID id , UpdateTaskRequest task);

    void deleteTask(UUID taskId);

    TaskDto getTaskById(UUID taskId);

    Page<TaskDto> getAllTasks(Pageable pageable, String search, String status, String priority);

    TaskUsersResponse getTaskUsersById(UUID taskId);

    TaskDto assignTaskToUser(UUID taskId, UUID userId);

    TaskDto assignTaskToUsers(UUID taskId, List<UUID> userIds);

    void unAssignTask(UUID taskId, UUID userId);
}
