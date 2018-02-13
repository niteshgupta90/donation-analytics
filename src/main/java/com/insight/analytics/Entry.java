package com.insight.analytics;

// It stores the fields of a record which are required to process input
public class Entry {
	String cmteID = "";
	String zipCode = "";
	String uid = "";
	int year = 0;
	double percentile = 0.0;
	double amount = 0;

	public Entry(String cmteID, String zipCode, String uid, int year, double percentile, double amount) {
		this.cmteID = cmteID;
		this.zipCode = zipCode;
		this.uid = uid;
		this.year = year;
		this.percentile = percentile;
		this.amount = amount;
	}

	public String getCmteID() {
		return cmteID;
	}

	public void setCmteID(String cmteID) {
		this.cmteID = cmteID;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public double getPercentile() {
		return percentile;
	}

	public void setPercentile(double percentile) {
		this.percentile = percentile;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
