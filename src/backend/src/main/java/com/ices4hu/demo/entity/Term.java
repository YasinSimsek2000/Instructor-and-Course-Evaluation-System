package com.ices4hu.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ices4hu.demo.enums.TermType;
import com.ices4hu.demo.model.TermDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(name = "term")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "term_name")
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private Date endDate;

    @Enumerated(EnumType.STRING)
    private TermType termType;

    public Term(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Term(TermDTO termDTO) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(TermDTO.DATE_PATTERN);

        this.id = termDTO.getId();
        this.name = termDTO.getName();
        try {
            this.startDate = dateFormat.parse(termDTO.getStartDate());
            this.endDate = dateFormat.parse(termDTO.getEndDate());
        } catch (ParseException e) {
            //
        }
        this.termType = termDTO.getTermType();
    }
}
