package com.david.questionservice.service;

import com.david.questionservice.model.Question;
import com.david.questionservice.model.QuestionRequestBody;
import com.david.questionservice.model.QuestionWrapper;
import com.david.questionservice.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public void createQuestions(Question question) {
        questionRepository.save(question);
    }

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numQuestions) {
        List<Integer> questions = questionRepository.findRandomQuestionByCategory(categoryName, numQuestions);
        return new ResponseEntity<>(questions, HttpStatus.OK);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Long> questionIds) {
        List<QuestionWrapper> wrappers = new ArrayList<>();

        List<Question> questions = new ArrayList<>();

        for (Long id:questionIds){
            questions.add(questionRepository.findById(id).get());
        }

        for (Question question: questions){
            QuestionWrapper wrapper = new QuestionWrapper();
            wrapper.setId(question.getId());
            wrapper.setQuestionTitle(question.getQuestionTitle());
            wrapper.setOption1(question.getOption1());
            wrapper.setOption2(question.getOption2());
            wrapper.setOption3(question.getOption3());
            wrapper.setOption4(question.getOption4());

            wrappers.add(wrapper);
        }

        return new ResponseEntity<>(wrappers,HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<QuestionRequestBody> requestBody) {
        int right = 0;
        for (QuestionRequestBody response:requestBody){
            Question question = questionRepository.findById(response.getId()).get();
            if (response.getResponse().equals(question.getRightAnswer()))
                right++;
        }

        return new ResponseEntity<>(right,HttpStatus.OK);
    }
}
