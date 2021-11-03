package com.bloggerproject.restbloggerproject.appuser.controller;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import com.bloggerproject.restbloggerproject.appuser.model.AppUserRole;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestBody;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper mapper;

    @Test
    void getAppUsersRedirectedToLogIn() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(redirectedUrl("http://localhost/login"));
    }
    @Test
    @WithMockUser(authorities = "read:all")
    void getAppUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "read:self")
    void getAppUsersNotAuth() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is(403));
    }

    @Test
    @WithMockUser(authorities = "read:all")
    void getAppUserAuth() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().is(200))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "read:self")
    void getAppUserNoAuth() throws Exception {
       mockMvc.perform(get("/users/1"))
                .andExpect(status().is(403))
                .andReturn();
    }

    @Test
    @WithMockUser
    void getLoggedInUser() throws Exception {
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser() throws Exception {
        AppUserForm newUser = new AppUserForm("testusername","firstname","lastname","email","password");
        String body = mapper.writeValueAsString(newUser);
            mockMvc.perform(
                    post("/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body)

            )
                    .andExpect(status().isOk())
                    .andReturn();

    }

    @Test
    void getAllBlogs() {
    }

    @Test
    void createBlog() {
    }

    @Test
    void helloEndpointExists() throws Exception {
        mockMvc.perform(get("/", "/hello"))
                .andExpect(status().isOk());
    }

}