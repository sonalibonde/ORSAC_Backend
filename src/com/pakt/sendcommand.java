package com.pakt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class sendcommand {
	public static void sendcmd(String phone) {

		StringBuffer buffer = new StringBuffer();
		java.net.URL url;
		String result;
		String line;
		String response = "";
		URLConnection connection;

		try {
//			url = new URL(
//					"http://quick.smseasy.in:8080/bulksms/bulksms?username=sse-shreyas&"
//					+ "password=shreyas&type=0&dlr=1&destination="+phone+"&source=QKRYDE&message=#ip2 209.190.15.18 8888");
			url = new URL("http://quick.smseasy.in:8080/bulksms/bulksms?username=sse-shreyas&password="
					+ "shreyas&type=0&dlr=1&destination="+phone+"&source=QKRYDE&message=#ip2 209.190.15.18 8888");
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
