package com.ram.jwtbackend.service.impl;

import com.ram.jwtbackend.dto.UserDTO;
import com.ram.jwtbackend.entity.User;
import com.ram.jwtbackend.repository.UserRepository;
import com.ram.jwtbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(UserDTO dto) {
        User user = new User();
        user.setEmailId(dto.getEmailId());
        user.setPhoneNumber(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(dto.getRole());
        user.setCreatedAt(LocalDateTime.now());
        user.setCreatedBy("admin");
        user.setModifiedBy("admin");
        user.setModificationCount(0);

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmailId(email.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

 
    @Override
    public User validateUser(String email, String password) {
        User user = userRepository.findByEmailId(email)
            .orElseThrow(() -> new RuntimeException("Invalid email"));
        

        System.out.println("🔒 Raw password: " + password);
        System.out.println("🔐 Encoded password from DB: " + user.getPassword());

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }


}
