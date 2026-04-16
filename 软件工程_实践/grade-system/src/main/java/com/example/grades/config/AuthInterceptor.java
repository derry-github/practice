package com.example.grades.config;

import com.example.grades.model.Student;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Student user = (Student) session.getAttribute("user");

        // 1. 检查是否登录
        if (user == null) {
            response.sendRedirect("/login");
            return false;
        }

        String uri = request.getRequestURI();

        // 2. 管理员权限校验
        if (uri.startsWith("/addStudent") || uri.startsWith("/addGrade")) {
            if (!"ADMIN".equals(user.getRole())) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "只有管理员可以执行此操作");
                return false;
            }
        }

        // 3. 学生权限校验：只能查/改自己的信息
        if (uri.startsWith("/student/")) {
            String targetStudentId = uri.replace("/student/", "");
            if (!"ADMIN".equals(user.getRole()) && !user.getStudentId().equals(targetStudentId)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "您无权访问其他学生的信息");
                return false;
            }
        }

        return true;
    }
}
