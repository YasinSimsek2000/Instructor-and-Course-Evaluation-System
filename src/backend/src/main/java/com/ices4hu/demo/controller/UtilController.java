package com.ices4hu.demo.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/util")
@AllArgsConstructor
public class UtilController {



    @GetMapping("/student")
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<?> getResponseWithStudent() {
        //System.out.println("user id: " + appUserService.findByUsername("student1"));
        return ResponseEntity.ok("Response taken succesfuly");
    }
}
