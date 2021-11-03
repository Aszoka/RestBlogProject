package com.bloggerproject.restbloggerproject.appuser.utils;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserValidation {

    //8-20 characters, must contain digits, lowercase, uppercase and special character(at least one from each)
    public boolean validPassword(String pwd){
        String pwdRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(pwdRegex);
        Matcher matcher = pattern.matcher(pwd);
        return matcher.matches();
    }

    public boolean validEmail(String email) {
        String emailRegex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean confirmPassword(String pwd, String confirmPwd){

        return pwd.equals(confirmPwd);
    }
}
