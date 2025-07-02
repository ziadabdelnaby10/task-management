package com.ziad.task.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
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
    @JsonIgnore
    private String password;
    private String email;
    private String phone;
    private User.UserRole role;
}