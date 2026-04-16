package com.example.grades.service;

import com.example.grades.model.Grade;
import com.example.grades.model.Student;
import com.example.grades.repository.GradeRepository;
import com.example.grades.repository.StudentRepository;
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
        studentRepo.save(s);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> getStudentById(String id) {
        return studentRepo.findById(id);
    }

    // 加个事务，保证查学生和存成绩要么全成功，要么全失败
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
