package lotte;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculateStuff {
	
	//////////만나이 구한뒤 어른/어린이/청소년 여부 파악 
	protected void calcAge(OrderData orderItem) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		orderItem.setTimeRN(Integer.parseInt(now.format(df)));
		
		if (orderItem.getSsn_t() == 1 || orderItem.getSsn_t() == 2) { //ssn_t가 1이나 2면 2000년 이전 출생, 
			orderItem.setSsn(orderItem.getSsn() + 19000000); // ssn 은 주민번호 앞자리, 거기에 더한 숫자는 950522를 19950522로 바꿔줌 
		} else { //ssn_t가 3이나 4면 2000년 이후 출생 
			orderItem.setSsn(orderItem.getSsn() + 20000000); // 020123을 20020123으로 바꾼다. 
		}
		
		orderItem.setAge((orderItem.getTimeRN() - orderItem.getSsn()) / 10000); // 만나이 
		
		if (orderItem.getAge() > 12 && orderItem.getAge() < 19) { // 청소년 13~18세 
			orderItem.setAge(StaticValue.AGE_TEEN);
		} else if (orderItem.getAge() > 2 && orderItem.getAge() < 13) { // 어린이 3~12세
			orderItem.setAge(StaticValue.AGE_CHILD);
		} else if (orderItem.getAge() < 3) { // 유아 0~2세 
			orderItem.setAge(StaticValue.AGE_BABY);
		} else if (orderItem.getAge() > 64) { // 노인 65세이상
			orderItem.setAge(StaticValue.AGE_ELDER);
		} else { // 성인요금 
			orderItem.setAge(StaticValue.AGE_ADULT);
		}
	}
	
	//////////가격 계산
	protected void calcPrice(OrderData orderItem) {
		int[][] priceList = {
				{15000, 47000, 54000, 62000, 47000}, // 주간 종합이용권, 왼쪽부터 유아, 어린이, 청소년, 어른, 노인 순 
				{15000, 36000, 43000, 50000, 36000}, // 야간 종합이용권
				{15000, 46000, 52000, 59000, 46000}, // 주간 파크이용권 
				{15000, 35000, 41000, 47000, 35000}  // 야간 파크이용권 
		};
		
		orderItem.setPrice(priceList[orderItem.getDayOrNight() + 2 * orderItem.getType() - 3][orderItem.getAge()] * orderItem.getNumber()); //해당하는 가격을 priceList에서 뽑아온후 갯수를 곱함 
		
		if (orderItem.getDiscount() == StaticValue.DISCOUNT_DISABLED || orderItem.getDiscount() == StaticValue.DISCOUNT_VETERAN) { // 장애인, 국가유공자 종합/파크이용권 50% 우대 
			orderItem.setPrice(orderItem.getPrice() / 2); 
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_SOLDIER && orderItem.getType() == StaticValue.TYPE_ALL) { // 휴가장병 종합이용권 49% 우대 (가격표에선 실질적으로 50% 할인에 500원 추가로 되어있음.) 
			orderItem.setPrice(orderItem.getPrice() / 2 + 500);
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_PREGNANT && orderItem.getType() == StaticValue.TYPE_ALL) { // 임산부 종합이용권 50% 우대
			orderItem.setPrice(orderItem.getPrice() / 2);
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_MULTICHILDS && orderItem.getType() == StaticValue.TYPE_ALL) { // 다자녀 종합이용권 30% 우대 
			orderItem.setPrice((int)(orderItem.getPrice() * 0.7));
		}
		System.out.printf("가격은 %d 원 입니다.\n감사합니다.\n\n", orderItem.getPrice()); // 가격 출력 
	}
}
