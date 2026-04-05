package com.example.demo.dtos;

import java.math.BigDecimal;

import com.example.demo.enumeration.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AufgabenResponse {
	
	private Long id;
	private Tag tag;
	private String aufgaben;
	private BigDecimal stunden;
	private BigDecimal gesamtstunden;
	private String abteilung;

}
