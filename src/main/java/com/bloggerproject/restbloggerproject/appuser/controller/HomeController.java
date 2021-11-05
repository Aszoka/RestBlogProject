package com.bloggerproject.restbloggerproject.appuser.controller;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import com.bloggerproject.restbloggerproject.appuser.service.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class HomeController {

    MyUserService myUserService;

    @GetMapping(value= {"/" , "/home"})
    public String getHome(){
        return "home";
    }

    @GetMapping("/my-error-page")
    public String accessDenied(){
        return "my-error-page";
    }

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userForm") AppUser userForm){
        return "login";
    }

    /*@PostMapping("/login")
    public String postLogin(@ModelAttribute("userForm") AppUser userForm, Model model){

        model.addAttribute("username", myUserService.getLoggedInUser().getUsername());
        model.addAttribute("password", myUserService.getLoggedInUser().getPassword());

        return "home";
    }*/

/*   @GetMapping("/register")
    public String getRegister(@ModelAttribute("signInForm") AppUserForm signInForm){
        return "login";
    }*/

 /*  @PostMapping("/register")
    public String postRegister(@ModelAttribute("signInForm") AppUserForm signInForm, Model model){
       model.addAttribute("usernameForm", signInForm.getUsernameForm());
       model.addAttribute("passwordForm", signInForm.getPasswordForm());
       model.addAttribute("firstNameForm", signInForm.getFirstNameForm());
       model.addAttribute("lastNameForm", signInForm.getLastNameForm());
       model.addAttribute("emailForm", signInForm.getEmailForm());

       myUserService.signUpUser(signInForm);

        return "home";
    }*/

    @GetMapping("/register")
    public String getRegister(Model model){
        model.addAttribute("appUserForm", new AppUserForm());
        return "login";
    }

}
