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
public class Comment {
    @SequenceGenerator(
            name = "comment_sequence",
            sequenceName = "comment_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "comment_sequence"
    )
    private Long commentId;
    @ManyToOne
    private AppUser postAuthor;
    @ManyToOne
    private Post commentedPost;

    @Column(columnDefinition = "LONGTEXT")
    private String commentText;
    @CreationTimestamp
    private Timestamp commentTime;

    @ManyToOne
    private Comment replyToComment;

    @OneToMany(mappedBy = "replyToComment")
    private List<Comment> replies;

    public Comment() {
        replies = new LinkedList<>();
    }

    // comment to post
    public Comment(AppUser postAuthor, Post commentedPost, String commentText) {
        this.postAuthor = postAuthor;
        this.commentedPost = commentedPost;
        this.commentText = commentText;
    }

    // reply to comment
    public Comment(AppUser postAuthor, Post commentedPost, String commentText, Comment replyToComment) {
        this.postAuthor = postAuthor;
        this.commentedPost = commentedPost;
        this.commentText = commentText;
        this.replyToComment = replyToComment;
    }
}

