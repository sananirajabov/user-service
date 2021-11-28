package com.sms.userservice.dto.user;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserFilter {

    private List<UUID> ids;
    private String role;
    private String name;
    private String surname;
    private String email;
}
