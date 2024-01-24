package com.ices4hu.demo.service;


import com.ices4hu.demo.entity.EnrollmentRequest;
import com.ices4hu.demo.entity.PasswordResetRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PasswordResetRequestService {
    PasswordResetRequest getById(Long id) throws Exception;

    PasswordResetRequest deleteById(Long id) throws Exception;


    PasswordResetRequest save(PasswordResetRequest passwordResetRequest) throws Exception;

    default List<PasswordResetRequest> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<PasswordResetRequest> getAllList(Sort sort) throws Exception;


}
