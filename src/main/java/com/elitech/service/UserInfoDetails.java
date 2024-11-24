package com.elitech.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.elitech.model.User;
public class UserInfoDetails implements UserDetails {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String userName;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public UserInfoDetails(User user) {
        this.userName = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }
}

