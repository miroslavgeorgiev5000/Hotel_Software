///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: AddPaymentWindow
//// Description:
////  Creates a popup window which can be used
////  to get payment information input from the user.
////  This includes - room number, month and amount of money.
//////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;



public class AddPaymentWindow extends JFrame {
	private int roomNum=-1;
	private int monthNum=-1;
	private double moneyPaid=-1;

	private boolean done=false;
	private int optionChosen=-1;


	private JLabel instructionstext= new JLabel("Please enter the room, month and amount paid below.",SwingConstants.CENTER);
	private JTextField RoomNum = new JTextField(5);
	private JTextField MoneyNum = new JTextField(5);

	private String[] months = new String[] {"January","February","March","April","May","June","July","August","September","October","November","December" };

	private JComboBox<String> MonthSelect = new JComboBox<>(months);

	private JPanel infoarea = new JPanel();
	private JPanel buttonarea = new JPanel();
	private CoolButton AddButton = new CoolButton("Add Payment");
	private CoolButton CancelButton = new CoolButton("Cancel");

	void setTextFieldProperTitle(JTextField l, String tname){
		TitledBorder t =  new TitledBorder(tname);
		t.setBorder(BorderFactory.createLineBorder(new Color(128,128,129)));
		l.setBorder(t);
	}
	void setTextFieldProperTitle(JComboBox l, String tname){
		TitledBorder t =  new TitledBorder(tname);
		t.setBorder(BorderFactory.createLineBorder(new Color(128,128,129)));
		l.setBorder(t);
	}
	public AddPaymentWindow(){


		setTitle("Add a payment");


		setTextFieldProperTitle(RoomNum, "Room #");
		setTextFieldProperTitle(MoneyNum, "Amount");
		setTextFieldProperTitle(MonthSelect, "Month");


		infoarea.add(RoomNum);
		infoarea.add(MonthSelect);
		infoarea.add(MoneyNum);

		AddButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				optionChosen=0;
				done=true;
				dispose();
			}
		});
		CancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				optionChosen=1;
				done=true;
				dispose();
			}
		});

		buttonarea.add(AddButton);
		buttonarea.add(CancelButton);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				optionChosen=-1;
				done=true;
				//dispose();
			}
		});

		setLayout(new GridLayout(3,1));

		add(instructionstext);
		add(infoarea);
		add(buttonarea);

		setSize(480,180);
		setResizable(false);

		setLocationRelativeTo(null);
		setVisible(true);
	}

    public int getChoice(){
		while(!done){
			try{
				Thread.currentThread().sleep(33);
			}catch(Exception e){e.printStackTrace();}
		}

		processEnteredData();

		return optionChosen;
	}

	private void processEnteredData(){
		String moneystr=MoneyNum.getText();
		String roomnumstr=RoomNum.getText();
		if(moneystr.equals("")){
			errorHappened(1);
			return;
		}
		if(roomnumstr.equals("")){
			errorHappened(2);
			return;
		}

		roomnumstr = roomnumstr.replace("#","");

		// make it easier for the user
		moneystr = moneystr.replace("$","");
		moneystr = moneystr.replace("£","");


		int res;
		try{
			res=Integer.parseInt(roomnumstr);
		}catch(Exception e){
			errorHappened(3);
			return;
		}

		Double res2;
		try{
			res2=Double.parseDouble(moneystr);
		}catch(Exception e){
			errorHappened(4);
			return;
		}

		roomNum = res;
		moneyPaid=res2;
		monthNum=(int)MonthSelect.getSelectedIndex();
//		System.out.println(monthNum);

	}

	public Object[] getResults(){
		Object[] out= new Object[3];
		out[0]= (Object)new Integer(roomNum);
		out[1]= (Object)new Integer(monthNum);
		out[2]= (Object)new Double(moneyPaid);
		return out;
	}

	void errorHappened(int num){
		if(optionChosen==0){
			optionChosen=-1;
			switch(num){
				case 1:
					new ConfirmationBox("Err 1","You must enter a value for the money paid.","Ok");
				break;
				case 2:
					new ConfirmationBox("Err 2","You must enter a value for the room number.","Ok");
				break;
				case 3:
					new ConfirmationBox("Err 3","Room number can only contain numbers.","Ok");
				break;
				case 4:
					new ConfirmationBox("Err 4","Amount of money can only be a floating point number.","Ok");
				break;
			}
		}
	}
}