package com.ices4hu.demo.model;

import com.ices4hu.demo.util.ICES4HUValidatable;
import com.ices4hu.demo.util.ICES4HUValidatorUtil;
import com.ices4hu.demo.util.NullCheckable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.NoSuchElementException;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserProfileDTO implements ICES4HUValidatable, NullCheckable {
    private String email;
    private String secondEmail;



    @Override
    public boolean isValid() throws Exception {
        if (!ICES4HUValidatorUtil.emailIsValid(this.email)) throw new Exception("Primary e-mail is nat valid!");
        if (!ICES4HUValidatorUtil.emailIsValid(this.secondEmail)) throw new Exception("Secondary e-mail is not valid!");

        return true;
    }

    @Override
    public void nullCheck() throws NullPointerException, NoSuchElementException {
        if (this.email == null) throw new NullPointerException("Primary e-mail can't be null!");
        if (this.email.isBlank()) throw new NullPointerException("Primary e-mail can't be blank!");
    }
}
