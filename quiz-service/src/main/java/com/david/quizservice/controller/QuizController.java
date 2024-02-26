package com.david.quizservice.controller;

import com.david.quizservice.model.QuestionWrapper;
import com.david.quizservice.model.QuizDto;
import com.david.quizservice.model.QuestionRequestBody;
import com.david.quizservice.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto) {
        return quizService.createQuiz(quizDto.getCategoryName(), quizDto.getNumQuestions(), quizDto.getTitle());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(@PathVariable Long id) {
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("/submit/{id}")
    public ResponseEntity<Long> subMitQuiz(@PathVariable Long id, @RequestBody List<QuestionRequestBody> quizRequestBody) {
        return quizService.calculateResult(id, quizRequestBody);
    }
}
