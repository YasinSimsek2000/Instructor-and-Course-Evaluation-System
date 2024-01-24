package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.FileDB;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.model.ResponseMessage;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@RestController
@RequestMapping("/profile-picture")
@AllArgsConstructor
public class ProfilePictureController {

    // TODO Update tis value according to your image ID of your default image on local database
    private static final String DEFAULT_PROFILE_PICTURE_UUID = "78d40728-6546-4fa9-a5e2-0bb7255b06a8";

    final AppUserServiceImpl appUserService;
    private FileStorageService storageService;

    @GetMapping("/get/{userId}")
    @PreAuthorize("true")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long userId) {
        AppUser user;
        user = appUserService.getById(userId);

        FileDB fileDB;
        try{
           fileDB = storageService.getFile(user.getProfilePictureId());
        }catch (Exception e){
            fileDB = storageService.getFile(DEFAULT_PROFILE_PICTURE_UUID);
        }



        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @Transactional
    @PostMapping("/set/{userId}")
    public ResponseEntity<ResponseMessage> uploadProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            var requesterUser = appUserService.findByUsername(getCurrentUsername());
            var user = appUserService.getById(userId);

            if(user.getId() != requesterUser.getId() && requesterUser.getRole() != Role.ADMIN){
                return ResponseEntity.badRequest().body(new ResponseMessage("You are not allowed to change this profile picture"));
            }

            var fileDB = storageService.store(file);


            storageService.removeByUUID(user.getProfilePictureId());

            user.setProfilePictureId(fileDB.getId());

            appUserService.update(userId, user);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the profile picture: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @Transactional
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseMessage> deleteProfilePicture(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            var requesterUser = appUserService.findByUsername(getCurrentUsername());
            var user = appUserService.getById(userId);

            if(user.getId() != requesterUser.getId() && requesterUser.getRole() != Role.ADMIN){
                return ResponseEntity.badRequest().body(new ResponseMessage("You are not allowed to change this profile picture"));
            }
            storageService.removeByUUID(user.getProfilePictureId());
            user.setProfilePictureId(null);
            appUserService.update(userId, user);

            message = "Deleted the profile picture successfully: " + file.getOriginalFilename();


            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "An error occured inside deleteProfilePicture not delete the profile picture: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }
}
