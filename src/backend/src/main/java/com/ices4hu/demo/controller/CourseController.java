package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Course;
import com.ices4hu.demo.entity.Department;
import com.ices4hu.demo.model.AppUserDTO;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.CourseServiceImpl;
import com.ices4hu.demo.service.impl.DepartmentServiceImpl;
import com.ices4hu.demo.util.ICES4HUUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {
    final CourseServiceImpl courseService;

    final AppUserServiceImpl appUserService;

    final DepartmentServiceImpl departmentService;


//    @GetMapping("/get-user/{id}")
//    @PreAuthorize("hasRole('ROLE_STUDENT')")
//    public ResponseEntity<?> getUser(@PathVariable Long id) {
//        //System.out.println("user id: " + appUserService.findByUsername("student1"));
//        return ResponseEntity.ok(appUserService.getById(id));
//    }

    @GetMapping("/get-instructors/{courseId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> getInstructors(@PathVariable Long courseId) {
        var course = courseService.getById(courseId);

        return ResponseEntity.ok(course.getInstructors().stream().map(AppUserDTO::new).collect(Collectors.toList()));
    }

    @GetMapping("/get-students/{courseId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_DEPARTMENT_MANAGER', 'ROLE_INSTRUCTOR')")
    public ResponseEntity<?> getStudents(@PathVariable Long courseId) {
        var course = courseService.getById(courseId);

        return ResponseEntity.ok(course.getStudents().stream().map(AppUserDTO::new).collect(Collectors.toList()));
    }


    @Transactional
    @PostMapping("/enroll/{courseId}")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> enroll(@PathVariable Long courseId) {
        try {
            AppUser appUser = appUserService.findByUsername(getCurrentUsername());

            Course course = courseService.getById(courseId);

            if (appUser.getTakenCourses().contains(course)) {
                return ResponseEntity.badRequest().body("Course already exists");
            }

            appUser.addCourse(course);

            appUserService.update(appUser.getId(), appUser);
            System.out.println("Student with id: " + appUser.getId() + " enrolled to course " + course.getName());
            return ResponseEntity.ok("Success! Student with id: " + appUser.getId() + " enrolled to course " + course.getName());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> save(@RequestBody Course course) {
        try {
            courseService.save(course);
            System.out.println("Course " + course.getName() + " added!");
            return ResponseEntity.ok("Success! Course " + course.getName() + " added with the id of " + courseService.findByDepartmentCodeAndCourseCode(course.getDepartmentCode(), course.getCourseCode()).getId() + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Course course) {
        try {
            courseService.update(id, course);
            System.out.println("Course " + course.getName() + " updated!");
            return ResponseEntity.ok("Success! Course " + course.getName() + " updated with the id of " + courseService.findByDepartmentCodeAndCourseCode(course.getDepartmentCode(), course.getCourseCode()).getId() + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        try {
            Course course = courseService.deleteById(id);
            System.out.println("Course " + course.getName() + " deleted!");
            return ResponseEntity.ok("Success! Course with the id of " + id + " deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> courseList() throws Exception {
        try {
            List<Course> courses = courseService.getAllList();
            System.out.println("Courses are listed successfully!");
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list/{departmentCode}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> courseListByDepartmentCode(@PathVariable String departmentCode) throws Exception {
        try {
            List<Course> courses = courseService.getAllByDepartmentCode(departmentCode);
            System.out.println("Courses with department code: " + departmentCode + " listed successfully!");
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(courseService.getById(id));
    }

    @PutMapping("/assign-instructor/{courseId}/{instructorId}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> assignInstructor(@PathVariable Long courseId, @PathVariable Long instructorId) {
        try {
            AppUser manager = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
            Course course = courseService.getById(courseId);
            Department department = departmentService.getByDepartmentManagerId(manager.getId()).orElseThrow(
                    () -> new Exception("You are not assigned to a department!")
            );

            if (!department.getDepartmentCode().equals(course.getDepartmentCode()))
                throw new Exception("You cannot edit courses from other departments");

            AppUser instructor = appUserService.getById(instructorId);

            if (instructor.getGivenCourses().contains(course)) {
                throw new Exception("Already assigned to the course!");
            }

            instructor.getGivenCourses().add(course);
            appUserService.update(instructorId, instructor);

            return ResponseEntity.ok("Success! Instructor assigned successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-instructor/{courseId}/{instructorId}")
    @Transactional
    @PreAuthorize("hasRole('ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> deleteInstructor(@PathVariable Long courseId, @PathVariable Long instructorId) {
        try {
            AppUser manager = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());
            Course course = courseService.getById(courseId);
            Department department = departmentService.getByDepartmentManagerId(manager.getId()).orElseThrow(
                    () -> new Exception("You are not assigned to a department!")
            );

            if (!department.getDepartmentCode().equals(course.getDepartmentCode()))
                throw new Exception("You cannot edit courses from other departments");

            AppUser instructor = appUserService.getById(instructorId);

            if (!instructor.getGivenCourses().contains(course)) {
                throw new Exception("Course is already not associated with the instructor");
            }

            instructor.getGivenCourses().remove(course);
            appUserService.update(instructorId, instructor);

            return ResponseEntity.ok("Success! Instructor deleted from the course!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
