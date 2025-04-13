package com.ram.jwtbackend.controller;

import com.ram.jwtbackend.dto.JwtResponse;
import com.ram.jwtbackend.dto.LoginRequest;
import com.ram.jwtbackend.dto.UserDTO;
import com.ram.jwtbackend.entity.User;
import com.ram.jwtbackend.service.UserService;
import com.ram.jwtbackend.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    // ✅ Register endpoint

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Registration Failed. Please try again.");
        }
    }

    


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            System.out.println("🔐 Login attempt for email: " + loginRequest.getEmailId());

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmailId(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtUtils.generateToken(authentication);
            System.out.println("✅ JWT Token generated: " + token);

            return ResponseEntity.ok(new JwtResponse(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(new JwtResponse("Login failed: " + e.getMessage()));
        }
    }

}
