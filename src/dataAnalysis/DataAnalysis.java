package dataAnalysis;

import java.io.IOException;

import ticketing.OrderData;

public class DataAnalysis {

	public static void main(String[] args) {
		ReportStuff rpt = new ReportStuff();
		WriteStuff wrt = new WriteStuff();
		try {
			rpt.reportBasics();
			rpt.reportByTime();
			rpt.reportByDate();
			rpt.reportByDiscount();
			
			wrt.writeByDate();
			wrt.writeByAge();
			wrt.writeByDiscount();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
