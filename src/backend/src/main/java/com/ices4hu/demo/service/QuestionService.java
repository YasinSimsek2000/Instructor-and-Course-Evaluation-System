package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.Question;
import org.springframework.stereotype.Service;

@Service
public interface QuestionService {

    Question getById(Long id) throws Exception;
    Question saveQuestion(Long surveyId, Question question) throws Exception;
    Question updateQuestion(Long id, Question question) throws Exception;
    Question deleteQuestion(Long questionId) throws Exception;
}
