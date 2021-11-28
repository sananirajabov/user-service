package com.sms.userservice.mapper;

import com.sms.userservice.dto.user.UserRequest;
import com.sms.userservice.dto.user.UserResponse;
import com.sms.userservice.dto.user.UserRole;
import com.sms.userservice.dto.user.UsersResponse;
import com.sms.userservice.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User toUser(UserRequest userRequest) {
        return User.builder()
                .role(UserRole.valueOf(userRequest.getRole()))
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .role(user.getRole().name())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();
    }

    public static UsersResponse toUsersResponse(List<User> users) {
        List<UserResponse> userResponses = users.stream()
                .map(UserMapper::toUserResponse)
                .collect(Collectors.toList());

        return UsersResponse.builder()
                .usersResponses(userResponses)
                .build();
    }
}
