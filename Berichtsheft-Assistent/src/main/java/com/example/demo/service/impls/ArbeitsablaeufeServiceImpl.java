package com.example.demo.service.impls;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entities.Arbeitsablaeufe;
import com.example.demo.entities.Benutzer;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.enumeration.BerichtsheftStatus;
import com.example.demo.repository.ArbeitsablaeufeRepository;
import com.example.demo.repository.BerichtsheftRepository;
import com.example.demo.service.ArbeitsablaeufeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArbeitsablaeufeServiceImpl implements ArbeitsablaeufeService{
	
	private final BerichtsheftRepository berichtsheftRepository;
	private final ArbeitsablaeufeRepository arbeitsablaeufeRepositry;

	@Override
	public Arbeitsablaeufe createArbeitsablaeufe(Berichtsheft berichtsheft, Benutzer akteur, BerichtsheftStatus status, String comment) {
		
		Arbeitsablaeufe arbeitsablaeufe = Arbeitsablaeufe
				.builder()
				.akteur(akteur)
				.berichtsheft(berichtsheft)
				.comment(comment)
				.status(status)
				.build();
		
		return arbeitsablaeufeRepositry.save(arbeitsablaeufe);
	}

	@Override
	public List<Arbeitsablaeufe> getArbeitsablaeufe(Long berichtsheftId) {
		
		return arbeitsablaeufeRepositry.findByBerichtsheftIdAndIsDeletedFalse(berichtsheftId);
	}

	@Override
	public Arbeitsablaeufe updateArbeitsablaeufe() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArbeitsablaeufe() {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
}
