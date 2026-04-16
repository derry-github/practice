package com.example.grades.service;

import com.example.grades.model.Student;
import java.util.List;
import java.util.Optional;

// 成绩管理的业务逻辑接口
public interface GradeService {
    // 登录验证
    Optional<Student> login(String id, String password);

    // 拿所有的学生列表
    List<Student> getAllStudents();

    // 存一个新学生
    void addStudent(String id, String name);

    // 查某个学生的详细信息
    Optional<Student> getStudentById(String id);

    // 给学生加一门课的成绩
    void addGrade(String studentId, String courseName, Double score);

    // 更新学生信息
    void updateStudent(String id, String name, String password);
}
