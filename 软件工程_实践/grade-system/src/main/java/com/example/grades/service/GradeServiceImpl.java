package com.example.grades.service;

import com.example.grades.model.Grade;
import com.example.grades.model.Student;
import com.example.grades.repository.GradeRepository;
import com.example.grades.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private GradeRepository gradeRepo;

    @PostConstruct
    public void init() {
        // 初始化管理员账号
        if (studentRepo.findById("admin").isEmpty()) {
            Student admin = new Student();
            admin.setStudentId("admin");
            admin.setName("系统管理员");
            admin.setPassword("admin");
            admin.setRole("ADMIN");
            studentRepo.save(admin);
        }
    }

    @Override
    public Optional<Student> login(String id, String password) {
        return studentRepo.findById(id)
                .filter(s -> s.getPassword().equals(password));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    @Transactional
    public void addStudent(String id, String name) {
        Student s = new Student();
        s.setStudentId(id);
        s.setName(name);
        s.setPassword("123456"); // 默认密码
        s.setRole("STUDENT");
        studentRepo.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(String id) {
        return studentRepo.findById(id);
    }

    @Override
    @Transactional
    public void updateStudent(String id, String name, String password) {
        studentRepo.findById(id).ifPresent(s -> {
            s.setName(name);
            if (password != null && !password.isEmpty()) {
                s.setPassword(password);
            }
            studentRepo.save(s);
        });
    }

    @Override
    @Transactional
    public void addGrade(String studentId, String courseName, Double score) {
        studentRepo.findById(studentId).ifPresent(s -> {
            Grade g = new Grade();
            g.setStudent(s);
            g.setCourseName(courseName);
            g.setScore(score);
            gradeRepo.save(g);
        });
    }
}
