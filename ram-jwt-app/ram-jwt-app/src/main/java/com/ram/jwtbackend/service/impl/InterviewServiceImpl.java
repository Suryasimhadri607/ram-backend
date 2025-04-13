package com.ram.jwtbackend.service.impl;

import com.ram.jwtbackend.dto.InterviewDTO;
import com.ram.jwtbackend.entity.Interview;
import com.ram.jwtbackend.entity.Student;
import com.ram.jwtbackend.repository.InterviewRepository;
import com.ram.jwtbackend.repository.StudentRepository;
import com.ram.jwtbackend.service.InterviewService;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewRepository interviewRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public Interview createInterview(InterviewDTO dto) {
        Student student = studentRepository.findByStudentId(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Interview interview = new Interview();
        interview.setStudent(student);
        interview.setCompany(dto.getCompany());
        interview.setInterviewer(dto.getInterviewer());
        interview.setInterviewStartTime(dto.getInterviewStartTime());
        interview.setInterviewEndTime(dto.getInterviewEndTime());
        interview.setInterviewDate(dto.getInterviewDate());
        interview.setInterviewStatus(dto.getInterviewStatus());
        interview.setOfferLetter(dto.getOfferLetter());
        interview.setInterviewComments(dto.getInterviewComments());

        // No need to manually set createdAt or createdBy — handled in @PrePersist
        interview.setCreatedBy("admin");
        interview.setModifiedBy("admin");
        interview.setModificationCount(1);

        return interviewRepository.save(interview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Interview> getAllInterviews() {
        return interviewRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Interview> getInterviewsByStudentId(String studentId) {
        return interviewRepository.findByStudentStudentId(studentId);
    }

    @Override
    @Transactional
    public void updateInterview(Long id, InterviewDTO dto) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        interview.setCompany(dto.getCompany());
        interview.setInterviewer(dto.getInterviewer());
        interview.setInterviewStartTime(dto.getInterviewStartTime());
        interview.setInterviewEndTime(dto.getInterviewEndTime());
        interview.setInterviewDate(dto.getInterviewDate());
        interview.setInterviewStatus(dto.getInterviewStatus());
        interview.setOfferLetter(dto.getOfferLetter());
        interview.setInterviewComments(dto.getInterviewComments());
        interview.setModifiedBy("admin");
        interview.setModificationCount(interview.getModificationCount() + 1);

        interviewRepository.save(interview);
    }

    @Override
    @Transactional
    public void updateInterviewForStudent(Long id, InterviewDTO dto, String studentId) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Interview not found"));

        if (!interview.getStudent().getStudentId().equals(studentId)) {
            throw new RuntimeException("Access denied: Interview does not belong to this student");
        }

        interview.setCompany(dto.getCompany());
        interview.setInterviewer(dto.getInterviewer());
        interview.setInterviewStartTime(dto.getInterviewStartTime());
        interview.setInterviewEndTime(dto.getInterviewEndTime());
        interview.setInterviewDate(dto.getInterviewDate());
        interview.setInterviewStatus(dto.getInterviewStatus());
        interview.setOfferLetter(dto.getOfferLetter());
        interview.setInterviewComments(dto.getInterviewComments());
        interview.setModifiedBy("student");
        interview.setModificationCount(interview.getModificationCount() + 1);

        interviewRepository.save(interview);
    }

    @Override
    @Transactional
    public void deleteInterview(Long id) {
        interviewRepository.deleteById(id);
    }
}
