package april_18;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LotteWorld {
	static int dayOrNight, type, ssn, ssn_t, number, discount, age, timeRN, price, sum;
	static String fileName= "C:\\Users\\user\\Documents\\LotteReport.csv";
	static File file = new File(fileName);
	static FileWriter fw;
	static BufferedReader br;
	static Scanner sc = new Scanner(System.in);
	
	//////////�޴� ��� 
	private void printMenu() {
		
		
		System.out.print("�ð��븦 �����ϼ���.\n1. �ְ���\n2. �߰��� (4�� ���� �����)\n-> ");
		dayOrNight = sc.nextInt();
		System.out.print("\n������ �����ϼ���.\n1. �����̿�� (�Ե����� + �μӹڹ���)\n2. ��ũ�̿�� (�Ե�����)\n-> ");
		type = sc.nextInt();
		sc.nextLine();
		ssn_t = 0;
		while (ssn_t < 1 || ssn_t > 4) {  //�ֹι�ȣ ���ڸ� ù���ڰ� 1~4�� �ƴϸ� �ٽ��Է��ض�. 
			System.out.print("\n�ֹι�ȣ�� �Է��ϼ���. (-����)\n-> ");
			ssn = Integer.parseInt(sc.nextLine().substring(0,7));
			ssn_t = ssn % 10;
			ssn /= 10;
			if (ssn_t < 1 || ssn_t > 4) {
				System.out.println("��Ȯ�� �ֹι�ȣ�� �Է����ּ���.");
			}
		}
		System.out.print("\n��� �ֹ��Ͻðڽ��ϱ�? (�ִ� 10��)\n-> ");
		number = sc.nextInt();
		System.out.print("\n�������� �����ϼ���.\n1. ���� (���� ���� �ڵ�ó��)\n2. �����\n3. ����������\n4. �ް��庴\n5. �ӻ��\n6. ���ڳ�\n-> ");
		discount = sc.nextInt();
	}

	//////////������ ���ѵ� �/���/û�ҳ� ���� �ľ� 
	private int getAge() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		timeRN = Integer.parseInt(now.format(df));
		
		if (ssn_t == 1 || ssn_t == 2) { //ssn_t�� 1�̳� 2�� 2000�� ���� ���, 
			ssn += 19000000; // ssn �� �ֹι�ȣ ���ڸ�, �ű⿡ ���� ���ڴ� 950522�� 19950522�� �ٲ��� 
		} else { //ssn_t�� 3�̳� 4�� 2000�� ���� ��� 
			ssn += 20000000; // 020123�� 20020123���� �ٲ۴�. 
		}
		
		int age = (timeRN - ssn) / 10000; // ������ 
		
		if (age > 12 && age < 19) { // û�ҳ� 13~18�� 
			age = 1;
		} else if (age > 2 && age < 13) { // ��� 3~12��
			age = 2;
		} else if (age < 3) { // ���� 0~2�� 
			age = 3;
		} else if (age > 64) { // ���� 65���̻�
			age = 4;
		} else { // ���ο�� 
			age = 0;
		}
		return age;
	}

	
	//////////���� ���
	int getPrice() {
		int[][] priceList = {
			{62000, 54000, 47000, 15000, 47000}, // �ְ� �����̿�� , ���ʺ��� �, û�ҳ�, ���, ����, ���� �� 
			{50000, 43000, 36000, 15000, 36000}, // �߰� �����̿��
			{59000, 52000, 46000, 15000, 46000}, // �ְ� ��ũ�̿�� 
			{47000, 41000, 35000, 15000, 35000}  // �߰� ��ũ�̿�� 
		};
		
		int price = priceList[dayOrNight + 2 * type - 3][age] * number; //�ش��ϴ� ������ priceList���� �̾ƿ��� ������ ���� 
		
		if (discount == 2 || discount == 3) { // �����, ���������� ����/��ũ�̿�� 50% ��� 
			price = price / 2; 
		} else if (discount == 4 && type == 1) { // �ް��庴 �����̿�� 49% ��� (����ǥ���� ���������� 50% ���ο� 500�� �߰��� �Ǿ�����.) 
			price = price / 2 + 500;
		} else if (discount == 5 && type == 1) { // �ӻ�� �����̿�� 50% ���
			price = price / 2;
		} else if (discount == 6 && type == 1) { // ���ڳ� �����̿�� 30% ��� 
			price = (int)(price * 0.7);
		}
		sum += price; // ���߿� ��� �ο��� �ֹ��� �������� ����ϱ����� �������� 
		return price;
	}
	
	//////////�ϳ��� �ֹ��� ���������� csv���Ͽ� ���پ� ��� 
	void writeIt() {
		String[] dayOrNightList = {"�ְ�", "�߰�"}; // �������·� ����Ǿ��ִ� ���������� ���ڿ��� �ٲ㼭 ����ϱ����� 
		String[] typeList = {"�����̿��", "��ũ�̿��"};
		String[] ageList = {"����", "û�ҳ�", "���", "����", "����"};
		String[] discountList = {"����", "�����", "����������", "�ް��庴", "�ӻ��", "���ڳ�"};
		
		try {
			fw.write(Integer.toString(timeRN) + ',' + dayOrNightList[dayOrNight - 1] + ',' + typeList[type - 1] + ',' + ageList[age] + ',' + number + ',' + price + ',' + discountList[discount - 1] + '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	////////// ��ϵ� csv���Ͽ��� �����͸� �ҷ��ͼ� �� ���â�� ���
	void readIt(){
		String line;
		System.out.print("========================== �Ե����� ==========================\n");
	
		try {
			br.readLine();
		
			while ((line = br.readLine()) != null) { // ���̻� ������������ 
				String[] data = line.split(",");
				System.out.printf("%-5s %8s %6s X %-3s %8s��   *������� %-8s\n", data[1], data[2], data[3], data[4], data[5], data[6]);		
			}
			System.out.printf("\n����� �Ѿ��� %d�� �Դϴ�.\n", sum);
			System.out.print("============================================================");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	////////// �����Լ�
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new FileReader(fileName));
		LotteWorld lw = new LotteWorld();
		
		int anotherOne, anotherOrder = 0;
		
		while (anotherOrder != 2) { // ���ο��ֹ��� �޴� �ݺ��� 
			fw = new FileWriter(file);
			fw.write("��¥,�ð���,����,���ɱ���,����,����,������\n");
			anotherOne = 0;
			while (anotherOne != 2) { // �ϳ��� �ֹ� �ȿ��� �ٸ� ����� �޴� �ݺ��� 
				lw.printMenu();
				age = lw.getAge();
				price = lw.getPrice();
				System.out.printf("������ %d �� �Դϴ�.\n�����մϴ�.\n\n", price); // ���� ��� 
				lw.writeIt();
				System.out.print("��� �߱� �Ͻðڽ��ϱ�?\n1. Ƽ�� �߱�\n2. ����\n->");
				anotherOne = sc.nextInt();
			}
			System.out.print("\nƼ�� �߱��� �����մϴ�. �����մϴ�.\n\n");
			fw.flush();
			lw.readIt();
			System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
			anotherOrder = sc.nextInt();
		}
		fw.close();
		br.close();
	}

}


