package com.ziad.task.model.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto implements Serializable {
    UUID id;
    String title;
    String description;
    String priority;
    String taskStats;
    LocalDateTime createdTime;
    LocalDateTime lastModifiedTime;
    LocalDate deadline;
    String createdByEmail;
}