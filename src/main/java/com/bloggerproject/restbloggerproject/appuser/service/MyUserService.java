package com.bloggerproject.restbloggerproject.appuser.service;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.forms.BlogForm;
import com.bloggerproject.restbloggerproject.appuser.model.AppUser;

import com.bloggerproject.restbloggerproject.appuser.model.AppUserRole;
import com.bloggerproject.restbloggerproject.appuser.model.Blog;
import com.bloggerproject.restbloggerproject.appuser.model.Template;
import com.bloggerproject.restbloggerproject.appuser.utils.UserValidation;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class MyUserService implements UserDetailsService {

    @PersistenceContext
    private final EntityManager em;

    PasswordEncoder passwordEncoder;
    UserValidation userValidation;

    public MyUserService(EntityManager em, PasswordEncoder passwordEncoder, UserValidation userValidation) {
        this.em = em;
        this.passwordEncoder = passwordEncoder;
        this.userValidation = userValidation;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return em.createQuery("SELECT user FROM AppUser user WHERE user.username = :name", AppUser.class)
                .setParameter("name", username)
                .getSingleResult();
    }

    @Transactional
    protected boolean isUsernameTaken(String username) {
        try {
            loadUserByUsername(username);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public AppUser getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof AppUser) {
                return (AppUser) principal;
            }
        }

        return null;
    }

    @Transactional
    public List<AppUserForm> getAppUsers () {
        List<AppUserForm> usersBack = new ArrayList<>();
        List <AppUser> appUserDB =
        em.createQuery("SELECT user FROM AppUser user", AppUser.class)
                .getResultList();

        for (AppUser appUser: appUserDB) {
            AppUserForm userBack = new AppUserForm(appUser);
            usersBack.add(userBack);
        }
        return usersBack;
    }

    public AppUser getAppUser (Long id) {
        return  em.createQuery("SELECT user FROM AppUser user WHERE user.id = :name", AppUser.class)
                .setParameter("name", id)
                .getSingleResult();
    }

    public AppUser signUpUser(AppUserForm appUser) {
        // todo: handle exceptions
        if(!isUsernameTaken(appUser.getUsernameForm())
                && userValidation.validEmail(appUser.getEmailForm())
                && userValidation.validPassword(appUser.getPasswordForm())
        ) {
            signUpUser(new AppUser(appUser, AppUserRole.USER));
            AppUser registered = (AppUser) loadUserByUsername(appUser.getUsernameForm());
            return registered;
        } else {
            System.out.println("error");
            return null;
        }

    }

    public void signUpUser(AppUser appUser) {

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
            em.persist(appUser);
    }

    public List<BlogForm> getAllBlogs() {

        List<Blog> allBlogsDB = em.createQuery("SELECT blog FROM Blog blog", Blog.class)
                .getResultList();

        List<BlogForm> blogsToBack = new ArrayList<>();

        for(Blog blog : allBlogsDB) {
            blogsToBack.add(new BlogForm(blog.getBlogTitle(), blog.getBlogAuthor().getId(), blog.getBlogTemplate().getTemplateId()));
        }
        return blogsToBack;
    }

    public void createBlog(BlogForm blog) {
        Template template = chooseTemplate(blog.getBlogTemplateId());
        AppUser appUser = getLoggedInUser();
        createBlog(new Blog(blog.getBlogTitleForm(), appUser, template));
    }

    // todo: create blogform
    public void createBlog(Blog blog) {
        em.persist(blog);
    }
    //uploaded by admin
    public void createTemplate(Template template) {
        em.persist(template);
    }

    public Template chooseTemplate(Long templateId) {
        return em.createQuery("SELECT template FROM Template template WHERE template.templateId = :name", Template.class)
                .setParameter("name", templateId)
                .getSingleResult();
    }
}
