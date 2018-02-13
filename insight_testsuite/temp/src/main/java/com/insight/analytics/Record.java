package com.insight.analytics;

import java.util.LinkedList;
import java.util.List;

// It stores the fields of a record which appears in output file
public class Record {
	private String cmteID = "";
	private String zipCode = "";
	private int year = 0;
	private long percentile = 0;
	private double sumContribution = 0; //total contribution form repeat donors from one area
	private long numTransaction = 0; //total number of transactions
	private List<Double> donationAmount = null; //list to store the donation amount from each repeat donor

	// constructor to initialize declared variables
	public Record(String cmteID, String zipCode, int year, long percentile, double sumContribution,
			long numTransaction) {
		this.cmteID = cmteID;
		this.zipCode = zipCode;
		this.year = year;
		this.percentile = percentile;
		this.sumContribution = sumContribution;
		this.numTransaction = numTransaction;
		donationAmount = new LinkedList<Double>();

	}

	// getters and setters
	public String getID() {
		return cmteID;
	}

	public void setID(String cmteID) {
		this.cmteID = cmteID;
	}

	public String getZIP() {
		return zipCode;
	}

	public void setZIP(String zipCode) {
		this.zipCode = zipCode;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public long getPercentile() {
		return percentile;
	}

	public void setPercentile(long percentile) {
		this.percentile = percentile;
	}

	public double getSumContribution() {
		return sumContribution;
	}

	public void setSumContribution(double sumContribution) {
		this.sumContribution = sumContribution;
	}

	public long getNumTransaction() {
		return numTransaction;
	}

	public void setNumTransaction(long numTransaction) {
		this.numTransaction = numTransaction;
	}
	
	public List<Double> getDonationAmount() {
		return donationAmount;
	}

	public void addDonationAmount(double amount) {
		donationAmount.add(amount);
	}	
}
