package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.PasswordResetRequest;
import com.ices4hu.demo.model.Password;
import com.ices4hu.demo.service.PasswordResetRequestService;
import com.ices4hu.demo.service.exception.UserNotFoundException;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.PasswordResetRequestServiceImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reset-password")
@RequiredArgsConstructor
public class PasswordResetRequestController {

    final PasswordResetRequestServiceImpl passwordResetRequestService;

    final AppUserServiceImpl appUserService;


    @DeleteMapping("/delete/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> delete(@PathVariable Long id) throws Exception {
        try {
            PasswordResetRequest passwordResetRequest = passwordResetRequestService.deleteById(id);
            System.out.println("PasswordResetRequest for id: " + passwordResetRequest.getId() + " deleted!");
            return ResponseEntity.ok("Success! PasswordResetRequest with the id of " + id + " deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody PasswordResetRequest passwordResetRequest) {
        try {
            passwordResetRequestService.save(passwordResetRequest);
            System.out.println("PasswordResetRequest for email: " + passwordResetRequest.getEmail() + " added!");
            return ResponseEntity.ok("Success! PasswordResetRequest for email: " +passwordResetRequest.getEmail() + " added!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/list")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<?> ResetPasswordList() throws Exception {
        try {
            List<PasswordResetRequest> requests = passwordResetRequestService.getAllList();
            System.out.println("PasswordResetRequests are listed successfully!");
            return ResponseEntity.ok(requests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
