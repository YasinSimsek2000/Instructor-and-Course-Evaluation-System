package com.ices4hu.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    Permission.ADMIN_READ,
                    Permission.ADMIN_UPDATE,
                    Permission.ADMIN_DELETE,
                    Permission.ADMIN_CREATE,
                    Permission.DEPARTMENT_MANAGER_READ,
                    Permission.DEPARTMENT_MANAGER_UPDATE,
                    Permission.DEPARTMENT_MANAGER_DELETE,
                    Permission.DEPARTMENT_MANAGER_CREATE,
                    Permission.INSTRUCTOR_READ,
                    Permission.INSTRUCTOR_UPDATE,
                    Permission.INSTRUCTOR_CREATE,
                    Permission.INSTRUCTOR_DELETE,
                    Permission.INSTRUCTOR_CREATE_SURVEY,
                    Permission.INSTRUCTOR_VIEW_ALL_ANSWERS,
                    Permission.STUDENT_READ,
                    Permission.STUDENT_UPDATE,
                    Permission.STUDENT_CREATE,
                    Permission.STUDENT_DELETE,
                    Permission.STUDENT_FILL_SURVEY
            )
    ),
    STUDENT(Set.of(
            Permission.STUDENT_FILL_SURVEY
    )),
    INSTRUCTOR(Set.of(
            Permission.INSTRUCTOR_CREATE_SURVEY,
            Permission.INSTRUCTOR_VIEW_ALL_ANSWERS
    )),
    DEPARTMENT_MANAGER(Collections.emptySet());

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Permission permission: this.permissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
