package com.ices4hu.demo.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    DEPARTMENT_MANAGER_READ("manager:read"),
    DEPARTMENT_MANAGER_UPDATE("manager:update"),
    DEPARTMENT_MANAGER_CREATE("manager:create"),
    DEPARTMENT_MANAGER_DELETE("manager:delete"),
    INSTRUCTOR_READ("instructor:read"),
    INSTRUCTOR_UPDATE("instructor:update"),
    INSTRUCTOR_CREATE("instructor:create"),
    INSTRUCTOR_DELETE("instructor:delete"),
    INSTRUCTOR_CREATE_SURVEY("instructor:create_survey"),
    INSTRUCTOR_VIEW_ALL_ANSWERS("instructor:view_all_answers"),
    STUDENT_READ("student:read"),
    STUDENT_UPDATE("student:update"),
    STUDENT_CREATE("student:create"),
    STUDENT_DELETE("student:delete"),
    STUDENT_FILL_SURVEY("student:fill-survey");

    @Getter
    private final String permission;
}
