package com.ram.jwtbackend.service.impl;

import com.ram.jwtbackend.dto.StudentDTO;
import com.ram.jwtbackend.entity.Student;
import com.ram.jwtbackend.repository.StudentRepository;
import com.ram.jwtbackend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ram.jwtbackend.enums.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public StudentDTO createStudent(StudentDTO dto) {
    	
        // 🔍 Debug statements
        System.out.println("📨 Creating student with ID: " + dto.getStudentId());
        System.out.println("💡 Course selected: " + dto.getCourse());
        
        Student student = new Student();
        student.setFirstname(dto.getFirstName());
        student.setLastname(dto.getLastName());
        student.setStudentId(dto.getStudentId());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhone());
        student.setPassword(dto.getPassword());
        student.setPhoto(dto.getPhoto());
        student.setCourse(Course.valueOf(dto.getCourse().toUpperCase()));
        student.setCreatedAt(LocalDateTime.now());
        student.setCreatedBy("admin");
        student.setModifiedBy("admin");
        student.setModificationCount(1);

        Student savedStudent = studentRepository.save(student);
        return convertToStudentDTO(savedStudent);
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream()
            .map(this::convertToStudentDTO)
            .collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        return studentRepository.findById(id)
            .map(this::convertToStudentDTO)
            .orElse(null);
    }

    @Override
    public void updateStudent(Long id, StudentDTO dto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFirstname(dto.getFirstName());
        student.setLastname(dto.getLastName());
        student.setStudentId(dto.getStudentId());
        student.setEmail(dto.getEmail());
        student.setPhoneNumber(dto.getPhone());
        student.setPassword(dto.getPassword());
        student.setPhoto(dto.getPhoto());
        student.setCourse(Course.valueOf(dto.getCourse().toUpperCase()));
        student.setModifiedBy("admin");
        student.setUpdatedAt(LocalDateTime.now());
        student.setModificationCount(student.getModificationCount() + 1);

        studentRepository.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO convertToStudentDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setFirstName(student.getFirstname());
        dto.setLastName(student.getLastname());
        dto.setStudentId(student.getStudentId());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhoneNumber());
        dto.setPassword(student.getPassword());
        dto.setPhoto(student.getPhoto());
        dto.setCourse(student.getCourse().toString());
        return dto;
    }
}
