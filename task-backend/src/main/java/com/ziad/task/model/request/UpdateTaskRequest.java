package com.ziad.task.model.request;

import com.ziad.task.model.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.ziad.task.model.entity.Task}
 */
public record UpdateTaskRequest(
        @NotNull(message = "Must Provide Title.")
        @NotBlank(message = "Title cannot be empty.")
        String title,
        String description,
        String priority,
        String taskStats,
        LocalDate deadline
) implements Serializable {
}