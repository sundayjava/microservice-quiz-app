package com.david.questionservice.controller;

import com.david.questionservice.model.Question;
import com.david.questionservice.model.QuestionRequestBody;
import com.david.questionservice.model.QuestionWrapper;
import com.david.questionservice.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    Environment environment;

    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return questionService.getAllQuestions();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("/add")
    public String addQuestion(@RequestBody Question question) {
        questionService.createQuestions(question);
        return "Question Added";
    }

    //generate
    @GetMapping("/generate")
    public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions) {
        return questionService.getQuestionsForQuiz(categoryName,numQuestions);
    }
    //getquestions {questionId}
    @PostMapping("/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Long> questionIds){
       System.out.println(environment.getProperty("local.server.port"));
        return questionService.getQuestionsFromId(questionIds);
    }
    //getScore
    @PostMapping("/getScore")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuestionRequestBody> requestBody){
        return questionService.getScore(requestBody);
    }
}
