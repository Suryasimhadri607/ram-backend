package com.ram.jwtbackend.service;

import com.ram.jwtbackend.dto.InterviewDTO;
import com.ram.jwtbackend.entity.Interview;

import java.util.List;

public interface InterviewService {

    Interview createInterview(InterviewDTO interviewDTO);

    List<Interview> getAllInterviews(); // Admin can fetch all interviews

    List<Interview> getInterviewsByStudentId(String studentId); // Student-specific fetch

    void updateInterview(Long id, InterviewDTO interviewDTO); // Admin update

    void updateInterviewForStudent(Long id, InterviewDTO interviewDTO, String studentId); // Student update

    void deleteInterview(Long id); // Admin delete
}
