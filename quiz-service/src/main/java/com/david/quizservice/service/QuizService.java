package com.david.quizservice.service;

import com.david.quizservice.feign.QuizInterface;
import com.david.quizservice.model.QuestionWrapper;
import com.david.quizservice.model.QuestionRequestBody;
import com.david.quizservice.model.Quiz;
import com.david.quizservice.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizRepository quizRepository;
    @Autowired
    QuizInterface quizInterface;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Long> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionIds(questions);
        quizRepository.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Long id) {
        Quiz quiz = quizRepository.findById(id).get();

        List<Long> questionIds = quiz.getQuestionIds();


        return quizInterface.getQuestionsFromId(questionIds);
    }

    public ResponseEntity<Long> calculateResult(Long id, List<QuestionRequestBody> quizRequestBody) {
        return quizInterface.getScore(quizRequestBody);
    }
}
