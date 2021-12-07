package com.rajaboff.userservice.resource;

import com.rajaboff.userservice.dto.user.UserRequest;
import com.rajaboff.userservice.dto.user.UserResponse;
import com.rajaboff.userservice.service.UserService;
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

    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(description = "Registered new user", responseCode = "200"),
            @ApiResponse(description = "User with email already exists", responseCode = "400"),
            @ApiResponse(description = "Invalid request body", responseCode = "400")
    })
    @PostMapping
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.registerUser(userRequest));
    }

    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(description = "Updated user", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404"),
            @ApiResponse(description = "Email is not updatable", responseCode = "400"),
            @ApiResponse(description = "Invalid request body", responseCode = "400")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID userId,
                                                   @Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.updateUser(userId, userRequest));
    }

    @Operation(summary = "Delete user")
    @ApiResponses(value = {
            @ApiResponse(description = "User deleted", responseCode = "204"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
