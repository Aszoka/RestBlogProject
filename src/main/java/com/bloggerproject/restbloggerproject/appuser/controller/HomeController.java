package com.bloggerproject.restbloggerproject.appuser.controller;

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

    @GetMapping("/login")
    public String getLogin(@ModelAttribute("userForm") AppUser userForm){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("userForm") AppUser userForm, Model model){

        model.addAttribute("username", myUserService.getLoggedInUser().getUsername());
        model.addAttribute("password", myUserService.getLoggedInUser().getPassword());

        return "home";
    }
}
