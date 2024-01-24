package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentCode(String departmentCode);

    Optional<Department> findByDepartmentManagerId(Long id);

    @Modifying
    @Query("update department set " +
            "name =:name," +
            "departmentCode =:departmentCode," +
            "facultyName =:facultyName," +
            "departmentManagerId =:departmentManagerId," +
            "updatedAt =:updatedAt" +
            " where id =:id")
    void updateDepartment(
            @Param("id") long id,
            @Param("name") String name,
            @Param("departmentCode") String departmentCode,
            @Param("facultyName") String facultyName,
            @Param("departmentManagerId") Long departmentManagerId,
            @Param("updatedAt") Date updatedAt
    );

    Department getDepartmentByDepartmentCode(String departmentCode);
}
