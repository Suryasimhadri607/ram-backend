package com.ram.jwtbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.ram.jwtbackend.enums.Role;


@Entity
@Table(name = "user_table") // match with your DB table name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailId;

    private String phoneNumber;

    private String password;

    private String createdBy;

    private String modifiedBy;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private int modificationCount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 10)
    private Role role;

    // Foreign key relation to Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

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
