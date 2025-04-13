package com.ram.jwtbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.ram.jwtbackend.dto.StudentDTO;

@Entity
@Table(name = "interview")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Interview {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Foreign key to Student
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    private String company;

    private String interviewer;

    private LocalTime interviewStartTime;

    private LocalTime interviewEndTime;

    private LocalDate interviewDate;

    private String interviewStatus; // e.g., Scheduled, Completed, Selected, Rejected

    private String offerLetter; // Could be file path or status ("Yes"/"No")

    private String interviewComments;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int modificationCount;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.modificationCount++;
    }

}
