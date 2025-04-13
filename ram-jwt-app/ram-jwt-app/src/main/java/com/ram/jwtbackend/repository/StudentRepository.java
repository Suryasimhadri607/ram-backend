package com.ram.jwtbackend.repository;

import com.ram.jwtbackend.dto.StudentDTO;
import com.ram.jwtbackend.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentId(String studentId);
}
