package lotte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PrintStuff {
	File file = new File(StaticValue.FILENAME);
	FileWriter fw;
	
	ArrayList<OrderData> orderList = new ArrayList<OrderData>();
	
	protected void putDataInArray(OrderData orderItem) {
		orderList.add(orderItem);
	}
	
	protected void writeFirstLine() throws IOException {
		fw = new FileWriter(file);
		fw.write("��¥,�ð���,����,���ɱ���,����,����,������\n");
	}
	
	////////// csv���Ͽ� ��� + array�� ����
	protected void writeALine(OrderData orderItem) throws IOException {
		String[] dayOrNightList = {"�ְ�", "�߰�"}; // �������·� ����Ǿ��ִ� ���������� ���ڿ��� �ٲ㼭 ����ϱ����� 
		String[] typeList = {"�����̿��", "��ũ�̿��"};
		String[] ageList = {"����", "���", "û�ҳ�", "����", "����"};
		String[] discountList = {"����", "�����", "����������", "�ް��庴", "�ӻ��", "���ڳ�"};
		
		try {
			fw.write(Integer.toString(orderItem.getTimeRN()) + ',' + dayOrNightList[orderItem.getDayOrNight() - 1] + ',' + typeList[orderItem.getType() - 1] + ',' + ageList[orderItem.getAge()] + ',' + orderItem.getNumber() + ',' + orderItem.getPrice() + ',' + discountList[orderItem.getDiscount() - 1] + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		putDataInArray(orderItem);
	}
		
	////////// �ֹ� ��� ���
	protected void printOrderResult(OrderData orderItem) {
		int i = 0;
		int sum = 0;
		
		System.out.println("\nƼ�� �߱��� �����մϴ�. �����մϴ�.");
		System.out.println("\n======================= �Ե����� =======================");
		
		for (i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getDayOrNight() == 1) {
				System.out.printf("%-4s", "�ְ�");
			} else {
				System.out.printf("%-4s", "�߰�");
			}
			
			if (orderList.get(i).getType() == 1) {
				System.out.printf("%8s", "�����̿��");
			} else {
				System.out.printf("%8s", "��ũ�̿��");
			}
			
			if (orderList.get(i).getAge() == StaticValue.AGE_BABY) {
				System.out.printf("%6s", "����");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_CHILD) {
				System.out.printf("%6s", "���");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_TEEN) {
				System.out.printf("%6s", "û�ҳ�");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_ADULT) {
				System.out.printf("%6s", "����");
			} else {
				System.out.printf("%6s", "����");
			}
			
			System.out.printf(" X %-2d %8d��   *������� ", orderList.get(i).getNumber(), orderList.get(i).getPrice());
			
			if (orderList.get(i).getDiscount() == 1) {
				System.out.printf("%-7s\n", "����");
			} else if (orderList.get(i).getDiscount() == 2) {
				System.out.printf("%-7s\n", "�����");
			} else if (orderList.get(i).getDiscount() == 3) {
				System.out.printf("%-7s\n", "����������");
			} else if (orderList.get(i).getDiscount() == 4) {
				System.out.printf("%-7s\n", "�ް��庴");
			} else if (orderList.get(i).getDiscount() == 5) {
				System.out.printf("%-7s\n", "�ӻ��");
			} else {
				System.out.printf("%-7s\n", "���ڳ�");
			}
		}
		
		for (i = 0; i < orderList.size(); i++) {
			sum += orderList.get(i).getPrice();
		}
		
		System.out.printf("\n����� �Ѿ��� %d�� �Դϴ�.\n", sum);
		System.out.print("======================================================");
	}
	
	protected void closeFileWriter() throws IOException {
		fw.close();
	}
}
