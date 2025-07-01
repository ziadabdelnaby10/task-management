package com.ziad.task.model.dto;

import com.ziad.task.model.entity.User;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.ziad.task.model.entity.User}
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private User.UserRole role;
}