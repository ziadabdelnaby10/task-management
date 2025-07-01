package com.ziad.task.mapper;

import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.entity.User;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.UpdateUserRequest;
import com.ziad.task.model.response.UserTasksResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TaskMapper.class})
public interface UserMapper {
    User toEntity(AddUserRequest request);

    UserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UpdateUserRequest updateUserRequest, @MappingTarget User user);

    @AfterMapping
    default void linkCreatedTasks(@MappingTarget User user) {
        user.getCreatedTasks().forEach(createdTask -> createdTask.setCreatedBy(user));
    }

    UserTasksResponse toUserTasks(User user);

    User toEntity(UserDto userDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(UserDto userDto, @MappingTarget User user);
}