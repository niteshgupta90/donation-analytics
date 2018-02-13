package com.insight.analytics;

//Stores the different amounts corresponding to a calendar year
import java.util.HashMap;
import java.util.Map;

public class CalendarYear {
	Map<Integer, Record> yearMap = null;

	public CalendarYear() {
		yearMap = new HashMap<>();
	}

	public Record getValue(Entry entry) {
		return yearMap.getOrDefault(entry.year, new Record(entry.cmteID, entry.zipCode, entry.year, 0, 0.0, 0));
	}

	public void setValue(Integer year, Record record) {
		yearMap.put(year, record);
	}
}
