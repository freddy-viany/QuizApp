package com.fredex.quizzapp.repository;

import com.fredex.quizzapp.model.Course;
import com.fredex.quizzapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {


    // You can add custom queries if needed
}
