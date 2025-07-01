package com.ziad.task.model.response;

import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.entity.Task;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.ziad.task.model.entity.Task}
 */
public record TaskUsersResponse(UUID id, String title, String description, Task.TaskStatus taskStats,
                                LocalDateTime createdTime, LocalDateTime lastModifiedTime, LocalDate deadline,
                                UserDto createdBy, Set<UserDto> assignedToUsers) implements Serializable {
}