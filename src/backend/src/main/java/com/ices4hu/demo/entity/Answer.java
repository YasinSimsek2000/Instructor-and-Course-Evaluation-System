package com.ices4hu.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ices4hu.demo.enums.QuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "answer")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rating_answer")
    private Byte rating;

    @Column(name = "open_ended_answer")
    private String answer;

    @Enumerated(EnumType.STRING)
    private QuestionType questionType;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "response_sheet_id", nullable = false)
    private ResponseSheet responseSheet;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;


    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;
}
