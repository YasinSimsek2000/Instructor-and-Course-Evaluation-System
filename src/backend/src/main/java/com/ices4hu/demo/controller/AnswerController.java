package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.model.AnswerDTO;
import com.ices4hu.demo.service.impl.AnswerServiceImpl;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.QuestionServiceImpl;
import com.ices4hu.demo.service.impl.SurveyServiceImpl;
import com.ices4hu.demo.util.ICES4HUUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
@AllArgsConstructor
public class AnswerController {

    final AppUserServiceImpl appUserService;
    final AnswerServiceImpl answerService;
    final QuestionServiceImpl questionService;
    final SurveyServiceImpl surveyService;

    @DeleteMapping("/delete/{questionId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> deleteAnswer(@PathVariable Long questionId, @RequestBody Answer answer)  throws Exception {
        AppUser answerOwner = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
        Question question = questionService.getById(questionId);

        if (question.getQuestionType() != answer.getQuestionType()) {
            throw new Exception("Question types do not match!");
        }

        //answer.setOwner(answerOwner);
        answer.setQuestion(question);

        answerService.deleteById(answer.getId());

        return ResponseEntity.ok("Answer deleted successfully!");
    }

    @PutMapping("/student-save/{questionId}")
    @PreAuthorize("hasAnyAuthority('student:fill-survey')")
    public ResponseEntity<?> studentSaveAnswer(@PathVariable Long questionId, @RequestBody Answer answer)  throws Exception {
        AppUser answerOwner = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
        Question question = questionService.getById(questionId);

        if (question.getQuestionType() != answer.getQuestionType()) {
            throw new Exception("Question types do not match!");
        }

        //answer.setOwner(answerOwner);
        answer.setQuestion(question);

        answerService.save(answer);

        return ResponseEntity.ok("Answer added successfully!");
    }

    @Transactional
    @PostMapping("/student-update/{questionId}")
    @PreAuthorize("hasAnyAuthority('student:fill-survey')")
    public ResponseEntity<?> studentUpdateAnswer(@PathVariable Long questionId, @RequestBody Answer answer) throws Exception {
        AppUser answerOwner = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
        Question question = questionService.getById(questionId);
        Answer oldAnswer = answerService.getById(answer.getId());

        if (question.getQuestionType() != answer.getQuestionType()) {
            throw new Exception("Question types do not match!");
        }
        //if (!answerOwner.equals(oldAnswer.getOwner())) {
        //     throw new Exception("You cannot edit others' questions!");
        //}

        answerService.update(answer.getId(), answer);

        return ResponseEntity.ok("Answer updated successfully!");
    }

    @GetMapping("/instructor/{questionId}")
    @PreAuthorize("hasAnyAuthority('instructor:view_all_answers')")
    public ResponseEntity<?> instructorViewAllAnswersAnonymously(@PathVariable Long questionId) throws Exception{
        AppUser currentUser = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
        Question question = questionService.getById(questionId);

        return ResponseEntity.ok(question.getAnswers().stream().map(AnswerDTO::new).collect(Collectors.toList()));
    }
}
