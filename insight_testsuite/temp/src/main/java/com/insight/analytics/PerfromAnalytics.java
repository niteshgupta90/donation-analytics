package com.insight.analytics;
//main class to perform the analytics operation
import java.io.IOException;

public class PerfromAnalytics {

	public static void main(String[] args) throws IOException {
		if (args.length < 2) {
			System.out.println("Invalid Input. Please provide two files as arguments.");
		}
		Processor pp = new Processor(args[0], args[1]);
	}

}
