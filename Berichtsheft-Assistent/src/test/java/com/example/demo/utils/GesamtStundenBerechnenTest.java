package com.example.demo.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class GesamtStundenBerechnenTest {

	@Test
	void shouldSumAllHours() {
		List<BigDecimal> stundenListe = List.of(new BigDecimal("4.5"), new BigDecimal("2.5"));

		BigDecimal result = GesamtStundenBerechnen.berechneGesamtstunden(stundenListe);

		assertEquals(new BigDecimal("7.0"), result);
	}

	@Test
	void shouldIgnoreNullHours() {
		List<BigDecimal> stundenListe = new ArrayList<>();
		stundenListe.add(new BigDecimal("1.5"));
		stundenListe.add(null);
		stundenListe.add(new BigDecimal("2.5"));

		BigDecimal result = GesamtStundenBerechnen.berechneGesamtstunden(stundenListe);

		assertEquals(new BigDecimal("4.0"), result);
	}

	@Test
	void shouldReturnZeroWhenListIsNull() {
		BigDecimal result = GesamtStundenBerechnen.berechneGesamtstunden(null);

		assertEquals(BigDecimal.ZERO, result);
	}
}
