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
	
	////////// 메인함수
	void startSystem() throws IOException {
		Scanner sc = new Scanner(System.in);
		CalculateStuff cal = new CalculateStuff();
		AskStuff ask = new AskStuff();
		PrintStuff prt = new PrintStuff();
		int anotherOne, anotherOrder = 0;
		
		do { // 새로운주문을 받는 반복문 
			prt.writeFirstLine();
			
			do {
				declareNewArray();
				ask.printMenu(orderItem);
				cal.calcAge(orderItem);
				cal.calcPrice(orderItem);
				prt.writeALine(orderItem);
				
				System.out.print("계속 발권 하시겠습니까?\n1. 티켓 발권\n2. 종료\n->");
				anotherOne = sc.nextInt();
			} while (anotherOne != 2); // 하나의 주문 안에서 다른 사람을 받는 반복문 
			
			prt.printOrderResult(orderItem);
			
			System.out.print("\n\n계속 진행 (1: 새로운 주문, 2: 프로그램 종료) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		prt.closeFileWriter();
	}

}