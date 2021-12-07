package com.rajaboff.userservice.service;

import com.rajaboff.userservice.dto.user.UserRequest;
import com.rajaboff.userservice.dto.user.UserResponse;

import java.util.UUID;

public interface UserService {

    UserResponse loginUser(String email, String password);

    UserResponse registerUser(UserRequest userRequest);

    UserResponse updateUser(UUID userId, UserRequest userRequest);

    void deleteUser(UUID userId);
}
