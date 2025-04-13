package com.ram.jwtbackend.controller;

import com.ram.jwtbackend.dto.StudentDTO;
import com.ram.jwtbackend.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StudentService studentService;

    // ✅ FIXED: Use GET for fetching all students
    @GetMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/students")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO dto) {
        StudentDTO savedStudent = studentService.createStudent(dto);
        return ResponseEntity.ok(savedStudent);
    }

    @PutMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateStudent(@PathVariable Long id, @RequestBody StudentDTO dto) {
        studentService.updateStudent(id, dto);
        return ResponseEntity.ok("Student updated successfully");
    }

    @DeleteMapping("/students/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}
