package com.ices4hu.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Question;
import com.ices4hu.demo.entity.Survey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SurveyDTO {
    private long id;
    private String name;
    private Long creatorId;
    private Long departmentId;
    private Date startDate;
    private Date endDate;
    private Date updatedAt;
    private Date createdAt;

    private List<QuestionDTO> questions;

    public SurveyDTO(Survey survey){
        this.id = survey.getId();
        this.name = survey.getName();
        this.creatorId = survey.getCreator().getId();
        this.departmentId = survey.getDepartmentId();
        this.startDate = survey.getStartDate();
        this.endDate = survey.getEndDate();
        this.updatedAt = survey.getUpdatedAt();
        this.createdAt = survey.getCreatedAt();
        this.questions = survey.getQuestions().stream().map(QuestionDTO::new).collect(Collectors.toList());

    }
}
