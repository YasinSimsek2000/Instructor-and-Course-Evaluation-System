package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.Department;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.DepartmentServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    final DepartmentServiceImpl departmentService;
    final AppUserServiceImpl appUserService;


    public DepartmentController(DepartmentServiceImpl departmentService, AppUserServiceImpl appUserService) {
        this.departmentService = departmentService;
        this.appUserService = appUserService;
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> save(@RequestBody Department department) {
        try {
            departmentService.save(department);
            System.out.println("Department " + department.getName() + " added!");
            return ResponseEntity.ok("Success! Department " + department.getName() + " added with the id of " + departmentService.getByDepartmentCode(department.getDepartmentCode()).get().getId() + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PostMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody Department department) throws Exception {
        try {
            if(id == null) throw new Exception("id can't be null");

            var departmentFromDb = departmentService.getByDepartmentCode(department.getDepartmentCode());
            if(departmentFromDb.isPresent() && id != departmentFromDb.get().getId()){
                throw new Exception("There's already a department with department id : " + departmentFromDb.get().getId());
            }
            departmentService.update(id, department);
            System.out.println("Department with the id " + id + " updated successfully!");
            return ResponseEntity.ok("Department with the id " + id + " updated successfully!");
        } catch (Exception e) {
            System.out.println("SIUUUUUUUUU");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Transactional
    @PutMapping("/set-department-manager/{departmentId}/{managerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> setDepartmentManager(@PathVariable Long departmentId, @PathVariable Long managerId) throws Exception {
        try {
            if(departmentId == null) throw new Exception("departmentId can't be null");
            if(managerId == null) throw new Exception("managerId can't be null");

            var departmentManager = appUserService.getById(managerId);
            departmentManager.setRole(Role.DEPARTMENT_MANAGER);
            appUserService.update(departmentManager.getId(), departmentManager);

            return ResponseEntity.ok("Department with the id " + departmentId + " updated successfully!");

//            var departmentFromDepartmentManagerId = departmentService.getByDepartmentManagerId(managerId);
//            if(departmentFromDepartmentManagerId.isPresent()){
//                throw new Exception("The user with id: " + managerId + " is already manager of departmentwith id: " + departmentFromDepartmentManagerId.get().getId());
//            }
//            departmentManager.setRole(Role.DEPARTMENT_MANAGER);
//
//            department.setDepartmentManagerId(managerId);
//            departmentService.update(departmentId, department);
//            departmentManager.setDepartmentId(departmentId);
//            appUserService.update(departmentManager.getId(), departmentManager);
//
//            System.out.println("Department with the id " + departmentId + " updated successfully!");
//            return ResponseEntity.ok("Department with the id " + departmentId + " updated successfully!");
        } catch (Exception e) {
            System.out.println("SIUUUUUUUUU");
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @Transactional
    @PutMapping("/remove-department-manager/{departmentId}/{managerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeDepartmentManager(@PathVariable Long departmentId, @PathVariable Long managerId) throws Exception {
        try {
            if(departmentId == null) throw new Exception("departmentId can't be null");
            if(managerId == null) throw new Exception("managerId can't be null");

            var department = departmentService.getById(departmentId);
            if(department.getDepartmentManagerId() == null){
                throw new Exception("Unassignment department manager failed. The department already has no department manager");
            }
            department.setDepartmentManagerId(null);
            departmentService.update(departmentId, department);

            System.out.println("Department manager with id: " + managerId + "is no longer manager of department with id: " + departmentId);
            return ResponseEntity.ok("Department manager with id: " + managerId + "is no longer manager of department with id: " + departmentId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getAllDepartments(){
        try{
            return  ResponseEntity.ok(departmentService.getAllList());
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(departmentService.deleteById(id));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
