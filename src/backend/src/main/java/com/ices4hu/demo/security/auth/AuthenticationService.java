package com.ices4hu.demo.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.repository.DepartmentRepository;
import com.ices4hu.demo.security.config.JwtService;
import com.ices4hu.demo.security.token.Token;
import com.ices4hu.demo.security.token.TokenRepository;
import com.ices4hu.demo.security.token.TokenType;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.DepartmentServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserRepository userRepository;

    private final DepartmentRepository departmentRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    private final AppUserServiceImpl appUserService;

    private final DepartmentServiceImpl departmentService;

    public void register(AppUser user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            AuthenticationResponse.builder()
                    .accessToken("User already exists!")
                    .refreshToken("User already exists!")
                    .build();
            return;
        }
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    /*
    public ManagementResponse makeManager(ManagementRequest managementRequest) throws Exception {
        var user = userRepository.findByUsername(managementRequest.getUsername()).orElseThrow();

        if(user.getRole() == Role.INSTRUCTOR){
            var department = departmentRepository.findByDepartmentCode(managementRequest.getDepartmentCode()).orElseThrow();
            user.setRole(Role.DEPARTMENT_MANAGER);
            department.setDepartmentManagerId(user.getId());
            appUserService.update(user.getId(), user);
           departmentService.update(department.getId(), department);
        }
        return null;
    }
    */

    private void saveUserToken(AppUser appUser, String jwtToken) {
        var token = Token.builder()
                .user(appUser)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(AppUser user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            var user = this.userRepository.findByUsername(username)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
