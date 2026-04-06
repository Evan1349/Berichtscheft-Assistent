package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.service.AufgabenService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@Tag(name = "Aufgaben Controller", description = "API feur Aufgaben")
public class AufgabenController {
	
	private final AufgabenService aufgabenService;
	
	@Operation(summary = "Create Aufgaben", description = "Creating a aufgaben by berichtsheft id and object.")
	@PostMapping("/berichtsheft/{berichtsheftId}/aufgaben")
	public ResponseEntity<AufgabenResponse> create(@PathVariable Long berichtsheftId, @Valid @RequestBody AufgabenRequest request){
		AufgabenResponse response = aufgabenService.createAufgaben(request, berichtsheftId);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@Operation(summary = "Get Aufgaben", description = "Finding all aufgaben by berichtsheft id.")
	@GetMapping("/berichtsheft/{berichtsheftId}/aufgaben")
	public ResponseEntity<List<AufgabenResponse>> read(@PathVariable Long berichtsheftId){
		List<AufgabenResponse> aufgaben = aufgabenService.getTageAufgaben(berichtsheftId);
		return ResponseEntity.status(HttpStatus.OK).body(aufgaben);
	}

	@Operation(summary = "update", description = "Updating a aufgaben by aufgaben id and object.")
	@PutMapping("/aufgaben/{aufgabenId}")
	public ResponseEntity<AufgabenResponse> update(@PathVariable Long aufgabenId, @Valid @RequestBody AufgabenRequest request){
		AufgabenResponse result = aufgabenService.updateAufgaben(request, aufgabenId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@Operation(summary = "delete", description = "Deleting a aufgaben by aufgaben id.")
	@DeleteMapping("/aufgaben/{aufgabenId}")
	public ResponseEntity<Void> delete(@PathVariable Long aufgabenId){
		aufgabenService.deleteAufgaben(aufgabenId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		
	}
}
