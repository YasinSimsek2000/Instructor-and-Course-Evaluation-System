package com.ices4hu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ices4hu.demo.enums.Role;
import com.ices4hu.demo.security.token.Token;
import com.ices4hu.demo.util.ICES4HUValidatable;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import com.ices4hu.demo.util.NullCheckable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity(name = "app_user")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser implements ICES4HUValidatable, NullCheckable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "second_email", unique = true)
    private String secondEmail;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "detail")
    private String detail;

    @Column(name = "banned")
    private Boolean banned;

    @Column(name = "department_id")
    private Long departmentId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties(value = "user", allowSetters = true)
    @JsonIgnore
    private List<Token> tokens;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "profile_picture_id")
    private String profilePictureId;



    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> takenCourses;


    public void addCourse(Course course) {
        takenCourses.add(course);
        course.getStudents().add(this);
    }


    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "course_instructor",
            joinColumns = @JoinColumn(name = "instructor_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))
    Set<Course> givenCourses;

    @Override
    public boolean isValid() throws Exception {
        if (!ICES4HUValidatorUtil.emailIsValid(this.email)) throw new Exception("Primary e-mail is nat valid!");
        if (!ICES4HUValidatorUtil.emailIsValid(this.secondEmail)) throw new Exception("Secondary e-mail is not valid!");

        return true;
    }

    @OneToMany(mappedBy="creator")
    private List<Survey> createdSurveys;


    @OneToMany(mappedBy="responder")
    private List<ResponseSheet> responseSheets;


    @Override
    public void nullCheck() throws NullPointerException, NoSuchElementException {
        if (this.username == null) throw new NullPointerException("Username can't be null!");
        if (this.username.isBlank()) throw new NullPointerException("Username can't be blank!");

        if (this.password == null) throw new NullPointerException("Password can't be null!");
        if (this.password.isBlank()) throw new NullPointerException("Password can't be blank!");

        if (this.email == null) throw new NullPointerException("Primary e-mail can't be null!");
        if (this.email.isBlank()) throw new NullPointerException("Primary e-mail can't be blank!");

        if (this.role == null) throw new NullPointerException("Role can't be null!");


    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return banned == null || !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        System.out.println("isEnabled(): " + (banned == null || !banned));
        return banned == null || !banned;
    }
}
