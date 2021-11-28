package com.sms.userservice.service;

import com.sms.userservice.dto.user.UserFilter;
import com.sms.userservice.dto.user.UserRequest;
import com.sms.userservice.dto.user.UserResponse;
import com.sms.userservice.dto.user.UsersResponse;

import java.util.UUID;

public interface UserService {

    UserResponse addUser(UserRequest userRequest);

    UsersResponse filterUsers(UserFilter userFilter);

    UserResponse updateUser(UserRequest userRequest, UUID userId);

    void deleteUser(UUID userId);
}
