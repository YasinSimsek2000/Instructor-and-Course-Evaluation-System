package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.Survey;
import com.ices4hu.demo.model.QuestionDTO;
import com.ices4hu.demo.model.SurveyDTO;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.QuestionServiceImpl;
import com.ices4hu.demo.service.impl.SurveyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;
import static com.ices4hu.demo.util.ICES4HUValidatorUtil.now;

@RestController
@RequestMapping("/survey")
@AllArgsConstructor
public class SurveyController {

    QuestionServiceImpl questionService;
    SurveyServiceImpl surveyService;
    AppUserServiceImpl appUserService;


    @GetMapping("/list-surveys")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> listSurveys() throws Exception {
        try {
            var user = appUserService.findByUsername(getCurrentUsername());
            return ResponseEntity.ok(surveyService.getAllList()
                    .stream().map(SurveyDTO::new).peek(it -> it.getQuestions().clear())
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-my-surveys")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> listMySurveys() throws Exception {
        try {
            var user = appUserService.findByUsername(getCurrentUsername());
            return ResponseEntity.ok(user.getCreatedSurveys()
                    .stream().map(SurveyDTO::new).peek(it -> it.getQuestions().clear())
                    .collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-questions/{surveyId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isSurveyAndCreatorSame(#surveyId)" +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> listQuestions(@PathVariable Long surveyId) throws Exception {
        try {
            var survey = surveyService.findById(surveyId);
            return ResponseEntity.ok(survey.getQuestions()
                    .stream().map(QuestionDTO::new).collect(Collectors.toList())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PutMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> saveSurvey(@RequestBody Survey survey) throws Exception {
        try {
            surveyService.save(survey);
            return ResponseEntity.ok("Survey with id: " + survey.getId() + " added succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{surveyId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isSurveyAndCreatorSame(#surveyId)" +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getSurvey(@PathVariable Long surveyId) throws Exception {
        try {
            var survey = surveyService.findById(surveyId);
            return ResponseEntity.ok(survey);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/update/{surveyId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isSurveyAndCreatorSame(#surveyId)" +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateSurvey(@PathVariable Long surveyId, @RequestBody Survey survey) throws Exception {
        try {
            surveyService.update(surveyId, survey);
            return ResponseEntity.ok("Survey with id: " + surveyId + " updated succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Transactional
    @DeleteMapping("/delete/{surveyId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isSurveyAndCreatorSame(#surveyId)" +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteSurvey(@PathVariable Long surveyId) throws Exception {
        try {
            surveyService.deleteById(surveyId);
            return ResponseEntity.ok("Survey with id: " + surveyId + " removed succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/student/get-my-surveys")
    @PreAuthorize("hasAnyRole('ROLE_STUDENT')")
    public ResponseEntity<?> getStudentSurvey() throws Exception {
        try {
            var user = appUserService.findByUsername(getCurrentUsername());
            var surveys = surveyService.findOpenSurveysByDepartment(user.getDepartmentId(), now());
            return ResponseEntity.ok(surveys);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
