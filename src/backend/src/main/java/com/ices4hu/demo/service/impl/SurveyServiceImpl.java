package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.entity.Survey;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.repository.QuestionRepository;
import com.ices4hu.demo.repository.SurveyRepository;
import com.ices4hu.demo.service.SurveyService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@AllArgsConstructor
@Service("surveyServiceImpl")
public class SurveyServiceImpl implements SurveyService {


    final SurveyRepository surveyRepository;
    final QuestionRepository questionRepository;
    final AppUserRepository appUserRepository;

    public boolean isSurveyAndCreatorSame(Long surveyId) throws Exception {
        var user = appUserRepository.findByUsername(getCurrentUsername()).orElseThrow();
        var survey = surveyRepository.findById(surveyId).orElseThrow(() -> new Exception("No survey found with id: " + surveyId));

        return user.getId() == survey.getCreator().getId();
    }

    public boolean isQuestionAndCreatorSame(Long questionId) throws Exception {
        var question = questionRepository.getQuestionsById(questionId).orElseThrow();
        var user = appUserRepository.findByUsername(getCurrentUsername()).orElseThrow();


        return user.getId() == question.getSurvey().getCreator().getId();
    }

    @Override
    public Survey findById(Long id) throws Exception {
        return surveyRepository.findById(id).orElseThrow(() -> new Exception("Survey with id: " + id + " could not found"));
    }

    @Override
    public Survey save(Survey survey) throws Exception {
        var user = appUserRepository.findByUsername(getCurrentUsername()).orElseThrow();
        survey.setCreator(user);
        survey.setDepartmentId(user.getDepartmentId());
        surveyRepository.save(survey);
        return survey;
    }

    @Override
    public Survey update(Long id, Survey survey) throws Exception {
        surveyRepository.updateSurvey(
                id,
                survey.getName(),
                survey.getStartDate(),
                survey.getEndDate(),
                ICES4HUValidatorUtil.now()
        );
        return survey;
    }



    @Override
    public Survey deleteById(Long id) throws Exception {
        var survey = surveyRepository.findById(id).orElseThrow(() -> new Exception("Survey with id: " + id + " could not found"));

        survey.getQuestions().stream().forEach(it -> questionRepository.deleteById(it.getId()));
        surveyRepository.deleteById(id);
        return survey;
    }

    @Override
    public List<Survey> getSurveysByInstructorId(Long id) {
        return surveyRepository.getAllByCreator(appUserRepository.findById(id).orElseThrow());
    }

    @Override
    public List<Survey> findOpenSurveysByDepartment(Long departmentId, Date now) {
        return surveyRepository.findOpenSurveysByDepartment(departmentId, now);
    }

    @Override
    public List<Survey> getAllList(Sort sort) throws Exception {
        return surveyRepository.findAll(sort).stream().toList();
    }
}
