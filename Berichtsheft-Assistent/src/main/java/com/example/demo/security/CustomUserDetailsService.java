package com.example.demo.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Benutzer;
import com.example.demo.repository.BenutzerRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	private final BenutzerRepository benutzerRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Benutzer benutzer = benutzerRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden."));

        return new CustomUserDetails(benutzer);
	}

}
