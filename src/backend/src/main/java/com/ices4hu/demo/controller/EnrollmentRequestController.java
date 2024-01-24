package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.EnrollmentRequest;
import com.ices4hu.demo.service.impl.EnrollmentRequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enroll-requests")
public class EnrollmentRequestController {
    final
    EnrollmentRequestServiceImpl enrollmentRequestService;

    public EnrollmentRequestController(EnrollmentRequestServiceImpl enrollmentRequestService) {
        this.enrollmentRequestService = enrollmentRequestService;
    }

    @GetMapping("/list")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> UserList() throws Exception {
        try {
            List<EnrollmentRequest> appUsers = enrollmentRequestService.getAllList();
            System.out.println("AppUsers are listed successfully!");
            return ResponseEntity.ok(appUsers);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
