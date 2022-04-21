package dataAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import ticketing.StaticValue;

public class WriteStuff {
	BufferedReader br;
	File file;
	FileWriter fw;
	
	protected void writeByDate() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		file = new File(StaticValue.FILENAME_BY_DATE);
		fw = new FileWriter(file);
		
		String line;
		int sum = 0;
		String dateOfUpperLine = "";
		
		fw.write("일자,총 매출\n");
		br.readLine();
		
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
			
			if (!lineArray[0].equals(dateOfUpperLine)) {
				if (dateOfUpperLine.length() != 0) {
					fw.write(sum + "\n");
				}
				fw.write(lineArray[0].substring(0, 4) + "-" + lineArray[0].substring(4, 6) + "-" + lineArray[0].substring(6) + ",");
				sum = Integer.parseInt(lineArray[5]); // 총 매출 계산 
				dateOfUpperLine = lineArray[0]; 
			} else {
				sum += Integer.parseInt(lineArray[5]);
			}
		}
		
		fw.write(sum + "\n");
		
		br.close();
		fw.close();
	}
	
	protected void writeByAge() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		file = new File(StaticValue.FILENAME_BY_AGE);
		fw = new FileWriter(file);
		
		String line;
		int[] dataDay = new int[StaticValue.AGE_NUM_OF_CHOICES];
		int[] dataNight = new int[StaticValue.AGE_NUM_OF_CHOICES];
		int revenueDay = 0;
		int revenueNight = 0;
		fw.write("구분,주간권,야간권\n");
		
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");

			if (lineArray[1].equals("주간")) {
				revenueDay += Integer.parseInt(lineArray[5]);
				
				if (lineArray[3].equals("유아")) {
					dataDay[StaticValue.AGE_BABY] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("어린이")) {
					dataDay[StaticValue.AGE_CHILD] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("청소년")) {
					dataDay[StaticValue.AGE_TEEN] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("성인")) {
					dataDay[StaticValue.AGE_ADULT] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("노인")) {
					dataDay[StaticValue.AGE_ELDER] += Integer.parseInt(lineArray[4]);
				}
			} else if (lineArray[1].equals("야간")) {
				revenueNight += Integer.parseInt(lineArray[5]);
				
				if (lineArray[3].equals("유아")) {
					dataNight[StaticValue.AGE_BABY] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("어린이")) {
					dataNight[StaticValue.AGE_CHILD] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("청소년")) {
					dataNight[StaticValue.AGE_TEEN] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("성인")) {
					dataNight[StaticValue.AGE_ADULT] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("노인")) {
					dataNight[StaticValue.AGE_ELDER] += Integer.parseInt(lineArray[4]);
				}
			} 
		}
		
		int numOfDayTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfDayTickets += dataDay[i];
		}
		
		int numOfNightTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfNightTickets += dataNight[i];
		}
		fw.write("유아," + dataDay[0] + "," + dataNight[0] + "\n");
		fw.write("어린이," + dataDay[1] + "," + dataNight[1] + "\n");
		fw.write("청소년," + dataDay[2] + "," + dataNight[2] + "\n");
		fw.write("어른," + dataDay[3] + "," + dataNight[3] + "\n");
		fw.write("노인," + dataDay[4] + "," + dataNight[4] + "\n");
		fw.write("합계" + numOfDayTickets + "," + numOfNightTickets + "\n");
		fw.write("매출," + revenueDay + "," + revenueNight + "\n");

		br.close();
		fw.close();
	}
	
	protected void writeByDiscount() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		file = new File(StaticValue.FILENAME_BY_DISCOUNT);
		fw = new FileWriter(file);
		
		String line;
		int sum = 0;
		int[] numOfTickets = new int[StaticValue.DISCOUNT_NUM_OF_CHOICES];
		
		System.out.print("===== 우대권 판매 현황 =====\n");
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
			
			if (lineArray[6].equals("없음")) {
				numOfTickets[StaticValue.DISCOUNT_NONE - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("장애인")) {
				numOfTickets[StaticValue.DISCOUNT_DISABLED - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("국가유공자")) {
				numOfTickets[StaticValue.DISCOUNT_VETERAN - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("휴가장병")) {
				numOfTickets[StaticValue.DISCOUNT_SOLDIER - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("임산부")) {
				numOfTickets[StaticValue.DISCOUNT_PREGNANT - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("다자녀")) {
				numOfTickets[StaticValue.DISCOUNT_MULTICHILDS - 1] += Integer.parseInt(lineArray[4]);
			}
		}
		
		for (int i = 0; i < numOfTickets.length; i++) {
			sum += numOfTickets[i];
		}
		
		fw.write("총 판매 티켓수," + sum + "\n");
		fw.write("우대 없음," + numOfTickets[StaticValue.DISCOUNT_NONE - 1] + "\n");
		fw.write("장애인," + numOfTickets[StaticValue.DISCOUNT_DISABLED - 1] + "\n");
		fw.write("국가유공자," + numOfTickets[StaticValue.DISCOUNT_VETERAN - 1] + "\n");
		fw.write("휴가장병," + numOfTickets[StaticValue.DISCOUNT_SOLDIER - 1] + "\n");
		fw.write("임산부," + numOfTickets[StaticValue.DISCOUNT_PREGNANT - 1] + "\n");
		fw.write("다자녀," + numOfTickets[StaticValue.DISCOUNT_MULTICHILDS - 1] + "\n");
		
		br.close();
		fw.close();
	}
}
