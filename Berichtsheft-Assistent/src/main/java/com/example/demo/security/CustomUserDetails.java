package com.example.demo.security;

import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entities.Benutzer;
import com.example.demo.enumeration.BenutzerRolle;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	private final Long id;
	private final String email;
	private final String password;
	private final BenutzerRolle role;
	   
	    public CustomUserDetails(Benutzer benutzer) {
	        this.id = benutzer.getId();
	        this.email = benutzer.getEmail();
	        this.password = benutzer.getPassword();
	        this.role = benutzer.getRolle();
	    }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}

	@Override
	public @Nullable String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
