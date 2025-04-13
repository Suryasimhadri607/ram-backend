package com.ram.jwtbackend.repository;

import com.ram.jwtbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailId(String email);
}
