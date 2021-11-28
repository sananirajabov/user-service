package com.sms.userservice.dto.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends UserRequest {

    private UUID id;
}
