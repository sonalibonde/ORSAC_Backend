package com.pakt;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import sun.misc.BASE64Encoder;

public class getLocationClass {
	public static void main(String args[]){
		getLoc(11.9928329999,78.51833);
	}
	public static String getLoc(double lat,double longi){
		int i = 0;
		String result = null;
//		shpName.add("India:coriddor_1");
	try {
		
			String webPage = "http://209.190.15.26:8686/location/location.asmx/LocationService?latitude="+lat+"&longitude="+longi;
		

			URL url = new URL(webPage);
			URLConnection urlConnection = url.openConnection();
			InputStream is = urlConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);

			int numCharsRead;
			char[] charArray = new char[1024];
			StringBuffer sb = new StringBuffer();
			while ((numCharsRead = isr.read(charArray)) > 0) {
				sb.append(charArray, 0, numCharsRead);
			}
			 result = sb.toString();
			 JSONObject json = new JSONObject(result);
			 JSONArray arr = json.getJSONArray("Location");
			 json = arr.getJSONObject(0);
			 result = json.getString("location");
//		System.out.println("Response from the server: " + result);
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return result;
	}
}
