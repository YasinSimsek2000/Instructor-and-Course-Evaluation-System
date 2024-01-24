package com.ices4hu.demo.service.impl;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.model.AppUserDTO;
import com.ices4hu.demo.model.AppUserPasswordDTO;
import com.ices4hu.demo.model.AppUserProfileDTO;
import com.ices4hu.demo.model.EmailDetails;
import com.ices4hu.demo.repository.AppUserRepository;
import com.ices4hu.demo.service.AppUserService;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.ices4hu.demo.util.ICES4HUUtils.RECIPIENT_EMAIL;
import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;
import static com.ices4hu.demo.util.ICES4HUValidatorUtil.defaultPasswordEncoder;
import static com.ices4hu.demo.util.ICES4HUValidatorUtil.generateRandomPassword;

@AllArgsConstructor
@Service
@Component
public class AppUserServiceImpl implements AppUserService {

    // private final Function<String, String> passwordEncodingMethod = ICES4HUValidatorUtil.defaultPasswordEncoder()::encode;
    final AppUserRepository appUserRepository;
    final EmailServiceImpl emailService;


    @Override
    public AppUser save(AppUser appUser) throws Exception {
        try {
            appUser.nullCheck();
            appUser.isValid();

            Optional<AppUser> tempUser = appUserRepository.findByUsername(appUser.getUsername());
            if (tempUser.isPresent()) {
                throw new Exception("Account creation failed. Username already in use.");
            }
            tempUser = appUserRepository.findByEmail(appUser.getEmail());
            if (tempUser.isPresent()) {
                throw new Exception("Account creation failed. Email already in use.");
            }
            tempUser = appUserRepository.findBySecondEmailOrNull(appUser.getSecondEmail());
            if (tempUser.isPresent()) {
                throw new Exception("Account creation failed. Second Email already in use.");
            }
            if (appUser.getRole() == Role.STUDENT) {
                tempUser = appUserRepository.findByDetail(appUser.getDetail());
                if (tempUser.isPresent() && tempUser.get().getRole() == Role.STUDENT) {
                    throw new Exception("Account creation failed. Student number already in use.");
                }
            }

            String uncipheredPassword = appUser.getPassword();
            appUser.setPassword(defaultPasswordEncoder().encode(appUser.getPassword()));
            appUser.setBanned(false);

            appUserRepository.save(appUser);

            this.sendNewUserEmail(appUser, uncipheredPassword);
            appUser.setPassword("***");
            return appUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public AppUser deleteById(Long id) throws Exception {
        try {
            AppUser appUser = appUserRepository.findById(id).orElseThrow();
            appUserRepository.deleteById(id);
//            authenticationService.revokeAllUserTokens(appUser);
            return appUser;

        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new Exception("User deletion failed. No such User found with given id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public AppUser update(Long id, AppUser appUser) throws Exception {
        try {
            appUser.nullCheck();
            appUser.isValid();

            Optional<AppUser> tempUser = appUserRepository.findByUsername(appUser.getUsername());
            if (tempUser.isPresent() && tempUser.get().getId() != id) {
                throw new Exception("Account update failed. Username already in use.");
            }
            tempUser = appUserRepository.findByEmail(appUser.getEmail());
            if (tempUser.isPresent() && tempUser.get().getId() != id) {
                throw new Exception("Account update failed. Email already in use.");
            }

            tempUser = appUserRepository.findBySecondEmailOrNull(appUser.getSecondEmail());
            if (tempUser.isPresent() && tempUser.get().getId() != id) {
                throw new Exception("Account update failed. Second Email already in use.");
            }

            if (appUser.getRole() == Role.STUDENT) {
                tempUser = appUserRepository.findByDetail(appUser.getDetail());
                if (tempUser.isPresent() && tempUser.get().getRole() == Role.STUDENT && tempUser.get().getId() != id) {
                    throw new Exception("Account update failed. Student number already in use.");
                }
            }


            appUser.setPassword(defaultPasswordEncoder().encode(appUser.getPassword()));


            appUserRepository.updateAppUser(
                    id,
                    appUser.getUsername(),
                    appUser.getPassword(),
                    appUser.getEmail(),
                    appUser.getSecondEmail(),
                    appUser.getRole(),
                    appUser.getDepartmentId(),
                    appUser.getDetail(),
                    ICES4HUValidatorUtil.now()
            );
            return appUser;
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw new Exception("User update failed. No such User found with given id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public AppUserProfileDTO selfUpdate(Long id, AppUserProfileDTO appUser) throws Exception {
        try {
            appUser.nullCheck();
            appUser.isValid();
            Optional<AppUser> tempUser = appUserRepository.findByEmail(appUser.getEmail());
            if (tempUser.isPresent() && tempUser.get().getId() != id) {
                throw new Exception("Account update failed. Email already in use.");
            }
            tempUser = appUserRepository.findBySecondEmailOrNull(appUser.getSecondEmail());
            if (tempUser.isPresent() && tempUser.get().getId() != id) {
                throw new Exception("Account update failed. Second Email already in use.");
            }

            appUserRepository.selfUpdate(
                    id,
                    appUser.getEmail(),
                    appUser.getSecondEmail(),
                    ICES4HUValidatorUtil.now()
            );
            return appUser;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public void selfUpdatePassword(Long id, AppUserPasswordDTO passwordDTO) throws Exception {
        try {
            passwordDTO.isValid();

            AppUser appUser = appUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No user found with id: " + id));

//            if (!oldPassword.equals(requestOldPassword)) {
//                throw new Exception("Old password is wrong");
//            }
            if (defaultPasswordEncoder().encode(passwordDTO.getNewPassword()).equals(appUser.getPassword())) {
                throw new Exception("Old password and new password can't be same");
            }

            resetPassword(id, passwordDTO.getNewPassword());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }


    @Override
    public AppUser resetPassword(Long id, String newPassword) throws Exception {
        if (newPassword.isEmpty()) {
            return resetPassword(id);
        }
        var user = appUserRepository.findById(id);
        if (user.isEmpty()) {
            throw new Exception("User not found with id: " + id + "Password reset failed.");
        }

        AppUser appUser = user.get();
        appUser.setPassword(newPassword);

        this.update(id, appUser); // there's no need to write "appUser = this.update(id, appUser);" since the operations are done with reference.
        sendPasswordResetEmail(appUser, newPassword);
        System.out.println("Password changed to " + newPassword + "for user with email: " + appUser.getEmail());


        return appUser;
    }

    @Override
    public AppUser resetPassword(Long id) throws Exception {
        return resetPassword(id, generateRandomPassword());
    }

    private void sendPasswordResetEmail(AppUser user, String newPassword) {
        String body = "Dear " + user.getUsername() + ",\n\n"
                + "Your password has been reset. Your new password is: " + newPassword + "\n\n"
                + "Please login using the new password and consider changing it after logging in.\n\n"
                + "Best regards,\n"
                + "Pro Googlers Team";
        //EmailDetails emailDetails = new EmailDetails(user.getEmail(), body, "ICES4HU - Password Reset Request", null);
        EmailDetails emailDetails = new EmailDetails(RECIPIENT_EMAIL, body, "ICES4HU - Password Reset Request", null);

        // For testing purposes, emails will only be sen to this account
        // EmailService class also be modified accordingly. If you want to use he fonxtion argument,
        // update the EmailService class accordingly.

        emailService.sendSimpleMail(emailDetails);

    }

    private void sendNewUserEmail(AppUser user, String password) {
        String body = "Dear " + user.getUsername() + ",\n\n"
                + "Your account for ICES4HU created successfully."
                + "Your username is: " + user.getUsername() + "\n\n"
                + "Your password is: " + password + "\n\n"

                + "Please login using the new password and consider changing it after logging in.\n\n"
                + "Best regards,\n"
                + "Pro Googlers Team";
        //EmailDetails emailDetails = new EmailDetails(user.getEmail(), body, "ICES4HU - Password Reset Request", null);
        EmailDetails emailDetails = new EmailDetails(RECIPIENT_EMAIL, body, "ICES4HU - Password Reset Request", null);

        // For testing purposes, emails will only be sen to this account
        // EmailService class also be modified accordingly. If you want to use he fonxtion argument,
        // update the EmailService class accordingly.

        emailService.sendSimpleMail(emailDetails);

    }


    @Override
    public List<AppUserDTO> getAllList(Sort sort) throws Exception {
        try {
            return appUserRepository.findAll(sort).stream().map(AppUserDTO::new).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<AppUserDTO> getAllWithRole(Role role, Sort sort) throws Exception {
        try {
            return appUserRepository.findAppUsersByRole(role).stream().map(AppUserDTO::new).toList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public AppUser getById(Long id) {
        try {
            return appUserRepository.findById(id)
                    .orElseThrow(() -> new Exception("User not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public boolean isIdBelongToUsername(Long id) {
        System.out.println("id: " + id);

        AppUser appUser = appUserRepository.findById(id).orElseThrow();
        System.out.println("username: " + appUser.getUsername());
        return appUser.getUsername().equals(getCurrentUsername());
    }
}
