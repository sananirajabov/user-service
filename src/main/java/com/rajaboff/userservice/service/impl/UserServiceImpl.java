package com.rajaboff.userservice.service.impl;

import com.rajaboff.httphandler.exception.EntityNotFoundException;
import com.rajaboff.httphandler.exception.ValidationFailedException;
import com.rajaboff.userservice.dto.user.UserRequest;
import com.rajaboff.userservice.dto.user.UserResponse;
import com.rajaboff.userservice.model.User;
import com.rajaboff.userservice.repository.UserRepository;
import com.rajaboff.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.rajaboff.userservice.mapper.UserMapper.toUser;
import static com.rajaboff.userservice.mapper.UserMapper.toUserResponse;
import static com.rajaboff.userservice.util.ErrorResponseFactory.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserResponse loginUser(String email, String password) {
        return null;
    }

    @Override
    public UserResponse registerUser(UserRequest userRequest) {
        validateUserEmailExists(userRequest.getEmail());
        User user =  toUser(userRequest);

        user.setId(UUID.randomUUID());
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateUser(UUID userId, UserRequest userRequest) {
        User user = validateUserExists(userId);
        validateEmailIsSameWithOldOne(user.getEmail(), userRequest.getEmail());

        user.setName(userRequest.getName());
        user.setSurname(userRequest.getSurname());
        user.setPasswordHash(passwordEncoder.encode(userRequest.getPassword()));

        return toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(UUID userId) {
        validateUserExists(userId);
        userRepository.deleteById(userId);
    }

    private User validateUserExists(UUID userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException(buildEntityNotFound(userId));
        }

        return user.get();
    }

    private void validateEmailIsSameWithOldOne(String oldEmail, String newEmail) {
        if (!oldEmail.equals(newEmail)) {
            throw new ValidationFailedException(buildEmailIsNotUpdatable(oldEmail));
        }
    }

    private void validateUserEmailExists(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);

        if (user.isPresent()) {
            throw new ValidationFailedException(buildEmailAlreadyExistsError(email));
        }
    }
}
