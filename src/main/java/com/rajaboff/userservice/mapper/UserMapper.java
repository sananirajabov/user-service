package com.rajaboff.userservice.mapper;

import com.rajaboff.userservice.dto.user.UserRequest;
import com.rajaboff.userservice.dto.user.UserResponse;
import com.rajaboff.userservice.dto.user.UsersResponse;
import com.rajaboff.userservice.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper() {
    }

    public static User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .surname(userRequest.getSurname())
                .email(userRequest.getEmail())
                .build();
    }

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
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
