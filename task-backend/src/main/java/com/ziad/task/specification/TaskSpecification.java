package com.ziad.task.specification;

import com.ziad.task.model.entity.Task;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecification {

    public static Specification<Task> filterTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Task> filterDescription(String description) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("description"), "%" + description.toLowerCase() + "%");
    }

    public static Specification<Task> filterPriority(String priority) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("priority"), "%" + priority.toLowerCase() + "%");
    }

    public static Specification<Task> filterStatus(String status) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("taskStats"), "%" + status.toLowerCase() + "%");
    }
}
