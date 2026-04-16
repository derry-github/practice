package com.example.grades.model;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String courseName;
    private Double score;
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
