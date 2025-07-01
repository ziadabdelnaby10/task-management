package com.ziad.task.model.request;

import com.ziad.task.model.entity.Task;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public record AddTaskRequest(
        @NotNull(message = "Must Provide Title.")
        @NotBlank(message = "Title cannot be empty.")
        String title,
        String description,
        Task.TaskStatus taskStats,
        LocalDate deadline,
        @NotNull(message = "Must Provide User ID")
        @NotEmpty(message = "User Id Cannot be empty")
        @org.hibernate.validator.constraints.UUID
        UUID createdByUserId) implements Serializable {
}