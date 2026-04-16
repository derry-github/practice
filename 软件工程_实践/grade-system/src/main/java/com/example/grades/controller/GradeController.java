package com.example.grades.controller;

import com.example.grades.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    // 进入主页，带上所有学生数据
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("students", gradeService.getAllStudents());
        return "index";
    }

    // 提交添加学生的表单
    @PostMapping("/addStudent")
    public String addStudent(@RequestParam String id, @RequestParam String name) {
        gradeService.addStudent(id, name);
        return "redirect:/";
    }

    // 查特定学号的详情（成绩单）
    @GetMapping("/student/{id}")
    public String viewGrades(@PathVariable String id, Model model) {
        gradeService.getStudentById(id).ifPresent(s -> model.addAttribute("student", s));
        return "student_view";
    }

    // 给学生加成绩
    @PostMapping("/addGrade")
    public String addGrade(@RequestParam String studentId, @RequestParam String courseName, @RequestParam Double score) {
        gradeService.addGrade(studentId, courseName, score);
        return "redirect:/student/" + studentId;
    }
}
