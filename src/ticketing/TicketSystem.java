package ticketing;

import java.io.IOException;

public class TicketSystem {
	OrderData orderItem;
	
	////////// 메인함수
	void startSystem() {		
		AskStuff ask = new AskStuff();
		CalculateStuff cal = new CalculateStuff();
		PrintStuff prt = new PrintStuff();
		
		try {
			do { // 새로운주문을 받는 반복문 
				prt.writeFirstLine();
				
				do {
					orderItem = new OrderData();
					ask.printMenu(orderItem);
					cal.calcAge(orderItem);
					cal.calcPrice(orderItem);
					prt.writeALine(orderItem);
					ask.askRepeat1(orderItem);
				} while (orderItem.getAnotherOne() != 2); // 하나의 주문 안에서 다른 사람을 받는 반복문 
				
				prt.printOrderResult(orderItem);
				ask.askRepeat2(orderItem);
			} while (orderItem.getAnotherOrder() != 2);
			
			prt.closeFileWriter();
		} catch (IOException e) {}
	}

}