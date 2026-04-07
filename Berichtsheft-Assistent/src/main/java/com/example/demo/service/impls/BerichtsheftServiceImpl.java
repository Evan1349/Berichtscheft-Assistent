package com.example.demo.service.impls;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;
import com.example.demo.entities.Benutzer;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.enumeration.BerichtsheftStatus;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ConflictException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.BenutzerRepository;
import com.example.demo.repository.BerichtsheftRepository;
import com.example.demo.security.BerichtsheftPermissionService;
import com.example.demo.security.CurrentUserService;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.service.BerichtsheftService;
import com.example.demo.utils.DateUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BerichtsheftServiceImpl implements BerichtsheftService {

	private final BerichtsheftRepository berichtsheftRepository;
	private final BenutzerRepository benutzerRepository;
	private final CurrentUserService currentUserService;
	private final BerichtsheftPermissionService permissionService;

	
	@Transactional
	@Override
	public BerichtsheftResponse createBerichtsheft(BerichtsheftRequest request) {
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		permissionService.checkCreator(currentUser);

		Benutzer benutzer = benutzerRepository.findByIdAndIsDeletedFalse(currentUser.getId())
		        .orElseThrow(() -> new ResourceNotFoundException("Benutzer nicht gefunden."));
		
		if (benutzer.getAusbilder() == null) {
		    throw new IllegalStateException("Benutzer hat keinen Ausbilder zugewiesen."); 
		}
		
		boolean exists = berichtsheftRepository.existsByAzubiIdAndJahrAndKw(
		        benutzer.getId(), request.getJahr(), request.getKw());
		if (exists) {
		    throw new ConflictException("Ein Berichtsheft für diese Kalenderwoche existiert bereits.");
		}
		
		Berichtsheft berichtsheft = Berichtsheft.builder()
				.nachweisNummer(request.getNachweisNummer())
				.ausbildungsjahr(request.getAusbildungsjahr())
				.wochenStart(DateUtils.getWochenStart(request.getJahr(), request.getKw()))
				.wochenEnde(DateUtils.getWochenEnde(request.getJahr(), request.getKw()))
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
		
		return berichtsheftRepository.findAllByAzubiIdAndIsDeletedFalse(benutzer.getId()).stream()
				.map(this::toResponse)
				.toList();
	}

	
	@Transactional
	@Override
	public BerichtsheftResponse updateBerichtsheft(Long berichtsheftId, BerichtsheftRequest request) {
		
		Berichtsheft updatecberichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(() -> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
		updatecberichtsheft.setNachweisNummer(request.getNachweisNummer());
		updatecberichtsheft.setAusbildungsjahr(request.getAusbildungsjahr());
		updatecberichtsheft.setWochenStart(DateUtils.getWochenStart(request.getJahr(), request.getKw()));
		updatecberichtsheft.setWochenEnde(DateUtils.getWochenEnde(request.getJahr(), request.getKw()));
		
		return toResponse(berichtsheftRepository.save(updatecberichtsheft));
	}

	
	@Transactional
	@Override
	public void deleteBerichtsheft(Long berichtsheftId) {
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(() -> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
	
		berichtsheft.setDeleted(true);
	}

	@Transactional
	@Override
	public void einreichen(Long berichtsheftId) {
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(() -> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		permissionService.checkOwner(berichtsheft, currentUser);
		
        if (!berichtsheft.getStatus().canBeSubmitted()) {
            throw new BadRequestException("Dieser Statuswechsel ist nicht erlaubt.");
        }

        berichtsheft.setStatus(BerichtsheftStatus.EINGEREICHT);
        berichtsheftRepository.save(berichtsheft);

	}

	@Override
	public void genehmigen(Long berichtsheftId) {
		// TODO Auto-generated method stub
	}

	@Override
	public void aenderungErforderlich(Long berichtsheftId) {
		// TODO Auto-generated method stub
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
