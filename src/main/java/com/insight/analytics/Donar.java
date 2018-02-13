package com.insight.analytics;

//Stores the calendar years corresponding to a ZIP Code of contributor
import java.util.HashMap;
import java.util.Map;

public class Donar {
	Map<String, CalendarYear> donarMap = null;

	public Donar() {
		donarMap = new HashMap<>();
	}

	public CalendarYear getValue(String zipCode) {
		return donarMap.getOrDefault(zipCode, new CalendarYear());
	}

	public void setValue(String zipCode, CalendarYear calendarYear) {
		donarMap.put(zipCode, calendarYear);
	}

}
