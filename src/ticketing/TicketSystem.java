package ticketing;

import java.io.IOException;

public class TicketSystem {
	OrderData orderItem;
	
	////////// �����Լ�
	void startSystem() {		
		AskStuff ask = new AskStuff();
		CalculateStuff cal = new CalculateStuff();
		PrintStuff prt = new PrintStuff();
		
		try {
			do { // ���ο��ֹ��� �޴� �ݺ��� 
				prt.writeFirstLine();
				
				do {
					orderItem = new OrderData();
					ask.printMenu(orderItem);
					cal.calcAge(orderItem);
					cal.calcPrice(orderItem);
					prt.writeALine(orderItem);
					ask.askRepeat1(orderItem);
				} while (orderItem.getAnotherOne() != 2); // �ϳ��� �ֹ� �ȿ��� �ٸ� ����� �޴� �ݺ��� 
				
				prt.printOrderResult(orderItem);
				ask.askRepeat2(orderItem);
			} while (orderItem.getAnotherOrder() != 2);
			
			prt.closeFileWriter();
		} catch (IOException e) {}
	}

}