package com.ices4hu.demo.util;

import com.ices4hu.demo.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ICES4HUUtils {

    private static AppUserRepository appUserRepository;

    private ICES4HUUtils(AppUserRepository appUserRepository){
        ICES4HUUtils.appUserRepository = appUserRepository;
    }

    public static String RECIPIENT_EMAIL = "ahmetmaruferdogan@gmail.com";

    public static String getCurrentUsername(){
       return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }

    public static Long getCurrentUserId(){
        return appUserRepository.findByUsername(getCurrentUsername()).get().getId();
    }


}
