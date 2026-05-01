package com.example.demo.service;

import java.util.List;

import com.example.demo.entities.Arbeitsablaeufe;
import com.example.demo.entities.Benutzer;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.enumeration.BerichtsheftStatus;

public interface ArbeitsablaeufeService {
	
	// CRUD
	public Arbeitsablaeufe createArbeitsablaeufe(Berichtsheft berichtsheft, Benutzer akteur, BerichtsheftStatus status, String comment);
	
	public List<Arbeitsablaeufe> getArbeitsablaeufe(Long berichtsheftId);
	
	public Arbeitsablaeufe updateArbeitsablaeufe();
	
	public void deleteArbeitsablaeufe();
}
