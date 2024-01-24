package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.ResponseSheet;
import com.ices4hu.demo.model.AnswerDTO;
import com.ices4hu.demo.service.ResponseSheetService;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.ResponseSheetServiceImpl;
import com.ices4hu.demo.service.impl.SurveyServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@RestController
@RequestMapping("/response-sheet")
@AllArgsConstructor
public class ResponseSheetController {

    final ResponseSheetServiceImpl responseSheetService;
    final AppUserServiceImpl appUserService;
    final SurveyServiceImpl surveyService;

    @PutMapping("/add/{surveyId}/{userId}")
    @PreAuthorize("true")
    public ResponseEntity<?> add(@PathVariable Long surveyId, @PathVariable Long userId){
        try{
            var survey = surveyService.findById(surveyId);
            var user = appUserService.getById(userId);

           responseSheetService.addResponseSheet(surveyId, userId);
            return ResponseEntity.ok().body("Response Sheet with id: "
                    + responseSheetService.findBySurveyAndResponder(survey, user).getId() +
                    " created successfully.");

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    @PutMapping("/set-answers/{sheetId}")
    @PreAuthorize("true")
    public ResponseEntity<?> setAnswers(@PathVariable Long sheetId, @RequestBody List<AnswerDTO> answers){
        try{
            var user = appUserService.findByUsername(getCurrentUsername());
            responseSheetService.setAnswers(sheetId, user.getId(), answers);
            return ResponseEntity.ok().body("Answers saved successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
