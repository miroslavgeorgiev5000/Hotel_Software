///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: AddTenantWindow
//// Description:
////  A popup window designed to get input from
////  the user which can be used to add a new tenant.
////  expects the user to enter appropriate data
////  (i.e. int for the room, string for the name)
//// ....basically validates whatev the user entered.
//////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;



public class AddTenantWindow extends JFrame {
	private int roomNum=-1;
	private String tenantName="";
	private boolean done=false;
	private int optionChosen=-1;


	private JLabel instructionstext= new JLabel("Please enter the tenant's name(s) and room number.",SwingConstants.CENTER);
	private JTextField RoomNum = new JTextField(5);
	private JTextField TenantName = new JTextField(30);
	private JPanel infoarea = new JPanel();
	private JPanel buttonarea = new JPanel();
	private CoolButton AddButton = new CoolButton("Add Tenant");
	private CoolButton CancelButton = new CoolButton("Cancel");

	void setTextFieldProperTitle(JTextField l, String tname){
		TitledBorder t =  new TitledBorder(tname);
		t.setBorder(BorderFactory.createLineBorder(new Color(128,128,129)));
		l.setBorder(t);
	}
	public AddTenantWindow(){


		setTitle("Add a tenant");


		setTextFieldProperTitle(RoomNum, "Room #");
		setTextFieldProperTitle(TenantName, "Name(s)");


		infoarea.add(RoomNum);
		infoarea.add(TenantName);

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
		String tenantname=TenantName.getText();
		String roomnumstr=RoomNum.getText();
		if(tenantname.equals("")){
			errorHappened(1);
			return;
		}
		if(roomnumstr.equals("")){
			errorHappened(2);
			return;
		}

		roomnumstr = roomnumstr.replace("#","");
		int res;
		try{
			res=Integer.parseInt(roomnumstr);
		}catch(Exception e){
			errorHappened(3);
			return;
		}

		roomNum = res;
		tenantName=tenantname;

	}

	public Object[] getResults(){
		Object[] out= new Object[2];
		out[0]= (Object)new Integer(roomNum);
		out[1]= (Object)tenantName;
		return out;
	}

	void errorHappened(int num){
		if(optionChosen==0){
			optionChosen=-1;
			switch(num){
				case 1:
					new ConfirmationBox("Err 1","You must enter a value for the tennant name.","Ok");
				break;
				case 2:
					new ConfirmationBox("Err 2","You must enter a value for the room number.","Ok");
				break;
				case 3:
					new ConfirmationBox("Err 3","Room number can only contain numbers.","Ok");
				break;
			}
		}
	}
}