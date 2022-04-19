package lotte;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class LotteWorld {
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
			orderItem.dayOrNight = sc.nextInt();
			if (orderItem.dayOrNight != Staticvalue.DAYORNIGHT_DAY && orderItem.dayOrNight != Staticvalue.DAYORNIGHT_NIGHT) {
				errorMsg();
			}
		} while (orderItem.dayOrNight != Staticvalue.DAYORNIGHT_DAY && orderItem.dayOrNight != Staticvalue.DAYORNIGHT_NIGHT);
		
		do {
			System.out.print("\n������ �����ϼ���.\n1. �����̿�� (�Ե����� + �μӹڹ���)\n2. ��ũ�̿�� (�Ե�����)\n-> ");
			orderItem.type = sc.nextInt();
			if (orderItem.type != Staticvalue.TYPE_ALL && orderItem.type != Staticvalue.TYPE_PARK) {
				errorMsg();
			}
		} while (orderItem.type != Staticvalue.TYPE_ALL && orderItem.type != Staticvalue.TYPE_PARK);
		
		sc.nextLine();
		do {  //�ֹι�ȣ ���ڸ� ù���ڰ� 1~4�� �ƴϸ� �ٽ��Է��ض�. 
			System.out.print("\n�ֹι�ȣ�� �Է��ϼ���. (-����)\n-> ");
			ssn = Integer.parseInt(sc.nextLine().substring(0,7));
			ssn_t = ssn % 10;
			ssn /= 10;
			if (ssn_t < Staticvalue.SSN_T_MIN || ssn_t > Staticvalue.SSN_T_MAX) {
				errorMsg();
			}
		} while (ssn_t < Staticvalue.SSN_T_MIN || ssn_t > Staticvalue.SSN_T_MAX);
		
		do {
			System.out.print("\n��� �ֹ��Ͻðڽ��ϱ�? (�ִ� 10��)\n-> ");
			orderItem.number = sc.nextInt();
			if (orderItem.number > 10) {
				errorMsg();
			}
		} while (orderItem.number > 10);
		
		
		do {
			System.out.print("\n�������� �����ϼ���.\n1. ���� (���� ���� �ڵ�ó��)\n2. �����\n3. ����������\n4. �ް��庴\n5. �ӻ��\n6. ���ڳ�\n-> ");
			orderItem.discount = sc.nextInt();
			if (orderItem.discount < Staticvalue.DISCOUNT_MIN || orderItem.discount > Staticvalue.DISCOUNT_MAX) {
				errorMsg();
			}
		} while (orderItem.discount < Staticvalue.DISCOUNT_MIN || orderItem.discount > Staticvalue.DISCOUNT_MAX);
	}

	//////////������ ���ѵ� �/���/û�ҳ� ���� �ľ� 
	private void getAge() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		orderItem.timeRN = Integer.parseInt(now.format(df));
		
		if (ssn_t == 1 || ssn_t == 2) { //ssn_t�� 1�̳� 2�� 2000�� ���� ���, 
			ssn += 19000000; // ssn �� �ֹι�ȣ ���ڸ�, �ű⿡ ���� ���ڴ� 950522�� 19950522�� �ٲ��� 
		} else { //ssn_t�� 3�̳� 4�� 2000�� ���� ��� 
			ssn += 20000000; // 020123�� 20020123���� �ٲ۴�. 
		}
		
		orderItem.age = (orderItem.timeRN - ssn) / 10000; // ������ 
		
		if (orderItem.age > 12 && orderItem.age < 19) { // û�ҳ� 13~18�� 
			orderItem.age = Staticvalue.AGE_TEEN;
		} else if (orderItem.age > 2 && orderItem.age < 13) { // ��� 3~12��
			orderItem.age = Staticvalue.AGE_CHILD;
		} else if (orderItem.age < 3) { // ���� 0~2�� 
			orderItem.age = Staticvalue.AGE_BABY;
		} else if (orderItem.age > 64) { // ���� 65���̻�
			orderItem.age = Staticvalue.AGE_ELDER;
		} else { // ���ο�� 
			orderItem.age = Staticvalue.AGE_ADULT;
		}
	}
	
	//////////���� ���
	private void getPrice() {
		int[][] priceList = {
				{15000, 47000, 54000, 62000, 47000}, // �ְ� �����̿��, ���ʺ��� ����, ���, û�ҳ�, �, ���� �� 
				{15000, 36000, 43000, 50000, 36000}, // �߰� �����̿��
				{15000, 46000, 52000, 59000, 46000}, // �ְ� ��ũ�̿�� 
				{15000, 35000, 41000, 47000, 35000}  // �߰� ��ũ�̿�� 
		};
		
		orderItem.price = priceList[orderItem.dayOrNight + 2 * orderItem.type - 3][orderItem.age] * orderItem.number; //�ش��ϴ� ������ priceList���� �̾ƿ��� ������ ���� 
		
		if (orderItem.discount == Staticvalue.DISCOUNT_DISABLED || orderItem.discount == Staticvalue.DISCOUNT_VETERAN) { // �����, ���������� ����/��ũ�̿�� 50% ��� 
			orderItem.price = orderItem.price / 2; 
		} else if (orderItem.discount == Staticvalue.DISCOUNT_SOLDIER && orderItem.type == Staticvalue.TYPE_ALL) { // �ް��庴 �����̿�� 49% ��� (����ǥ���� ���������� 50% ���ο� 500�� �߰��� �Ǿ�����.) 
			orderItem.price = orderItem.price / 2 + 500;
		} else if (orderItem.discount == Staticvalue.DISCOUNT_PREGNANT && orderItem.type == Staticvalue.TYPE_ALL) { // �ӻ�� �����̿�� 50% ���
			orderItem.price = orderItem.price / 2;
		} else if (orderItem.discount == Staticvalue.DISCOUNT_MULTICHILDS && orderItem.type == Staticvalue.TYPE_ALL) { // ���ڳ� �����̿�� 30% ��� 
			orderItem.price = (int)(orderItem.price * 0.7);
		}
		sum += orderItem.price; // ���߿� ��� �ο��� �ֹ��� �������� ����ϱ����� �������� 
		System.out.printf("������ %d �� �Դϴ�.\n�����մϴ�.\n\n", orderItem.price); // ���� ��� 
	}
	
	////////// csv���Ͽ� ��� + array�� ����
	private void writeIt() {
		String[] dayOrNightList = {"�ְ�", "�߰�"}; // �������·� ����Ǿ��ִ� ���������� ���ڿ��� �ٲ㼭 ����ϱ����� 
		String[] typeList = {"�����̿��", "��ũ�̿��"};
		String[] ageList = {"����", "���", "û�ҳ�", "����", "����"};
		String[] discountList = {"����", "�����", "����������", "�ް��庴", "�ӻ��", "���ڳ�"};
		
		try {
			fw.write(Integer.toString(orderItem.timeRN) + ',' + dayOrNightList[orderItem.dayOrNight - 1] + ',' + typeList[orderItem.type - 1] + ',' + ageList[orderItem.age] + ',' + orderItem.number + ',' + orderItem.price + ',' + discountList[orderItem.discount - 1] + '\n');
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
			if (orderList.get(i).dayOrNight == 1) {
				System.out.printf("%-4s", "�ְ�");
			} else {
				System.out.printf("%-4s", "�߰�");
			}
			
			if (orderList.get(i).type == 1) {
				System.out.printf("%8s", "�����̿��");
			} else {
				System.out.printf("%8s", "��ũ�̿��");
			}
			
			if (orderList.get(i).age == Staticvalue.AGE_BABY) {
				System.out.printf("%6s", "����");
			} else if (orderList.get(i).age == Staticvalue.AGE_CHILD) {
				System.out.printf("%6s", "���");
			} else if (orderList.get(i).age == Staticvalue.AGE_TEEN) {
				System.out.printf("%6s", "û�ҳ�");
			} else if (orderList.get(i).age == Staticvalue.AGE_ADULT) {
				System.out.printf("%6s", "����");
			} else {
				System.out.printf("%6s", "����");
			}
			
			System.out.printf(" X %-2d %8d��   *������� ", orderList.get(i).number, orderList.get(i).price);
			
			if (orderList.get(i).discount == 1) {
				System.out.printf("%-7s\n", "����");
			} else if (orderList.get(i).discount == 2) {
				System.out.printf("%-7s\n", "�����");
			} else if (orderList.get(i).discount == 3) {
				System.out.printf("%-7s\n", "����������");
			} else if (orderList.get(i).discount == 4) {
				System.out.printf("%-7s\n", "�ް��庴");
			} else if (orderList.get(i).discount == 5) {
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
	public static void main(String[] args) throws IOException {
		LotteWorld lw = new LotteWorld();
		int anotherOne, anotherOrder = 0;
		
		do { // ���ο��ֹ��� �޴� �ݺ��� 
			fw = new FileWriter(file);
			fw.write("��¥,�ð���,����,���ɱ���,����,����,������\n");
			
			do {
				lw.printMenu();
				lw.getAge();
				lw.getPrice();
				lw.writeIt();
				
				System.out.print("��� �߱� �Ͻðڽ��ϱ�?\n1. Ƽ�� �߱�\n2. ����\n->");
				anotherOne = sc.nextInt();
			} while (anotherOne != 2); // �ϳ��� �ֹ� �ȿ��� �ٸ� ����� �޴� �ݺ��� 
			
			lw.printIt();
			
			System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		fw.close();
	}

}