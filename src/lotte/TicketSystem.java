package lotte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TicketSystem {
	OrderData orderItem;
	
	
	private void declareNewArray() {
		orderItem = new OrderData();
	}
	
	////////// �����Լ�
	void startSystem() throws IOException {
		Scanner sc = new Scanner(System.in);
		CalculateStuff cal = new CalculateStuff();
		AskStuff ask = new AskStuff();
		PrintStuff prt = new PrintStuff();
		int anotherOne, anotherOrder = 0;
		
		do { // ���ο��ֹ��� �޴� �ݺ��� 
			prt.writeFirstLine();
			
			do {
				declareNewArray();
				ask.printMenu(orderItem);
				cal.calcAge(orderItem);
				cal.calcPrice(orderItem);
				prt.writeALine(orderItem);
				
				System.out.print("��� �߱� �Ͻðڽ��ϱ�?\n1. Ƽ�� �߱�\n2. ����\n->");
				anotherOne = sc.nextInt();
			} while (anotherOne != 2); // �ϳ��� �ֹ� �ȿ��� �ٸ� ����� �޴� �ݺ��� 
			
			prt.printOrderResult(orderItem);
			
			System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		prt.closeFileWriter();
	}

}