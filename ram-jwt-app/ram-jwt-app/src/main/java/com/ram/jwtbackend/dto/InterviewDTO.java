package com.ram.jwtbackend.dto;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data

public class InterviewDTO {
    private String studentId;  // foreign key
    private String company;
    private String interviewer;
    private LocalTime interviewStartTime;
    private LocalTime interviewEndTime;
    private LocalDate interviewDate;
    private String interviewStatus;
    private String offerLetter;
    private String interviewComments;

}
