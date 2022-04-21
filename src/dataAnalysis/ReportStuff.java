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
		System.out.print("================ ���� �� �Ǹ���Ȳ ================\n");
		
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");

			if (lineArray[1].equals("�ְ�")) {
				revenueDay += Integer.parseInt(lineArray[5]);
				
				if (lineArray[3].equals("����")) {
					dataDay[StaticValue.AGE_BABY] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("���")) {
					dataDay[StaticValue.AGE_CHILD] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("û�ҳ�")) {
					dataDay[StaticValue.AGE_TEEN] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("����")) {
					dataDay[StaticValue.AGE_ADULT] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("����")) {
					dataDay[StaticValue.AGE_ELDER] += Integer.parseInt(lineArray[4]);
				}
			} else if (lineArray[1].equals("�߰�")) {
				revenueNight += Integer.parseInt(lineArray[5]);
				
				if (lineArray[3].equals("����")) {
					dataNight[StaticValue.AGE_BABY] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("���")) {
					dataNight[StaticValue.AGE_CHILD] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("û�ҳ�")) {
					dataNight[StaticValue.AGE_TEEN] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("����")) {
					dataNight[StaticValue.AGE_ADULT] += Integer.parseInt(lineArray[4]);
				} else if (lineArray[3].equals("����")) {
					dataNight[StaticValue.AGE_ELDER] += Integer.parseInt(lineArray[4]);
				}
			} 
		}
		
		int numOfTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfTickets += dataDay[i];
		}
		System.out.printf("�ְ��� �� %d��\n", numOfTickets);
		System.out.printf("���� %d��, ��� %d��, û�ҳ� %d��, � %d��, ���� %d��\n", dataDay[0], dataDay[1], dataDay[2], dataDay[3], dataDay[4]);
		System.out.printf("�ְ��� ���� : %d��\n\n", revenueDay);
		
		numOfTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfTickets += dataNight[i];
		}
		System.out.printf("�߰��� �� %d��\n", numOfTickets);
		System.out.printf("���� %d��, ��� %d��, û�ҳ� %d��, � %d��, ���� %d��\n", dataNight[0], dataNight[1], dataNight[2], dataNight[3], dataNight[4]);
		System.out.printf("�߰��� ���� : %d��\n", revenueNight);

		System.out.println("----------------------------------------------\n");
		br.close();
	}
	
	protected void reportByDate() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		String line;
		String dateOfUpperLine = "";
		int revenue = 0;
		int sum = 0;
		System.out.print("=========== ���� �� ������Ȳ ===========\n");
		br.readLine(); // ���� ��ŵ
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
	
			if (!lineArray[0].equals(dateOfUpperLine)) {
				if (dateOfUpperLine.length() != 0) {
					System.out.printf("%10d��\n", sum);
				}
				System.out.printf("%4s�� %2s�� %2s�� : �� ����", lineArray[0].substring(0, 4), lineArray[0].substring(4, 6), lineArray[0].substring(6));
				sum = Integer.parseInt(lineArray[5]); // �� ���� ��� 
				dateOfUpperLine = lineArray[0]; 
			} else {
				sum += Integer.parseInt(lineArray[5]);
			}
		}
		System.out.printf("%10d��\n", sum);
		System.out.println("------------------------------------\n");
		br.close();
	}
	
	protected void reportByDiscount() throws IOException {
		br = new BufferedReader(new FileReader(StaticValue.FILENAME));
		String line;
		int sum = 0;
		int[] numOfTickets = new int[StaticValue.DISCOUNT_NUM_OF_CHOICES];
		
		System.out.print("===== ���� �Ǹ� ��Ȳ =====\n");
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
			
			if (lineArray[6].equals("����")) {
				numOfTickets[StaticValue.DISCOUNT_NONE - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("�����")) {
				numOfTickets[StaticValue.DISCOUNT_DISABLED - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("����������")) {
				numOfTickets[StaticValue.DISCOUNT_VETERAN - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("�ް��庴")) {
				numOfTickets[StaticValue.DISCOUNT_SOLDIER - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("�ӻ��")) {
				numOfTickets[StaticValue.DISCOUNT_PREGNANT - 1] += Integer.parseInt(lineArray[4]);
			} else if (lineArray[6].equals("���ڳ�")) {
				numOfTickets[StaticValue.DISCOUNT_MULTICHILDS - 1] += Integer.parseInt(lineArray[4]);
			}
		}
		
		for (int i = 0; i < numOfTickets.length; i++) {
			sum += numOfTickets[i];
		}
		
		System.out.printf("�� �Ǹ� Ƽ�ϼ�  : %5d��\n��� ����      : %5d��\n�����        : %5d��\n����������     : %5d��\n�ް��庴       : %5d��\n�ӻ��        : %5d��\n���ڳ�        : %5d��\n", sum, numOfTickets[0], numOfTickets[1], numOfTickets[2], numOfTickets[3], numOfTickets[4], numOfTickets[5]);
		System.out.println("------------------------\n");
		br.close();
		
		
	}
}
