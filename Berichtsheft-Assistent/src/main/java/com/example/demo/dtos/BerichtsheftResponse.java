package com.example.demo.dtos;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entities.Aufgaben;
import com.example.demo.enumeration.BerichtsheftStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BerichtsheftResponse {

	private Long id;
	private String vorname;
	private String nachname;
	private int nachweisNummer;
	private int ausbildungsjahr;
	private LocalDate wochenStart;
	private LocalDate wochenEnde;
	private BerichtsheftStatus status;
	private List<Aufgaben> aufgaben;
	
}
