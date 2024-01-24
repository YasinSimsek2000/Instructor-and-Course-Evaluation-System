package com.ices4hu.demo.model;

import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AnswerDTO {
    //private final long id;
    private final byte rating;
    private final String answer;
    private final QuestionType questionType;
    private final Long responseSheetId;
    private final Long questionId;
    private final Date updatedAt;
    private final Date createdAt;

    public AnswerDTO(Answer answer) {
        //this.id = answer.getId();
        this.rating = answer.getRating();
        this.answer = answer.getAnswer();
        this.questionType = answer.getQuestionType();
        this.responseSheetId = answer.getResponseSheet().getId();
        this.questionId = answer.getQuestion().getId();
        this.updatedAt = answer.getUpdatedAt();
        this.createdAt = answer.getCreatedAt();
    }
}
