package lotte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class TicketSystem {
	int ssn, ssn_t, sum;
	static String fileName= "C:\\Users\\user\\Documents\\LotteReport.csv";
	static File file = new File(fileName);
	static FileWriter fw;
	static Scanner sc = new Scanner(System.in);
	
	OrderData orderItem;
	ArrayList<OrderData> orderList = new ArrayList<OrderData>();
	
	private static void errorMsg() {
		System.out.println("정확한 값을 입력해주세요.");
	}
	
	//////////메뉴 출력 
	private void printMenu() {
		orderItem = new OrderData();
		
		do {
			System.out.print("시간대를 선택하세요.\n1. 주간권\n2. 야간권 (4시 이후 입장시)\n-> ");
			orderItem.setDayOrNight(sc.nextInt());
			if (orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_DAY() && orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_NIGHT()) {
				errorMsg();
			}
		} while (orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_DAY() && orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_NIGHT());
		
		do {
			System.out.print("\n권종을 선택하세요.\n1. 종합이용권 (롯데월드 + 민속박물관)\n2. 파크이용권 (롯데월드)\n-> ");
			orderItem.setType(sc.nextInt());
			if (orderItem.getType() != Staticvalue.getTYPE_ALL() && orderItem.getType() != Staticvalue.getTYPE_PARK()) {
				errorMsg();
			}
		} while (orderItem.getType() != Staticvalue.getTYPE_ALL() && orderItem.getType() != Staticvalue.getTYPE_PARK());
		
		sc.nextLine();
		do {  //주민번호 뒷자리 첫글자가 1~4가 아니면 다시입력해라. 
			System.out.print("\n주민번호를 입력하세요. (-제외)\n-> ");
			ssn = Integer.parseInt(sc.nextLine().substring(0,7));
			ssn_t = ssn % 10;
			ssn /= 10;
			if (ssn_t < Staticvalue.getSSN_T_MIN() || ssn_t > Staticvalue.getSSN_T_MAX()) {
				errorMsg();
			}
		} while (ssn_t < Staticvalue.getSSN_T_MIN() || ssn_t > Staticvalue.getSSN_T_MAX());
		
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
			if (orderItem.getDiscount() < Staticvalue.getDISCOUNT_MIN() || orderItem.getDiscount() > Staticvalue.getDISCOUNT_MAX()) {
				errorMsg();
			}
		} while (orderItem.getDiscount() < Staticvalue.getDISCOUNT_MIN() || orderItem.getDiscount() > Staticvalue.getDISCOUNT_MAX());
	}

	//////////만나이 구한뒤 어른/어린이/청소년 여부 파악 
	private void calcAge() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		orderItem.setTimeRN(Integer.parseInt(now.format(df)));
		
		if (ssn_t == 1 || ssn_t == 2) { //ssn_t가 1이나 2면 2000년 이전 출생, 
			ssn += 19000000; // ssn 은 주민번호 앞자리, 거기에 더한 숫자는 950522를 19950522로 바꿔줌 
		} else { //ssn_t가 3이나 4면 2000년 이후 출생 
			ssn += 20000000; // 020123을 20020123으로 바꾼다. 
		}
		
		orderItem.setAge((orderItem.getTimeRN() - ssn) / 10000); // 만나이 
		
		if (orderItem.getAge() > 12 && orderItem.getAge() < 19) { // 청소년 13~18세 
			orderItem.setAge(Staticvalue.getAGE_TEEN());
		} else if (orderItem.getAge() > 2 && orderItem.getAge() < 13) { // 어린이 3~12세
			orderItem.setAge(Staticvalue.getAGE_CHILD());
		} else if (orderItem.getAge() < 3) { // 유아 0~2세 
			orderItem.setAge(Staticvalue.getAGE_BABY());
		} else if (orderItem.getAge() > 64) { // 노인 65세이상
			orderItem.setAge(Staticvalue.getAGE_ELDER());
		} else { // 성인요금 
			orderItem.setAge(Staticvalue.getAGE_ADULT());
		}
	}
	
	//////////가격 계산
	private void calcPrice() {
		int[][] priceList = {
				{15000, 47000, 54000, 62000, 47000}, // 주간 종합이용권, 왼쪽부터 유아, 어린이, 청소년, 어른, 노인 순 
				{15000, 36000, 43000, 50000, 36000}, // 야간 종합이용권
				{15000, 46000, 52000, 59000, 46000}, // 주간 파크이용권 
				{15000, 35000, 41000, 47000, 35000}  // 야간 파크이용권 
		};
		
		orderItem.setPrice(priceList[orderItem.getDayOrNight() + 2 * orderItem.getType() - 3][orderItem.getAge()] * orderItem.getNumber()); //해당하는 가격을 priceList에서 뽑아온후 갯수를 곱함 
		
		if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_DISABLED() || orderItem.getDiscount() == Staticvalue.getDISCOUNT_VETERAN()) { // 장애인, 국가유공자 종합/파크이용권 50% 우대 
			orderItem.setPrice(orderItem.getPrice() / 2); 
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_SOLDIER() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // 휴가장병 종합이용권 49% 우대 (가격표에선 실질적으로 50% 할인에 500원 추가로 되어있음.) 
			orderItem.setPrice(orderItem.getPrice() / 2 + 500);
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_PREGNANT() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // 임산부 종합이용권 50% 우대
			orderItem.setPrice(orderItem.getPrice() / 2);
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_MULTICHILDS() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // 다자녀 종합이용권 30% 우대 
			orderItem.setPrice((int)(orderItem.getPrice() * 0.7));
		}
		sum += orderItem.getPrice(); // 나중에 모든 인원의 주문이 끝났을때 출력하기위한 가격총합 
		System.out.printf("가격은 %d 원 입니다.\n감사합니다.\n\n", orderItem.getPrice()); // 가격 출력 
	}
	
	////////// csv파일에 기록 + array에 저장
	private void writeIt() {
		String[] dayOrNightList = {"주간", "야간"}; // 숫자형태로 저장되어있는 선택지들을 문자열로 바꿔서 기록하기위함 
		String[] typeList = {"종합이용권", "파크이용권"};
		String[] ageList = {"유아", "어린이", "청소년", "성인", "노인"};
		String[] discountList = {"없음", "장애인", "국가유공자", "휴가장병", "임산부", "다자녀"};
		
		try {
			fw.write(Integer.toString(orderItem.getTimeRN()) + ',' + dayOrNightList[orderItem.getDayOrNight() - 1] + ',' + typeList[orderItem.getType() - 1] + ',' + ageList[orderItem.getAge()] + ',' + orderItem.getNumber() + ',' + orderItem.getPrice() + ',' + discountList[orderItem.getDiscount() - 1] + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		inputDataInArray();
	}
		
	////////// 주문 결과 출력
	private void printIt() {
		System.out.println("\n티켓 발권을 종료합니다. 감사합니다.");
		System.out.println("\n======================= 롯데월드 =======================");
		int i = 0;
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
			
			if (orderList.get(i).getAge() == Staticvalue.getAGE_BABY()) {
				System.out.printf("%6s", "유아");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_CHILD()) {
				System.out.printf("%6s", "어린이");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_TEEN()) {
				System.out.printf("%6s", "청소년");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_ADULT()) {
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
				System.out.printf("%7s\n", "다자녀");
			}
			
		}
		System.out.printf("\n입장료 총액은 %d원 입니다.\n", sum);
		System.out.print("======================================================");
	}

	private void inputDataInArray() {
		orderList.add(orderItem);
	}
	
	////////// 메인함수
	void startSystem() throws IOException {
		int anotherOne, anotherOrder = 0;
		
		do { // 새로운주문을 받는 반복문 
			fw = new FileWriter(file);
			fw.write("날짜,시간대,권종,연령구분,수량,가격,우대사항\n");
			
			do {
				printMenu();
				calcAge();
				calcPrice();
				writeIt();
				
				System.out.print("계속 발권 하시겠습니까?\n1. 티켓 발권\n2. 종료\n->");
				anotherOne = sc.nextInt();
			} while (anotherOne != 2); // 하나의 주문 안에서 다른 사람을 받는 반복문 
			
			printIt();
			
			System.out.print("\n\n계속 진행 (1: 새로운 주문, 2: 프로그램 종료) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		fw.close();
	}

}