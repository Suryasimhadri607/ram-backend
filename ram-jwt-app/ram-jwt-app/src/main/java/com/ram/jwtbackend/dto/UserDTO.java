package com.ram.jwtbackend.dto;

import com.ram.jwtbackend.enums.Role;

import lombok.Data;

@Data

public class UserDTO {
    private String emailId;
    private String password;
    private String phone;
    private Role role;

}
