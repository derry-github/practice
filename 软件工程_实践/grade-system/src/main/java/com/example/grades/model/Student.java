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

    private String password; // 密码

    private String role; // 角色: ADMIN, STUDENT

    // 一个学生对应多个成绩，删学生时把成绩也一块儿删了
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Grade> grades;
}
