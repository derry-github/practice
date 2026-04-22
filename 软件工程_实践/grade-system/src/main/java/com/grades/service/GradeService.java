package com.grades.service;

import com.grades.model.Course;
import com.grades.model.Student;
import java.util.List;
import java.util.Optional;

public interface GradeService {
    // 登录与基础查询
    Optional<Student> login(String id, String password);
    List<Student> getAllStudents();
    Optional<Student> getStudentById(String id);

    // 学生维护
    void addStudent(String id, String name);
    void updateStudent(String id, String name, String password);
    void deleteStudent(String id);

    // 课程维护
    List<Course> getAllCourses();
    void addCourse(String code, String name);
    void deleteCourse(String code);

    // 成绩维护
    void addGrade(String studentId, String courseCode, Double score);
    void deleteGrade(Long gradeId);
    void updateGrade(Long gradeId, Double score);
}
