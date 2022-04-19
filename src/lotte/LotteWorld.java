package lotte;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
	static BufferedReader br;
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
			if (orderItem.dayOrNight != 1 && orderItem.dayOrNight != 2) {
				errorMsg();
			}
		} while (orderItem.dayOrNight != 1 && orderItem.dayOrNight != 2);
		
		do {
			System.out.print("\n������ �����ϼ���.\n1. �����̿�� (�Ե����� + �μӹڹ���)\n2. ��ũ�̿�� (�Ե�����)\n-> ");
			orderItem.type = sc.nextInt();
			if (orderItem.type != 1 && orderItem.type != 2) {
				errorMsg();
			}
		} while (orderItem.type != 1 && orderItem.type != 2);
		
		sc.nextLine();
		do {  //�ֹι�ȣ ���ڸ� ù���ڰ� 1~4�� �ƴϸ� �ٽ��Է��ض�. 
			System.out.print("\n�ֹι�ȣ�� �Է��ϼ���. (-����)\n-> ");
			ssn = Integer.parseInt(sc.nextLine().substring(0,7));
			ssn_t = ssn % 10;
			ssn /= 10;
			if (ssn_t < 1 || ssn_t > 4) {
				errorMsg();
			}
		} while (ssn_t < 1 || ssn_t > 4);
		
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
			if (orderItem.discount < 1 || orderItem.discount > 6) {
				errorMsg();
			}
		} while (orderItem.discount < 1 || orderItem.discount > 6);
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
			{62000, 54000, 47000, 15000, 47000}, // �ְ� �����̿�� , ���ʺ��� �, û�ҳ�, ���, ����, ���� �� 
			{50000, 43000, 36000, 15000, 36000}, // �߰� �����̿��
			{59000, 52000, 46000, 15000, 46000}, // �ְ� ��ũ�̿�� 
			{47000, 41000, 35000, 15000, 35000}  // �߰� ��ũ�̿�� 
		};
		
		orderItem.price = priceList[orderItem.dayOrNight + 2 * orderItem.type - 3][orderItem.age] * orderItem.number; //�ش��ϴ� ������ priceList���� �̾ƿ��� ������ ���� 
		
		if (orderItem.discount == 2 || orderItem.discount == 3) { // �����, ���������� ����/��ũ�̿�� 50% ��� 
			orderItem.price = orderItem.price / 2; 
		} else if (orderItem.discount == 4 && orderItem.type == 1) { // �ް��庴 �����̿�� 49% ��� (����ǥ���� ���������� 50% ���ο� 500�� �߰��� �Ǿ�����.) 
			orderItem.price = orderItem.price / 2 + 500;
		} else if (orderItem.discount == 5 && orderItem.type == 1) { // �ӻ�� �����̿�� 50% ���
			orderItem.price = orderItem.price / 2;
		} else if (orderItem.discount == 6 && orderItem.type == 1) { // ���ڳ� �����̿�� 30% ��� 
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
	
	private void printIt() {
		String line;
		System.out.print("========================== �Ե����� ==========================\n");
		int i = 0;
		for (i = 0; i < orderList.size(); i++) {
			if (orderList.get(i).dayOrNight == 1) {
				System.out.printf("%-6s", "�ְ�");
			} else {
				System.out.printf("%-6s", "�߰�");
			}
			
			if (orderList.get(i).type == 1) {
				System.out.printf("%9s", "�����̿��");
			} else {
				System.out.printf("%9s", "��ũ�̿��");
			}
			
			if (orderList.get(i).age == Staticvalue.AGE_BABY) {
				System.out.printf("%7s", "����");
			} else if (orderList.get(i).age == Staticvalue.AGE_CHILD) {
				System.out.printf("%7s", "���");
			} else if (orderList.get(i).age == Staticvalue.AGE_TEEN) {
				System.out.printf("%7s", "û�ҳ�");
			} else if (orderList.get(i).age == Staticvalue.AGE_ADULT) {
				System.out.printf("%7s", "����");
			} else {
				System.out.printf("%7s", "����");
			}
			
			System.out.printf(" X %-3d %8d��   *������� ", orderList.get(i).number, orderList.get(i).price);
			
			if (orderList.get(i).discount == 1) {
				System.out.printf("%7s\n", "����");
			} else if (orderList.get(i).discount == 2) {
				System.out.printf("%7s\n", "�����");
			} else if (orderList.get(i).discount == 3) {
				System.out.printf("%7s\n", "����������");
			} else if (orderList.get(i).discount == 4) {
				System.out.printf("%7s\n", "�ް��庴");
			} else if (orderList.get(i).discount == 5) {
				System.out.printf("%7s\n", "�ӻ��");
			} else {
				System.out.printf("%7s\n", "���ڳ�");
			}
			
		}
		System.out.printf("\n����� �Ѿ��� %d�� �Դϴ�.\n", sum);
		System.out.print("============================================================");
		
		
	}

	private void inputDataInArray() {
		orderList.add(orderItem);
	}
	
	
	////////// �����Լ�
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new FileReader(fileName));
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
			
			System.out.print("\nƼ�� �߱��� �����մϴ�. �����մϴ�.\n\n");
			fw.flush();
			lw.printIt();
			
			System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
			anotherOrder = sc.nextInt();
		} while (anotherOrder != 2);
		fw.close();
		br.close();
	}

}