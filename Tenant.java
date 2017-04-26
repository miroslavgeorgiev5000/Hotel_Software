///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: Tenant
//// Description:
////  Generalized tenant structure
////  holds room number, name and an arraylist of payments.
////  the rest is alot of set/get functions
//////////////////////////////////////////


import java.util.*;

public class Tenant{
	private int roomnum=-1;
	private String name="";
	private ArrayList<Payment> payments = new ArrayList<Payment>();

	public Tenant(int r, String n){
		roomnum=r;
		name=n;
	}

	public void setRoom(int r){
		roomnum=r;
	}
	public void setName(String n){
		name=n;
	}
	public int getRoom(){
		return roomnum;
	}
	public String getName(){
		return name;
	}

	public void addPayment(Payment p) {
		payments.add(p);
	}

	public ArrayList<Payment> getPayments() {
		return payments;
	}

	public String toString(){
		String out = roomnum+"\t\t"+name;
		return out;
	}
}