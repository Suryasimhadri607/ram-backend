package com.ram.jwtbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;

import com.ram.jwtbackend.enums.Course;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Student {
	
	@Enumerated(EnumType.STRING)
	private Course course;
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name is required")
    private String firstname;

    @NotBlank(message = "Last name is required")
    private String lastname;

    @Column(unique = true, nullable = false)
    private String studentId; // Used as a foreign key in User table

    @Email(message = "Invalid email format")
    private String email;

    @Column(length = 10)
    private String phoneNumber;

    private String password;

    private String photo; // Can store image filename or URL (e.g., /images/student1.jpg)

 
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
