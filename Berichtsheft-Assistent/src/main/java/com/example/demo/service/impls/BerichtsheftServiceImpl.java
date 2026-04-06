package com.example.demo.service.impls;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;
import com.example.demo.entities.Benutzer;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.BerichtsheftRepository;
import com.example.demo.service.BerichtsheftService;
import com.example.demo.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BerichtsheftServiceImpl implements BerichtsheftService {

	private final BerichtsheftRepository berichtsheftRepository;
	private final BenutzerRepository benutzerRepository;

	@Transactional
	@Override
	public BerichtsheftResponse createBerichtsheft(Long benutzerId, BerichtsheftRequest request) {

		Benutzer benutzer = benutzerRepository.findById(benutzerId)
				.orElseThrow(() -> new ResourceNotFoundException("Benutzer nicht gefunden."));
		
		LocalDate start = DateUtils.getWochenStart(request.getJahr(), request.getKw());
	    LocalDate end = start.plusDays(6);
		
		Berichtsheft berichtsheft = Berichtsheft.builder()
				.nachweisNummer(request.getNachweisNummer())
				.ausbildungsjahr(request.getAusbildungsjahr())
				.wochenStart(start)
				.wochenEnde(end)
				.azubi(benutzer)
				.pruefer(benutzer.getAusbilder())
				.build();

		return toResponse(berichtsheftRepository.save(berichtsheft), benutzer);
	}

	@Override
	public List<BerichtsheftResponse> getBerichtshefte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BerichtsheftResponse updateBerichtsheft(Long berichtsheftId, BerichtsheftRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBerichtsheft(Long berichtsheftId) {
		// TODO Auto-generated method stub
		
	}


	private BerichtsheftResponse toResponse(Berichtsheft berichtsheft, Benutzer benutzer) {
		return BerichtsheftResponse.builder()
				.id(berichtsheft.getId())
				.vorname(benutzer.getVorname())
				.nachname(benutzer.getNachname())
				.nachweisNummer(berichtsheft.getNachweisNummer())
				.ausbildungsjahr(berichtsheft.getAusbildungsjahr())
				.wochenStart(berichtsheft.getWochenStart())
				.wochenEnde(berichtsheft.getWochenEnde())
				.status(berichtsheft.getStatus())
				.aufgaben(berichtsheft.getAufgaben())
				.build();
		
	}


}
