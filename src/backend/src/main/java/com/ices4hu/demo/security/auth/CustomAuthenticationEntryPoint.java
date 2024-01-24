package com.ices4hu.demo.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public CustomAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        if (request.getRequestURI().equals("/connection/authenticate")) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            Map<String, Object> data = new HashMap<>();
            data.put("timestamp", System.currentTimeMillis());
            data.put("status", HttpStatus.FORBIDDEN.value());
            data.put("error", "Authentication failed");
            data.put("message", authException.getMessage());

            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(data));
        } else {
            response.sendError(HttpStatus.FORBIDDEN.value(), authException.getMessage());
        }
    }
}
