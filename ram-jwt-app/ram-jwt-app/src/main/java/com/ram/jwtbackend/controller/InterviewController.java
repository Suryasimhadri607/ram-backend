package com.ram.jwtbackend.controller;

import com.ram.jwtbackend.dto.InterviewDTO;
import com.ram.jwtbackend.entity.Interview;
import com.ram.jwtbackend.entity.User;
import com.ram.jwtbackend.service.InterviewService;
import com.ram.jwtbackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
public class InterviewController {

    private final InterviewService interviewService;
    private final UserService userService;

    // =================== ADMIN ===================

    @PostMapping("/admin/add")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createInterview(@RequestBody InterviewDTO dto) {
        interviewService.createInterview(dto);
        return ResponseEntity.ok("Interview created successfully");
    }

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<InterviewDTO>> getAllInterviews() {
        List<Interview> interviews = interviewService.getAllInterviews();
        List<InterviewDTO> dtos = interviews.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateInterview(@PathVariable Long id, @RequestBody InterviewDTO dto) {
        interviewService.updateInterview(id, dto);
        return ResponseEntity.ok("Interview updated successfully");
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok("Interview deleted successfully");
    }

    // =================== STUDENT ===================

    @GetMapping("/student")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<InterviewDTO>> getMyInterviews(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        List<Interview> interviews = interviewService.getInterviewsByStudentId(user.getStudent().getStudentId());
        List<InterviewDTO> dtos = interviews.stream().map(this::convertToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/student/update/{id}")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<String> updateMyInterview(@PathVariable Long id,
                                                    @RequestBody InterviewDTO dto,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getUserByEmail(userDetails.getUsername());
        interviewService.updateInterviewForStudent(id, dto, user.getStudent().getStudentId());
        return ResponseEntity.ok("Your interview updated successfully");
    }

    // ========== PRIVATE CONVERTER METHOD ==========

    private InterviewDTO convertToDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setStudentId(interview.getStudent().getStudentId());
        dto.setCompany(interview.getCompany());
        dto.setInterviewer(interview.getInterviewer());
        dto.setInterviewStartTime(interview.getInterviewStartTime());
        dto.setInterviewEndTime(interview.getInterviewEndTime());
        dto.setInterviewDate(interview.getInterviewDate());
        dto.setInterviewStatus(interview.getInterviewStatus());
        dto.setOfferLetter(interview.getOfferLetter());
        dto.setInterviewComments(interview.getInterviewComments());
        return dto;
    }
}
