package com.pakt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class sendMailtoOperation {

	
	
	public static void sendSMS(String message) throws UnsupportedEncodingException {

		System.out.println("in messahge send");
		StringBuffer buffer = new StringBuffer();
		java.net.URL url;
		String result;
		String line;
		String response = "";
		URLConnection connection;
		
		URLEncoder.encode(message,"UTF-8");
		String datamessage="http://quick.smseasy.in:8080/bulksms/bulksms?username=sse-shreyas&password=123456&type=0&dlr=1&destination=9503434688&source=QKRYDE&message="+message+"";
		try {
			url = new URL(datamessage);
					
//					"http://quick.smseasy.in:8080/bulksms/bulksms?username=sse-shreyas&password=shreyas&type=0&dlr=1&destination="+phone+"&source=ELocator&message=OTP:"+otp+"");
			
			connection = url.openConnection();
			connection.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			wr.flush();
			wr.close();

			BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = rd.readLine()) != null) {
				response += line;
			}
			wr.close();
			rd.close();

			System.out.println("\n *INDEX RESPONSE XML * \n\n" + response + "\n\n");

		} catch (Exception e) {
			System.out.println("Errors..." + e);
		}
	}
	
}
