package com.pakt;

import java.security.Key;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Authentication {
	public static void main(String args[]){
//		System.out.println("value = "+Authentication.encrypt(Authentication.decrypt("hello")));
		try{
		Class.forName("org.postgresql.Driver");
		Connection con = DriverManager.getConnection("jdbc:postgresql://209.190.15.18:5432/gurgoan_vtsdb", "postgres", "takshak11");
		Connection con1 = DriverManager.getConnection("jdbc:postgresql://209.190.15.18:5432/vtsdb", "postgres", "takshak11");
		// System.out.println("Connected in websservice device ");
		
			String sqlselect4 = "SELECT recordid, datatimestamp, datamessage, clientfqdn, clientport FROM devicedata_logs;";

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
			// remark, flag, companyid, assetid, available, vendorid
			while (rs4.next()) {
				System.out.println("in while");
				String sqlselect1 =null;
		    	sqlselect1 = "INSERT INTO devicedata_logs(recordid, datatimestamp, datamessage, clientfqdn,"
		    			+ " clientport) "
		    			+" VALUES ('"+rs4.getString(1)+"', '"+rs4.getString(2)+"', '"+rs4.getString(3)+"', '"+rs4.getString(4)+"', '"+rs4.getString(5)+"');";
		    
		    PreparedStatement	ps = con1.prepareStatement(sqlselect1);
			 boolean rs = ps.execute();
			}
			con.close();
			con1.close();
			
		}catch(Exception e){
				System.out.println(e);
			}
		
	}
	public static String encrypt(String text) {

	      try {

	         String key = "Kal54869Kal54869"; // 128 bit key
	         
	         // Create key and cipher
	         Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	         Cipher cipher = Cipher.getInstance("AES");
	         
	         // encrypt the text
	         cipher.init(Cipher.ENCRYPT_MODE, aesKey);
	         byte[] encrypted = cipher.doFinal(text.getBytes());
//	         System.err.println(new String(encrypted));
	         return(new String(encrypted));
	         
	      }catch(Exception e) {
	         e.printStackTrace();
	         return null;
	      }
	    }
	    
	    public static String decrypt(String text) {

	        try {

	           String key = "Kal54869Kal54869"; // 128 bit key
	           // Create key and cipher
	           Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
	           Cipher cipher = Cipher.getInstance("AES");
	          
	            //decrypt the text
	           cipher.init(Cipher.DECRYPT_MODE, aesKey);
	           String decrypted = new String(cipher.doFinal(text.getBytes()));
//	           System.err.println(decrypted);
	           return(new String(decrypted));
	        }catch(Exception e) {
	           e.printStackTrace();
	           return null;
	        }
	      }
}
