package com.ziad.task.service;

import com.ziad.task.mapper.TaskMapper;
import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.response.TaskUsersResponse;
import com.ziad.task.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Override
    public TaskDto addTask(AddTaskRequest task) {
        var taskEntity = taskMapper.toEntity(task);
        return taskMapper.toDto(taskRepository.save(taskEntity));
    }

    @Transactional
    @Override
    public TaskDto updateTask(UUID id , UpdateTaskRequest task) {
        var oldTask = taskRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var updatedTask = taskMapper.partialUpdate(task , oldTask);
        return taskMapper.toDto(taskRepository.save(updatedTask));
    }

    @Modifying
    @Transactional
    @Override
    public void deleteTask(UUID taskId) {
        taskRepository.deleteById(taskId);
    }

    @Transactional(readOnly = true)
    @Override
    public TaskDto getTaskById(UUID taskId) {
        return taskMapper.toDto(taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<TaskDto> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable).map(taskMapper::toDto);
    }

    @Override
    public TaskUsersResponse getTaskUsersById(UUID taskId) {
        var task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        return taskMapper.toTaskUser(task);
    }
}
