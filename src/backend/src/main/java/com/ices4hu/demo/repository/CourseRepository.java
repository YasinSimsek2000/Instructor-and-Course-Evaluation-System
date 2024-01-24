package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.Course;
import com.ices4hu.demo.enums.CourseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<CourseRepository> findByCourseCode(int courseCode);

    Optional<Course> findByDepartmentCodeAndCourseCode(String departmentCode, int courseCode);

    //Optional<Course> deleteByDepartmentCodeAndCourseCode(String departmentCode, int courseCode);

    List<Course> findAllByDepartmentCode(String departmentCode) throws Exception;

    @Modifying
    @Query("update course set " +
            "name =:name," +
            "departmentCode =:departmentCode," +
            "courseCode =:courseCode," +
            "type =:type," +
            "creditT =:creditT," +
            "creditP =:creditP," +
            "creditEcts =:creditEcts," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void updateCourse(
            @Param("id") long id,
            @Param("name") String name,
            @Param("departmentCode") String departmentCode,
            @Param("courseCode") int courseCode,
            @Param("type") CourseType type,
            @Param("creditT") int creditT,
            @Param("creditP") int creditP,
            @Param("creditEcts") int creditEcts,
            @Param("updatedAt") Date updatedAt
    );


}
