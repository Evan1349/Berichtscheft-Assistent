package com.example.demo.security;

import org.springframework.stereotype.Component;

import com.example.demo.entities.Berichtsheft;
import com.example.demo.enumeration.BenutzerRolle;
import com.example.demo.exception.AccessDeniedException;

@Component
public class BerichtsheftPermissionService {
	
    public void checkOwner(Berichtsheft berichtsheft, CustomUserDetails currentUser) {
        if (!berichtsheft.getAzubi().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Keine Berechtigung für diese Aktion.");
        }
    }
    
    public void checkCreator(CustomUserDetails currentUser) {
        if (currentUser.getRole()!= BenutzerRolle.AZUBI) {
            throw new AccessDeniedException("Keine Berechtigung für diese Aktion.");
        }
    }

}
