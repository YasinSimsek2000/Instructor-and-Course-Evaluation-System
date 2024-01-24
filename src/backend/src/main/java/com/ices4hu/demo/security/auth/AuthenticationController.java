package com.ices4hu.demo.security.auth;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.EnrollmentRequest;
import com.ices4hu.demo.entity.PasswordResetRequest;
import com.ices4hu.demo.service.impl.EnrollmentRequestServiceImpl;
import com.ices4hu.demo.service.impl.PasswordResetRequestServiceImpl;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.ices4hu.demo.enums.Role.ADMIN;

@RestController
@RequestMapping("/connection")
@PermitAll
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final EnrollmentRequestServiceImpl enrollmentRequestService;
    private final PasswordResetRequestServiceImpl passwordResetRequestService;


    @GetMapping("/test")
    public ResponseEntity<?> getTest() {

        var admin = AppUser.builder()
                .username("admin")
                .email("admin@mail.com")
                .secondEmail(null)
                .password(ICES4HUValidatorUtil.defaultPasswordEncoder().encode("password"))
                .role(ADMIN)
                .detail(null)
                .build();
        authenticationService.register(admin);

        return new ResponseEntity<>("Api is up!", HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollRequest(
            @RequestBody EnrollmentRequest enrollmentRequest
    ) {
        try {
            enrollmentRequestService.save(enrollmentRequest);
            return ResponseEntity.ok("Request sent!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPasswordRequest(
            @RequestBody PasswordResetRequest passwordResetRequest
    ) {
        try {
            passwordResetRequestService.save(passwordResetRequest);
            return ResponseEntity.ok("Request sent!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }


}
