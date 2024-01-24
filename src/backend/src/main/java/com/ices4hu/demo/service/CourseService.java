package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Course;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CourseService {
    Course save(Course course) throws Exception;

    Course deleteById(Long id) throws Exception;

    Course findByDepartmentCodeAndCourseCode(String departmentCode, int courseCode) throws Exception;

    //Course deleteByDepartmentCodeAndCourseCode(String departmentCode, int courseCode) throws Exception;

    Course update(Long id, Course course) throws Exception;

    default List<Course> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<Course> getAllList(Sort sort) throws Exception;

    List<Course> getAllByDepartmentCode(String departmentCode) throws Exception;

    Course getById(Long id) throws Exception;

}
