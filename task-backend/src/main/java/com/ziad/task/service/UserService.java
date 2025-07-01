package com.ziad.task.service;

import com.ziad.task.mapper.UserMapper;
import com.ziad.task.model.dto.UserDto;
import com.ziad.task.model.entity.User;
import com.ziad.task.model.request.AddUserRequest;
import com.ziad.task.model.request.UpdateUserRequest;
import com.ziad.task.model.response.UserTasksResponse;
import com.ziad.task.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto addUser(AddUserRequest user) {
        var savedUser = userMapper.toEntity(user);
        savedUser.setPassword(passwordEncoder.encode(user.password()));
        return userMapper.toDto(userRepository.save(savedUser));
    }

    @Modifying
    @Transactional
    @Override
    public UserDto updateUser(UUID id, UpdateUserRequest user) {
        var oldUser = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var updatedUser = userMapper.partialUpdate(user, oldUser);
        return userMapper.toDto(userRepository.save(updatedUser));
    }

    @Modifying
    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<UserDto> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserTasksResponse getUserTasksById(UUID userId) {
        var user = userRepository.findById(userId).orElseThrow(EntityNotFoundException::new);
        return userMapper.toUserTasks(user);
    }
}
