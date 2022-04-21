package lotte;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculateStuff {
	
	//////////������ ���ѵ� �/���/û�ҳ� ���� �ľ� 
	protected void calcAge(OrderData orderItem) {
		LocalDate now = LocalDate.now();
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		orderItem.setTimeRN(Integer.parseInt(now.format(df)));
		
		if (orderItem.getSsn_t() == 1 || orderItem.getSsn_t() == 2) { //ssn_t�� 1�̳� 2�� 2000�� ���� ���, 
			orderItem.setSsn(orderItem.getSsn() + 19000000); // ssn �� �ֹι�ȣ ���ڸ�, �ű⿡ ���� ���ڴ� 950522�� 19950522�� �ٲ��� 
		} else { //ssn_t�� 3�̳� 4�� 2000�� ���� ��� 
			orderItem.setSsn(orderItem.getSsn() + 20000000); // 020123�� 20020123���� �ٲ۴�. 
		}
		
		orderItem.setAge((orderItem.getTimeRN() - orderItem.getSsn()) / 10000); // ������ 
		
		if (orderItem.getAge() > 12 && orderItem.getAge() < 19) { // û�ҳ� 13~18�� 
			orderItem.setAge(StaticValue.AGE_TEEN);
		} else if (orderItem.getAge() > 2 && orderItem.getAge() < 13) { // ��� 3~12��
			orderItem.setAge(StaticValue.AGE_CHILD);
		} else if (orderItem.getAge() < 3) { // ���� 0~2�� 
			orderItem.setAge(StaticValue.AGE_BABY);
		} else if (orderItem.getAge() > 64) { // ���� 65���̻�
			orderItem.setAge(StaticValue.AGE_ELDER);
		} else { // ���ο�� 
			orderItem.setAge(StaticValue.AGE_ADULT);
		}
	}
	
	//////////���� ���
	protected void calcPrice(OrderData orderItem) {
		int[][] priceList = {
				{15000, 47000, 54000, 62000, 47000}, // �ְ� �����̿��, ���ʺ��� ����, ���, û�ҳ�, �, ���� �� 
				{15000, 36000, 43000, 50000, 36000}, // �߰� �����̿��
				{15000, 46000, 52000, 59000, 46000}, // �ְ� ��ũ�̿�� 
				{15000, 35000, 41000, 47000, 35000}  // �߰� ��ũ�̿�� 
		};
		
		orderItem.setPrice(priceList[orderItem.getDayOrNight() + 2 * orderItem.getType() - 3][orderItem.getAge()] * orderItem.getNumber()); //�ش��ϴ� ������ priceList���� �̾ƿ��� ������ ���� 
		
		if (orderItem.getDiscount() == StaticValue.DISCOUNT_DISABLED || orderItem.getDiscount() == StaticValue.DISCOUNT_VETERAN) { // �����, ���������� ����/��ũ�̿�� 50% ��� 
			orderItem.setPrice(orderItem.getPrice() / 2); 
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_SOLDIER && orderItem.getType() == StaticValue.TYPE_ALL) { // �ް��庴 �����̿�� 49% ��� (����ǥ���� ���������� 50% ���ο� 500�� �߰��� �Ǿ�����.) 
			orderItem.setPrice(orderItem.getPrice() / 2 + 500);
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_PREGNANT && orderItem.getType() == StaticValue.TYPE_ALL) { // �ӻ�� �����̿�� 50% ���
			orderItem.setPrice(orderItem.getPrice() / 2);
		} else if (orderItem.getDiscount() == StaticValue.DISCOUNT_MULTICHILDS && orderItem.getType() == StaticValue.TYPE_ALL) { // ���ڳ� �����̿�� 30% ��� 
			orderItem.setPrice((int)(orderItem.getPrice() * 0.7));
		}
		System.out.printf("������ %d �� �Դϴ�.\n�����մϴ�.\n\n", orderItem.getPrice()); // ���� ��� 
	}
}
