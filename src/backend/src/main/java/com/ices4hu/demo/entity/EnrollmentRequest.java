package com.ices4hu.demo.entity;

import com.ices4hu.demo.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "enroll_request")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "detail")
    private String detail;

    @Column(name = "message")
    private String message;

    @Enumerated(EnumType.STRING)
    private Role role;

}
