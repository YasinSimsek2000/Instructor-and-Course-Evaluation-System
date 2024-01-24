package com.ices4hu.demo.repository;

import com.ices4hu.demo.entity.EnrollmentRequest;
import com.ices4hu.demo.entity.PasswordResetRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRequestRepository extends JpaRepository<PasswordResetRequest, Long> {
    Optional<PasswordResetRequest> findById(Long id);
}
