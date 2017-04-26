///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: Payment
//// Description:
////  a simple structure of a payment
////  containing month of payment and money paid.
////
//////////////////////////////////////////


public class Payment {
	private int month; // 0 to 11 ( 11 being december)
	private double money;

	public Payment(int mth, double mney) {
		month = mth;
		money = mney;
	}

	public int getMonth() {
		return month;
	}

	public double getMoney() {
		return money;
	}

	public String toString() {
		return month+": "+money;
	}

}