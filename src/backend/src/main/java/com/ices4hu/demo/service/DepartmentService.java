package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Department;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DepartmentService {
    Department save(Department department) throws Exception;

    Department deleteById(Long id) throws Exception;

    Department update(Long id, Department department) throws Exception;

    default List<Department> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<Department> getAllList(Sort sort) throws Exception;

    Department getById(Long id);

    Optional<Department> getByDepartmentCode(String departmentCode);
    AppUser getDepartmentManagerByDepartmentId(Long id);

    Optional<Department> getByDepartmentManagerId(Long id);

}
