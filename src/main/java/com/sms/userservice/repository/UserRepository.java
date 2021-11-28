package com.sms.userservice.repository;

import com.sms.userservice.dto.user.UserRole;
import com.sms.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findUserByEmail(String email);

    List<User> findUsersByRoleOrNameOrSurnameOrEmail(UserRole role,
                                                     String name,
                                                     String surname,
                                                     String email);
}