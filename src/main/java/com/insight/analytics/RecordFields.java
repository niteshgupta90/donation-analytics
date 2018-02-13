package com.insight.analytics;

// Stores the Indexes and MIN/MAX values corresponding to the each field of a record
public class RecordFields {
	public final static int CMTE_ID = 0;
	public final static int NAME = 7;
	public final static int ZIP_CODE = 10;
	public final static int TRANSACTION_DT = 13;
	public final static int TRANSACTION_AMT = 14;
	public final static int OTHER_ID = 15;

	public final static int CMTE_ID_MAX = 9;
	public final static int CMTE_ID_MIN = 1;

	public final static int NAME_MAX = 200;
	public final static int NAME_MIN = 1;

	public final static int ZIP_CODE_MAX = 9;
	public final static int ZIP_CODE_MIN = 5;

	public final static int TRANSACTION_DT_MAX = 8;
	public final static int TRANSACTION_DT_MIN = 8;

	public final static int TRANSACTION_AMT_MAX = 14;
	public final static int TRANSACTION_AMT_MIN = 1;

	public final static int OTHER_ID_MAX = 0;
	public final static int OTHER_ID_MIN = 0;

}
