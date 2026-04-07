package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.BerichtsheftRequest;
import com.example.demo.dtos.BerichtsheftResponse;
import com.example.demo.service.BerichtsheftService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Berichtsheft Controller", description = "API feur Berichtsheft")
public class BerichtsheftController {
	
	private final BerichtsheftService berichtsheftService;
	
	@Operation(summary = "Create Berichtsheft", description = "Creating a berichtsheft by benutzer id and object.")
	@PreAuthorize("hasRole('AZUBI')")
	@PostMapping
	public ResponseEntity<BerichtsheftResponse> create(@Valid @RequestBody BerichtsheftRequest request){
		BerichtsheftResponse response = berichtsheftService.createBerichtsheft(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary = "Get Berichtsheft", description = "Finding all berichtsheft by benutzer id.")
	@GetMapping("/benutzer/{benutzerId}/berichtsheft")
	public ResponseEntity<List<BerichtsheftResponse>> read(@PathVariable Long benutzerId){
		List<BerichtsheftResponse> berichtsheft = berichtsheftService.getBerichtshefte(benutzerId);
		return ResponseEntity.status(HttpStatus.OK).body(berichtsheft);
	}

	@Operation(summary = "update", description = "Updating a berichtsheft by its id and object.")
	@PutMapping("/berichtsheft/{berichtsheftId}")
	public ResponseEntity<BerichtsheftResponse> update(@PathVariable Long berichtsheftId, @Valid @RequestBody BerichtsheftRequest request){
		BerichtsheftResponse result = berichtsheftService.updateBerichtsheft(berichtsheftId, request);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "delete", description = "Deleting a berichtsheft by its id.")
	@DeleteMapping("/berichtsheft/{berichtsheftId}")
	public ResponseEntity<Void> delete(@PathVariable Long berichtsheftId){
		berichtsheftService.deleteBerichtsheft(berichtsheftId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
	
    @Operation(summary = "Submit Berichtsheft", description = "Submit a Berichtsheft for review.")
    @PreAuthorize("hasRole('AZUBI')")
    @PatchMapping("/berichtsheft/{berichtsheftId}/einreichen")
    public ResponseEntity<Void> submit(@PathVariable Long berichtsheftId) {
        berichtsheftService.einreichen(berichtsheftId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
