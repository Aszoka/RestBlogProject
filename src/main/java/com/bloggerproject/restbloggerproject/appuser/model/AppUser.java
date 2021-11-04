package com.bloggerproject.restbloggerproject.appuser.model;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table
@Getter
@Setter
public class AppUser implements UserDetails {

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    @CreationTimestamp
    private Timestamp regTime;
    @Column(nullable = false)
    private String password;
    @Enumerated
    private AppUserRole role;

    private boolean isLocked;

    //@OneToMany(mappedBy = "blogAuthor")
    @Transient
    private List<Blog> blogList;

    public AppUser() {
        blogList = new LinkedList<>();
    }

    public AppUser(String username,
                   String firstName,
                   String lastName,
                   String email,
                   String password,
                   AppUserRole role) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
        blogList = new LinkedList<>();
    }

    public AppUser(String username,
                   String firstName,
                   String lastName,
                   String email,
                   String password) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public AppUser(AppUserForm user, AppUserRole role) {
        //this comes from the form at the registration
        this(user.getUsernameForm(), user.getFirstNameForm(), user.getLastNameForm(), user.getEmailForm(), user.getPasswordForm(), role);
    }

    public AppUser(AppUserForm appUserForm) {
        this(appUserForm.getUsernameForm(), appUserForm.getFirstNameForm(),appUserForm.getLastNameForm(),appUserForm.getEmailForm());
        blogList = new LinkedList<>();
    }

    public AppUser(String usernameForm, String firstNameForm, String lastNameForm, String emailForm) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getGrantedAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return !isLocked;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isLocked;
    }

    @Override
    public boolean isEnabled() {
        return !isLocked;
    }
}
