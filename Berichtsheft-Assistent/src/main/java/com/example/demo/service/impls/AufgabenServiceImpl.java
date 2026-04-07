package com.example.demo.service.impls;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.entities.Aufgaben;
import com.example.demo.entities.Berichtsheft;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.AufgabenRepository;
import com.example.demo.repository.BerichtsheftRepository;
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
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(()-> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
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
		
		Berichtsheft berichtsheft = berichtsheftRepository.findByIdAndIsDeletedFalse(berichtsheftId)
				.orElseThrow(()-> new ResourceNotFoundException("Berichtsheft nicht gefunden."));
		
		return aufgabenRepository.findByBerichtsheftIdAndIsDeletedFalse(berichtsheft.getId())
				.stream()
				.map(this::toResponse)
				.toList();
	}

	@Transactional
	@Override
	public AufgabenResponse updateAufgaben(AufgabenRequest request, Long aufgabenId) {
		
		Aufgaben updatedaufgaben = aufgabenRepository.findByIdAndIsDeletedFalse(aufgabenId)
				.orElseThrow(()-> new ResourceNotFoundException("Aufgaben nicht gefunden."));
		
		updatedaufgaben.setTag(request.getTag());
		updatedaufgaben.setAufgaben(request.getAufgaben().trim());
		updatedaufgaben.setStunden(request.getStunden());
		updatedaufgaben.setAbteilung(request.getAbteilung().trim());
		
		return toResponse(aufgabenRepository.save(updatedaufgaben));
	}
	
	@Transactional
	@Override
	public void deleteAufgaben(Long aufgabenId) {
		
		Aufgaben aufgaben = aufgabenRepository.findByIdAndIsDeletedFalse(aufgabenId)
				.orElseThrow(()-> new ResourceNotFoundException("Aufgaben nicht gefunden."));
		
		aufgaben.setDeleted(true);

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
