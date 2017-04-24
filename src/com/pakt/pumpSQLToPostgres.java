package com.pakt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.pakt.pojos.processdata;

public class pumpSQLToPostgres {
static Connection connection = null;
	
	private final String userName = "sa";
	//private final String password = "sql2012";
	private final String password = "(@sa!)2Bmp"; 
	//private final String url = "jdbc:sqlserver://182.74.188.186:1433;databaseName=dbQuikRyde";
	private final String url = "jdbc:sqlserver://180.151.100.243:1433;databaseName=VTSDB";
	
	public void ConnectionToSQL(){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
			connection = DriverManager.getConnection(url, userName, password);
			//System.out.println("MSSQL Connected Successfully");
		}catch(Exception e){
			//logger.error("Exception occured during MSSQL getConnection message={} ex={}", e.getMessage(), e);
			connection = null;
		}
	}
	
	
	public void ConnectionToPostgreSQL(){
		try{
			Class.forName("org.postgresql.Driver").newInstance();
			connection = DriverManager.getConnection("jdbc:postgresql://61.0.175.102:5432/dbrsrtc", "postgres", "nrda@2016");
			//System.out.println("MSSQL Connected Successfully");
		}catch(Exception e){
			//logger.error("Exception occured during MSSQL getConnection message={} ex={}", e.getMessage(), e);
			connection = null;
		}
	}
	
	
	public static void main(String args[]){
		List<processdata> data = new ArrayList<processdata>();
		try{
			pumpSQLToPostgres con = new pumpSQLToPostgres();
			con.ConnectionToSQL();
			String sqlselect4 = "select [VehicleID]  ,[VehicleRegNo] ,[OrgID],[DeviceID] ,[HubID] ,[GPSStatus],[Lat], "+
        	"[Lon],[NoOfSatellite],[Alt],[Heading],[Speed],[TimeZone],[CellID], "+
        	"[OdoMeter],[DeviceTamperingStatus],[MainBatteryStatus],[IgnitionStatus],[DigitalInput1], "+
        	"[DigitalInput2],[DigitalInput3],[DigitalInput4],[DigitalOutput1],[DigitalOutput2], "+
        	"[AnalogInput1],[AnalogInput2],[InternalBatteryChargingStatus],[InternalBatteryVoltage], "+
        	"[InternalBatteryChargingCondition],[TypeOfData],[AlertType],[GSMSignalStrength], "+
        	"[Version],[RecievedDateTime],[Panic],[StringDateTime], [ClientID] from ProcessedRawData";

			Statement st4 = connection.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//			ml.loginid, ml.ownersid, md.contactperson, ml.roleid::text,
//			mc.companyid, mc.contactpersonname, ml.controlid
			
			while (rs4.next()) {
				int index = 1;
				processdata obj = new processdata();
				obj.setVehicleID(rs4.getString(index++));
				obj.setVehicleRegNo(rs4.getString(index++));
				obj.setOrgID(rs4.getString(index++));
				obj.setDeviceID(rs4.getString(index++));
				obj.setHubID(rs4.getString(index++));
				obj.setGPSStatus(rs4.getString(index++));
				obj.setLat(rs4.getString(index++));
				obj.setLon(rs4.getString(index++));
				obj.setNoOfSatellite(rs4.getString(index++));
				obj.setAlt(rs4.getString(index++));
				obj.setHeading(rs4.getString(index++));
				obj.setSpeed(rs4.getString(index++));
				obj.setTimeZone(rs4.getString(index++));
				obj.setCellID(rs4.getString(index++));
				obj.setOdoMeter(rs4.getString(index++));
				obj.setDeviceTamperingStatus(rs4.getString(index++));
				obj.setMainBatteryStatus(rs4.getString(index++));
				obj.setIgnitionStatus(rs4.getString(index++));
				obj.setDigitalInput1(rs4.getString(index++));
				obj.setDigitalInput2(rs4.getString(index++));
				obj.setDigitalInput3(rs4.getString(index++));
				obj.setDigitalInput4(rs4.getString(index++));
				obj.setDigitalOutput1(rs4.getString(index++));
				obj.setDigitalOutput2(rs4.getString(index++));
				obj.setAnalogInput1(rs4.getString(index++));
				obj.setAnalogInput2(rs4.getString(index++));
				obj.setInternalBatteryChargingStatus(rs4.getString(index++));
				obj.setInternalBatteryVoltage(rs4.getString(index++));
				obj.setInternalBatteryChargingCondition(rs4.getString(index++));
				obj.setTypeOfData(rs4.getString(index++));
				obj.setAlertType(rs4.getString(index++));
				obj.setGSMSignalStrength(rs4.getString(index++));
				obj.setVersion(rs4.getString(index++));
				obj.setRecievedDateTime(rs4.getString(index++));
				obj.setPanic(rs4.getString(index++));
				obj.setStringDateTime(rs4.getString(index++));
				data.add(obj);
			}
		}catch(Exception e){
			System.out.println(e);
		}
		System.out.println(data.size());
	}
}
