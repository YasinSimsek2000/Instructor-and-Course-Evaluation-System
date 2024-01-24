package com.ices4hu.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ices4hu.demo.enums.CourseType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity(name = "course")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "department_code", nullable = false)
    private String departmentCode;

    @Column(name = "course_code", nullable = false)
    private Integer courseCode;

    @Column(name = "type", nullable = false)
    private CourseType type;

    @Column(name = "credit_t", nullable = false)
    private Integer creditT;

    @Column(name = "credit_p", nullable = false)
    private Integer creditP;

    @Column(name = "credit_ects", nullable = false)
    private Integer creditEcts;


    @JsonIgnore
    @ManyToMany(mappedBy = "takenCourses")
    List<AppUser> students;

    @JsonIgnore
    @ManyToMany(mappedBy = "givenCourses")
    List<AppUser> instructors;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;



}
