package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.AufgabenRequest;
import com.example.demo.dtos.AufgabenResponse;
import com.example.demo.service.AufgabenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/aufgaben")
public class Aufgabencontroller {
	
	private final AufgabenService aufgabenService;
	
	@PostMapping
	public ResponseEntity<AufgabenResponse> create(@Valid @RequestBody AufgabenRequest request){
		AufgabenResponse response = aufgabenService.createAufgaben(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}


}
