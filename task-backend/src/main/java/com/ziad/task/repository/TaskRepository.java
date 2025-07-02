package com.ziad.task.repository;

import com.ziad.task.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID>, JpaSpecificationExecutor<Task> {

    @Query("select t from Task t left join fetch t.assignedToUsers where t.id =:taskId")
    Optional<Task> findTaskUsersById(@Param("taskId") UUID taskId);
}