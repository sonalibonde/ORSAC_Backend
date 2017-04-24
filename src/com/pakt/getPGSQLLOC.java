package com.pakt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class getPGSQLLOC {
	Connection con = null;
	
	public getPGSQLLOC(){
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://209.190.15.18:5432/location", "postgres", "takshak11");
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	public String getLOC(double lat, double longi){
		String address = null;
		try{
			String sqlselect4 = "select attr_1||', '||attr_2||', '||attr_3||', '||attr_4 from locationpoint_bound "+
						 " where ((ST_Distance( "+
				  		 " ST_Transform(ST_GeomFromText('POINT("+longi+" "+lat+")',4326),3857), "+
						 "  ST_Transform(ST_GeomFromText(ST_AsText(geom),4326),3857) "+
						 " ))/1000)<2  order by ((ST_Distance( "+
						 " ST_Transform(ST_GeomFromText('POINT("+longi+" "+lat+")',4326),3857), "+
						 " ST_Transform(ST_GeomFromText(ST_AsText(geom),4326),3857) "+
						 " ))/1000) asc limit 1";

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int indexno=1;

			while (rs4.next()) {
				address = rs4.getString(1);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		return address;
	}
}
