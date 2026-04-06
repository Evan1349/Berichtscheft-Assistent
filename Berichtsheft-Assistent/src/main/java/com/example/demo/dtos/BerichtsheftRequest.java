package com.example.demo.dtos;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BerichtsheftRequest {

	@NotNull(message = "Der NachweisNummer darf nicht null sein.")
	@Positive(message = "Die Nachweisnummer muss groesser als 0 sein.")
	private Integer nachweisNummer;

	@NotNull(message = "Der Ausbildungsjahr darf nicht null sein.")
	@Range(min = 1, max = 4)
	private Integer ausbildungsjahr;
	
	@NotNull
    @Min(2024)
    private Integer jahr;
	
	@NotNull
    @Range(min = 1, max = 53)
    private Integer kw;
}
