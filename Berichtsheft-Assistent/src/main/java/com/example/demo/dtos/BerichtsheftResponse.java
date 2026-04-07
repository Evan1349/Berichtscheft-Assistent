package com.example.demo.dtos;

import java.time.LocalDate;
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
	private int nachweisNummer;
	private int ausbildungsjahr;
	private LocalDate wochenStart;
	private LocalDate wochenEnde;
	private BerichtsheftStatus status;
	
}
