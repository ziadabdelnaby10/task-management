package com.ziad.task.service;

import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.dto.TaskDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ITaskService {

    TaskDto addTask(AddTaskRequest task);

    TaskDto updateTask(UUID id , UpdateTaskRequest task);

    void deleteTask(UUID taskId);

    TaskDto getTaskById(UUID taskId);

    Page<TaskDto> getAllTasks(Pageable pageable);
}
