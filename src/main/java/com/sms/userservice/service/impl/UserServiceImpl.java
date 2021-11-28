package com.sms.userservice.service.impl;

import com.sms.userservice.dto.user.*;
import com.sms.userservice.exception.EntityNotFoundException;
import com.sms.userservice.exception.ValidationFailedException;
import com.sms.userservice.model.User;
import com.sms.userservice.repository.UserRepository;
import com.sms.userservice.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.sms.userservice.mapper.UserMapper.*;
import static com.sms.userservice.util.ErrorResponseFactory.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse addUser(UserRequest userRequest) {
        validateEmailExists(userRequest);

        User user = toUser(userRequest);
        user.setId(UUID.randomUUID());

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UsersResponse filterUsers(UserFilter userFilter) {
        UserRole userRole = userFilter.getRole() == null
                ? UserRole.STUDENT
                : UserRole.valueOf(userFilter.getRole());

        List<User> filteredUsers = userRepository.findUsersByRoleOrNameOrSurnameOrEmail(
                userRole,
                userFilter.getName(),
                userFilter.getSurname(),
                userFilter.getEmail());

        return toUsersResponse(filteredUsers);
    }

    @Override
    public UserResponse updateUser(UserRequest userRequest, UUID userId) {
        validateUpdateUser(userRequest, userId);

        User user = toUser(userRequest);
        user.setId(userId);

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID userId) {
        validateUserExists(userId);
        userRepository.deleteById(userId);
    }

    private void validateUserExists(UUID userId) {
        if (userRepository.findById(userId).isEmpty()) {
            throw new EntityNotFoundException(buildEntityNotFound(userId));
        }
    }

    private void validateEmailExists(UserRequest userRequest) {
        if (userRepository.findUserByEmail(userRequest.getEmail()).isPresent()) {
            throw new ValidationFailedException(buildEmailAlreadyExistsError(userRequest.getEmail()));
        }
    }

    private void validateUpdateUser(UserRequest userRequest, UUID userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException(buildEntityNotFound(userId));
        }

        if (!user.get().getEmail().equals(userRequest.getEmail())) {
            throw new ValidationFailedException(buildEmailIsNotUpdatable(user.get().getEmail()));
        }
    }
}
