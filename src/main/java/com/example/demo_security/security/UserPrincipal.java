package com.example.demo_security.security;

import com.example.demo_security.entity.Authority;
import com.example.demo_security.entity.Role;
import com.example.demo_security.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:33
 * @project demo_security
 */


public class UserPrincipal implements UserDetails {
    private final User user;

    public UserPrincipal(User user) {
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();

        Collection<Role> roles = user.getRoles();
        Collection<Authority> authorityCollection = new HashSet<>();

        if (roles == null) return  authorities;

        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityCollection.addAll(role.getAuthorities());
        });

        authorityCollection.forEach(authority -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
