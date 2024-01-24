package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.ResponseSheet;
import com.ices4hu.demo.entity.Survey;
import com.ices4hu.demo.model.AnswerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResponseSheetService {

    // Yhis method won't be used for creating new survey by student.
    // But addResponseSheet method will be used


    ResponseSheet save(ResponseSheet responseSheet) throws Exception;

    ResponseSheet findBySurveyAndResponder(Survey survey, AppUser responder);

    void addResponseSheet(Long surveyId, Long userId) throws Exception;

    void setAnswers(Long responseSheetId, Long userId, List<AnswerDTO> answers) throws Exception;



}
