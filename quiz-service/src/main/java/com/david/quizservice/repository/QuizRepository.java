package com.david.quizservice.repository;

import com.david.quizservice.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;


public interface QuizRepository extends JpaRepository<Quiz,Long> {
}
