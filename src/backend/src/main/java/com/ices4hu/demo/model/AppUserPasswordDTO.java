package com.ices4hu.demo.model;

import com.ices4hu.demo.util.ICES4HUValidatable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUserPasswordDTO implements ICES4HUValidatable {
    private String oldPassword;
    private String newPassword;

    @Override
    public boolean isValid() throws Exception {
        if(oldPassword.equals(newPassword)) throw new Exception("Old password can't be same with new password");
        if(newPassword.isBlank()) throw new Exception("New password can't be blank");

        return true;

    }
}
