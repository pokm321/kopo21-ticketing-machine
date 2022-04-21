package ticketing;

import java.util.Scanner;

public class AskStuff {
	Scanner sc = new Scanner(System.in);
	
	private static void errorMsg() {
		System.out.println("��Ȯ�� ���� �Է����ּ���.");
	}

	//////////�޴� ��� 
	protected void printMenu(OrderData orderItem) {
		do {
			System.out.print("�ð��븦 �����ϼ���.\n1. �ְ���\n2. �߰��� (4�� ���� �����)\n-> ");
			orderItem.setDayOrNight(sc.nextInt());
			if (orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_DAY && orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_NIGHT) {
				errorMsg();
			}
		} while (orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_DAY && orderItem.getDayOrNight() != StaticValue.DAYORNIGHT_NIGHT);
		
		do {
			System.out.print("\n������ �����ϼ���.\n1. �����̿�� (�Ե����� + �μӹڹ���)\n2. ��ũ�̿�� (�Ե�����)\n-> ");
			orderItem.setType(sc.nextInt());
			if (orderItem.getType() != StaticValue.TYPE_ALL && orderItem.getType() != StaticValue.TYPE_PARK) {
				errorMsg();
			}
		} while (orderItem.getType() != StaticValue.TYPE_ALL && orderItem.getType() != StaticValue.TYPE_PARK);
		
		sc.nextLine();
		do {  //�ֹι�ȣ ���ڸ� ù���ڰ� 1~4�� �ƴϸ� �ٽ��Է��ض�. 
			System.out.print("\n�ֹι�ȣ�� �Է��ϼ���. (-����)\n-> ");
			orderItem.setSsn(Integer.parseInt(sc.nextLine().substring(0,7)));
			orderItem.setSsn_t(orderItem.getSsn() % 10);
			orderItem.setSsn(orderItem.getSsn() / 10);
			if (orderItem.getSsn_t() < StaticValue.SSN_T_MIN || orderItem.getSsn_t() > StaticValue.SSN_T_MAX) {
				errorMsg();
			}
		} while (orderItem.getSsn_t() < StaticValue.SSN_T_MIN || orderItem.getSsn_t() > StaticValue.SSN_T_MAX);
		
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
			if (orderItem.getDiscount() < StaticValue.DISCOUNT_MIN || orderItem.getDiscount() > StaticValue.DISCOUNT_MAX) {
				errorMsg();
			}
		} while (orderItem.getDiscount() < StaticValue.DISCOUNT_MIN || orderItem.getDiscount() > StaticValue.DISCOUNT_MAX);
	}
	
	protected void askRepeat1(OrderData orderItem) {
		System.out.print("��� �߱� �Ͻðڽ��ϱ�?\n1. Ƽ�� �߱�\n2. ����\n->");
		orderItem.setAnotherOne(sc.nextInt());
	}
	
	protected void askRepeat2(OrderData orderItem) {
		System.out.print("\n\n��� ���� (1: ���ο� �ֹ�, 2: ���α׷� ����) : ");
		orderItem.setAnotherOrder(sc.nextInt());
	}
}
