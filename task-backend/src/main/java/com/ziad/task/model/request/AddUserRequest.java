package com.ziad.task.model.request;

import com.ziad.task.model.entity.User;
import jakarta.validation.constraints.*;

import java.io.Serializable;

/**
 * DTO for {@link com.ziad.task.model.entity.User}
 */
public record AddUserRequest(
        @NotNull(message = "Must Provide First Name.")
        @NotBlank(message = "First Name cannot be empty.")
        String firstName,
        @NotNull(message = "Must Provide Second Name.")
        @NotEmpty(message = " Second Name cannot be empty.")
        String lastName,
        @NotNull(message = "Must Provide Password.")
        @NotEmpty(message = "Password cannot be empty.")
        String password,
        @NotNull(message = "Must Provide The Email")
        @NotEmpty(message = "Email Cannot be empty")
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,
        String phone,
        User.UserRole role) implements Serializable {
}