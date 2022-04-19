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
		System.out.println("��Ȯ�� ���� �Է����ּ���.");
	}
	
	//////////�޴� ��� 
	private void printMenu() {
		orderItem = new OrderData();
		
		do {
			System.out.print("�ð��븦 �����ϼ���.\n1. �ְ���\n2. �߰��� (4�� ���� �����)\n-> ");
			orderItem.setDayOrNight(sc.nextInt());
			if (orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_DAY() && orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_NIGHT()) {
				errorMsg();
			}
		} while (orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_DAY() && orderItem.getDayOrNight() != Staticvalue.getDAYORNIGHT_NIGHT());
		
		do {
			System.out.print("\n������ �����ϼ���.\n1. �����̿�� (�Ե����� + �μӹڹ���)\n2. ��ũ�̿�� (�Ե�����)\n-> ");
			orderItem.setType(sc.nextInt());
			if (orderItem.getType() != Staticvalue.getTYPE_ALL() && orderItem.getType() != Staticvalue.getTYPE_PARK()) {
				errorMsg();
			}
		} while (orderItem.getType() != Staticvalue.getTYPE_ALL() && orderItem.getType() != Staticvalue.getTYPE_PARK());
		
		sc.nextLine();
		do {  //�ֹι�ȣ ���ڸ� ù���ڰ� 1~4�� �ƴϸ� �ٽ��Է��ض�. 
			System.out.print("\n�ֹι�ȣ�� �Է��ϼ���. (-����)\n-> ");
			ssn = Integer.parseInt(sc.nextLine().substring(0,7));
			ssn_t = ssn % 10;
			ssn /= 10;
			if (ssn_t < Staticvalue.getSSN_T_MIN() || ssn_t > Staticvalue.getSSN_T_MAX()) {
				errorMsg();
			}
		} while (ssn_t < Staticvalue.getSSN_T_MIN() || ssn_t > Staticvalue.getSSN_T_MAX());
		
		do {
			System.out.print("\n��� �ֹ��Ͻðڽ��ϱ�? (�ִ� 10��)\n-> ");
			orderItem.setNumber(sc.nextInt());
			if (orderItem.getNumber() > 10) {
				errorMsg();
			}
		} while (orderItem.getNumber() > 10);
		
		
		do {
			System.out.print("\n�������� �����ϼ���.\n1. ���� (���� ���� �ڵ�ó��)\n2. �����\n3. ����������\n4. �ް��庴\n5. �ӻ��\n6. ���ڳ�\n-> ");
			orderItem.setDiscount(sc.nextInt());
			if (orderItem.getDiscount() < Staticvalue.getDISCOUNT_MIN() || orderItem.getDiscount() > Staticvalue.getDISCOUNT_MAX()) {
				errorMsg();
			}
		} while (orderItem.getDiscount() < Staticvalue.getDISCOUNT_MIN() || orderItem.getDiscount() > Staticvalue.getDISCOUNT_MAX());
	}

	//////////������ ���ѵ� �/���/û�ҳ� ���� �ľ� 
	private void calcAge() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		orderItem.setTimeRN(Integer.parseInt(now.format(df)));
		
		if (ssn_t == 1 || ssn_t == 2) { //ssn_t�� 1�̳� 2�� 2000�� ���� ���, 
			ssn += 19000000; // ssn �� �ֹι�ȣ ���ڸ�, �ű⿡ ���� ���ڴ� 950522�� 19950522�� �ٲ��� 
		} else { //ssn_t�� 3�̳� 4�� 2000�� ���� ��� 
			ssn += 20000000; // 020123�� 20020123���� �ٲ۴�. 
		}
		
		orderItem.setAge((orderItem.getTimeRN() - ssn) / 10000); // ������ 
		
		if (orderItem.getAge() > 12 && orderItem.getAge() < 19) { // û�ҳ� 13~18�� 
			orderItem.setAge(Staticvalue.getAGE_TEEN());
		} else if (orderItem.getAge() > 2 && orderItem.getAge() < 13) { // ��� 3~12��
			orderItem.setAge(Staticvalue.getAGE_CHILD());
		} else if (orderItem.getAge() < 3) { // ���� 0~2�� 
			orderItem.setAge(Staticvalue.getAGE_BABY());
		} else if (orderItem.getAge() > 64) { // ���� 65���̻�
			orderItem.setAge(Staticvalue.getAGE_ELDER());
		} else { // ���ο�� 
			orderItem.setAge(Staticvalue.getAGE_ADULT());
		}
	}
	
	//////////���� ���
	private void calcPrice() {
		int[][] priceList = {
				{15000, 47000, 54000, 62000, 47000}, // �ְ� �����̿��, ���ʺ��� ����, ���, û�ҳ�, �, ���� �� 
				{15000, 36000, 43000, 50000, 36000}, // �߰� �����̿��
				{15000, 46000, 52000, 59000, 46000}, // �ְ� ��ũ�̿�� 
				{15000, 35000, 41000, 47000, 35000}  // �߰� ��ũ�̿�� 
		};
		
		orderItem.setPrice(priceList[orderItem.getDayOrNight() + 2 * orderItem.getType() - 3][orderItem.getAge()] * orderItem.getNumber()); //�ش��ϴ� ������ priceList���� �̾ƿ��� ������ ���� 
		
		if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_DISABLED() || orderItem.getDiscount() == Staticvalue.getDISCOUNT_VETERAN()) { // �����, ���������� ����/��ũ�̿�� 50% ��� 
			orderItem.setPrice(orderItem.getPrice() / 2); 
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_SOLDIER() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // �ް��庴 �����̿�� 49% ��� (����ǥ���� ���������� 50% ���ο� 500�� �߰��� �Ǿ�����.) 
			orderItem.setPrice(orderItem.getPrice() / 2 + 500);
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_PREGNANT() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // �ӻ�� �����̿�� 50% ���
			orderItem.setPrice(orderItem.getPrice() / 2);
		} else if (orderItem.getDiscount() == Staticvalue.getDISCOUNT_MULTICHILDS() && orderItem.getType() == Staticvalue.getTYPE_ALL()) { // ���ڳ� �����̿�� 30% ��� 
			orderItem.setPrice((int)(orderItem.getPrice() * 0.7));
		}
		sum += orderItem.getPrice(); // ���߿� ��� �ο��� �ֹ��� �������� ����ϱ����� �������� 
		System.out.printf("������ %d �� �Դϴ�.\n�����մϴ�.\n\n", orderItem.getPrice()); // ���� ��� 
	}
	
	////////// csv���Ͽ� ��� + array�� ����
	private void writeIt() {
		String[] dayOrNightList = {"�ְ�", "�߰�"}; // �������·� ����Ǿ��ִ� ���������� ���ڿ��� �ٲ㼭 ����ϱ����� 
		String[] typeList = {"�����̿��", "��ũ�̿��"};
		String[] ageList = {"����", "���", "û�ҳ�", "����", "����"};
		String[] discountList = {"����", "�����", "����������", "�ް��庴", "�ӻ��", "���ڳ�"};
		
		try {
			fw.write(Integer.toString(orderItem.getTimeRN()) + ',' + dayOrNightList[orderItem.getDayOrNight() - 1] + ',' + typeList[orderItem.getType() - 1] + ',' + ageList[orderItem.getAge()] + ',' + orderItem.getNumber() + ',' + orderItem.getPrice() + ',' + discountList[orderItem.getDiscount() - 1] + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		inputDataInArray();
	}
		
	////////// �ֹ� ��� ���
	private void printIt() {
		System.out.println("\nƼ�� �߱��� �����մϴ�. �����մϴ�.");
		System.out.println("\n======================= �Ե����� =======================");
		int i = 0;
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
			
			if (orderList.get(i).getAge() == Staticvalue.getAGE_BABY()) {
				System.out.printf("%6s", "����");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_CHILD()) {
				System.out.printf("%6s", "���");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_TEEN()) {
				System.out.printf("%6s", "û�ҳ�");
			} else if (orderList.get(i).getAge() == Staticvalue.getAGE_ADULT()) {
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
				System.out.printf("%7s\n", "���ڳ�");
			}
			
		}
		System.out.printf("\n����� �Ѿ��� %d�� �Դϴ�.\n", sum);
		System.out.print("======================================================");
	}

	private void inputDataInArray() {
		orderList.add(orderItem);
	}
	
	////////// �����Լ�
	void startSystem() throws IOException {
		int anotherOne, anotherOrder = 0;
		
		do { // ���ο��ֹ��� �޴� �ݺ��� 
			fw = new FileWriter(file);
			fw.write("��¥,�ð���,����,���ɱ���,����,����,������\n");
			
			do {
				printMenu();
				calcAge();
				calcPrice();
				writeIt();
				
				System.out.print("��� �߱� �Ͻðڽ��ϱ�?\n1. Ƽ�� �߱�\n2. ����\n->");
				anotherOne = sc.nextInt();
			} while (anotherOne != 2); // �ϳ��� �ֹ� �ȿ��� �ٸ� ����� �޴� �ݺ��� 
			
			printIt();
			
			System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		fw.close();
	}

}