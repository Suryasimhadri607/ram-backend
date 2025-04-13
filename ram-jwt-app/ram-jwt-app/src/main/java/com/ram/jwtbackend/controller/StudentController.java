package com.ram.jwtbackend.controller;

import com.ram.jwtbackend.dto.StudentDTO;
import com.ram.jwtbackend.entity.User;
import com.ram.jwtbackend.service.StudentService;
import com.ram.jwtbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final UserService userService;
    private final StudentService studentService;

    // =================== Student Profile (Self Access) ===================

    @GetMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<StudentDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(studentService.getStudentById(user.getStudent().getId()));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                @RequestBody StudentDTO dto) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        studentService.updateStudent(user.getStudent().getId(), dto);
        return ResponseEntity.ok("Profile updated");
    }
}
