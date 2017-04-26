///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: SearchWindow
//// Description:
////  Creates a popup GUI window which can be used
////  to get a string input from the user.
////
//////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;



public class SearchWindow extends JFrame {

	private String tenantName="";
	private boolean done=false;
	private int optionChosen=-1;


	private JLabel instructionstext= new JLabel("Please enter the tenant's name(s) and room number.",SwingConstants.CENTER);
	private JTextField TenantName = new JTextField(30);
	private JPanel infoarea = new JPanel();
	private JPanel buttonarea = new JPanel();
	private CoolButton SearchButton = new CoolButton("Search");
	private CoolButton CancelButton = new CoolButton("Cancel");

	void setTextFieldProperTitle(JTextField l, String tname){
		TitledBorder t =  new TitledBorder(tname);
		t.setBorder(BorderFactory.createLineBorder(new Color(128,128,129)));
		l.setBorder(t);
	}
	public SearchWindow(){


		setTitle("Search for a tenant");


		setTextFieldProperTitle(TenantName, "Name(s)");


		infoarea.add(TenantName);

		SearchButton.addActionListener(new ActionListener(){
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

		buttonarea.add(SearchButton);
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

		if(tenantname.equals("")){
			errorHappened(1);
			return;
		}

		tenantName=tenantname;
	}

	public Object[] getResults(){
		Object[] out= new Object[1];
		out[0]= (Object)tenantName;
		return out;
	}

	void errorHappened(int num){
		if(optionChosen==0){
			optionChosen=-1;
			switch(num){
				case 1:
					new ConfirmationBox("Err 1","You must enter a value for the tenant name.","Ok");
				break;
			}
		}
	}
}