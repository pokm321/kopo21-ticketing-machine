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
		
		fw.write("����,�� ����\n");
		br.readLine();
		
		while ((line = br.readLine()) != null) {
			String[] lineArray = line.split(",");
			
			if (!lineArray[0].equals(dateOfUpperLine)) {
				if (dateOfUpperLine.length() != 0) {
					fw.write(sum + "\n");
				}
				fw.write(lineArray[0].substring(0, 4) + "-" + lineArray[0].substring(4, 6) + "-" + lineArray[0].substring(6) + ",");
				sum = Integer.parseInt(lineArray[5]); // �� ���� ��� 
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
		fw.write("����,�ְ���,�߰���\n");
		
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
		
		int numOfDayTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfDayTickets += dataDay[i];
		}
		
		int numOfNightTickets = 0;
		for (int i = 0; i < StaticValue.AGE_NUM_OF_CHOICES; i++) {
			numOfNightTickets += dataNight[i];
		}
		fw.write("����," + dataDay[0] + "," + dataNight[0] + "\n");
		fw.write("���," + dataDay[1] + "," + dataNight[1] + "\n");
		fw.write("û�ҳ�," + dataDay[2] + "," + dataNight[2] + "\n");
		fw.write("�," + dataDay[3] + "," + dataNight[3] + "\n");
		fw.write("����," + dataDay[4] + "," + dataNight[4] + "\n");
		fw.write("�հ�" + numOfDayTickets + "," + numOfNightTickets + "\n");
		fw.write("����," + revenueDay + "," + revenueNight + "\n");

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
		
		fw.write("�� �Ǹ� Ƽ�ϼ�," + sum + "\n");
		fw.write("��� ����," + numOfTickets[StaticValue.DISCOUNT_NONE - 1] + "\n");
		fw.write("�����," + numOfTickets[StaticValue.DISCOUNT_DISABLED - 1] + "\n");
		fw.write("����������," + numOfTickets[StaticValue.DISCOUNT_VETERAN - 1] + "\n");
		fw.write("�ް��庴," + numOfTickets[StaticValue.DISCOUNT_SOLDIER - 1] + "\n");
		fw.write("�ӻ��," + numOfTickets[StaticValue.DISCOUNT_PREGNANT - 1] + "\n");
		fw.write("���ڳ�," + numOfTickets[StaticValue.DISCOUNT_MULTICHILDS - 1] + "\n");
		
		br.close();
		fw.close();
	}
}
