package com.ices4hu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "response_sheet")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseSheet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="survey_id", nullable=false)
    private Survey survey;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="responder_id", nullable=false)
    private AppUser responder;


    @OneToMany(mappedBy="responseSheet")
    private List<Answer> answers;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "edit_time")
    private int editTime = 0;

}
