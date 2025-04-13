package com.ram.jwtbackend.service;

import com.ram.jwtbackend.dto.UserDTO;
import com.ram.jwtbackend.entity.User;

public interface UserService {
    User registerUser(UserDTO userDTO);
    User validateUser(String email, String password);
    User getUserByEmail(String email);
}
