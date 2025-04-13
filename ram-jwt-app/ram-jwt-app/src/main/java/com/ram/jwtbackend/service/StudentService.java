package com.ram.jwtbackend.service;

import com.ram.jwtbackend.dto.StudentDTO;


import java.util.List;

public interface StudentService {
    StudentDTO createStudent(StudentDTO studentDTO);
    List<StudentDTO> getAllStudents();
    StudentDTO getStudentById(Long id);
    void updateStudent(Long id, StudentDTO dto);
    void deleteStudent(Long id);
}
