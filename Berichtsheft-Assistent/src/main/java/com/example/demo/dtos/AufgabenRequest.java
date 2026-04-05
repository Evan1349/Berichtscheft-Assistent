package com.example.demo.dtos;

import java.math.BigDecimal;

import com.example.demo.enumeration.Tag;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AufgabenRequest {

	@NotNull
	private Tag tag;
	
	@NotBlank(message = "Der Inhalt darf nicht leer sein.")
	@Size(max=2000, message = "Der Inhalt ist zu lang (max. 2000 Zeichen).")
	private String aufgaben;
	
	@NotNull
	@DecimalMin(value = "0.1", message = "Mindestens 0,1 Stunden.")
    @DecimalMax(value = "10.0", message = "Maximal 10 Stunden pro Eintrag.")
	private BigDecimal stunden;
	
	@NotBlank(message = "Die Abteilung darf nicht leer sein.")
	@Size(max = 100, message = "Die Abteilung ist zu lang (max. 100 Zeichen).")
	private String abteilung;

}