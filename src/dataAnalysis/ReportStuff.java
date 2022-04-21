package dataAnalysis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ticketing.OrderData;
import ticketing.StaticValue;

public class ReportStuff {
	BufferedReader br;
	
	protected void reportBasics() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));

		System.out.printf("================================ %s ================================\n", StaticValue.FILENAME);
		String line;
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
			for (int i = 0; i < lineArray.length; i++) {
				System.out.printf("%-10s", lineArray[i]);
			}
			System.out.println();
		}
		System.out.println("---------------------------------------------------------------------------------\n");
		br.close();
	}
	
	protected void reportByTime() throws NumberFormatException, IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		
		String line;
		int[] dataDay = new int[StaticValue.AGE_NUM_OF_CHOICES];
		int[] dataNight = new int[StaticValue.AGE_NUM_OF_CHOICES];
		int revenueDay = 0;
		int revenueNight = 0;
		System.out.print("================ 권종 별 판매현황 ================\n");
		
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
		
		int numOfTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfTickets += dataDay[i];
		}
		System.out.printf("주간권 총 %d매\n", numOfTickets);
		System.out.printf("유아 %d매, 어린이 %d매, 청소년 %d매, 어른 %d매, 노인 %d매\n", dataDay[0], dataDay[1], dataDay[2], dataDay[3], dataDay[4]);
		System.out.printf("주간권 매출 : %d원\n\n", revenueDay);
		
		numOfTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfTickets += dataNight[i];
		}
		System.out.printf("야간권 총 %d매\n", numOfTickets);
		System.out.printf("유아 %d매, 어린이 %d매, 청소년 %d매, 어른 %d매, 노인 %d매\n", dataNight[0], dataNight[1], dataNight[2], dataNight[3], dataNight[4]);
		System.out.printf("야간권 매출 : %d원\n", revenueNight);

		System.out.println("----------------------------------------------\n");
		br.close();
	}
	
	protected void reportByDate() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		String line;
		String dateOfUpperLine = "";
		int revenue = 0;
		int sum = 0;
		System.out.print("=========== 일자 별 매출현황 ===========\n");
		br.readLine(); // 한줄 스킵
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
	
			if (!lineArray[0].equals(dateOfUpperLine)) {
				if (dateOfUpperLine.length() != 0) {
					System.out.printf("%10d원\n", sum);
				}
				System.out.printf("%4s년 %2s월 %2s일 : 총 매출", lineArray[0].substring(0, 4), lineArray[0].substring(4, 6), lineArray[0].substring(6));
				sum = Integer.parseInt(lineArray[5]); // 총 매출 계산 
				dateOfUpperLine = lineArray[0]; 
			} else {
				sum += Integer.parseInt(lineArray[5]);
			}
		}
		System.out.printf("%10d원\n", sum);
		System.out.println("------------------------------------\n");
		br.close();
	}
	
	protected void reportByDiscount() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
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
		
		System.out.printf("총 판매 티켓수  : %5d매\n우대 없음      : %5d매\n장애인        : %5d매\n국가유공자     : %5d매\n휴가장병       : %5d매\n임산부        : %5d매\n다자녀        : %5d매\n", sum, numOfTickets[0], numOfTickets[1], numOfTickets[2], numOfTickets[3], numOfTickets[4], numOfTickets[5]);
		System.out.println("------------------------\n");
		br.close();
		
		
	}
}
