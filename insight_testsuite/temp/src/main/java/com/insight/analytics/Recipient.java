package com.insight.analytics;

// Stores the repeat donors corresponding a recipient ID
import java.util.HashMap;
import java.util.Map;

public class Recipient {
	public static Map<String, Donar> recipientMap = null;

	public Recipient() {
		recipientMap = new HashMap<>();
	}

	public Donar getValue(String cmteID) {
		return recipientMap.getOrDefault(cmteID, new Donar());
	}

	public void setValue(String cmteID, Donar donar) {
		recipientMap.put(cmteID, donar);
	}
}
