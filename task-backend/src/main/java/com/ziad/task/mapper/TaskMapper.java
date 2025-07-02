package com.ziad.task.mapper;

import com.ziad.task.model.request.AddTaskRequest;
import com.ziad.task.model.dto.TaskDto;
import com.ziad.task.model.entity.Task;
import com.ziad.task.model.request.UpdateTaskRequest;
import com.ziad.task.model.response.TaskUsersResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface TaskMapper {

    Task toEntity(AddTaskRequest request);

    @Mapping(target = "createdByEmail" , source = "createdBy.email")
    TaskDto toDto(Task task);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Task partialUpdate(UpdateTaskRequest updateTaskRequest, @MappingTarget Task task);

    TaskUsersResponse toTaskUser(Task task);

}