package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Course;
import com.ices4hu.demo.repository.CourseRepository;
import com.ices4hu.demo.service.CourseService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {


    final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Course save(Course course) throws Exception {
        Optional<Course> courseOptional = courseRepository.findByDepartmentCodeAndCourseCode(course.getDepartmentCode(), course.getCourseCode());
        if (courseOptional.isPresent()) {
            throw new Exception("There's already course exists with department code: " + course.getDepartmentCode() + " and course code: " + course.getCourseCode());
        }
        courseRepository.save(course);
        return course;
    }

    @Override
    public Course deleteById(Long id) throws Exception {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isPresent()) {
            courseRepository.deleteById(id);
            return course.get();
        }
        throw new Exception("The course with id: " + id + " couldn't found.");
    }


    @Override
    public Course findByDepartmentCodeAndCourseCode(String departmentCode, int courseCode) throws Exception {

        try {
            return courseRepository.findByDepartmentCodeAndCourseCode(departmentCode, courseCode).orElseThrow();

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }




    @Override
    public Course update(Long id, Course course) throws Exception {
        try {

            courseRepository.findById(id).orElseThrow(() -> new Exception("No course found with id: " + id));

            Optional<Course> oldCourse = courseRepository.findByDepartmentCodeAndCourseCode(course.getDepartmentCode(), course.getCourseCode());

            // Unique department and course code combination assigned, or they didn't change at all.
            if(!oldCourse.isPresent() || (oldCourse.isPresent() && id == oldCourse.get().getId())){
                courseRepository.updateCourse(
                        id,
                        course.getName(),
                        course.getDepartmentCode(),
                        course.getCourseCode(),
                        course.getType(),
                        course.getCreditT(),
                        course.getCreditP(),
                        course.getCreditEcts(),
                        ICES4HUValidatorUtil.now()
                );
                return course;
            }

            throw new Exception("Course update failed. There's already a course exists with department and course code combination: " + course.getDepartmentCode() + course.getCourseCode());

        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Course> getAllList(Sort sort) throws Exception {
        try {
            return courseRepository.findAll(sort).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Course> getAllByDepartmentCode(String departmentCode) throws Exception{
        try {
            return courseRepository.findAllByDepartmentCode(departmentCode).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Course getById(Long id) {
        try {
            return courseRepository.findById(id)
                    .orElseThrow(() -> new Exception("Course not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
