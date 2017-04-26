///////////////////////////////////////////
//// Miroslav Georgiev
//// electricity256@yahoo.com
//////////////////
//// Class: FileHandler
//// Description:
////  contains 4 static functions
////   fileWrite		-writes a string to a file
////   fileRead 		-reads a file into a string
////   decodeFileToData   - takes a string from an already written file and
////						attempts to decode it
////						into an arraylist of tenants
////						with their appropriate payments inserted
////   prepareDataForFile - takes an arraylist of tenants and puts together
////						all of the data as it appends payment information aswell
////						into a string. this string is ready to be written to a file.
//////////////////////////////////////////


import java.io.*;
import java.util.*;


public class FileHandler {
	public static int fileWrite(String filename, String contents){
		Writer writer = null;

		try {
		    writer = new BufferedWriter(
		    			  new OutputStreamWriter(
		    					new FileOutputStream(filename),
		    					"utf-8"
		    			  )
		    		);
		    writer.write(contents);
		} catch (IOException ex) {
		  return 1;
		} finally {
		   try {writer.close();} catch (Exception ex) {
			   // ignore
		   }
		}
		return 0;
	}

	public static String fileRead(String filename){
		String content = null;
		File file = new File(filename);
		try {
			FileReader reader = new FileReader(file);
			char[] chars = new char[(int) file.length()];
			reader.read(chars);
			content = new String(chars);
			reader.close();
		} catch (IOException e) {
			//e.printStackTrace();
			return null;
		}
		return content;
	}

	public static String prepareDataForFile(ArrayList<Tenant> a){
		String data="";
		if(a == null){
			return "";
		}
		if(a.size() == 0){
			return "";
		}
		for(Tenant al : a){
			String payments="";
			boolean first=true;
			for( Payment p : al.getPayments()){
				String tmp = "-";
				if(first){
					tmp="";
					first=false;
				}
				payments += tmp+p.getMonth()+"#"+p.getMoney();
			}

			data = data + al.getRoom() +","+ al.getName() +"," + payments +"\n";
		}
		return data;
	}

	public static ArrayList<Tenant> decodeFileToData(String a){
		if(a==null){
			return null;
		}
		if(a.trim()==""){
			return null;
		}

		ArrayList<Tenant> out = new ArrayList<Tenant>();
		String [] eachTenant = a.split("\n");


		for(int l=0; l< eachTenant.length; l++){
			String [] tenantData = eachTenant[l].split(",");
			try{
				Tenant temptenant = new Tenant(Integer.parseInt(tenantData[0]), tenantData[1] ) ;
				//System.out.println(temptenant);
				//System.out.println(tenantData.length);
				if(tenantData.length>2){
					String[] eachpayment = tenantData[2].split("-");
					for(int k=0;k<eachpayment.length;k++){
						String [] paymentdata= eachpayment[k].split("#");
						int room = Integer.parseInt(paymentdata[0]);
						double amount = Double.parseDouble(paymentdata[1]);
						temptenant.addPayment(new Payment(room,amount));
					}
				}
				out.add(temptenant);
			}catch(Exception e){
				//e.printStackTrace();
				return null;
			}

		}
		return out;
	}


}
