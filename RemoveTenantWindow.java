///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: RemoveTenantWindow
//// Description:
////  A popup window which gives the user
////  the ability to input exactly which tenant
////  to remove - by room number, or by name.
////  this class only validates the input
////  and the input is later on used in StudentHotelGUI
////  in an async thread
//////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;



public class RemoveTenantWindow extends JFrame {
	private int roomNum=-1;
	private String tenantName="";
	private boolean done=false;
	private int optionChosen=-1;

	private int showWhat=0;
	private Color bgColor = new Color(236,233,216);

	private JLabel instructionstext= new JLabel("Select by what you wish to remove a tenant and enter the information.",SwingConstants.CENTER);
	private JTextField RoomNum = new JTextField(5);
	private JTextField TenantName = new JTextField(30);
	private JPanel infoarea = new JPanel();
	private JPanel buttonarea = new JPanel();
	private CoolPanel radiopanel = new CoolPanel("Remove by:");

	private CoolButton RemoveButton = new CoolButton("Remove Tenant");
	private CoolButton CancelButton = new CoolButton("Cancel");

    ButtonGroup group = new ButtonGroup();

	void setTextFieldProperTitle(JTextField l, String tname){
		TitledBorder t =  new TitledBorder(tname);
		t.setBorder(BorderFactory.createLineBorder(new Color(128,128,129)));
		l.setBorder(t);
	}

	public RemoveTenantWindow(){


		setTitle("Remove a tenant");


		setTextFieldProperTitle(RoomNum, "Room #");
		setTextFieldProperTitle(TenantName, "Name(s)");


		infoarea.add(RoomNum);
		infoarea.add(TenantName);
		TenantName.setVisible(false);
		getContentPane().setBackground(bgColor);


		final JRadioButton byRoomNum = new JRadioButton("Room Number");
		final JRadioButton byName = new JRadioButton("Tenant Name");
		byRoomNum.setBackground(bgColor);
		byName.setBackground(bgColor);

		byRoomNum.setSelected(true);
		byRoomNum.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(showWhat == 1){
					showWhat=0;
					RoomNum.setVisible(true);
					TenantName.setVisible(false);
					byName.setSelected(false);
					invalidate();
					revalidate();
				}
			}
		});

		byName.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(showWhat==0){
					showWhat=1;
					RoomNum.setVisible(false);
					TenantName.setVisible(true);
					byRoomNum.setSelected(false);
					invalidate();
					revalidate();
				}
			}
		});

		radiopanel.add(byRoomNum);
		radiopanel.add(byName);
		//radiopanel.add(group);

		RemoveButton.addActionListener(new ActionListener(){
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

		buttonarea.add(RemoveButton);
		buttonarea.add(CancelButton);
		buttonarea.setBackground(bgColor);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				optionChosen=-1;
				done=true;
				//dispose();
			}
		});

		setLayout(new GridLayout(4,1));

		infoarea.setBackground(bgColor);
		add(instructionstext);
		add(radiopanel);
		add(infoarea);
		add(buttonarea);

		setSize(480,240);
		//setResizable(false);

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
		if(showWhat==0){ // then we verify and return room number
			String roomnumstr=RoomNum.getText();
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
		}

		if(showWhat==1){ // then we verify and return name
			String tenantname=TenantName.getText();
			if(tenantname.equals("")){
				errorHappened(1);
				return;
			}
			tenantName=tenantname;
		}
	}

	public Object[] getResults(){
		Object[] out= new Object[2];
		out[0]= (Object)new Integer(showWhat);
		if(showWhat==0){
			out[1]= (Object)new Integer(roomNum);
		}
		if(showWhat==1){
			out[1]= (Object)tenantName;
		}
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