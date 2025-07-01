package com.ziad.task.model.request;

import com.ziad.task.model.entity.User;

import java.io.Serializable;

/**
 * DTO for {@link com.ziad.task.model.entity.User}
 */
public record UpdateUserRequest(String firstName, String lastName, String phone,
                                User.UserRole role) implements Serializable {
}