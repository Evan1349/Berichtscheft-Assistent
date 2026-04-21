package com.example.demo.service.impls;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.entities.Aufgaben;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.BerichtsheftRepository;
import com.example.demo.security.CurrentUserService;
import com.example.demo.security.CustomUserDetails;
import com.example.demo.security.PermissionService;
import com.example.demo.service.AufgabenService;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;



/**
 * Diese Service-Klasse verarbeitet die Anwendungslogik fuer Aufgaben.
 * Sie erstellt neue Eintraege und wandelt Entitaeten in Response-DTOs um.
 */

@Service
@RequiredArgsConstructor
public class AufgabenServiceImpl implements AufgabenService{

	private final AufgabenRepository aufgabenRepository;
	private final BerichtsheftRepository berichtsheftRepository;
	private final CurrentUserService currentUserService;
	private final PermissionService permissionService;

	/**
	 * Erstellt einen neuen Aufgaben-Eintrag aus der validierten Anfrage.
	 * Vor dem Speichern werden Textfelder mit trim() bereinigt.
	 * Anschliessend wird der gespeicherte Datensatz als Response-DTO zurueckgegeben.
	 *
	 * @param request die vom Benutzer gesendeten Aufgabendaten
	 * @return der gespeicherte Aufgaben-Eintrag als Response-DTO
	 */
	@Transactional
	@Override
	public AufgabenResponse createAufgaben(AufgabenRequest request, Long berichtsheftId) {
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(()-> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
        if (!berichtsheft.getStatus().canBeSubmitted()) {
            throw new BadRequestException("Dieser Statuswechsel ist nicht erlaubt.");
        }
		
		if (berichtsheft == null || berichtsheft.isDeleted()) {
	        throw new ResourceNotFoundException("Berichtsheft nicht gefunden.");
	    }	
		
		permissionService.checkOwner(berichtsheft, currentUser);
		
		Aufgaben aufgaben = Aufgaben.builder()
				.berichtsheft(berichtsheft)
				.tag(request.getTag())
				.aufgaben(request.getAufgaben().trim())
				.stunden(request.getStunden())
				.abteilung(request.getAbteilung().trim())
				.build();

		return toResponse(aufgabenRepository.save(aufgaben));
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<AufgabenResponse> getTageAufgaben(Long berichtsheftId) {
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(()-> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
		permissionService.checkReader(berichtsheft, currentUser);
		
		return aufgabenRepository.findByBerichtsheftIdAndIsDeletedFalse(berichtsheft.getId())
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	@Override
	public AufgabenResponse updateAufgaben(AufgabenRequest request, Long aufgabenId) {
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		Aufgaben updatedaufgaben = aufgabenRepository.findByIdAndIsDeletedFalse(aufgabenId)
				.orElseThrow(()-> new ResourceNotFoundException("Aufgaben nicht gefunden."));
		
		Berichtsheft berichtsheft = updatedaufgaben.getBerichtsheft();
		
        if (!berichtsheft.getStatus().canBeSubmitted()) {
            throw new BadRequestException("Dieser Statuswechsel ist nicht erlaubt.");
        }
		
		if (berichtsheft == null || berichtsheft.isDeleted()) {
	        throw new ResourceNotFoundException("Berichtsheft nicht gefunden.");
	    }		
		
		permissionService.checkOwner(berichtsheft, currentUser);
		
		updatedaufgaben.setTag(request.getTag());
		updatedaufgaben.setAufgaben(request.getAufgaben().trim());
		updatedaufgaben.setStunden(request.getStunden());
		updatedaufgaben.setAbteilung(request.getAbteilung().trim());
		
		return toResponse(aufgabenRepository.save(updatedaufgaben));
	}
	
	@Transactional
	@Override
	public void deleteAufgaben(Long aufgabenId) {
		
		CustomUserDetails currentUser = currentUserService.getCurrentUser();
		
		Aufgaben deletedaufgaben = aufgabenRepository.findByIdAndIsDeletedFalse(aufgabenId)
				.orElseThrow(()-> new ResourceNotFoundException("Aufgaben nicht gefunden."));
		
		Berichtsheft berichtsheft = deletedaufgaben.getBerichtsheft();
		
        if (!berichtsheft.getStatus().canBeSubmitted()) {
            throw new BadRequestException("Dieser Statuswechsel ist nicht erlaubt.");
        }
        
		if (berichtsheft == null || berichtsheft.isDeleted()) {
	        throw new ResourceNotFoundException("Berichtsheft nicht gefunden.");
	    }	
		
		permissionService.checkOwner(berichtsheft, currentUser);
		
		deletedaufgaben.setDeleted(true);

	}
		
	/**
	 * Wandelt eine Aufgaben-Entitaet in ein Response-DTO fuer die API um.
	 *
	 * @param aufgaben die Aufgaben-Entitaet aus der Datenbank
	 * @return die API-Antwort mit den Aufgaben-Daten
	 */		
			
	private AufgabenResponse toResponse(Aufgaben aufgaben) {
		return AufgabenResponse.builder()
				.id(aufgaben.getId())
				.tag(aufgaben.getTag())
				.aufgaben(aufgaben.getAufgaben())
				.stunden(aufgaben.getStunden())
				.abteilung(aufgaben.getAbteilung())
				.build();
		
	}

}
