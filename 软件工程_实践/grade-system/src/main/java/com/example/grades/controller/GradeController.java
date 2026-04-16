package com.example.grades.controller;

import com.example.grades.model.Student;
import com.example.grades.service.GradeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("students", gradeService.getAllStudents());
        model.addAttribute("courses", gradeService.getAllCourses());
        return "index";
    }

    // 学生管理
    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String id, @RequestParam String name) {
        gradeService.addStudent(id, name);
        return "redirect:/";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable String id) {
        gradeService.deleteStudent(id);
        return "redirect:/";
    }

    @PostMapping("/updateStudent")
    public String updateStudent(@RequestParam String id, @RequestParam String name, @RequestParam(required = false) String password, HttpSession session) {
        gradeService.updateStudent(id, name, password);
        Student currentUser = (Student) session.getAttribute("user");
        if (currentUser != null && currentUser.getStudentId().equals(id)) {
            gradeService.getStudentById(id).ifPresent(s -> session.setAttribute("user", s));
        }
        return "redirect:/student/" + id + "?success";
    }

    @GetMapping("/student/{id}")
    public String viewGrades(@PathVariable String id, Model model) {
        gradeService.getStudentById(id).ifPresent(s -> model.addAttribute("student", s));
        model.addAttribute("allCourses", gradeService.getAllCourses());
        return "student_view";
    }

    // 课程管理
    @PostMapping("/addCourse")
    public String addCourse(@RequestParam String code, @RequestParam String name) {
        gradeService.addCourse(code, name);
        return "redirect:/";
    }

    @GetMapping("/deleteCourse/{code}")
    public String deleteCourse(@PathVariable String code) {
        gradeService.deleteCourse(code);
        return "redirect:/";
    }

    // 成绩管理
    @PostMapping("/addGrade")
    public String addGrade(@RequestParam String studentId, @RequestParam String courseCode, @RequestParam Double score) {
        gradeService.addGrade(studentId, courseCode, score);
        return "redirect:/student/" + studentId;
    }

    @GetMapping("/deleteGrade/{studentId}/{gradeId}")
    public String deleteGrade(@PathVariable String studentId, @PathVariable Long gradeId) {
        gradeService.deleteGrade(gradeId);
        return "redirect:/student/" + studentId;
    }

    @PostMapping("/updateGrade")
    public String updateGrade(@RequestParam String studentId, @RequestParam Long gradeId, @RequestParam Double score) {
        gradeService.updateGrade(gradeId, score);
        return "redirect:/student/" + studentId;
    }
}
