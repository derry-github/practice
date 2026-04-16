package com.example.grades.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    @Column(name = "student_id")
    private String studentId; // 学号

    private String name; // 姓名

    // 一个学生对应多个成绩，删学生时把成绩也一块儿删了
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;
}
