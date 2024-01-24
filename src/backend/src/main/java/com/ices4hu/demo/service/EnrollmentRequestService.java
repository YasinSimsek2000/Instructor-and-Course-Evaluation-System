package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.EnrollmentRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EnrollmentRequestService {
    EnrollmentRequest save(EnrollmentRequest enrollmentRequest) throws Exception;

    default List<EnrollmentRequest> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<EnrollmentRequest> getAllList(Sort sort) throws Exception;

}
