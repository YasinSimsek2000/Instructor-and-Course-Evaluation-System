package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.model.*;
import com.ices4hu.demo.security.auth.AuthenticationService;
import com.ices4hu.demo.service.exception.UserNotFoundException;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.EmailServiceImpl;
import com.ices4hu.demo.util.ICES4HUUtils;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class AppUserController {
    final AppUserServiceImpl appUserService;
    final AuthenticationService authenticationService;
    final EmailServiceImpl emailService;


    @GetMapping("/current")
    public ResponseEntity<?> getCurrentName() {
        //System.out.println("user id: " + appUserService.findByUsername("student1"));
        return ResponseEntity.ok(appUserService.findByUsername("student1"));
    }

    // TODO add the PreAuthorize annotation
    @GetMapping(value = {"/get-taken-courses/{id}", "/get-taken-courses"})
    public ResponseEntity<?> getTakenCourses(@PathVariable Optional<Long> id) {
        AppUser user;
        if (id.isPresent()) {
            user = appUserService.getById(id.get());
        } else {
            user = appUserService.findByUsername(getCurrentUsername());
        }
        return ResponseEntity.ok(user.getTakenCourses().stream().map(CourseDTO::new).collect(Collectors.toList()));
    }

    // TODO add the PreAuthorize annotation
    @GetMapping(value = {"/get-given-courses/{id}", "/get-given-courses"})
    public ResponseEntity<?> getGivenCourses(@PathVariable Optional<Long> id) {
        AppUser user;
        if (id.isPresent()) {
            user = appUserService.getById(id.get());
        } else {
            user = appUserService.findByUsername(getCurrentUsername());
        }
        return ResponseEntity.ok(user.getGivenCourses().stream().map(CourseDTO::new).collect(Collectors.toList()));
    }

    @PostMapping("/save")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> save(@RequestBody AppUser appUser) {
        try {
            if (appUser.getPassword() == null || appUser.getPassword().isBlank() || appUser.getPassword().isEmpty()) {
                String password = ICES4HUValidatorUtil.generateRandomPassword();
                appUser.setPassword(password);
            }
            appUserService.save(appUser);
            System.out.println("User " + appUser.getUsername() + " added!");

            return ResponseEntity.ok("Success! User " + appUser.getUsername() + " added with the id of " + appUserService.findByUsername(appUser.getUsername()).getId() + "!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/update/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> put(@PathVariable Long id, @RequestBody AppUser appUser) {
        try {
            appUserService.update(id, appUser);
            System.out.println("User with the id " + id + " updated successfully!");
            return ResponseEntity.ok("User with the id " + id + " updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Transactional
    @PutMapping("/self-update")

    public ResponseEntity<?> selfPut(@RequestBody AppUserProfileDTO appUser) {
        try {
            Long id = appUserService.findByUsername(getCurrentUsername()).getId();
            System.out.println(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
            System.out.println(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword());
            appUserService.selfUpdate(id, appUser);

            // authenticationService.authenticate(new AuthenticationRequest(appUser.getUsername(), ));

            System.out.println("User with the id " + id + " updated successfully!");
            return ResponseEntity.ok("User with the id " + id + " updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            AppUser appUser = appUserService.deleteById(id);
            System.out.println("User " + appUser.getUsername() + " deleted!");
            return ResponseEntity.ok("Success! User with the id of " + id + " deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @Transactional
    @PutMapping("/ban/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> ban(@PathVariable Long id) {
        try {
            AppUser appUser = appUserService.getById(id);
            if (appUser.getRole() != Role.STUDENT) {
                throw new Exception("Users which has no role " + Role.STUDENT + " can't be banned");
            }
            appUser.setBanned(true);
            appUserService.update(id, appUser);
            System.out.println("User " + appUser.getUsername() + " banned!");
            return ResponseEntity.ok("Success! User with the id of " + id + " banned!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/remove-ban/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> removeBan(@PathVariable Long id) {
        try {
            AppUser appUser = appUserService.getById(id);

            appUser.setBanned(false);
            appUserService.update(id, appUser);
            System.out.println("Removed ban of User: " + appUser.getUsername());
            return ResponseEntity.ok("Success! Removed ban of User with the id of: " + id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/reset-password")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<?> resetPassword(@RequestBody Password request) {
        try {
            appUserService.resetPassword(request.getUserId(), request.getPassword());
            return ResponseEntity.ok("Password reset successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Transactional
    @PutMapping("/self-reset-password")
    public ResponseEntity<?> selfResetPassword(@RequestBody AppUserPasswordDTO appUser) {
        try {
            Long id = appUserService.findByUsername(getCurrentUsername()).getId();
//            System.out.println(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
//            System.out.println(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPassword());

            appUserService.selfUpdatePassword(id, appUser);

            System.out.println("Password reset successful");
            return ResponseEntity.ok("User with the id " + id + " password reset successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {"/list", "/list/{role}"})
    @PreAuthorize("hasAnyRole({'ROLE_ADMIN'})")
    public ResponseEntity<?> UserList(@PathVariable Optional<Role> role) {
        try {
            List<AppUserDTO> appUsers;
            if (role.isEmpty()) {
                appUsers = appUserService.getAllList();
            } else {
                appUsers = appUserService.getAllWithRole(role.get());
            }

            System.out.println("AppUsers are listed successfully!");
            return ResponseEntity.ok(appUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list-instructor")
    @PreAuthorize("hasAnyRole({'ROLE_DEPARTMENT_MANAGER'})")
    public ResponseEntity<?> instructorList() {
        try {
            AppUser appUser = appUserService.findByUsername(ICES4HUUtils.getCurrentUsername());

            List<AppUserDTO> appUsers;

            appUsers = appUserService.getAllWithRole(Role.INSTRUCTOR);

            System.out.println("AppUsers are listed successfully!");
            return ResponseEntity.ok(
                    appUsers.stream()
                            .filter(
                                    user -> user.getDepartmentId().equals(appUser.getDepartmentId()))
                            .toList()
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
