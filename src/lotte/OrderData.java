package lotte;

public class OrderData {
	private int dayOrNight; // 권종
	private int type; // 이용시간
	private int number; // 주민번호
	private int discount; // 주문갯수
	private int age; // 나이 (연령타입)
	private int price; // 가격
	private int timeRN; // 현재날짜
	private int ssn;
	private int ssn_t;
	
	// getters and setters
	public int getDayOrNight() {
		return dayOrNight;
	}
	public void setDayOrNight(int dayOrNight) {
		this.dayOrNight = dayOrNight;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}	
	public int getTimeRN() {
		return timeRN;
	}
	public void setTimeRN(int timeRN) {
		this.timeRN = timeRN;
	}
	public int getSsn() {
		return ssn;
	}
	public void setSsn(int ssn) {
		this.ssn = ssn;
	}
	public int getSsn_t() {
		return ssn_t;
	}
	public void setSsn_t(int ssn_t) {
		this.ssn_t = ssn_t;
	}

}
