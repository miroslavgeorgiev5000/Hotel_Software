///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: StudentHotelGUI
//// Description:
////  the main class for the software.
////  the constructor contains most of the definitions
////  and events for searching, removing, adding, saving
////  and loading the tenants and payments.
////  if you keep track at the button names
////  for "button_" prefixed buttons and where
////  they are used, the rest becomes straightforward
//////////////////////////////////////////



import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.text.*;
import java.util.*;

public class StudentHotelGUI extends JFrame implements ActionListener {

	private Color bgcolor = new Color(236,233,216);
	ArrayList<Tenant> tenants = new ArrayList<Tenant>();

	private CoolButton button_display = new CoolButton("Display Tenants");
	private CoolButton button_search = new CoolButton("Search");
	private CoolButton button_addtenant = new CoolButton("Add Tenant");
	private CoolButton button_removetenant = new CoolButton("Remove Tenant");
	private CoolButton button_makepayment = new CoolButton("Make a Paymet");
	private CoolButton button_listpayments = new CoolButton("List Payments");
	private CoolButton button_saveandquit = new CoolButton("Save and Quit");
	private CoolButton button_quitwithoutsaving = new CoolButton("Quit Without Saving");
	private JTextArea viewtext = new JTextArea(24,35);
	private JScrollPane scroll = new JScrollPane(viewtext);
	private CoolPanel p1 = new CoolPanel("View");
	private CoolPanel p2 = new CoolPanel("Control");
	private String[] months = new String[] {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec" };


public void updatePayments(){
	String s = "";
	for( Tenant t : tenants ){
		s += "----------------------------------------\n";
		s += "Payments for room "+t.getRoom()+" - "+t.getName()+"\n";
		s += "Month\t\tAmount\n";
		for( Payment p : t.getPayments() ){
			s += months[p.getMonth()]+"\t\t£"+p.getMoney()+"\n";
		}
	}
	viewtext.setText(s);
}

	public static void centerWin(Window frame) {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.setLocation(x, y);
	}
	public StudentHotelGUI(){
		setTitle("STUDENT HOTEL SOFTWARE");
		setLayout(new FlowLayout());
		setSize(640,480);
		getContentPane().setBackground(bgcolor);

		button_listpayments.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//String lol = "";
				//lol += headerStr();
				//lol += allTenants();
				//viewtext.setText(lol);
				updatePayments();
				ScrollToBottom();

			}
		});

		button_display.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String lol = "";
				lol += headerStr();
				lol += allTenants();
				viewtext.setText(lol);
				ScrollToBottom();

			}
		});

		button_removetenant.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						RemoveTenantWindow winhandle=  new RemoveTenantWindow();
						if(winhandle.getChoice()==0){
							Object[]tenantinfo = winhandle.getResults();
							int removeByWhat = (Integer)tenantinfo[0];
							if(removeByWhat==0){ // then find by room number and remove
								int roomn = (Integer)tenantinfo[1];
								System.out.println("*Removing tenant* by Room number: "+roomn);
								boolean found = false;
								for(Tenant t : tenants){
									if(t.getRoom() == roomn){
										tenants.remove(t);
										found=true;
										break;
									}
								}
								if(!found){
									new ConfirmationBox("Room not in use","Could not remove tenant - room not occupied.","Ok");
								}

							}
							if(removeByWhat==1){ // then find by name and remove
								String tenantn = (String)tenantinfo[1];
								System.out.println("*Removing tenant* by Tenant Name: "+tenantn);
								boolean found = false;
								int matches=0;
								Tenant temp = new Tenant(-1,"");
								for(Tenant t : tenants){
									if(t.getName().toLowerCase().contains(tenantn.toLowerCase())){
										//tenants.remove(t);
										temp=t;
										found=true;
										matches++;
										//break;
									}
								}
								if(matches > 1){
									new ConfirmationBox("More than one tenants found","<html><center>More than one tenants were found ("+tenantn+"). Please try a more detailed input</center></html>","Ok");
									return;
								}
								if(!found){
									new ConfirmationBox("Tenant not found","Could not find a tenant with such name ("+tenantn+").","Ok");
									return;
								}
								tenants.remove(temp);
							}

//							int roomnum=(Integer)tenantinfo[0];
//							String tname=(String)tenantinfo[1];
							// todo Remove tenant here
							//tenants.add(new Tenant(roomnum,tname));
							refreshContent();
						}

						//winhandle=null;
						//winhandle.dispose();
						System.out.println("Stopping thread");

					}
				};
				System.out.println("Starting thread");
				event.start();
			}
		});

		button_quitwithoutsaving.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						if(new ConfirmationBox(
						  "Quit without saving",
						  "Are you sure you want to quit without saving?",
						  "Yes,No").
						  getChoice()==0){
							System.exit(0);
						}
						refreshContent();
						//stop();
					}
				};
				event.start();

			}
		});

		button_saveandquit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						if(new ConfirmationBox(
						  "Save and Quit",
						  "Are you sure you want to save all changes and quit?",
						  "Yes,No").
						  getChoice()==0){
							// todo save here
							if(doSave()!=0){
								new ConfirmationBox("Error when saving","There was an error when saving! Please retry or call an expert.","Ok");
								refreshContent();
								return;
							}
							System.exit(0);
						}
						//stop();
					}
				};
				event.start();
			}
		});
		button_search.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						SearchWindow winhandle=  new SearchWindow();
						if(winhandle.getChoice()==0){
							Object[]tenantinfo = winhandle.getResults();
							String tname=(String)tenantinfo[0];
							int tcount=0;
							ArrayList<Tenant> tenantsresult = new ArrayList<Tenant>();
							for(Tenant t : tenants){
								if(t.getName().toLowerCase().contains(tname.toLowerCase())){
									tenantsresult.add(t);
									tcount++;
								}
							}
							if(tcount==0){
								new ConfirmationBox("No results","There were no results matching \""+tname+"\". Please try different search terms.","Ok");
							}

							String res = SearchResFormat(tenantsresult);
							viewtext.setText(res);
							//tenants.add(new Tenant(roomnum,tname));
							//System.out.println("*adding tenant* Room: "+roomnum+"\tname: "+tname);
							//updatePayments();
							ScrollToBottom();
						}

						//winhandle=null;
						//winhandle.dispose();
						System.out.println("Stopping thread");

					}
				};
				System.out.println("Starting thread");
				event.start();
			}
		});
		button_makepayment.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						AddPaymentWindow winhandle=  new AddPaymentWindow();
						if(winhandle.getChoice()==0){
							Object[]tenantinfo = winhandle.getResults();
							int roomnum=(Integer)tenantinfo[0];
							int monthnum=(Integer)tenantinfo[1];
							double moneynum=(Double)tenantinfo[2];

							boolean found = false;
							for(Tenant t : tenants){
								if(t.getRoom() == roomnum){
									t.addPayment(new Payment(monthnum,moneynum));
									found=true;
									break;
								}
							}
							if(!found){
								new ConfirmationBox("Room not in use","Could not add payment - room not occupied.","Ok");
							}
							//tenants.add(new Tenant(roomnum,tname));
							//System.out.println("*adding tenant* Room: "+roomnum+"\tname: "+tname);
							updatePayments();
							ScrollToBottom();
						}

						//winhandle=null;
						//winhandle.dispose();
						System.out.println("Stopping thread");

					}
				};
				System.out.println("Starting thread");
				event.start();
			}
		});
		button_addtenant.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Thread event = new Thread(){
					public void run(){
						AddTenantWindow winhandle=  new AddTenantWindow();
						if(winhandle.getChoice()==0){
							Object[]tenantinfo = winhandle.getResults();
							int roomnum=(Integer)tenantinfo[0];
							String tname=(String)tenantinfo[1];
							// check if room is already occupied
							for(Tenant t: tenants){
								if(t.getRoom()==roomnum){
									new ConfirmationBox("Room Busy","The room you have entered is already in use by someonne.","Ok");
									return;
								}
							}
							tenants.add(new Tenant(roomnum,tname));
							System.out.println("*adding tenant* Room: "+roomnum+"\tname: "+tname);
							refreshContent();
						}

						//winhandle=null;
						//winhandle.dispose();
						System.out.println("Stopping thread");

					}
				};
				System.out.println("Starting thread");
				event.start();
			}
		});

		add(p1);
		add(p2);

		viewtext.setEditable(false);
		viewtext.setBackground(new Color(255,255,255));


		p1.add(scroll);

		p2.setLayout(new GridLayout(8, 1));
		p2.setPreferredSize(new Dimension(200,420));
		p2.add(button_display);
		p2.add(button_search);
		p2.add(button_addtenant);
		p2.add(button_removetenant);
		p2.add(button_makepayment);
		p2.add(button_listpayments);
		p2.add(button_saveandquit);
		p2.add(button_quitwithoutsaving);

		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		onStartup(); // load and display tenants


		//centerWin(this);
	}


	public void actionPerformed(ActionEvent e) {
		//if(e.getSource() == addTenant) {
		//}
	}

	public String allTenants(){
		String outstr="";
		for(Tenant t : tenants){
			outstr += t + "\n";
		}
		return outstr;
	}

	public String headerStr(){
		String lol="";
		lol += "Showing all "+tenants.size()+" tenants.\n";
		lol += "--------------------------------------------------------------------------------\n";
		lol += "Room\t\tName\n";
		lol += "--------------------------------------------------------------------------------\n";
		return lol;
	}
	public String SearchResFormat(ArrayList<Tenant> res){
		String lol="";
		lol += "Search has returned "+res.size()+" results:\n";
		lol += "--------------------------------------------------------------------------------\n";
		lol += "Room\t\tName\n";
		lol += "--------------------------------------------------------------------------------\n";
		for(Tenant t : res){
			lol += t+"\n";
		}
		return lol;
	}

	public void onStartup(){
		//load
		tenants = doLoad();
		String a="Welcome Back!\n";
		a+=headerStr();
		a+=allTenants();
		viewtext.setText(a);
	}

	public void refreshContent(){
		String a="";
		a+=headerStr();
		a+=allTenants();
		viewtext.setText(a);
	}

	private void ScrollToBottom(){
		JScrollBar vertical = scroll.getVerticalScrollBar();
		vertical.setValue( vertical.getMaximum() );
	}

	public int doSave(){
		int res = FileHandler.fileWrite("savedTenants.txt",FileHandler.prepareDataForFile(tenants));

		return res;
	}

	public ArrayList<Tenant> doLoad(){
		ArrayList<Tenant> loadresult = FileHandler.decodeFileToData(FileHandler.fileRead("savedTenants.txt"));
		if(loadresult == null){

			Thread event = new Thread(){
				public void run(){
					if(new ConfirmationBox(
						"Error reading file",
						"<html>Error reading file. <br>It's either open in another application, corrupted or not yet created.</html>",
						"I Understand,Please Explain").
						getChoice()==1){
							ConfirmationBox t = new ConfirmationBox("Why am i getting \"Error reading file\" message?",
												"<html><center><br>If this is the first time you are running the application, it is normal to receive this error message as the data file isn't created just yet. You need to \"save and exit\" before there is a file to read. If you are certain, however, that the data file <br>exists then it is either used by another application or it was corrupted.</center></html>",
												"Ok");
										t.setSize(480,200);
										//t.invalidate();
										//t.validate();
					}
				}
			};
			event.start();
			return new ArrayList<Tenant>();
		}
		return loadresult;
	}
}