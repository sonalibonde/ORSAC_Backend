package com.pakt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pakt.pojos.DataReceived_Dump1;
import com.pakt.pojos.login_details;

public class MainClass {

	public static void main(String args[])
	{
		
		try{
		List<DataReceived_Dump1> data =new ArrayList<DataReceived_Dump1>();
		
		Connection con = null;
		String message="";
		String location="";
		Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://209.190.15.18:5432/dbChennai", "postgres", "takshak11");
	String sqlselect4 = "SELECT veh.vehicleregno,conn.latitude,conn.longitude,conn.vehiclespeed"
	 +" FROM dblocator.msttblvehicle as veh inner join "
	+ " dblocator.msttblvehicleassigngps as asv on veh.vehicleid= asv.vehicleid "
	 + "inner join  dblocator.msttbldevice as dev on dev.deviceid=asv.deviceid "
	 +"inner join connected_device_master as conn on conn.imeino=dev.uniqueid where conn.vehiclespeed>50";

	
	Statement st4 = con.createStatement();
	ResultSet rs4 = st4.executeQuery(sqlselect4);
	while (rs4.next()) {
//		DataReceived_Dump1 obj=new DataReceived_Dump1();
		try{
//			location=getLocationClass.getLoc(Double.parseDouble(rs4.getString(2)),
//						Double.parseDouble(rs4.getString(3)));
		}catch(Exception e){
		  System.out.println(e);
		}
////	//	System.out.println("location = "+obj.getLocation()+"hello");
		
		
	message=rs4.getString(1)+" "+rs4.getString(4)+" "+location+"\n";
		
	}
	sendMailtoOperation op=new sendMailtoOperation();
	
//	message = message.replace('-', ' ');
	op.sendSMS(message);
	System.out.println("message"+message);
	}catch(Exception ex)
		{
			System.out.println("ex"+ex);
		}
		
		
	}
	
	
}
