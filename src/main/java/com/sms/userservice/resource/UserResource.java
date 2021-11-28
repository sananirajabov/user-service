package com.sms.userservice.resource;

import com.sms.userservice.dto.user.UserFilter;
import com.sms.userservice.dto.user.UserRequest;
import com.sms.userservice.dto.user.UserResponse;
import com.sms.userservice.dto.user.UsersResponse;
import com.sms.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "User")
@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add new user")
    @ApiResponses(value = {
            @ApiResponse(description = "Added new user", responseCode = "201"),
            @ApiResponse(description = "User with email already exists", responseCode = "400"),
            @ApiResponse(description = "Invalid request body", responseCode = "400")
    })
    @PostMapping
    public ResponseEntity<UserResponse> addUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.addUser(userRequest));
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(description = "User updated", responseCode = "200"),
            @ApiResponse(description = "Email is not updatable", responseCode = "400"),
            @ApiResponse(description = "Invalid request body", responseCode = "400"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@Valid @RequestBody UserRequest userRequest,
                                                   @PathVariable UUID id) {
        return ResponseEntity.ok(userService.updateUser(userRequest, id));
    }

    @Operation(summary = "Filter users")
    @ApiResponse(description = "Users filtered", responseCode = "200")
    @PostMapping("/filter")
    public ResponseEntity<UsersResponse> filterUsers(@RequestBody UserFilter userFilter) {
        return ResponseEntity.ok(userService.filterUsers(userFilter));
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted", responseCode = "204"),
            @ApiResponse(description = "User not found", responseCode = "404"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
