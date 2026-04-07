package com.example.demo.service.impls;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;
import com.example.demo.entities.Benutzer;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.enumeration.BenutzerRolle;
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

		Benutzer benutzer = benutzerRepository.findByIdAndRolleAndIsDeletedFalse(benutzerId, BenutzerRolle.AZUBI)
				.filter(b -> b.getAusbilder() != null)
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

		return toResponse(berichtsheftRepository.save(berichtsheft));
	}

	@Transactional(readOnly = true)
	@Override
	public List<BerichtsheftResponse> getBerichtshefte(Long benutzerId) {
		
		Benutzer benutzer = benutzerRepository.findByIdAndIsDeletedFalse(benutzerId)
				.orElseThrow(() -> new ResourceNotFoundException("Benutzer nicht gefunden."));
		
		return berichtsheftRepository.findAllByAzubiIdAndIsDeletedFalse(benutzer.getId())
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	@Override
	public BerichtsheftResponse updateBerichtsheft(Long berichtsheftId, BerichtsheftRequest request) {
		
		Berichtsheft updatecberichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(() -> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
		LocalDate start = DateUtils.getWochenStart(request.getJahr(), request.getKw());
	    LocalDate end = start.plusDays(6);
		
		updatecberichtsheft.setNachweisNummer(request.getNachweisNummer());
		updatecberichtsheft.setAusbildungsjahr(request.getAusbildungsjahr());
		updatecberichtsheft.setWochenStart(start);
		updatecberichtsheft.setWochenEnde(end);
		
		return toResponse(berichtsheftRepository.save(updatecberichtsheft));
	}

	@Transactional
	@Override
	public void deleteBerichtsheft(Long berichtsheftId) {
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(() -> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
	
		berichtsheft.setDeleted(true);
	}


	private BerichtsheftResponse toResponse(Berichtsheft berichtsheft) {
		return BerichtsheftResponse.builder()
				.id(berichtsheft.getId())
				.nachweisNummer(berichtsheft.getNachweisNummer())
				.ausbildungsjahr(berichtsheft.getAusbildungsjahr())
				.wochenStart(berichtsheft.getWochenStart())
				.wochenEnde(berichtsheft.getWochenEnde())
				.status(berichtsheft.getStatus())
				.build();
		
	}


}
