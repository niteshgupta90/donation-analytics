package com.insight.analytics;

//Processes input file record by record, computes the percentile, stores the REQUIRED values and writes to output file. 
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
	private Set<Integer> indexDictionary = null; // stores required fields' indexes
	private Set<String> uniqueID = null; // unique id associated with each Donor
	private Recipient receiver = null; // Object of the class which stores the values corresponding to each recipient
	private String output = "output/repeat_donors.txt"; //output file name

	// Constructor to initialize values of the declared variables. Calls method readContents().
	public Processor(String file1, String file2) throws IOException {
		indexDictionary = new HashSet<>(Arrays.asList(RecordFields.CMTE_ID, RecordFields.NAME, RecordFields.ZIP_CODE,
				RecordFields.TRANSACTION_DT, RecordFields.TRANSACTION_AMT, RecordFields.OTHER_ID));
		uniqueID = new HashSet<>();
		receiver = new Recipient();
		readContents(file1, file2);
	}

	// read the contents of both the input files, process values and write on output file
	@SuppressWarnings("resource") 
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
					if (isValid == false)
						break;
				}
			}
			if (isValid == false)
				continue;
			Entry newEntry = processRequiredData(token);
			//check if the donor is repeat donor, if yes then process
			if (uniqueID.contains(newEntry.uid)) {
				String output = addToDatabase(newEntry, percentileVal);
				bw.write(output); //writes output to the file
				bw.newLine();
			} else {
				uniqueID.add(newEntry.uid);
			}

		}
		bw.close();
		fw.close();
	}

	// process records and adds various output values to internal database (data structures)
	private String addToDatabase(Entry entry, double percentileValue) {
		Donar donar = receiver.getValue(entry.getCmteID()); //object of donor class corresponding to a receiver ID
		CalendarYear donationYear = donar.getValue(entry.getZipCode()); //calendar year of donation from a contributor 
																	//for a particular recipient
		Record newRecord = donationYear.getValue(entry);
		newRecord.setNumTransaction(newRecord.getNumTransaction()+1);
		newRecord.addDonationAmount(entry.getAmount());
		newRecord.setSumContribution(newRecord.getSumContribution() + entry.getAmount());
		
		//calculates percentile
		newRecord.setPercentile(getPercentile(entry.getAmount(), newRecord.getNumTransaction(), newRecord.getDonationAmount(),
				percentileValue));
		
		//store values in corresponding objects
		donationYear.setValue(entry.getYear(), newRecord);
		donar.setValue(entry.getZipCode(), donationYear);
		receiver.setValue(entry.getCmteID(), donar);
		
		//get output string
		String output = getString(newRecord);
		return output;
	}

	// get the unique donor id for each record
	private String getID(String name, String zipCode) {
		StringBuilder sb = new StringBuilder(name);
		sb.append(zipCode);
		return sb.toString();
	}

	// process each record and store values to an object of Entry type.
	private Entry processRequiredData(String[] token) {
		String cmteID = token[RecordFields.CMTE_ID];
		String zipCode = token[RecordFields.ZIP_CODE].substring(0, 5);
		int year = Integer.parseInt(token[RecordFields.TRANSACTION_DT].substring(4, 8));
		double amount = Double.parseDouble(token[RecordFields.TRANSACTION_AMT]);
		double percentile = 0.0;
		String uid = getID(token[RecordFields.NAME], zipCode);
		return new Entry(cmteID, zipCode, uid, year, percentile, amount);
	}

	// get the output string
	private String getString(Record record) {
		StringBuilder sb = new StringBuilder();
		sb.append(record.getID()).append("|").append(record.getZIP()).append("|").append(Integer.toString(record.getYear()))
				.append("|").append(Long.toString(record.getPercentile())).append("|")
				.append(Long.toString((long) record.getSumContribution())).append("|")
				.append(Long.toString(record.getNumTransaction()));
		return sb.toString();
	}

	// calculates the percentile
	private long getPercentile(double amount, long numTransaction, List<Double> donationAmount, double percentile) {
		Collections.sort(donationAmount); //orders the list (sorts in increasing order)
		double val = (percentile / 100.0) * numTransaction; //calculate rank
		int desiredIndex = (int) Math.ceil(val); 
		return (long) Math.ceil(donationAmount.get(desiredIndex - 1)); //return the value corresponding to the index 
																		//from the ordered list
	}

}
