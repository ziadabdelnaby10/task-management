package com.ziad.task.model.response;

import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.entity.User;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.ziad.task.model.entity.User}
 */
public record UserTasksResponse(UUID id, String firstName, String lastName, String email, String phone,
                                User.UserRole role, Set<TaskDto> createdTasks,
                                Set<TaskDto> assignedTasks) implements Serializable {
}