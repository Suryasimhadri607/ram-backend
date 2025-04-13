package com.ram.jwtbackend.dto;
import lombok.Data;

@Data


public class StudentDTO {
	
    private String firstName;
    private String lastName;
    private String studentId;
    private String email;
    private String phone;
    private String photo; // base64 encoded or URL
    private String course;
    private String password;

}
