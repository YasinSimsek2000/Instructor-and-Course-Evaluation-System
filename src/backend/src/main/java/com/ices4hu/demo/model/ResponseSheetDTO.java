package com.ices4hu.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ices4hu.demo.entity.Answer;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Survey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSheetDTO {
    private long id;
    private Survey survey;
    private AppUser responder;
    private List<Answer> answers;
    private Date updatedAt;
    private Date createdAt;
    private int editTime = 0;
}

