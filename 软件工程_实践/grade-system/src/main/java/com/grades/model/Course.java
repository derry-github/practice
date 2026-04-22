package com.grades.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Course {
    @Id
    @Column(name = "course_code")
    private String courseCode; // 课程代码

    private String courseName; // 课程名称
}
