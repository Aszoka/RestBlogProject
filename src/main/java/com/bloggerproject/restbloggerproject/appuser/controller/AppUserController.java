package com.bloggerproject.restbloggerproject.appuser.controller;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.forms.BlogForm;
import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import com.bloggerproject.restbloggerproject.appuser.model.Blog;
import com.bloggerproject.restbloggerproject.appuser.model.Template;
import com.bloggerproject.restbloggerproject.appuser.service.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
public class AppUserController {


    MyUserService myUserService;

    @GetMapping(value = {"/","/hello"})
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }

    //read:all
    @GetMapping("/users")
    public ResponseEntity<List<AppUserForm>> getAppUsers() {
        ResponseEntity<List<AppUserForm>> users = new ResponseEntity<List<AppUserForm>>(myUserService.getAppUsers(),HttpStatus.OK);
        return users;
    }

    //read:all
    // todo rewrite to appUserForm
    @GetMapping(path = "users/{userId}")
    public ResponseEntity<AppUser> getAppUser(@PathVariable Long userId) { //if the variable name and pathVar differs, use("") after the annotation
        return new ResponseEntity<>(myUserService.getAppUser(userId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public AppUser getLoggedInUser() {
       return myUserService.getLoggedInUser();
    }


   @PostMapping("/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUserForm appUser) {
         return new ResponseEntity<>(myUserService.signUpUser(appUser), HttpStatus.OK);
    }

    @GetMapping (path = "/blogs")
    public List<BlogForm> getAllBlogs() {
        return myUserService.getAllBlogs();
    }

    @PostMapping("/templates")
    public void addTemplates(@RequestBody Template template) {
        myUserService.createTemplate(template);
    }

    @PostMapping("/blogs")
    public void createBlog(@RequestBody BlogForm blog, Long templateId) {
        AppUser appUser = myUserService.getLoggedInUser();
        blog.setAuthorId(appUser.getId());
        blog.setBlogTemplateId(templateId);
        myUserService.createBlog(blog);
    }
}
