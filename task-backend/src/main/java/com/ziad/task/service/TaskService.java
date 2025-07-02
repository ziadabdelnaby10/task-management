package com.ziad.task.service;

import com.ziad.task.mapper.TaskMapper;
import com.ziad.task.model.entity.Task;
import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.response.TaskUsersResponse;
import com.ziad.task.repository.TaskRepository;
import com.ziad.task.repository.UserRepository;
import com.ziad.task.specification.TaskSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TaskService implements ITaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final UserRepository userRepository;

    @Override
    public TaskDto addTask(AddTaskRequest task) {
        var taskEntity = taskMapper.toEntity(task);
//        var createdUser = userRepository.getReferenceById(task.createdByUserId());//This is better with jpa because it only references the user entity but not executing a query for it
        var createdUser = userRepository.findById(task.createdByUserId()).orElseThrow(EntityNotFoundException::new);
        taskEntity.setCreatedBy(createdUser);
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
    public Page<TaskDto> getAllTasks(Pageable pageable, String title, String description, String status, String priority) {
        Specification<Task> taskSpecification = TaskSpecification.filterTitle("");
        if(title!= null && !title.isEmpty())
            taskSpecification.and(TaskSpecification.filterTitle(title));
        if(description != null && !description.isEmpty())
            taskSpecification.and(TaskSpecification.filterDescription(description));
        if(status != null && !status.isEmpty())
            taskSpecification.and(TaskSpecification.filterStatus(status));
        if(priority != null && !priority.isEmpty())
            taskSpecification.and(TaskSpecification.filterPriority(priority));
        return taskRepository.findAll(taskSpecification, pageable).map(taskMapper::toDto);
    }

    @Override
    public TaskUsersResponse getTaskUsersById(UUID taskId) {
        var task = taskRepository.findTaskUsersById(taskId).orElseThrow(EntityNotFoundException::new);
        return taskMapper.toTaskUser(task);
    }

    @Transactional
    @Override
    public TaskDto assignTaskToUser(UUID taskId, UUID userId) {
        var task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        var taskUser = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        task.getAssignedToUsers().add(taskUser);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    @Override
    public TaskDto assignTaskToUsers(UUID taskId, List<UUID> userIds) {
        var task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        var users = userIds.stream().map(userRepository::getReferenceById).collect(Collectors.toSet());
        task.setAssignedToUsers(users);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Modifying
    @Transactional
    @Override
    public void unAssignTask(UUID taskId, UUID userId) {
        var task = taskRepository.findById(taskId).orElseThrow(EntityNotFoundException::new);
        var taskUser = userRepository.getReferenceById(userId);
        task.getAssignedToUsers().remove(taskUser);
    }
}
