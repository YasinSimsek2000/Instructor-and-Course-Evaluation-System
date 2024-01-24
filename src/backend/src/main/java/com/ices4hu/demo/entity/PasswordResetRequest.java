package com.ices4hu.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ices4hu.demo.util.ICES4HUValidatable;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity(name = "password_reset_request")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest implements ICES4HUValidatable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;


    @JsonIgnore
    @Override
    public boolean isValid() throws Exception {
        if (!ICES4HUValidatorUtil.emailIsValid(this.email)) throw new Exception("Primary e-mail is nat valid!");
        return true;
    }

}
