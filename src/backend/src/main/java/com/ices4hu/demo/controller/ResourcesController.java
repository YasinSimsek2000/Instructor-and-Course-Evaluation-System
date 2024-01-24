package com.ices4hu.demo.controller;

import com.ices4hu.demo.entity.AppUser;
import com.ices4hu.demo.entity.Resources;
import com.ices4hu.demo.service.impl.AppUserServiceImpl;
import com.ices4hu.demo.service.impl.ResourcesServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ices4hu.demo.util.ICES4HUUtils.getCurrentUsername;

@RestController
@RequestMapping("/resources")
@AllArgsConstructor
public class ResourcesController {

    final ResourcesServiceImpl newsService;
    final AppUserServiceImpl appUserService;

    @PostMapping("/save")
    @PreAuthorize("hasRole('ROLE_DEPARTMENT_MANAGER')")
    public ResponseEntity<?> save(@RequestBody String message) {
        try {
            AppUser appUser = appUserService.findByUsername(getCurrentUsername());

            Resources resources = new Resources();
            resources.setDepartmentId(appUser.getDepartmentId());
            resources.setText(message);

            newsService.saveResources(resources);
            System.out.println("News added!");
            return ResponseEntity.ok("Success! News added!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_DEPARTMENT_MANAGER', 'ROLE_INSTRUCTOR')")
    public ResponseEntity<?> get() {
        try {
            AppUser appUser = appUserService.findByUsername(getCurrentUsername());

            List<Resources> resourcesList = newsService.getResourcesByDepartmentId(appUser.getDepartmentId());

            return ResponseEntity.ok(resourcesList);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
