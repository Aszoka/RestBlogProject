package com.bloggerproject.restbloggerproject.appuser.forms;

import com.bloggerproject.restbloggerproject.appuser.model.AppUser;
import com.bloggerproject.restbloggerproject.appuser.model.Template;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BlogForm {

    private String blogTitleForm;
    private Long authorId;
    private Long blogTemplateId;

    public BlogForm() {
    }

    public BlogForm(String blogTitleForm, Long authorId, Long blogTemplateId) {
        this.blogTitleForm = blogTitleForm;
        this.authorId = authorId;
        this.blogTemplateId = blogTemplateId;
    }
}
