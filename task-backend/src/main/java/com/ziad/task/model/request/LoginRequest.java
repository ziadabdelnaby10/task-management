package com.ziad.task.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginRequest(
        @NotNull(message = "Must Provide The Email")
        @NotEmpty(message = "Email Cannot be empty")
        @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
        String email,
        @NotNull(message = "Must Provide Password.")
        @NotEmpty(message = "Password cannot be empty.")
        String password
) {
}
