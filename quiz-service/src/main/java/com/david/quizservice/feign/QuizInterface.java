package com.david.quizservice.feign;

import com.david.quizservice.model.QuestionRequestBody;
import com.david.quizservice.model.QuestionWrapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {

    @GetMapping("/question/generate")
    public ResponseEntity<List<Long>> getQuestionsForQuiz(@RequestParam String categoryName, @RequestParam Integer numQuestions);

    @PostMapping("/question/getQuestions")
    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Long> questionIds);

    @PostMapping("/question/getScore")
    public ResponseEntity<Long> getScore(@RequestBody List<QuestionRequestBody> requestBody);
}
