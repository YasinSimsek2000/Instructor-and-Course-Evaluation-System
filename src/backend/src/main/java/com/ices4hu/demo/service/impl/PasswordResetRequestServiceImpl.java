package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.PasswordResetRequest;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.repository.PasswordResetRequestRepository;
import com.ices4hu.demo.service.PasswordResetRequestService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordResetRequestServiceImpl implements PasswordResetRequestService {

    final PasswordResetRequestRepository passwordResetRequestRepository;

    final AppUserRepository appUserRepository;


    public PasswordResetRequestServiceImpl(PasswordResetRequestRepository passwordResetRequestRepository, AppUserRepository appUserRepository) {
        this.passwordResetRequestRepository = passwordResetRequestRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public PasswordResetRequest getById(Long id) throws Exception {
        return passwordResetRequestRepository.findById(id)
                .orElseThrow(() -> new Exception("No password reset request found with id: " + id));
    }

    @Override
    public PasswordResetRequest deleteById(Long id) throws Exception {
        try {
            PasswordResetRequest passwordResetRequest = passwordResetRequestRepository.findById(id).orElseThrow();
            passwordResetRequestRepository.deleteById(id);
            return passwordResetRequest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public PasswordResetRequest save(PasswordResetRequest passwordResetRequest) throws Exception {
        try{
            passwordResetRequest.isValid();
            passwordResetRequestRepository.save(passwordResetRequest);
            return passwordResetRequest;
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public List<PasswordResetRequest> getAllList(Sort sort) throws Exception {
        try {
            return passwordResetRequestRepository.findAll(sort).stream().toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
