package com.example.cosinuslibrarysystem.enums;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

public enum UserRole {
    MODERATOR,
    ADMIN,

    USER;

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority>authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}