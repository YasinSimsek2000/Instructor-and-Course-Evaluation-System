package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.ResponseSheet;
import com.ices4hu.demo.entity.Survey;
import com.ices4hu.demo.enums.QuestionType;
import com.ices4hu.demo.model.AnswerDTO;
import com.ices4hu.demo.repository.*;
import com.ices4hu.demo.service.ResponseSheetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ices4hu.demo.util.ICES4HUValidatorUtil.now;


@AllArgsConstructor
@Service
@Component
public class ResponseSheetServiceImpl implements ResponseSheetService {

    final ResponseSheetRepository responseSheetRepository;
    final SurveyRepository surveyRepository;
    final QuestionRepository questionRepository;
    final AnswerRepository answerRepository;
    final AppUserRepository appUserRepository;

    public Answer toAnswer(AnswerDTO answerDTO){
        Answer answer = new Answer();
        answer.setRating(answerDTO.getRating());
        answer.setAnswer(answerDTO.getAnswer());
        answer.setQuestionType(answerDTO.getQuestionType());
        answer.setResponseSheet(responseSheetRepository.findById(answerDTO.getResponseSheetId()).orElseThrow());
        answer.setQuestion(questionRepository.findById(answerDTO.getQuestionId()).orElseThrow());
        answer.setCreatedAt(answerDTO.getCreatedAt());
        answer.setUpdatedAt(answerDTO.getUpdatedAt());
        return answer;
    }

    @Override
    public ResponseSheet save(ResponseSheet responseSheet) throws Exception{

        responseSheetRepository.save(responseSheet);
        return responseSheet;
    }

    @Override
    public ResponseSheet findBySurveyAndResponder(Survey survey, AppUser responder) {
        return responseSheetRepository.findBySurveyAndResponder(survey, responder).orElseThrow();
    }

    @Override
    public void addResponseSheet(Long surveyId, Long userId) throws Exception{
        var user = appUserRepository.findById(userId).orElseThrow();
        var survey = surveyRepository.findById(surveyId).orElseThrow();
        var responseSheet = responseSheetRepository.findBySurveyAndResponder(survey, user);

        if(responseSheet.isPresent()){
            throw new Exception("A survey sheet for given student already exists.");
        }

        var newResponseSheet = new ResponseSheet();
        newResponseSheet.setSurvey(survey);
        newResponseSheet.setResponder(user);
        newResponseSheet.setUpdatedAt(now());
        responseSheetRepository.save(newResponseSheet);


    }

    @Override
    public void setAnswers(Long responseSheetId, Long userId, List<AnswerDTO> answers) throws Exception{


        var responseSheet = responseSheetRepository.findById(responseSheetId).orElseThrow();
        var answerList = answers.stream().map(this::toAnswer).collect(Collectors.toList());


        var oldAnswers = responseSheet.getAnswers();
        while (!oldAnswers.isEmpty()){
            answerRepository.deleteById(oldAnswers.remove(0).getId());
        }

        // TODO Change with stream
        for (Answer i: answerList) {
            if(i.getQuestion().getSurvey() != responseSheet.getSurvey()){
                throw new Exception("Incompatible Answer and question pair found.");
            }

            if(i.getQuestion().getQuestionType() != i.getQuestionType()){
                throw new Exception("Incompatible Answer and question type pair found.");
            }
            if(i.getQuestionType() == QuestionType.MULTIPLE_CHOICE){
                byte rating = i.getRating();
                if(rating < 1 || rating > 10){
                    throw new Exception("Invalid rating point.");
                }
            }
            answerRepository.save(i);
        }

        responseSheet.setEditTime(responseSheet.getEditTime()+1);
        responseSheet.setAnswers(answerList);
        //responseSheet.setAnswers(answers);
    }
}
