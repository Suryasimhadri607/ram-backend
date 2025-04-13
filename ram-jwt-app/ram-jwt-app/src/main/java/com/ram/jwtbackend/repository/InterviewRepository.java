package com.ram.jwtbackend.repository;

import com.ram.jwtbackend.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByStudentStudentId(String studentId);
}
