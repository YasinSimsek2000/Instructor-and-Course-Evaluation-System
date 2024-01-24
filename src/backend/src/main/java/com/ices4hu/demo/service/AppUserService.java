package com.ices4hu.demo.service;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.model.AppUserDTO;
import com.ices4hu.demo.model.AppUserPasswordDTO;
import com.ices4hu.demo.model.AppUserProfileDTO;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AppUserService {
    AppUser save(AppUser appUser) throws Exception;

    AppUser deleteById(Long id) throws Exception;

    AppUser update(Long id, AppUser appUser) throws Exception;

    AppUserProfileDTO selfUpdate(Long id, AppUserProfileDTO appUserProfileDTO) throws Exception;

    void selfUpdatePassword(Long id, AppUserPasswordDTO passwordDTO) throws Exception;

    AppUser resetPassword(Long id, String newPassword) throws Exception;

    AppUser resetPassword(Long id) throws Exception;

    default List<AppUserDTO> getAllList() throws Exception {
        return getAllList(Sort.by(Sort.Direction.ASC, "id"));
    }

    List<AppUserDTO> getAllList(Sort sort) throws Exception;

    default List<AppUserDTO> getAllWithRole(Role role) throws Exception {
        return getAllWithRole(role, Sort.by(Sort.Direction.DESC, "id"));
    }

    List<AppUserDTO> getAllWithRole(Role role, Sort sort) throws Exception;

    AppUser getById(Long id);

    AppUser findByUsername(String username);

    boolean isIdBelongToUsername(Long id);


}
