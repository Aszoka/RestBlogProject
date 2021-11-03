package com.bloggerproject.restbloggerproject.appuser.model;

import com.google.common.collect.Sets;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.bloggerproject.restbloggerproject.appuser.model.AppUserPermission.*;
import static com.bloggerproject.restbloggerproject.appuser.model.AppUserPermission.WRITE;

@Getter
public enum AppUserRole {
    ADMIN(Sets.newHashSet(READ_ALL, WRITE, CREATE,DELETE_ALL, MODIFY_ALL)),
    MODERATOR(Sets.newHashSet(READ_ALL, WRITE, CREATE,DELETE_SELF, MODIFY_ALL)),
    USER(Sets.newHashSet(READ_BLOG, READ_SELF, WRITE,CREATE,DELETE_SELF, MODIFY_SELF)),
    UNDEFINED(Sets.newHashSet(READ_BLOG));

    private final Set<AppUserPermission> permissions;

    AppUserRole(Set<AppUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
        Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}
