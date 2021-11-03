package com.bloggerproject.restbloggerproject.appuser.model;

import com.bloggerproject.restbloggerproject.appuser.forms.AppUserForm;
import com.bloggerproject.restbloggerproject.appuser.forms.BlogForm;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Blog {

    @SequenceGenerator(
            name = "blog_sequence",
            sequenceName = "blog_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "blog_sequence"
    )
    private Long blogId;
    @Column(columnDefinition = "varchar (150) default 'no title'")
    private String blogTitle; // not title as default

    @ManyToOne
    private AppUser blogAuthor;
    @ManyToOne
    private Template blogTemplate;
    @Transient
    //@OneToMany(mappedBy = "blog")
    private List<Post> postList;

    public Blog(String blogTitle, AppUser blogAuthor, Template blogTemplate) {
        this.blogTitle = blogTitle;
        this.blogAuthor = blogAuthor;
        this.blogTemplate = blogTemplate;
        postList = new LinkedList<>();
    }

    public Blog(String blogTitle, Template blogTemplate) {
        this.blogTitle = blogTitle;
        this.blogTemplate = blogTemplate;
        postList = new LinkedList<>();
    }
    public Blog(String blogTitle) {
        this.blogTitle = blogTitle;
        postList = new LinkedList<>();
    }

    public Blog(BlogForm blogForm, AppUser author, Template template) {
        this.blogTitle = blogForm.getBlogTitleForm();
        this.blogAuthor = author;
        this.blogTemplate = template;

        postList = new LinkedList<>();
    }

    public Blog(AppUser author, Template template) {

        this.blogAuthor = author;
        this.blogTemplate = template;

        postList = new LinkedList<>();
    }

    public Blog() {
        postList = new LinkedList<>();
    }
}
