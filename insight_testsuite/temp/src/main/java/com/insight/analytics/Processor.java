package com.insight.analytics;

// It processes input file record by record, computes the percentile, stores the values in the database and writes to output file. 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Processor {
	private Set<Integer> indexDictionary = null; // stores required field values
	private Set<String> uniqueID = null; // unique id associated with each donar
	private Recipient receiver = null; // Stores the values corresponding to
										// each recipient
	private String output = "output/repeat_donors.txt";

	// Constructor to initialize values of the declared variables
	public Processor(String file1, String file2) throws IOException {
		indexDictionary = new HashSet<>(Arrays.asList(RecordFields.CMTE_ID, RecordFields.NAME, RecordFields.ZIP_CODE,
				RecordFields.TRANSACTION_DT, RecordFields.TRANSACTION_AMT, RecordFields.OTHER_ID));
		uniqueID = new HashSet<>();
		receiver = new Recipient();
		readContents(file1, file2);
	}

	@SuppressWarnings("resource") // read the contents of both the files,
									// process values and write on output file
	private void readContents(String file1, String file2) throws IOException {
		BufferedReader contentReader = new BufferedReader(new FileReader(file1));
		BufferedReader percentileReader = new BufferedReader(new FileReader(file2));

		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);

		String line = "";
		double percentileVal = Double.parseDouble(percentileReader.readLine());

		while ((line = contentReader.readLine()) != null) {
			boolean isValid = false;
			String[] token = line.split("\\|");
			for (int i = 0; i < token.length; i++) {
				if (indexDictionary.contains(i)) {
					isValid = Validate.checkValidity(token[i], i);
				}
			}
			if (isValid == false)
				continue;
			Entry newEntry = processRequiredData(token);

			if (uniqueID.contains(newEntry.uid)) {
				String output = addToDatabase(newEntry, percentileVal);
				bw.write(output);
				bw.newLine();
			} else {
				uniqueID.add(newEntry.uid);
			}

		}
		bw.close();
		fw.close();
	}

	// process records and adds various output values to internal database
	private String addToDatabase(Entry entry, double percentileValue) {
		Donar donar = receiver.getValue(entry.cmteID);
		CalendarYear donationYear = donar.getValue(entry.zipCode);
		Record newRecord = donationYear.getValue(entry);
		newRecord.numTransaction++;
		newRecord.donationAmount.add(entry.amount);
		newRecord.sumContribution += entry.amount;
		newRecord.percentile = getPercentile(entry.amount, newRecord.numTransaction, newRecord.donationAmount,
				percentileValue);
		donationYear.setValue(entry.year, newRecord);
		donar.setValue(entry.zipCode, donationYear);
		receiver.setValue(entry.cmteID, donar);
		String output = getString(newRecord);
		return output;
	}

	// get the unique donar id for each record
	private String getID(String s1, String s2) {
		StringBuilder sb = new StringBuilder(s1);
		sb.append(s2);
		return sb.toString();
	}

	// process each record and store values to an object of Entry type.
	private Entry processRequiredData(String[] token) {
		String cmteID = token[RecordFields.CMTE_ID];
		String zipCode = token[RecordFields.ZIP_CODE].substring(0, 5);
		int year = Integer.parseInt(token[RecordFields.TRANSACTION_DT].substring(4, 8));
		double amount = Double.parseDouble(token[RecordFields.TRANSACTION_AMT]);
		double percentile = 0.0;
		String uid = getID(token[RecordFields.NAME], token[RecordFields.ZIP_CODE]);
		return new Entry(cmteID, zipCode, uid, year, percentile, amount);
	}

	// get the output string
	private String getString(Record record) {
		StringBuilder sb = new StringBuilder();
		sb.append(record.cmteID).append("|").append(record.zipCode).append("|").append(Integer.toString(record.year))
				.append("|").append(Long.toString(record.percentile)).append("|")
				.append(Long.toString((long) record.sumContribution)).append("|")
				.append(Long.toString(record.numTransaction));
		return sb.toString();
	}

	// calculates the percentile
	private long getPercentile(double amount, long numTransaction, List<Double> donationAmount, double percentile) {
		Collections.sort(donationAmount);
		double val = (percentile / 100.0) * numTransaction;
		int desiredIndex = (int) Math.ceil(val);
		return (long) Math.ceil(donationAmount.get(desiredIndex - 1));
	}

}
