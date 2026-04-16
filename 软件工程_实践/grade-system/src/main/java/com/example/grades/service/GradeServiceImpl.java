package com.example.grades.service;

import com.example.grades.model.Course;
import com.example.grades.model.Grade;
import com.example.grades.model.Student;
import com.example.grades.repository.CourseRepository;
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
    @Autowired
    private CourseRepository courseRepo;

    @PostConstruct
    public void init() {
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
        return studentRepo.findById(id).filter(s -> s.getPassword().equals(password));
    }

    @Override
    public List<Student> getAllStudents() { return studentRepo.findAll(); }

    @Override
    public Optional<Student> getStudentById(String id) { return studentRepo.findById(id); }

    @Override
    @Transactional
    public void addStudent(String id, String name) {
        Student s = new Student();
        s.setStudentId(id);
        s.setName(name);
        s.setPassword("123456");
        s.setRole("STUDENT");
        studentRepo.save(s);
    }

    @Override
    @Transactional
    public void updateStudent(String id, String name, String password) {
        studentRepo.findById(id).ifPresent(s -> {
            s.setName(name);
            if (password != null && !password.isEmpty()) s.setPassword(password);
            studentRepo.save(s);
        });
    }

    @Override
    @Transactional
    public void deleteStudent(String id) { studentRepo.deleteById(id); }

    @Override
    public List<Course> getAllCourses() { return courseRepo.findAll(); }

    @Override
    @Transactional
    public void addCourse(String code, String name) {
        Course c = new Course();
        c.setCourseCode(code);
        c.setCourseName(name);
        courseRepo.save(c);
    }

    @Override
    @Transactional
    public void deleteCourse(String code) { courseRepo.deleteById(code); }

    @Override
    @Transactional
    public void addGrade(String studentId, String courseCode, Double score) {
        studentRepo.findById(studentId).ifPresent(s -> {
            courseRepo.findById(courseCode).ifPresent(c -> {
                Grade g = new Grade();
                g.setStudent(s);
                g.setCourse(c);
                g.setScore(score);
                gradeRepo.save(g);
            });
        });
    }

    @Override
    @Transactional
    public void deleteGrade(Long gradeId) { gradeRepo.deleteById(gradeId); }

    @Override
    @Transactional
    public void updateGrade(Long gradeId, Double score) {
        gradeRepo.findById(gradeId).ifPresent(g -> {
            g.setScore(score);
            gradeRepo.save(g);
        });
    }
}
