package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Department;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.repository.DepartmentRepository;
import com.ices4hu.demo.service.DepartmentService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    DepartmentRepository departmentRepository;
    AppUserRepository appUserRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AppUserRepository appUserRepository){
        this.departmentRepository = departmentRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Department save(Department department) throws Exception {

        try{
            var departmentOnDb = departmentRepository.findByDepartmentCode(department.getDepartmentCode());
            if(departmentOnDb.isPresent()){
                throw new Exception("There's already a department defined with this department code: " + department.getDepartmentCode());
            }
            departmentRepository.save(department);
            return department;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public Department deleteById(Long id) throws Exception {
        try {
            Department department = departmentRepository.findById(id).orElseThrow();
            departmentRepository.deleteById(id);
            return department;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Department update(Long id, Department department) throws Exception {
        try {
//            var departmentOnDb = departmentRepository.findByDepartmentCode(department.getDepartmentCode());
//            if(departmentOnDb.isPresent()){
//                throw new Exception("There's already a department defined with this department code: " + department.getDepartmentCode());
//            }
            departmentRepository.updateDepartment(
                    id,
                    department.getName(),
                    department.getDepartmentCode(),
                    department.getFacultyName(),
                    department.getDepartmentManagerId(),
                    ICES4HUValidatorUtil.now()
            );
            return department;
        } catch (Exception e) {
            System.out.println("SIUUUUUUUUU");
            //e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<Department> getAllList(Sort sort) throws Exception {
        try {
            return departmentRepository.findAll(sort).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Department getById(Long id) {
        try {
            return departmentRepository.findById(id)
                    .orElseThrow(() -> new Exception("Department not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Department> getByDepartmentCode(String departmentCode) {
        return departmentRepository.findByDepartmentCode(departmentCode);

    }

    @Override
    public AppUser getDepartmentManagerByDepartmentId(Long id) {
        try{
            var department = departmentRepository.findById(id).orElseThrow();
            return appUserRepository.findById(department.getId()).orElseThrow();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Department> getByDepartmentManagerId(Long id) {
        try{
            return departmentRepository.findByDepartmentManagerId(id);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
