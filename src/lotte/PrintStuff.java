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
		fw.write("날짜,시간대,권종,연령구분,수량,가격,우대사항\n");
	}
	
	////////// csv파일에 기록 + array에 저장
	protected void writeALine(OrderData orderItem) throws IOException {
		String[] dayOrNightList = {"주간", "야간"}; // 숫자형태로 저장되어있는 선택지들을 문자열로 바꿔서 기록하기위함 
		String[] typeList = {"종합이용권", "파크이용권"};
		String[] ageList = {"유아", "어린이", "청소년", "성인", "노인"};
		String[] discountList = {"없음", "장애인", "국가유공자", "휴가장병", "임산부", "다자녀"};
		
		try {
			fw.write(Integer.toString(orderItem.getTimeRN()) + ',' + dayOrNightList[orderItem.getDayOrNight() - 1] + ',' + typeList[orderItem.getType() - 1] + ',' + ageList[orderItem.getAge()] + ',' + orderItem.getNumber() + ',' + orderItem.getPrice() + ',' + discountList[orderItem.getDiscount() - 1] + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		putDataInArray(orderItem);
	}
		
	////////// 주문 결과 출력
	protected void printOrderResult(OrderData orderItem) {
		int i = 0;
		int sum = 0;
		
		System.out.println("\n티켓 발권을 종료합니다. 감사합니다.");
		System.out.println("\n======================= 롯데월드 =======================");
		
		for (i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).getDayOrNight() == 1) {
				System.out.printf("%-4s", "주간");
			} else {
				System.out.printf("%-4s", "야간");
			}
			
			if (orderList.get(i).getType() == 1) {
				System.out.printf("%8s", "종합이용권");
			} else {
				System.out.printf("%8s", "파크이용권");
			}
			
			if (orderList.get(i).getAge() == StaticValue.AGE_BABY) {
				System.out.printf("%6s", "유아");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_CHILD) {
				System.out.printf("%6s", "어린이");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_TEEN) {
				System.out.printf("%6s", "청소년");
			} else if (orderList.get(i).getAge() == StaticValue.AGE_ADULT) {
				System.out.printf("%6s", "성인");
			} else {
				System.out.printf("%6s", "노인");
			}
			
			System.out.printf(" X %-2d %8d원   *우대적용 ", orderList.get(i).getNumber(), orderList.get(i).getPrice());
			
			if (orderList.get(i).getDiscount() == 1) {
				System.out.printf("%-7s\n", "없음");
			} else if (orderList.get(i).getDiscount() == 2) {
				System.out.printf("%-7s\n", "장애인");
			} else if (orderList.get(i).getDiscount() == 3) {
				System.out.printf("%-7s\n", "국가유공자");
			} else if (orderList.get(i).getDiscount() == 4) {
				System.out.printf("%-7s\n", "휴가장병");
			} else if (orderList.get(i).getDiscount() == 5) {
				System.out.printf("%-7s\n", "임산부");
			} else {
				System.out.printf("%-7s\n", "다자녀");
			}
		}
		
		for (i = 0; i < orderList.size(); i++) {
			sum += orderList.get(i).getPrice();
		}
		
		System.out.printf("\n입장료 총액은 %d원 입니다.\n", sum);
		System.out.print("======================================================");
	}
	
	protected void closeFileWriter() throws IOException {
		fw.close();
	}
}
