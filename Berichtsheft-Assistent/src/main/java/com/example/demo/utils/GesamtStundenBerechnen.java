package com.example.demo.utils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class GesamtStundenBerechnen {

	public static BigDecimal berechneGesamtstunden(List<BigDecimal> stundenListe) {
		if (stundenListe == null) {
			return BigDecimal.ZERO;
		}

		return stundenListe.stream()
				.filter(Objects::nonNull)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}
}
