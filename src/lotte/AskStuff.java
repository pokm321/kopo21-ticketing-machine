package lotte;

import java.util.Scanner;

public class AskStuff {
	
	private static void errorMsg() {
		System.out.println("정확한 값을 입력해주세요.");
	}
	
	//////////메뉴 출력 
	protected void printMenu(OrderData orderItem) {
		Scanner sc = new Scanner(System.in);
		
		do {
			System.out.print("시간대를 선택하세요.\n1. 주간권\n2. 야간권 (4시 이후 입장시)\n-> ");
			orderItem.setDayOrNight(sc.nextInt());
			if (orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_DAY && orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_NIGHT) {
				errorMsg();
			}
		} while (orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_DAY && orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_NIGHT);
		
		do {
			System.out.print("\n권종을 선택하세요.\n1. 종합이용권 (롯데월드 + 민속박물관)\n2. 파크이용권 (롯데월드)\n-> ");
			orderItem.setType(sc.nextInt());
			if (orderItem.getType() != StaticValue.TYPE_ALL && orderItem.getType() != StaticValue.TYPE_PARK) {
				errorMsg();
			}
		} while (orderItem.getType() != StaticValue.TYPE_ALL && orderItem.getType() != StaticValue.TYPE_PARK);
		
		sc.nextLine();
		do {  //주민번호 뒷자리 첫글자가 1~4가 아니면 다시입력해라. 
			System.out.print("\n주민번호를 입력하세요. (-제외)\n-> ");
			orderItem.setSsn(Integer.parseInt(sc.nextLine().substring(0,7)));
			orderItem.setSsn_t(orderItem.getSsn() % 10);
			orderItem.setSsn(orderItem.getSsn() / 10);
			if (orderItem.getSsn_t() < StaticValue.SSN_T_MIN || orderItem.getSsn_t() > StaticValue.SSN_T_MAX) {
				errorMsg();
			}
		} while (orderItem.getSsn_t() < StaticValue.SSN_T_MIN || orderItem.getSsn_t() > StaticValue.SSN_T_MAX);
		
		do {
			System.out.print("\n몇개를 주문하시겠습니까? (최대 10개)\n-> ");
			orderItem.setNumber(sc.nextInt());
			if (orderItem.getNumber() > 10) {
				errorMsg();
			}
		} while (orderItem.getNumber() > 10);
		
		
		do {
			System.out.print("\n우대사항을 선택하세요.\n1. 없음 (나이 우대는 자동처리)\n2. 장애인\n3. 국가유공자\n4. 휴가장병\n5. 임산부\n6. 다자녀\n-> ");
			orderItem.setDiscount(sc.nextInt());
			if (orderItem.getDiscount() < StaticValue.DISCOUNT_MIN || orderItem.getDiscount() > StaticValue.DISCOUNT_MAX) {
				errorMsg();
			}
		} while (orderItem.getDiscount() < StaticValue.DISCOUNT_MIN || orderItem.getDiscount() > StaticValue.DISCOUNT_MAX);
	}
}
