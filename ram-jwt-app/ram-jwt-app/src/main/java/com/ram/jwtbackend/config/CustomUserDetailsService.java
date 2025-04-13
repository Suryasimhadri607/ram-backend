package com.ram.jwtbackend.config;

import com.ram.jwtbackend.entity.User;
import com.ram.jwtbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmailId(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Print role for debug (optional)
        System.out.println("🛡️ Loaded user with role: " + user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getEmailId(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
}
