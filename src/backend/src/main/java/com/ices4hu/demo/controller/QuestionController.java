package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.QuestionServiceImpl;
import com.ices4hu.demo.service.impl.SurveyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/question")
@AllArgsConstructor
public class QuestionController {

    QuestionServiceImpl questionService;
    SurveyServiceImpl surveyService;

    AppUserServiceImpl appUserService;




    @PutMapping("/save/{surveyId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isSurveyAndCreatorSame(#surveyId) " +
            "or hasRole('ROLE_ADMIN')")

    public ResponseEntity<?> saveQuestion(@PathVariable Long surveyId, @RequestBody Question question) throws Exception {
        try {
            questionService.saveQuestion(surveyId, question);
            return ResponseEntity.ok("Question with id: " + question.getId() + " succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isQuestionAndCreatorSame(#questionId) " +
            "or hasRole('ROLE_ADMIN')")

    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) throws Exception {
        try {
            var question = questionService.getById(questionId);
            return ResponseEntity.ok(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/update/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isQuestionAndCreatorSame(#questionId) " +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) throws Exception {
        try {
            questionService.updateQuestion(questionId, question);
            return ResponseEntity.ok("Question with id: " + question.getId() + " succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_INSTRUCTOR', 'ROLE_DEPARTMENT_MANAGER') " +
            "and @surveyServiceImpl.isQuestionAndCreatorSame(#questionId) " +
            "or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) throws Exception {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.ok("Question with id: " + questionId + " deleted succesfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

