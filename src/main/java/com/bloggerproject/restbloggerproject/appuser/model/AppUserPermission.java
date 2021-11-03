package com.bloggerproject.restbloggerproject.appuser.model;

import lombok.Getter;

@Getter
public enum AppUserPermission {
    READ_BLOG("read:blog"),
    READ_ALL("read:all"),
    READ_SELF("read:self"),
    CREATE("create"), // blog
    WRITE("write"), // comment, post
    MODIFY_SELF("modify:self"),
    MODIFY_ALL("modify:all"),
    DELETE_SELF("delete:self"),
    DELETE_ALL("delete:all");

    private final String permission;

    AppUserPermission(String permission) {
        this.permission = permission;
    }
}
