package com.grades.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_code")
    private Course course;

    private Double score;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
