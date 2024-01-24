package com.ices4hu.demo.model;

import com.ices4hu.demo.entity.Course;
import com.ices4hu.demo.enums.CourseType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private long id;
    private String name;
    private String departmentCode;
    private Integer courseCode;
    private CourseType type;
    private Integer creditT;
    private Integer creditP;
    private Integer creditEcts;

    public CourseDTO(Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.departmentCode = course.getDepartmentCode();
        this.courseCode = course.getCourseCode();
        this.type = course.getType();
        this.creditT = course.getCreditT();
        this.creditP = course.getCreditP();
        this.creditEcts = course.getCreditEcts();
    }

}
