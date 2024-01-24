package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.repository.QuestionRepository;
import com.ices4hu.demo.repository.SurveyRepository;
import com.ices4hu.demo.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ices4hu.demo.util.ICES4HUValidatorUtil.now;

@Service
@AllArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    final SurveyRepository surveyRepository;
    final QuestionRepository questionRepository;
    final AppUserRepository appUserRepository;

    @Override
    public Question getById(Long id) throws Exception {
        return questionRepository.findById(id).orElseThrow(() -> new Exception("Question with id: " + id + " could not found") );
    }

    @Override
    public Question saveQuestion(Long surveyId, Question question) throws Exception {

        var survey = surveyRepository.findById(surveyId).orElseThrow();
        question.setSurvey(survey);

        if(question.getSurvey().getStartDate().before(now())){
            throw new Exception("Question save operation failed. Survey already started.");
        }

        survey.getQuestions().add(question);
        questionRepository.save(question);

        return question;
    }

    @Override
    public Question updateQuestion(Long id, Question question) throws Exception {
        if(question.getSurvey().getStartDate().before(now())){
            throw new Exception("Question update failed. Survey already started.");
        }
        questionRepository.updateQuestion(id, question.getDescription(), now());
        return question;
    }

    @Override
    public Question deleteQuestion(Long questionId) throws Exception {
        var question = questionRepository.getQuestionsById(questionId).orElseThrow(() -> new Exception("Question with id: " + questionId + " could not found"));
        if(question.getSurvey().getStartDate().before(now())){
            throw new Exception("Question deletion failed. Survey already started.");
        }

        questionRepository.deleteById(questionId);
        return question;
    }
}
