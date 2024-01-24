package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.EnrollmentRequest;
import com.ices4hu.demo.repository.EnrollmentRequestRepository;
import com.ices4hu.demo.service.EnrollmentRequestService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentRequestServiceImpl implements EnrollmentRequestService {
    final
    EnrollmentRequestRepository enrollmentRequestRepository;

    public EnrollmentRequestServiceImpl(EnrollmentRequestRepository enrollmentRequestRepository) {
        this.enrollmentRequestRepository = enrollmentRequestRepository;
    }


    @Override
    public EnrollmentRequest save(EnrollmentRequest enrollmentRequest) throws Exception {
        try {
            enrollmentRequestRepository.save(enrollmentRequest);
            return enrollmentRequest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<EnrollmentRequest> getAllList(Sort sort) throws Exception {
        try {
            return enrollmentRequestRepository.findAll(sort).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
