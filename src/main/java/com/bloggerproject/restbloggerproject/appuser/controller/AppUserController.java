package com.bloggerproject.restbloggerproject.appuser.controller;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.forms.BlogForm;
import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import com.bloggerproject.restbloggerproject.appuser.model.Blog;
import com.bloggerproject.restbloggerproject.appuser.model.Template;
import com.bloggerproject.restbloggerproject.appuser.service.MyUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.*;

@RestController
@AllArgsConstructor
public class AppUserController {

    // handling the authorities in WebSecConfig

    MyUserService myUserService;

 /*   @GetMapping(value = {"/","/hello"})
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("hello", HttpStatus.OK);
    }*/

    //read:all
    @GetMapping("/users")
    public ResponseEntity<List<AppUserForm>> getAppUsers() {
        try {
            ResponseEntity<List<AppUserForm>> users = new ResponseEntity<List<AppUserForm>>(myUserService.getAppUsers(),HttpStatus.OK);
            return users;
        } catch (AccessDeniedException e) {
            System.out.println("no no!!!");
            return null;
        }

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

    @PostMapping(value="/register")
    public ResponseEntity<AppUser> registerUser(@RequestBody AppUserForm appUserForm) {

        return new ResponseEntity<>(myUserService.signUpUser(appUserForm), HttpStatus.OK);
    }


    // read:all
    @GetMapping (path = "/blogs")
    public ResponseEntity<List<BlogForm>> getAllBlogs() {
        ResponseEntity<List<BlogForm>> allBlogs = new ResponseEntity(myUserService.getAllBlogs(), HttpStatus.OK);
        return allBlogs;
    }

    // role ADMIN
    @PostMapping("/templates")
    public void addTemplates(@RequestBody Template template) {
        myUserService.createTemplate(template);
    }

    // logged in users only
    @PostMapping("/blogs")
    public void createBlog(@RequestBody BlogForm blog, Long templateId) {
        AppUser appUser = myUserService.getLoggedInUser();
        blog.setAuthorId(appUser.getId());
        blog.setBlogTemplateId(templateId);
        myUserService.createBlog(blog);
    }
}
