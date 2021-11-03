package com.bloggerproject.restbloggerproject.appuser.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Post {
    @SequenceGenerator(
            name = "post_sequence",
            sequenceName = "post_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "post_sequence"
    )
    private Long postId;
    @ManyToOne
    private AppUser postAuthor;
    @ManyToOne
    private Blog blog;
    @Column(columnDefinition = "VARCHAR(80) default 'no title'")
    private String postTitle;
    @Column(columnDefinition = "LONGTEXT")
    private String text;
    @Column(columnDefinition = "BOOL default true")
    private boolean published;
    @CreationTimestamp
    private Timestamp publishingTime;

    @OneToMany(mappedBy = "commentedPost")
    private List<Comment> comments;

    public Post() {
        comments = new LinkedList<>();
    }

    public Post(AppUser postAuthor, Blog blog, String postTitle, String text, boolean published) {
        this.postAuthor = postAuthor;
        this.blog = blog;
        this.postTitle = postTitle;
        this.text = text;
        this.published = published;
    }
}
