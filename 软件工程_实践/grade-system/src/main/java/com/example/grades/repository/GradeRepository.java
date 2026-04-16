package com.example.grades.repository;
import com.example.grades.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {}
