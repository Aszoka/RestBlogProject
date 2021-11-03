package com.bloggerproject.restbloggerproject.appuser.forms;

import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppUserForm {

    private String usernameForm;
    private String firstNameForm;
    private String lastNameForm;
    private String emailForm;
    private String passwordForm;

    //ez kell mindenképp!!!!!!!!! XDDD
    public AppUserForm() {
    }

    // this goes to the controller when someone registers
    public AppUserForm(String usernameForm, String firstNameForm, String lastNameForm, String emailForm, String passwordForm) {
        this.usernameForm = usernameForm;
        this.firstNameForm = firstNameForm;
        this.lastNameForm = lastNameForm;
        this.emailForm = emailForm;
        this.passwordForm = passwordForm;
    }

    // this goes to the admin when querying userdata
    public AppUserForm(String usernameForm, String firstNameForm, String lastNameForm, String emailForm) {
        this.usernameForm = usernameForm;
        this.firstNameForm = firstNameForm;
        this.lastNameForm = lastNameForm;
        this.emailForm = emailForm;
    }
    // this goes to the admin when querying userdata
    public AppUserForm(AppUser appUser) {
        this(appUser.getUsername(), appUser.getFirstName(), appUser.getLastName(), appUser.getEmail());
    }


}
