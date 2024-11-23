package com.elitech.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.elitech.model.Role;
import com.elitech.model.User;

public class UserInfoDetails implements UserDetails {
	String userName=null;
	String password=null;
	List<GrantedAuthority> authorities;
	

	public UserInfoDetails(User user) {
		super();
		this.userName = user.getEmail();
		this.password = user.getPassword();
		
		List<Role> roles=user.getRoles();
		roles.forEach(r->{
		authorities.add(new SimpleGrantedAuthority(r.getRoleName()));	
		});
		
				
				;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

}
