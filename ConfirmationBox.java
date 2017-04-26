///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: ConfirmationBox
//// Description:
////  An easy to use popup box.
////  Used on many ocasions to get yes/no
////  input from the user or simply to
////  alert the user of what's happening.
//////////////////////////////////////////


import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;

public class ConfirmationBox extends JFrame{
	CoolButton[] btnarr;
	int optionChosen=-1;
	boolean done=false;
	public ConfirmationBox(String title, String content, String buttons){
		String[]btnstxt=buttons.split(",");
		btnarr = new CoolButton[btnstxt.length];
		for( int k=0; k<btnstxt.length; k++){
			btnarr[k] = new CoolButton(btnstxt[k]);
			btnarr[k].addActionListener(new ActionListener(){
				int Id;
				public ActionListener setId(int i){
					Id=i;
					return this;
				}
				public void actionPerformed(ActionEvent e){
					optionChosen=Id;
	        		done=true;
					dispose();
				}
			}.setId(k));
		}
		this.addWindowListener(new WindowAdapter() {
			        public void windowClosing(WindowEvent e) {
			        	optionChosen=-1;
			        	done=true;
						//dispose();
			        }

    	});

    	//setPreferredSize(new Dimension(480,140));
    	setSize(480,140);
    	setResizable(false);
		setLayout(new FlowLayout());
    	JPanel buttonContainer = new JPanel();
    	buttonContainer.setLayout(new FlowLayout());
    	JLabel label_content= new JLabel(content,SwingConstants.CENTER);
		label_content.setLayout(new FlowLayout());
		label_content.setPreferredSize(new Dimension(480,120));

    	for(int k=0;k<btnarr.length;k++){
    		buttonContainer.add(btnarr[k]);
		}

    	setLayout(new GridLayout(2,1));
    	add(label_content);
    	add(buttonContainer);

		setTitle(title);

		setLocationRelativeTo(null);
    	setVisible(true);

	}

	//public void setSize(int x,int y){

	//}

    public int getChoice(){
		while(!done){
			try{
				Thread.currentThread().sleep(33);
			}catch(Exception e){e.printStackTrace();}
		}
		return optionChosen;
	}

}