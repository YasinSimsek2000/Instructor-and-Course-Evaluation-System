package com.ices4hu.demo.model;

import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.enums.QuestionType;

import java.util.Date;

public class QuestionDTO {

    private long id;
    private Long surveyId;
    private String description;

    private QuestionType questionType;
    private Date updatedAt;
    private Date createdAt;

    public QuestionDTO(Question question){

        this.id = question.getId();
        this.surveyId = question.getSurvey().getId();
        this.description = question.getDescription();
        this.questionType = question.getQuestionType();
        this.updatedAt = question.getUpdatedAt();
        this.createdAt = question.getCreatedAt();
    }
}
