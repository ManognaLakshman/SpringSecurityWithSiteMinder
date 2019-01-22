package com.ndg.springdemo.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CurrentUserModel {
	public CurrentUserModel(String displayName,Collection<? extends GrantedAuthority> roles) {
		super();
		this.name = displayName;
		this.roles = roles;
	}
	private final String name;
	public String getName() {
		return name;
	}
	private final Collection<? extends GrantedAuthority> roles;
	public Collection<? extends GrantedAuthority> getRoles() {
		return roles;
	}
	
}
