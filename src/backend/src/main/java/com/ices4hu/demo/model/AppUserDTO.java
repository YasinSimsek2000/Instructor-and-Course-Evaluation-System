package com.ices4hu.demo.model;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDTO {
    private long id;
    private String username;
    private String email;
    private String secondEmail;
    private Role role;
    private String detail;
    private Long departmentId;
    private Date updatedAt;
    private Date createdAt;

    private String imageId;

    public AppUserDTO(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
        this.email = appUser.getEmail();
        this.secondEmail = appUser.getSecondEmail();
        this.role = appUser.getRole();
        this.detail = appUser.getDetail();
        this.departmentId = appUser.getDepartmentId();
        this.updatedAt = appUser.getUpdatedAt();
        this.createdAt = appUser.getCreatedAt();
        this.imageId = appUser.getProfilePictureId();
    }

    // Getters and setters (if needed)
}
