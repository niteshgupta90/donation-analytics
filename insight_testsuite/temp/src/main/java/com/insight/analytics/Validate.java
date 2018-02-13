package com.insight.analytics;

// Validates the input data
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validate {
	static String dateFormat = "MMddyyyy"; //desired date format

	// check validity of the each record for the desired fields
	public static boolean checkValidity(String s, int i) {
		boolean isValid = false;
		int len = s.length();
		switch (i) {
		case RecordFields.CMTE_ID:
			isValid = len >= RecordFields.CMTE_ID_MIN && len <= RecordFields.CMTE_ID_MAX;
			break;
		case RecordFields.NAME:
			isValid = len >= RecordFields.NAME_MIN && len <= RecordFields.NAME_MAX;
			break;
		case RecordFields.ZIP_CODE:
			isValid = len >= RecordFields.ZIP_CODE_MIN && len <= RecordFields.ZIP_CODE_MAX;
			break;
		case RecordFields.TRANSACTION_DT:
			if (len >= RecordFields.TRANSACTION_DT_MIN && len <= RecordFields.TRANSACTION_DT_MAX) {
				isValid = isValidDate(s, dateFormat);
			}
			break;
		case RecordFields.TRANSACTION_AMT:
			isValid = len >= RecordFields.TRANSACTION_AMT_MIN && len <= RecordFields.TRANSACTION_AMT_MAX;
			break;
		case RecordFields.OTHER_ID:
			isValid = len >= RecordFields.OTHER_ID_MIN && len <= RecordFields.OTHER_ID_MAX;
			break;
		default:
			break;
		}
		return isValid;
	}

	// validate the date
	private static boolean isValidDate(String inputDate, String format) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		try {
			Date date = sdf.parse(inputDate);
		} catch (ParseException e) {
			return false;
		}
		return true;
	}
}
