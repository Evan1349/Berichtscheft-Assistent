package com.example.demo.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;


public class DateUtils {
	
	public static LocalDate getWochenStart(Integer year, Integer weekNumber) {
		if (year == null || weekNumber == null) return null;
		return LocalDate.of(year, 1, 4)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekNumber)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    public static LocalDate getWochenEnde(LocalDate startDate) {
        return startDate.plusDays(6); 
    }

}
