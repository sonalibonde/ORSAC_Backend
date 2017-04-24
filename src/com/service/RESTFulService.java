package com.service;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.poi.util.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pakt.AESCrypt;
import com.pakt.Authentication;
import com.pakt.EmailSend;
import com.pakt.ReadExcelFile;
import com.pakt.SearchBloxREST;
//import com.pakt.SearchBloxREST;
import com.pakt.ValidateTokens;
import com.pakt.generateOTP;
import com.pakt.getLocationClass;
//import com.pakt.generateOTP;
//import com.pakt.getLocationClass;
import com.pakt.imageConvertor;
import com.pakt.otpAuthenticate;
import com.pakt.pojos.DailyReport_details;
import com.pakt.pojos.DataReceived_Dump1;
import com.pakt.pojos.Devicedash_sold;
import com.pakt.pojos.GetListObj;
import com.pakt.pojos.MultipleDeviceCreation;
import com.pakt.pojos.Multipledeviceassng_jsonlist;
import com.pakt.pojos.TotalDevice;
import com.pakt.pojos.TransData;
import com.pakt.pojos.Vehicle_data;
import com.pakt.pojos.alert_log_details;
import com.pakt.pojos.approval_det;
import com.pakt.pojos.asset_details;
import com.pakt.pojos.bang_imei;
import com.pakt.pojos.company_details;
import com.pakt.pojos.companywisedevice_details;
import com.pakt.pojos.customer_details;
import com.pakt.pojos.customerdevice_assigndetails;
import com.pakt.pojos.customerwisedevice;
import com.pakt.pojos.daywisevehiclesReport;
import com.pakt.pojos.dealer_count;
import com.pakt.pojos.dealer_details;
import com.pakt.pojos.dealerassign_details;
import com.pakt.pojos.dealercustomers_details;
import com.pakt.pojos.dealerdash;
import com.pakt.pojos.dealerdevice_assigndetails;
import com.pakt.pojos.dealerreport_det;
import com.pakt.pojos.device_count;
import com.pakt.pojos.device_details;
import com.pakt.pojos.deviceassign_details;
import com.pakt.pojos.devicesale;
import com.pakt.pojos.devicesalecustomer;
import com.pakt.pojos.district_details;
import com.pakt.pojos.districtambulance_details;
import com.pakt.pojos.districtwisecount;
import com.pakt.pojos.forget_passDetails;
import com.pakt.pojos.fueltype_details;
import com.pakt.pojos.geofence_details;
import com.pakt.pojos.geofencereportdet;
import com.pakt.pojos.georeport;
import com.pakt.pojos.getloc;
import com.pakt.pojos.historylog_details;
import com.pakt.pojos.idleReport_details;
import com.pakt.pojos.iginition_details;
import com.pakt.pojos.imeilist;
import com.pakt.pojos.jsonlist;
import com.pakt.pojos.livedet;
import com.pakt.pojos.tripdet;
import com.pakt.pojos.livevehicle;
import com.pakt.pojos.login_details;
import com.pakt.pojos.loginresponse;
import com.pakt.pojos.lustatus_details;
import com.pakt.pojos.make_details;
import com.pakt.pojos.menu_details;
import com.pakt.pojos.minereport_det;
import com.pakt.pojos.model_details;
import com.pakt.pojos.networkName;
import com.pakt.pojos.network_details;
import com.pakt.pojos.overspeed_details;
import com.pakt.pojos.parentcomp_details;
import com.pakt.pojos.poidet;
import com.pakt.pojos.rawdata;
import com.pakt.pojos.rawdet;
import com.pakt.pojos.role_details;
import com.pakt.pojos.shelter_det;
import com.pakt.pojos.shelter_details;
import com.pakt.pojos.sim_details;
import com.pakt.pojos.simassign_details;
import com.pakt.pojos.subdealers;
import com.pakt.pojos.subdealersold;
import com.pakt.pojos.total_devices;
import com.pakt.pojos.user_details;
import com.pakt.pojos.vehicle_details;
import com.pakt.pojos.vehicleassigntogps_details;
import com.pakt.pojos.vehiclesummary;
import com.pakt.pojos.vehiclesummary_report;
import com.pakt.pojos.vehicletype_details;
import com.pakt.pojos.vendor_details;
import com.pakt.pojos.vts_Live_Vehicle;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.pakt.pojos.devicemanager;
import com.pakt.pojos.tripveh;
import com.pakt.pojos.circlewisetrip;
import com.pakt.pojos.circledetails;
import com.pakt.pojos.circlewisetripdetails;

//import Sync_Data._InsertProcess;

@Path("/CallService")
public class RESTFulService {
	ValidateTokens vt = new ValidateTokens();

	@GET
	@Path("/login1")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateLogin(@QueryParam("username") String user, @QueryParam("password") String password) {
		List<login_details> data = new ArrayList<login_details>();
		Connection con = null;
		try {
//		System.out.println("hello");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			String sqlselect4 = "select * from dblocator.login('" + user + "','" + password + "')";
//			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//			ml.loginid, ml.ownersid, md.contactperson, ml.roleid::text,
//			mc.companyid, mc.contactpersonname, ml.controlid
			while (rs4.next()) {
				login_details obj =new login_details();
				
				if(rs4.getString(1).equals("10044")){
					obj.setLoginid("10001");
				}else{
					obj.setLoginid(rs4.getString(1));
				}
				
				obj.setOwnersid(rs4.getString(2));
				obj.setOwnersname(rs4.getString(3));
				obj.setRoleid(rs4.getString(4));
				obj.setCompanyid(rs4.getString(5));
				obj.setCompanyname(rs4.getString(6));
				obj.setControlid(rs4.getString(7));
				
				java.util.Date dd = new java.util.Date();
				Timestamp tt = new Timestamp(dd.getTime());

				String str = obj.getOwnersname() + "&&" + obj.getCompanyid() + "&&" + obj.getLoginid() + "&&"
						+ obj.getCompanyname() + "&&" + obj.getControlid() + "&&" + tt.toLocaleString() + "&&"
						+ obj.getRoleid() + "&&" + obj.getOwnersid() + "&&" + user + "&&" + password;
				
//				 obj.getOwnersname() + "&&" + obj.getCompanyid() + "&&" + obj.getLoginid() + "&&"
//						0									1							2
//				+ obj.getCompanyname() + "&&" + obj.getControlid() + "&&" + tt.toLocaleString() + "&&"
//						3									4							5
//				+ obj.getRoleid() + "&&" + obj.getOwnersid() + "&&" + user + "&&" + password;
//						6						7						8				9
				String k = AESCrypt.encrypt(str);
			//System.out.println("key ="+k);
				k = k.replace("+", "nkm");
				obj.setKey(k);
				data.add(obj);
			}
			if (data.isEmpty()) {
				return Response.status(404).entity(data).build();
				//return data;
			} else {
//				System.out.println("in else");
//				loginresponse lr=new loginresponse();
//				lr.setData(data);
//				lr.setSuccess("true");
				return Response.status(200).entity(data).build();
				//return data;
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
			
			//return data;
		} finally {
			try {
				con.close();
				// System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}
	
	
	
	
	@GET
	@Path("/loginkey")
	@Produces(MediaType.APPLICATION_JSON)
	public Response loginkey(@QueryParam("key") String key) {
		List<login_details> data = new ArrayList<login_details>();
		Connection con = null;
		try {
			String user = "";
			String password = "";
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			String sqlselect3 = "select loginname, password from dblocator.msttbluserlogin where key = '"+key+"'";
			//System.out.println(sqlselect3);
			Statement st3 = con.createStatement();
			ResultSet rs3 = st3.executeQuery(sqlselect3);
			while(rs3.next()){
				user = rs3.getString(1);
				password = rs3.getString(2);
			}
			
			System.out.println(user+" "+password);
			String sqlselect4 = "select * from dblocator.login('" + user + "','" + password + "')";
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//			ml.loginid, ml.ownersid, md.contactperson, ml.roleid::text,
//			mc.companyid, mc.contactpersonname, ml.controlid
			while (rs4.next()) {
				System.out.println("in rs");
				login_details obj =new login_details();
				
				if(rs4.getString(1).equals("10044")){
					obj.setLoginid("10001");
				}else{
					obj.setLoginid(rs4.getString(1));
				}
				
				obj.setOwnersid(rs4.getString(2));
				obj.setOwnersname(rs4.getString(3));
				obj.setRoleid(rs4.getString(4));
				obj.setCompanyid(rs4.getString(5));
				obj.setCompanyname(rs4.getString(6));
				obj.setControlid(rs4.getString(7));
				
				java.util.Date dd = new java.util.Date();
				Timestamp tt = new Timestamp(dd.getTime());

				String str = obj.getOwnersname() + "&&" + obj.getCompanyid() + "&&" + obj.getLoginid() + "&&"
						+ obj.getCompanyname() + "&&" + obj.getControlid() + "&&" + tt.toLocaleString() + "&&"
						+ obj.getRoleid() + "&&" + obj.getOwnersid() + "&&" + user + "&&" + password;
				
//				 obj.getOwnersname() + "&&" + obj.getCompanyid() + "&&" + obj.getLoginid() + "&&"
//						0									1							2
//				+ obj.getCompanyname() + "&&" + obj.getControlid() + "&&" + tt.toLocaleString() + "&&"
//						3									4							5
//				+ obj.getRoleid() + "&&" + obj.getOwnersid() + "&&" + user + "&&" + password;
//						6						7						8				9
				String k = AESCrypt.encrypt(str);
			System.out.println("key ="+k);
				k = k.replace("+", "nkm");
				obj.setKey(k);
				data.add(obj);
			}
			
			if (data.isEmpty()) {
				return Response.status(404).entity(data).build();
				//return data;
			} else {
//				System.out.println("in else");
//				loginresponse lr=new loginresponse();
//				lr.setData(data);
//				lr.setSuccess("true");
				return Response.status(200).entity(data).build();
				//return data;
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
			
			//return data;
		} finally {
			try {
				con.close();
				// System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e);
			}
		}

	}
	
//	select blk_name01,dist_name,name_of_vl,gp_name
//	from odisha_village
//	where st_contains(geom, (ST_MakePoint(85.248253386, 21.677597)))=true;
	
	// --------------------------------------select
	// Function-------------------------------------
	@GET
	@Path("/livevehicle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response livepositionpacket(@QueryParam("companyid") String companyid,@QueryParam("customerid") String customerid,
			@QueryParam("pageno") String pageno,@QueryParam("itemsPerPage") String itemsPerPage,
			@QueryParam("overspeedlimit")String overspeedlimit,
			@QueryParam("searchbydeviceImeino")String searchbydeviceImeino,@QueryParam("district") String district,
			@QueryParam("compname") String compname
		) {
		List<vts_Live_Vehicle> data = new ArrayList<vts_Live_Vehicle>();
		Connection con = null;
		long vgps = 0, ngps = 0, onig = 0, offig = 0,start=0,end=0;
		int count=0;
		 String iginitionno="";
		 String gpsstatusOn="";
		try {
			if(compname.equals("All")){
				compname = "";
			}
			System.out.println("overspeedlimit"+overspeedlimit);
			if(Integer.parseInt(pageno)!=1){
				start = Integer.parseInt(itemsPerPage) * (Integer.parseInt(pageno)-1);
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}else{
				start = 0;
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}
			String tablename = null;
			//System.out.println("in type"+type);
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
System.out.println("Connected in websservice live ");
			
if(searchbydeviceImeino==null)
{
	searchbydeviceImeino="";
}

if(district==null)
{
	district="";
}else if(district.equals("All")){
	district="";
}


			String sqlselect4 = "select * from dblocator.selectprocedure('selectlivevehicle2', '" + companyid
					+ "', '"+customerid+"', '"+start+"', '"+end+"', '"+searchbydeviceImeino+"', '"+district+"', '"+compname+"', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			System.out.println("livevh"+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
			SimpleDateFormat sdfnew1 = new SimpleDateFormat("dd-MMM-yyyy");
			
			
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int indexno=1;
			while (rs4.next()) {

				vts_Live_Vehicle obj = new vts_Live_Vehicle();
//				lpp.reactorkey, lpp.updatedtimestamp, lpp.packettime, lpp.imeino, lpp.gpsstatus, lpp.packetdate+lpp.packettime, 
//				   lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.gpsstatus, lpp.gpsstatus,mv.vehicleregno,
//				   lpp.gpsstatus, lpp.vehicledirection, md.deviceid,mt.vehicletypename,
//				   mss.mobilenumber,mc.customername,lpp.ignumber,lpp.temperstatus,
				obj.setRecordid(rs4.getString(1));
				java.util.Date parseTimestamp = sdf.parse(rs4.getString(2));
				//System.out.println("inn date"+rs4.getString(2));
				obj.setDatatimestamp(sdfnew.format(parseTimestamp));
				obj.setPackettype(rs4.getString(3));
				//System.out.println("inn pac"+rs4.getString(3));
				obj.setImeino(rs4.getString(4));
				//System.out.println("inn imeino"+rs4.getString(4));
				obj.setSequenceno(rs4.getString(5));
				//System.out.println("inn seq"+rs4.getString(5));
				obj.setGpsdatetime(rs4.getString(6));
				//System.out.println("inn gpsdate"+rs4.getString(6));
				obj.setLatitude(rs4.getString(7));
				//System.out.println("inn dlat"+rs4.getString(8));
				obj.setLongitude(rs4.getString(8));
				//System.out.println("inn dlon"+rs4.getString(8));
				Double speed = Double.parseDouble(rs4.getString(9));
				//System.out.println("inn speedte"+rs4.getString(9));
				// System.out.println("index = "+speed);
				obj.setVehiclespeed(String.valueOf(speed));
				obj.setCoarseoverground(rs4.getString(10));
				//System.out.println("inn coares"+rs4.getString(10));
				obj.setTracksatellite(rs4.getString(11));
				//System.out.println("inn trac"+rs4.getString(11));
				try{
				obj.setVehicleid(rs4.getString(12));
				}catch(Exception e){
						obj.setVehicleid(rs4.getString(4));
				}
				//System.out.println("inn veh"+rs4.getString(12));
				try{
					if(rs4.getString(26)!=null){
						obj.setLocation(rs4.getString(26));
					}else{
						obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude())));
					}
					
				}catch(Exception e){
				  //System.out.println(e);
				}
////			//	System.out.println("location = "+obj.getLocation()+"hello");
				if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
					
					 String message="Location Not Found"+" "+"Lat-"+obj.getLatitude()+" "+"Long-"+obj.getLongitude();
					obj.setLocation(message);
				}
				try{
					obj.setCid(rs4.getString(27));
				}catch(Exception e){
					  //System.out.println(e);
				}
//			//	System.out.println("location = "+obj.getLocation());
				obj.setVehicledirection(reticondir(rs4.getInt(14)));
				//System.out.println("inn dir"+rs4.getString(14));
				obj.setIgnumber(iginition(Integer.parseInt(rs4.getString(19))));
				//System.out.println("inn ign"+rs4.getString(19));
				String ign = rs4.getString(19);
				if(Double.parseDouble(obj.getVehiclespeed())>0.00 && ign.equals("0")){
					obj.setIgnumber(iginition(1));
					ign = "1";
				}//else{
//					obj.setIgnumber(iginition(0));
//					ign = "0";
//				}
				
				obj.setGpsstatus(GPS(rs4.getString(13)));
				
				
				obj.setDeviceid(rs4.getString(15));
				//System.out.println("devid"+rs4.getString(15));
				try{
				obj.setVehicletype(vehicletype(rs4.getString(16)));
				}catch(Exception e){
					obj.setVehicletype(vehicletype("h"));
				}
				//System.out.println("inn type"+rs4.getString(16));
				try{
				obj.setMobileno(rs4.getString(17));
				//System.out.println("inn mobil"+rs4.getString(17));
				obj.setCustomername(rs4.getString(18));
				//System.out.println("inn cust"+rs4.getString(18));
				}catch(Exception e){
//					System.out.println(e);
				}
				parseTimestamp = sdfnew.parse(obj.getDatatimestamp());
				java.util.Date newdate = new java.util.Date();
//				long diffMinutes = compareTwoTimeStamps(new Timestamp(newdate.getTime()),
//						new Timestamp(parseTimestamp.getTime()));
				//System.out.println("ign = "+ign+" "+obj.getVehiclespeed()+" "+rs4.getString(13));
				if (ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) > 0.0) {
					obj.setVehiclestatus(vehiclestatus("Running"));
					//System.out.println("Running");
				} else 
					{if(ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) == 0.0) {
						obj.setVehiclestatus(vehiclestatus("Halt"));
						//System.out.println("Halt");
					} 
					else {
						obj.setVehiclestatus(vehiclestatus("Stop"));
						//System.out.println("Stop");
					}
}
//				obj.setVehiclespeed(obj.getVehiclespeed()+ " KM/Hr");
				count = Integer.parseInt(rs4.getString(20));
				//System.out.println("inn count"+rs4.getString(20));
				obj.setRowno(Integer.parseInt(rs4.getString(21)));
				//System.out.println("inn row"+rs4.getString(21));
				try
				{
				obj.setTemperstatus(gettemperStatus(rs4.getString(22)));
				//System.out.println("inn t"+rs4.getString(22));
				//System.out.println(gettemperStatus(rs4.getString(22)+""+obj.getTemperstatus()));
				}catch(Exception ex){}
				obj.setAc(getacStatus("0"));
				if(Double.parseDouble(obj.getVehiclespeed()) > Double.parseDouble(overspeedlimit))
				{	
					obj.setOverspeed("Overspeed");
				}
				else
				{	
					obj.setOverspeed("Not Overspeed");
					
				}
				
				//System.out.println("datetime"+"new date"+sdfnew1.format(new Date())+"  "+"pareds date"+sdfnew1.format(parseTimestamp) );
				obj.setColor(rs4.getString(23));
				try{
					obj.setGeofence(rs4.getString(24));
				}catch(Exception e){
					
				}
				
				try{
					obj.setDistrict(rs4.getString(25));
				}catch(Exception e){
				}
				
				obj.setDirectionname(directionname(rs4.getInt(14)));
				
//				try{if((sdfnew1.format(new Date())).compareTo(sdfnew1.format(parseTimestamp))>0)
//				{
//					obj.setColor("1");
//				}else
//				{
//					//System.out.println("in eseif");
//					obj.setColor("0");
//					
//				}
//				}catch(Exception fg){}
				
				
//				System.out.println("latitude"+rs4.getString(7));
//				System.out.println("londitude"+rs4.getString(8));
				indexno++;
				data.add(obj);
				
			}
			livedet det = new livedet();
			det.setData(data);
			det.setTotal_count(count);
			System.out.println(data.size());
			return Response.status(200).entity(det).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@POST
	@Path("/readexcel")
	@Produces(MediaType.APPLICATION_JSON)
	public Response livepositionpacket(String name, 
			@QueryParam("customerid") String customerid, @QueryParam("loginid") String loginid,
			@QueryParam("makeid") String makeid, @QueryParam("modelid") String modelid) {
//		System.out.println(name+" "+loginid+" "+customerid+" "+makeid+" "+modelid);
		ReadExcelFile.read(name, loginid, customerid, makeid, modelid);
		System.out.println("readed successfully");
		return Response.status(200).entity("Success")
				.build();
	}
	
	@GET
	@Path("/getlocation")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLocation(@QueryParam("lat") String lat,@QueryParam("lon") String lon){
		try{
			//System.out.println("in");
			getloc obj = new getloc();
			obj.setLocation(getLocationClass.getLoc(Double.parseDouble(lat), Double.parseDouble(lon)));
			return Response.status(200).entity(obj).build();
		} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} 
	}

	
	/////////////////////////////
	
	
	@GET
	@Path("/districtwiseCount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response districtwiseCount() {
		List<districtwisecount> data = new ArrayList<districtwisecount>();
		System.out.println("userdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			String sqlselect4 = "select * from dblocator.selectprocedure('selectdistcount',"
					+ " '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			
			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			while (rs4.next()) {
				districtwisecount obj = new districtwisecount();
					obj.setCount(rs4.getInt(1));
					obj.setDistrict_name(rs4.getString(2));
//					System.out.println(obj.getCount());
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	///////////
	
	
	
	
	
	
	
	@GET
	@Path("/livevehiclebyname")
	@Produces(MediaType.APPLICATION_JSON)
	public Response livevehiclebyname(@QueryParam("companyid") String companyid,@QueryParam("customerid") String customerid,
			@QueryParam("pageno") String pageno,@QueryParam("itemsPerPage") String itemsPerPage,@QueryParam("overspeedlimit")String overspeedlimit,
			@QueryParam("deviceid")String deviceid,@QueryParam("type")String type,@QueryParam("district") String district) {
		List<vts_Live_Vehicle> data = new ArrayList<vts_Live_Vehicle>();
		Connection con = null;
		long vgps = 0, ngps = 0, onig = 0, offig = 0,start=0,end=0;
		int count=0;
		String ignitionon="";
		String ignitionoff="";
		String gpson="";
		String  gpsoff="";
		String running="";
		String halt="";
		String stop="";
		String tamper="";
		String totalvehicle="";
		String  downVehicles="";
		String overspeed="-1";
			try {
			//System.out.println("overspeedlimit"+overspeedlimit);
			if(Integer.parseInt(pageno)!=1){
				start = Integer.parseInt(itemsPerPage) * (Integer.parseInt(pageno)-1);
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}else{
				start = 0;
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}
			String tablename = null;
			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
System.out.println("Connected in websservice live vehbyname");
		try{
//System.out.println("type"+type);
if(type.equals("ignnumberon"))
{
	System.out.println("igon");
	ignitionon="1";
	
}
else if(type.equals("ignnumberoff"))
{System.out.println("ignoff");
	ignitionoff="0";
	
}
else if(type.equals("gpson"))
{System.out.println("gpson ");
	gpson="1";
	
}
else if(type.equals("gpsoff"))
{
	System.out.println("gpsoff ");
	gpsoff="0";

}
if(type.equals("halt"))
{
	System.out.println("hault ");
	halt="halt";
}
if(type.equals("running"))
{
	
	running="running";
}
if(type.equals("stop"))
{
	System.out.println("stop ");
	stop="stop";
}
if(type.equals("temper"))
{
	System.out.println("tamper ");
	tamper="tamper";
}
if(type.equals("all"))
{
	System.out.println("totalvehicle");
	totalvehicle="totalvehicle";
}
if(type.equals("downVehicles"))
{
	System.out.println("downVehicles");
	downVehicles="downVehicles";
}
if(type.equals("overspeed"))
{System.out.println("in overspeed");
	//overspeed="overspeed";
	overspeed = overspeedlimit;
}

if(district==null)
{
	district="";
}else if(district.equals("All")){
	district="";
}
		}catch(Exception e){
			System.out.println(e);
		}
			String sqlselect4 = "select * from dblocator.selectprocedure('selectlivevehicleByName3', '" + companyid
					+ "', '"+customerid+"', '"+start+"', '"+end+"', '"+deviceid+"', '"+ignitionon+"', '"+ignitionoff+"', '"+gpson+"', "
					+ "'"+gpsoff+"', '"+halt+"', '"+running+"', '"+stop+"', '"+tamper+"', '"+downVehicles+"', '"+district+"', '"+overspeed+"', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			System.out.println("livevh   "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int indexno=1;
			while (rs4.next()) {

				vts_Live_Vehicle obj = new vts_Live_Vehicle();
//				lpp.reactorkey, lpp.updatedtimestamp, lpp.packettime, lpp.imeino, lpp.gpsstatus, lpp.packetdate+lpp.packettime, 
//				   lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.gpsstatus, lpp.gpsstatus,mv.vehicleregno,
//				   lpp.gpsstatus, lpp.vehicledirection, md.deviceid,mt.vehicletypename,
//				   mss.mobilenumber,mc.customername,lpp.ignumber,lpp.temperstatus,
				obj.setRecordid(rs4.getString(1));
				java.util.Date parseTimestamp = sdf.parse(rs4.getString(2));
				//System.out.println("inn date"+rs4.getString(2));
				obj.setDatatimestamp(sdfnew.format(parseTimestamp));
				obj.setPackettype(rs4.getString(3));
				//System.out.println("inn pac"+rs4.getString(3));
				obj.setImeino(rs4.getString(4));
				//System.out.println("inn imeino"+rs4.getString(4));
				obj.setSequenceno(rs4.getString(5));
				//System.out.println("inn seq"+rs4.getString(5));
				obj.setGpsdatetime(rs4.getString(6));
				//System.out.println("inn gpsdate"+rs4.getString(6));
				obj.setLatitude(rs4.getString(7));
				//System.out.println("inn dlat"+rs4.getString(8));
				obj.setLongitude(rs4.getString(8));
				//System.out.println("inn dlon"+rs4.getString(8));
				Double speed = Double.parseDouble(rs4.getString(9));
				//System.out.println("inn speedte"+rs4.getString(9));
				// System.out.println("index = "+speed);
				obj.setVehiclespeed(String.valueOf(speed));
				obj.setCoarseoverground(rs4.getString(10));
				//System.out.println("inn coares"+rs4.getString(10));
				obj.setTracksatellite(rs4.getString(11));
				//System.out.println("inn trac"+rs4.getString(11));
				try{
					obj.setVehicleid(rs4.getString(12));
					}catch(Exception e){
						obj.setVehicleid(rs4.getString(4));
					}
				//System.out.println("inn veh"+rs4.getString(12));
				try{
					if(rs4.getString(26)!=null){
						obj.setLocation(rs4.getString(26));
					}else{
						obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitude()), Double.parseDouble(obj.getLongitude())));
					}
				}catch(Exception e){
				  //System.out.println(e);
				}
//			//	System.out.println("location = "+obj.getLocation()+"hello");
				if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
					obj.setLocation("Location Not Found");
				}
				try{
					obj.setCid(rs4.getString(27));
				}catch(Exception e){
					  //System.out.println(e);
				}
//			//	System.out.println("location = "+obj.getLocation());
				obj.setVehicledirection(reticondir(rs4.getInt(14)));
				//System.out.println("inn dir"+rs4.getString(14));
				obj.setIgnumber(iginition(Integer.parseInt(rs4.getString(19))));
				
				//System.out.println("inn ign"+rs4.getString(19));
				String ign = rs4.getString(19);
				if(Double.parseDouble(obj.getVehiclespeed())>0.00 && ign.equals("0")){
					obj.setIgnumber(iginition(1));
					ign = "1";
				}
//				if(Double.parseDouble(obj.getVehiclespeed())>0.00){
//					obj.setIgnumber(iginition(1));
//					ign = "1";
//				}else{
//					obj.setIgnumber(iginition(0));
//					ign = "0";
//				}
				
				obj.setGpsstatus(GPS(rs4.getString(13)));
				//System.out.println("inn gpssts"+rs4.getString(13));
				
				obj.setDeviceid(rs4.getString(15));
				//System.out.println("devid"+rs4.getString(15));
				try{
				obj.setVehicletype(vehicletype(rs4.getString(16)));
				}catch(Exception e){
//					System.out.println(e);
				}
				//System.out.println("inn type"+rs4.getString(16));
				try{
				obj.setMobileno(rs4.getString(17));
				
				//System.out.println("inn mobil"+rs4.getString(17));
				obj.setCustomername(rs4.getString(18));
				//System.out.println("inn cust"+rs4.getString(18));
				}catch(Exception e){
//					System.out.println(e);
				}
				parseTimestamp = sdfnew.parse(obj.getDatatimestamp());
				java.util.Date newdate = new java.util.Date();
//				long diffMinutes = compareTwoTimeStamps(new Timestamp(newdate.getTime()),
//						new Timestamp(parseTimestamp.getTime()));
//				System.out.println("ign = "+ign);
				if (ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) > 0.0) {
					obj.setVehiclestatus(vehiclestatus("Running"));
				} else {
					if (ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) == 0.0) {
						obj.setVehiclestatus(vehiclestatus("Halt"));
					} 
					else {
						obj.setVehiclestatus(vehiclestatus("Stop"));
					}
				}
//				obj.setVehiclespeed(obj.getVehiclespeed()+ " KM/Hr");
				count = Integer.parseInt(rs4.getString(20));
				//System.out.println("inn count"+rs4.getString(20));
				obj.setRowno(Integer.parseInt(rs4.getString(21)));
				//System.out.println("inn row"+rs4.getString(21));
				try
				{
				obj.setTemperstatus(gettemperStatus(rs4.getString(22)));
				obj.setColor(rs4.getString(23));
				try{
					obj.setGeofence(rs4.getString(24));
				}catch(Exception e){
					
				}
				try{
					obj.setDistrict(rs4.getString(25));
				}catch(Exception e){
				}
				
				//System.out.println("inn t"+rs4.getString(22));
				//System.out.println(gettemperStatus(rs4.getString(22)+""+obj.getTemperstatus()));
				}catch(Exception ex){}
				obj.setAc(getacStatus("0"));
				if(Double.parseDouble(obj.getVehiclespeed()) > Double.parseDouble(overspeedlimit))
				{
					obj.setOverspeed("Overspeed");
				}
				else
				{
					obj.setOverspeed("Not Overspeed");
				}
				obj.setDirectionname(directionname(rs4.getInt(14)));
				indexno++;
				data.add(obj);
				
			}
			System.out.println(count);
			livedet det = new livedet();
			det.setData(data);
			det.setTotal_count(count);
			return Response.status(200).entity(det).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



	
	
	
	
	
	@GET
	@Path("/nonpollingdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getNonPolling(@QueryParam("loginid") String loginid,@QueryParam("nonpollinDevices") String nonpollinDevices){
		List<device_details> data = new ArrayList<device_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice non polling device ");
			System.out.println(nonpollinDevices);
//			int k = 0, flag = 1;
//			while (k < list.size()) {
			int start=0,end=0;
			int index=1;
//			if(Integer.parseInt(pageno)!=1){
//				start = Integer.parseInt(itemsPerPage) * (Integer.parseInt(pageno)-1);
//				end = start + Integer.parseInt(itemsPerPage);
//				start++;
//			}else{
//				start = 0;
//				end = start + Integer.parseInt(itemsPerPage);
//				start++;
//			}
//			
//			if(nonpollinDevices==null)
//			{
//				nonpollinDevices="";
//			}
			if(nonpollinDevices==null){
				nonpollinDevices = "";
			}
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectnonpolling', '"
						+ loginid + "', '"+nonpollinDevices+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select md.deviceid, md.uniqueid, ms.mobilenumber, "
					+ " (select mc1.customername from dblocator.msttbldevice as md1  "
					+ "	inner join dblocator.msttblmappingdevices_toall as mdm1 on md1.deviceid = mdm1.deviceid "
					+ " inner join dblocator.msttbluserlogin c1 on mdm1.loginid = c1.loginid "
					+ " inner join dblocator.msttblcustomer as mc1 on mc1.customerid = c1.ownersid "
					+ " where md1.deviceid = md.deviceid and md1.flag=0  limit 1) as customer "
					+ " from dblocator.msttbldevice as md "
					+ " left join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = md.deviceid "
					+ " left join dblocator.msttblsim as ms on ms.simid = dsm.simid "
					+ " inner join dblocator.msttblmappingdevices_toall as mdm on mdm.deviceid = md.deviceid "
					+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mdm.loginid "
					+ "	where ml.loginid = '"+loginid+"'::numeric and md.uniqueid not in (select imeino from connected_device_master) and "
					+ " case when('"+nonpollinDevices+"'!='')  then md.uniqueid like '%'||'"+nonpollinDevices+"'||'%'  else md.flag=0  end ; ";
				
				System.out.println("nonpolling"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;

				while (rs4.next()) {
					
					try {
//						System.out.println("in rs");
						device_details obj = new device_details();
					try
					{
						try{
						obj.setDeviceid(Long.parseLong(rs4.getString(1)));
						}catch(Exception e){
							
						}
						//System.out.println("decvice id  ="+rs4.getString(1));
						try{
						obj.setUniqueid(rs4.getString(2));
						}catch(Exception e){
							
						}
						//System.out.println("imeino ="+rs4.getString(2));
						try{
						obj.setSimnumber(rs4.getString(3));
						}catch(Exception e){
							
						}
						//System.out.println("simnumber  ="+rs4.getString(3));
						try{
						obj.setCustomername(rs4.getString(4));
						}catch(Exception e){
							obj.setCustomername("Not Availiable");
						}
					}catch(Exception e)
					{
						System.out.println(e);
					}
					obj.setRowno(index);
					index++;
						data.add(obj);
						
					} catch (Exception e) {
						System.out.println("err = " + e);
					}
					
				}
				System.out.println("in nonpolliging"+data.size());
			return Response.status(200).entity(data).build();
		}catch(Exception e){
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	



	
	
	
	
	
	
	@GET
	@Path("/sendcommand")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendcommand(@QueryParam("imeino") String imeino) {
	 Connection con = null;
	 String mobno;
	 try{
	 	Class.forName("org.postgresql.Driver");
		con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice check user");

		// String sqlselect4="select vehicle_creation()";
//		String sqlselect4 = "select * from db_nrda_dborissa.dblocator.selectprocedure('selectForgotpassword', "
//				+ "'"+param+"', '', '', '', '', '', '', '', "
//				+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//				+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

		/*String sqlselect4 = "select * from dblocator.selectprocedure('getmobno', '"
				+ imeino + "', '', '', '', '', '', '', '', "
				+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
				+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
		
		    String sqlselect4=" select ms.mobilenumber from dblocator.msttbldevice as md "
		    		+ " inner join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = md.deviceid "
		    		+ "	inner join dblocator.msttblsim as ms on ms.simid = dsm.simid "
		    		+ " where md.uniqueid = '"+imeino+"'::text; ";
	
		Statement st4 = con.createStatement();
		ResultSet rs4 = st4.executeQuery(sqlselect4);
		System.out.println("query"+sqlselect4);
		while(rs4.next()){
			mobno = rs4.getString(1);
			System.out.println("mobno"+mobno);
			com.pakt.sendcommand.sendcmd("8446819467");
		}
		return Response.status(200).entity("Command Send").build();
	 }catch(Exception e){
		 System.out.println(e);
		 return Response.status(404).entity("Command Not Send").build();
	 }
 }
	
	
	
	public String gettemperStatus(String temper){
		String temperstatus=null;
		 if(temper.equals("NNN")){
			 temperstatus="Normal";
			// temperstatus="resources/imagenew/temper.png";
	      }
		 else if(temper.equals("NTN")){
			 temperstatus="Box";
	      }
		 else if(temper.equals("SNN")){
			 temperstatus="Sim";
	      }
		 else if(temper.equals("NNB")){
			 temperstatus="Battery";
	      }
		 else
		 {
			 temperstatus="Tamper";
		     
		 }
		return(temperstatus);
	}
	



public String getacStatus(String ac){
	String acstatus=null;
	 if(ac.equals("0")){
		 acstatus="Not Connected";
		 
		 //acstatus="resources/imagenew/battery.png";
      }
	 else{
		 acstatus="Connected";
		// acstatus="resources/images/notass1.png";
      }
	return(acstatus);
}


	
	public static long compareTwoTimeStamps(java.sql.Timestamp currentTime, java.sql.Timestamp oldTime) {
		long milliseconds1 = oldTime.getTime();
		long milliseconds2 = currentTime.getTime();

		long diff = milliseconds2 - milliseconds1;
		long diffSeconds = diff / 1000;
		long diffMinutes = diff / (60 * 1000);
		long diffHours = diff / (60 * 60 * 1000);
		long diffDays = diff / (24 * 60 * 60 * 1000);

		return diffMinutes;
	}

//	@GET
//	@Path("/gpsdetails")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response gpsDetails(@QueryParam("companyid") String companyid, @QueryParam("customerid") String customerid,@QueryParam("overspeed")String overspeed) {
//		long ovp = 0, vgps = 0, tamper=0,ngps = 0, onig = 0, offig = 0, total = 0, work = 0, nowork = 0, red = 0, yellow = 0, green = 0;
//		Connection con = null;
//		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Class.forName("org.postgresql.Driver");
//			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			System.out.println("Connected in websservice gps details");
//			
//			String sqlselect4 = "select * from dblocator.selectprocedure('selectlivevehicle', '" + companyid
//					+ "', '"+customerid+"', '1', '69', '', '', '', '', "
//					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
//
//			Statement st4 = con.createStatement();
//			ResultSet rs4 = st4.executeQuery(sqlselect4);
//			// livevehicle obj ;
//			// recordid, datatimestamp, packettype, imeino, sequenceno,
//			// gpsdatetime,
//			// latitude, longitude, devicespeed, coarseoverground,
//			// tracksatellite
//			
//			while (rs4.next()) {
//
//				
//				vts_Live_Vehicle obj = new vts_Live_Vehicle();
//				// recordid, datatimestamp, packettype, imeino, vehicleid,
//				// gpsstatus,
//				// packetdate, packettime, latitude, latitudedirection,
//				// longitude,
//				// longitudedirection, vehiclespeed, vehicledirection,
//				// vehiclestatus,
//				// temperstatus, panicstatus, batteryvoltage, swversion, tripid,
//				// ignumber, checksum
//				obj.setRecordid(rs4.getString(1));
//				obj.setDatatimestamp(rs4.getString(2));
//				obj.setPackettype(rs4.getString(3));
//				obj.setImeino(rs4.getString(4));
//				obj.setSequenceno(rs4.getString(5));
//				obj.setGpsdatetime(rs4.getString(6));
//				obj.setLatitude(rs4.getString(7));
//				obj.setLongitude(rs4.getString(8));
//				obj.setVehiclespeed(rs4.getString(9));
//				obj.setCoarseoverground(rs4.getString(10));
//				obj.setTracksatellite(rs4.getString(11));
//				obj.setGpsstatus(rs4.getString(13));
////			//	System.out.println("val = "+obj.getGpsstatus());
//				
////					obj.setIgnumber(rs4.getString(19));
//				String ign = "0";
//				if(Double.parseDouble(obj.getVehiclespeed())>0.00){
//					obj.setIgnumber("1");
//					ign = "1";
//				}else{
//					obj.setIgnumber("0");
//					ign = "0";
//				}
//					if(Double.parseDouble(obj.getVehiclespeed())>Double.parseDouble(overspeed)){
//					      ovp++;	
//					}
//				
//					if(obj.getTemperstatus()!="NNN")
//					{
//						tamper++;
//					}
//					
////				obj.setGpsstatus("C");
//
//				if (obj.getGpsstatus().contains("0")) {
//					ngps++;
//				} else {
//					vgps++;
//				}
//
//				if (Integer.parseInt(obj.getIgnumber()) == 1) {
//					onig++;
//				} else {
//					offig++;
//				}
//				java.util.Date parseTimestamp = sdf.parse(obj.getGpsdatetime());
//				java.util.Date newdate = new java.util.Date();
//				// System.out.println("time = "+newdate+"
//				// "+obj.getDatatimestamp());
//				if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
//						&& (parseTimestamp.getYear() == newdate.getYear())) {
//					// System.out.println("data no ="+parseTimestamp.getTime()+"
//					// "+newdate.getTime());
//					work++;
//				} else {
//					// System.out.println("data ="+parseTimestamp.getTime()+"
//					// "+newdate.getTime());
//					nowork++;
//				}
//
//				// long diff = newdate.getTime() - parseTimestamp.getTime();
////				long diffMinutes = compareTwoTimeStamps(new Timestamp(newdate.getTime()),
////						new Timestamp(parseTimestamp.getTime()));
////				if (diffMinutes > 60 || rs4.getString(13).equals("0") || rs4.getString(19).equals("0")) {
////					red++;
////				} else {
////					if (Double.parseDouble(obj.getVehiclespeed()) == 0.00) {
////						yellow++;
////					} else {
////						green++;
////					}
////				}
//				
//				
//				if (ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) > 0.0 && rs4.getString(13).equals("1")) {
//					green++;
//				} else {
//					if (ign.equals("1") && Double.parseDouble(obj.getVehiclespeed()) == 0.0 && rs4.getString(13).equals("1")) {
//						yellow++;
//					} 
//					else {
//						red++;
//					}
//				}
//
//				total++;
//
//			}
//		//	System.out.println("data = " + vgps + " " + ngps + " " + onig + " " + offig + " " + green + " " + yellow + " " + red);
//			vt.setIgnitionoff(offig);
//			vt.setIgnitionon(onig);
//			vt.setValidgps(vgps);
//			vt.setInvalidgps(ngps);
//			vt.setTotalVehicles(total);
//			vt.setWorking_devices(work);
//			vt.setNot_working_devices(nowork);
//			vt.setGreen(green);
//			vt.setRed(red);
//			vt.setYellow(yellow);
//			vt.setAc(0);
//			vt.setTemper(tamper);
//			vt.setOverspeed(ovp);
//			return Response.status(200).entity(vt).build();
//		} catch (Exception e) {
//		 System.out.println(e);
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
//		} finally {
//			try {
//				con.close(); // System.out.println("connection closed");
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//	
	
	@GET
	@Path("/gpsdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gpsDetails(@QueryParam("companyid") String companyid, @QueryParam("customerid") String customerid,@QueryParam("overspeed")String overspeed) {
		long ovp = 0, vgps = 0, tamper=0,ngps = 0, onig = 0, offig = 0, total = 0, work = 0, nowork = 0, red = 0, yellow = 0, green = 0, nworking= 0;
		Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice gps details");
			
			String sqlselect4 = "select * from dblocator.selectprocedure('selectgpsdetails', '" + companyid
					+ "', '"+customerid+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			
			/*String sqlselect4=" if '"+customerid+"'::numeric !=10001 "
					+ " then "
					+ " select roleid into did from dblocator.msttbluserlogin where loginid = '"+customerid+"'::numeric; "
					+ " if did = 1001 "
					+ " then "
					+ "	select  distinct(lpp.reactorkey), lpp.updatedtimestamp, lpp.packettime, lpp.imeino, lpp.gpsstatus, lpp.packetdate+lpp.packettime, "
					+ " lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.gpsstatus, lpp.gpsstatus,mv.vehicleregno, "
					+ " lpp.gpsstatus, lpp.vehicledirection, md.deviceid,mt.vehicletypename,mss.mobilenumber,mc.customername,lpp.ignumber,lpp.tamperstatus "
					+ " from	connected_device_master as lpp "
					+ " inner join dblocator.msttbldevice as md on md.uniqueid = lpp.imeino "
					+ " inner join dblocator.msttblmappingdevices_toall as mdm on mdm.deviceid = md.deviceid "
					+ " left join dblocator.msttbldevicesimmap as msd on msd.deviceid= md.deviceid "
					+ " left join dblocator.msttblsim as mss on mss.simid=msd.simid "
					+ " inner join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
					+ " inner join dblocator.msttblvehicle as mv on mv.vehicleid = veh.vehicleid "
					+ " inner join dblocator.msttblvehicletype as mt on mt.vehicletypeid=mv.vehicletypeid "
					+ " inner join dblocator.msttbluserlogin as mu on mu.loginid = mv.loginid "
					+ " inner join dblocator.msttblcustomer as mc on mc.customerid = mu.ownersid "
					+ " where mdm.loginid='"+customerid+"'::numeric; "
					+ " else "
					+ " select  lpp.reactorkey, lpp.updatedtimestamp, lpp.packettime, lpp.imeino, lpp.gpsstatus, lpp.packetdate+lpp.packettime, "
					+ " lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.gpsstatus, lpp.gpsstatus,mv.vehicleregno, "
					+ " lpp.gpsstatus, lpp.vehicledirection, md.deviceid,mt.vehicletypename,mss.mobilenumber,mc.customername,lpp.ignumber,lpp.tamperstatus "
					+ " from	dblocator.msttblvehicle as mv "
					+ " inner join dblocator.msttbluserlogin as mu on mu.loginid = mv.loginid "
					+ " inner join dblocator.msttblcustomer as mc on mc.customerid = mu.ownersid "
					+ " inner join dblocator.msttblvehicletype as mt on mt.vehicletypeid=mv.vehicletypeid "
					+ " inner join dblocator.msttblmappingvehicles_toall as mdm on mdm.vehicleid = mv.vehicleid "
					+ " inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mdm.vehicleid "
					+ " inner join dblocator.msttbldevice as md on md.deviceid = veh.deviceid "
					+ " left join dblocator.msttbldevicesimmap as msd on msd.deviceid= md.deviceid "
					+ " left join dblocator.msttblsim as mss on mss.simid=msd.simid "
					+ " inner join connected_device_master as lpp on lpp.imeino = md.uniqueid "
					+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mdm.loginid "
					+ " where ml.companyid='" + companyid + "::numeric and mdm.loginid='"+customerid+"'::numeric and mv.isapproved = true; "
					+ " end if; "
					+ " else "
					+ " select  distinct(lpp.reactorkey), lpp.updatedtimestamp, lpp.packettime, lpp.imeino, lpp.gpsstatus, lpp.packetdate+lpp.packettime, "
					+ " lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.gpsstatus, lpp.gpsstatus,mv.vehicleregno, "
					+ " lpp.gpsstatus, lpp.vehicledirection, md.deviceid,mt.vehicletypename,mss.mobilenumber,mc.customername,lpp.ignumber,lpp.tamperstatus "
					+ " from	connected_device_master as lpp "
					+ " inner join dblocator.msttbldevice as md on md.uniqueid = lpp.imeino "
					+ " left join dblocator.msttbldevicesimmap as msd on msd.deviceid= md.deviceid "
					+ " left join dblocator.msttblsim as mss on mss.simid=msd.simid "
					+ " inner join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
					+ " inner join dblocator.msttblvehicle as mv on mv.vehicleid = veh.vehicleid "
					+ " inner join dblocator.msttblvehicletype as mt on mt.vehicletypeid=mv.vehicletypeid "
					+ " inner join dblocator.msttbluserlogin as mu on mu.loginid = mv.loginid "
					+ " inner join dblocator.msttblcustomer as mc on mc.customerid = mu.ownersid; "
					+ " end if ; ";*/
			
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			// livevehicle obj ;
			// recordid, datatimestamp, packettype, imeino, sequenceno,
			// gpsdatetime,
			// latitude, longitude, devicespeed, coarseoverground,
			// tracksatellite
			
			while (rs4.next()) {

				
				vts_Live_Vehicle obj = new vts_Live_Vehicle();
				obj.setRecordid(rs4.getString(1));
				obj.setDatatimestamp(rs4.getString(2));
				obj.setPackettype(rs4.getString(3));
				obj.setImeino(rs4.getString(4));
				obj.setSequenceno(rs4.getString(5));
				obj.setGpsdatetime(rs4.getString(6));
				obj.setLatitude(rs4.getString(7));
				obj.setLongitude(rs4.getString(8));
				obj.setVehiclespeed(rs4.getString(9));
				obj.setCoarseoverground(rs4.getString(10));
				obj.setTracksatellite(rs4.getString(11));
				obj.setGpsstatus(rs4.getString(13));
				obj.setTemperstatus(rs4.getString(20));
				obj.setIgnumber(rs4.getString(19));
//					String ign = "0";
					if(Double.parseDouble(obj.getVehiclespeed())>0.00 && obj.getIgnumber().equals("0") ){
						obj.setIgnumber("1");
					}
					//else{
//						obj.setIgnumber("0");
//						ign = "0";
//					}
//					System.out.println("val = "+obj.getTemperstatus()+" "+overspeed);
					if(Double.parseDouble(obj.getVehiclespeed())>Double.parseDouble(overspeed)){
					      ovp++;	
					}
				try{
					if(!obj.getTemperstatus().equals("NNN"))
					{
						tamper++;
					}
				}catch(Exception e){
					//System.out.println(e);
				}
					
//				obj.setGpsstatus("C");

				if (obj.getGpsstatus().contains("0")) {
					ngps++;
				} else {
					vgps++;
				}

				if (Integer.parseInt(obj.getIgnumber()) == 1) {
					onig++;
				} else {
					offig++;
				}
				java.util.Date parseTimestamp = sdf.parse(obj.getGpsdatetime());
				java.util.Date newdate = new java.util.Date();
				// System.out.println("time = "+newdate+"
				// "+obj.getDatatimestamp());
				if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
						&& (parseTimestamp.getYear() == newdate.getYear())) {
					work++;
				} else {

					nowork++;
				}

				
				
				if (obj.getIgnumber().equals("1") && Double.parseDouble(obj.getVehiclespeed()) > 0.0) {
					if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
							&& (parseTimestamp.getYear() == newdate.getYear())) {
							green++;
					}else{
						nworking++;
					}
				} else 
				{
					if (obj.getIgnumber().equals("1") && Double.parseDouble(obj.getVehiclespeed()) == 0.0) {
						if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
								&& (parseTimestamp.getYear() == newdate.getYear())) {
							yellow++;
						}else{
							nworking++;
						}
					} 
					else {
						if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
								&& (parseTimestamp.getYear() == newdate.getYear())) {
							red++;
						}else{
							nworking++;
						}
					}
				}

				total++;

			}
		//	System.out.println("data = " + vgps + " " + ngps + " " + onig + " " + offig + " " + green + " " + yellow + " " + red);
			vt.setIgnitionoff(offig);
			vt.setIgnitionon(onig);
			vt.setValidgps(vgps);
			vt.setInvalidgps(ngps);
			vt.setTotalVehicles(total);
			vt.setWorking_devices(work);
			vt.setNot_working_devices(nowork);
			vt.setGreen(green);
			vt.setRed(red);
			vt.setYellow(yellow);
			vt.setAc(0);
			vt.setTemper(tamper);
			vt.setOverspeed(ovp);
			vt.setNworking(nworking);
			return Response.status(200).entity(vt).build();
		} catch (Exception e) {
		 System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/alertdashData")
	@Produces(MediaType.APPLICATION_JSON)
	public Response alertdashData(@QueryParam("pageno") String pageno, @QueryParam("itemsPerPage") String itemsPerPage, @QueryParam("ownersid") String ownersid) {
		//System.out.println("in overspeed"+overspeed+" companyid"+companyid+" overspeed"+overspeed);
		Connection con = null;
		int rowno=1;
		List<georeport> data =new ArrayList<georeport>();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice alertdash ");
			long	start=0,end=0;
			if(Integer.parseInt(pageno)!=1){
				start = Integer.parseInt(itemsPerPage) * (Integer.parseInt(pageno)-1);
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}else{
				start = 0;
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}
			
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			
			            
			
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectDashboardAlerts2', '" + start
					+ "', '"+end+"', '"+ownersid+"', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
				 	+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			   
			String sqlselect1=" select count(*) "
					+ " from dblocator.msttblvehicle as mv "
					+ " inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid "
					+ " inner join dblocator.msttbldevice as md on veh.deviceid = md.deviceid "
					+ " inner join connected_device_master as lpp on lpp.imeino = md.uniqueid "
					+ " where  (lpp.vehiclespeed>50) and (lpp.updatedtimestamp > current_timestamp - interval '120 MINS'); ";
			
			 Statement ST=con.createStatement();
			ResultSet RS1=ST.executeQuery(sqlselect1);
			int cnt = 0;
			while(RS1.next()){
				cnt = RS1.getInt(1);
			}
			String sqlselect2=" select * from  (select mv.vehicleregno, lpp.updatedtimestamp, lpp.latitude, lpp.longitude, mv.district, mv.district, "
					+ " case when lpp.vehiclespeed>50 then 'overspeed' else 'normal' end, "
					+ " case when lpp.tamperstatus!='C' then 'tamper' else 'normal' end, '"+cnt+"', "
					+ " row_number() over (ORDER BY updatedtimestamp desc)as rownum,lpp.vehiclespeed, mc.companyname "
					+ " from dblocator.msttblvehicle as mv "
					+ "  inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid "
					+ " inner join dblocator.msttbldevice as md on veh.deviceid = md.deviceid "
					+ " inner join connected_device_master as lpp on lpp.imeino = md.uniqueid "
					+ " left join dblocator.msttbluserlogin as ml on ml.loginid = md.loginid "
					+ " left join dblocator.msttblcompany as mc on mc.companyid = ml.ownersid "
					+ " where  (lpp.vehiclespeed>50) and (lpp.updatedtimestamp > current_timestamp - interval '120 MINS') ) as a "
					+ "  where rownum between '" + start + "'::numeric and '"+end+"'::numeric; ";
			 
			
			System.out.println("query"+sqlselect2);
			//Statement st4 = con.createStatement();
			ResultSet rs4 = ST.executeQuery(sqlselect2);
			String alert = "";
			while (rs4.next()) {
				georeport obj = new georeport();
				obj.setVehicleno(rs4.getString(1));
				java.util.Date parseTimestamp = sdf.parse(rs4.getString(2));
				obj.setDatetime(sdfnew.format(parseTimestamp));
			//System.out.println(rs4.getString(1));
				//System.out.println("lat"+rs4.getString(3)+" lon"+rs4.getString(4));
				try{
					obj.setLocation(getLocationClass.getLoc(Double.parseDouble(rs4.getString(3)),
							Double.parseDouble(rs4.getString(4))));
				}catch(Exception e){
				  System.out.println(e);
				}
				if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
					obj.setLocation("Location Not Found");
				}
				
				
				
//				obj.setLocation(getLocationClass.getLoc(Double.parseDouble(rs4.getString(3)),
//						Double.parseDouble(rs4.getString(4))));
				obj.setDistrict(rs4.getString(5));
				obj.setBaseloc(rs4.getString(6));
				if(rs4.getString(7).equals("overspeed")){
					alert = "Overspeed";
				}
//				else if(rs4.getString(8).equals("tamper")){
//					alert = "Box Open";
//				}
				obj.setRowno(Integer.parseInt(rs4.getString(10)));
				rowno++;
				obj.setCount(Integer.parseInt(rs4.getString(9)));
				obj.setAlerttype(alert);
				obj.setSpeed(rs4.getString(11));
				obj.setCompanyname(rs4.getString(12));
				data.add(obj);
				alert = "";
			}
			
	
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		 System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	@GET
	@Path("/gpsdetailsdistrict")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gpsdetailsdistrict(@QueryParam("companyid") String companyid, @QueryParam("customerid") String customerid,@QueryParam("overspeed")String overspeed,@QueryParam("district")String district) {
		long ovp = 0, vgps = 0, nworking=0, tamper=0,ngps = 0, onig = 0, offig = 0, total = 0, work = 0, nowork = 0, red = 0, yellow = 0, green = 0;
		Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice gps details");
			
			String sqlselect4 = "select * from dblocator.selectprocedure('selectgpsdetailsdistrict', '" + companyid
					+ "', '"+customerid+"', '"+district+"', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			// livevehicle obj ;
			// recordid, datatimestamp, packettype, imeino, sequenceno,
			// gpsdatetime,
			// latitude, longitude, devicespeed, coarseoverground,
			// tracksatellite
			
			while (rs4.next()) {

				
				vts_Live_Vehicle obj = new vts_Live_Vehicle();
				obj.setRecordid(rs4.getString(1));
				obj.setDatatimestamp(rs4.getString(2));
				obj.setPackettype(rs4.getString(3));
				obj.setImeino(rs4.getString(4));
				obj.setSequenceno(rs4.getString(5));
				obj.setGpsdatetime(rs4.getString(6));
				obj.setLatitude(rs4.getString(7));
				obj.setLongitude(rs4.getString(8));
				obj.setVehiclespeed(rs4.getString(9));
				obj.setCoarseoverground(rs4.getString(10));
				obj.setTracksatellite(rs4.getString(11));
				obj.setGpsstatus(rs4.getString(13));
				obj.setTemperstatus(rs4.getString(20));
				obj.setIgnumber(rs4.getString(19));
//					String ign = "0";
					if(Double.parseDouble(obj.getVehiclespeed())>0.00 && obj.getIgnumber().equals("0") ){
						obj.setIgnumber("1");
					}
					//else{
//						obj.setIgnumber("0");
//						ign = "0";
//					}
//					System.out.println("val = "+obj.getTemperstatus()+" "+overspeed);
					if(Double.parseDouble(obj.getVehiclespeed())>Double.parseDouble(overspeed)){
					      ovp++;	
					}
				try{
					if(!obj.getTemperstatus().equals("NNN"))
					{
						tamper++;
					}
				}catch(Exception e){
					//System.out.println(e);
				}
					
//				obj.setGpsstatus("C");

				if (obj.getGpsstatus().contains("0")) {
					ngps++;
				} else {
					vgps++;
				}

				if (Integer.parseInt(obj.getIgnumber()) == 1) {
					onig++;
				} else {
					offig++;
				}
				java.util.Date parseTimestamp = sdf.parse(obj.getGpsdatetime());
				java.util.Date newdate = new java.util.Date();
				// System.out.println("time = "+newdate+"
				// "+obj.getDatatimestamp());
				if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
						&& (parseTimestamp.getYear() == newdate.getYear())) {
					work++;
				} else {

					nowork++;
				}

				
				
				if (obj.getIgnumber().equals("1") && Double.parseDouble(obj.getVehiclespeed()) > 0.0) {
					if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
							&& (parseTimestamp.getYear() == newdate.getYear())) {
							green++;
					}else{
						nworking++;
					}
				} else 
				{
					if (obj.getIgnumber().equals("1") && Double.parseDouble(obj.getVehiclespeed()) == 0.0) {
						if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
								&& (parseTimestamp.getYear() == newdate.getYear())) {
							yellow++;
						}else{
							nworking++;
						}
					} 
					else {
						if ((parseTimestamp.getDate() == newdate.getDate()) && (parseTimestamp.getDay() == newdate.getDay())
								&& (parseTimestamp.getYear() == newdate.getYear())) {
							red++;
						}else{
							nworking++;
						}
					}
				}

				total++;

			}
		//	System.out.println("data = " + vgps + " " + ngps + " " + onig + " " + offig + " " + green + " " + yellow + " " + red);
			vt.setIgnitionoff(offig);
			vt.setIgnitionon(onig);
			vt.setValidgps(vgps);
			vt.setInvalidgps(ngps);
			vt.setTotalVehicles(total);
			vt.setWorking_devices(work);
			vt.setNot_working_devices(nowork);
			vt.setGreen(green);
			vt.setRed(red);
			vt.setYellow(yellow);
			vt.setAc(0);
			vt.setTemper(tamper);
			vt.setOverspeed(ovp);
			return Response.status(200).entity(vt).build();
		} catch (Exception e) {
		 System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	
	@GET
	@Path("/dailyreportDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dailyreportDetails(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("fdate") String fdate,@QueryParam("tdate") String tdate,
			@QueryParam("loginid1") String loginid1) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	//System.out.println("Connected in websservice daily details summary");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// devicemapid,dealerid, dealername,
			// deviceid,devicename,salecost, credit_money, payement_mode
			String sqlselect4 = "select * from dblocator.getdailyreport('"+vehicleno+"','"+fdate+"',"
					+ "'"+tdate+"','"+loginid1+"')";
			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//			//	System.out.println(" rs = "+rs);
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("error").build();
			}

		}

		catch (Exception e) {
		//System.out.println("indeat"+e);
			return Response.status(404).entity("error").build();
		} finally {
			try {
				con.close(); //System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("/dailyreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dailyreport() {
	List<DailyReport_details> data = new ArrayList<DailyReport_details>();
	Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice ");
//			selectvstop
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdailyreport', '"
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" SELECT dv.vehicleno, dv.tdate, dv.distance, (SELECT sum(distance) FROM dblocator.dailyvehreport where vehicleno = dv.vehicleno group by vehicleno ) "
					+ " FROM dblocator.dailyvehreport as dv; ";

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			String vno = "h";  int i =0;int k=0;
			String dist="";
			while (rs4.next()) {
try
{
				
				DailyReport_details obj = new DailyReport_details();
				if(i==0){
					obj.setVehicleno(rs4.getString(1));
				}
				if(!vno.equals(rs4.getString(1))&& !dist.equals(rs4.getString(4))){
					obj.setVehicleno(rs4.getString(1));
					obj.setTotal(String.valueOf(Math.round(Double.parseDouble(rs4.getString(4)) * 100.0) / 100.0));
				}else{
					obj.setVehicleno("");
				}
			
				
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date parseTimestamp = sdf1.parse(rs4.getString(2));
//				dd.setDatereceived(sdfnew.format(parseTimestamp));
//				dd.setTrackdate(parseTimestamp.toLocaleString());
				obj.setTodate(sdfnew.format(parseTimestamp));
				//obj.setTodate(rs4.getString(2));
				obj.setDistance(String.valueOf(Math.round(Double.parseDouble(rs4.getString(3)) * 100.0) / 100.0));
				data.add(obj);
				vno=rs4.getString(1);
				dist=String.valueOf(Math.round(Double.parseDouble(rs4.getString(4)) * 100.0) / 100.0);
				i++;
//			System.out.println(rs4.getString(1)+""+rs4.getString(2)+""+rs4.getString(3)+""+rs4.getString(4));
}catch(Exception ex){System.out.println(ex);}
}
			if(data.isEmpty()){
				//System.out.println("empty data");
				return Response.status(404).entity("").build();
			}else{
//				System.out.println("data = "+data);
				return Response.status(200).entity(data).build();
			}
			
		} catch (Exception e) {
			System.out.println("e1"+e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/vehiclesummaryreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehiclesummaryDetails() {
//		if(vehicleSummaryReport(deviceid, fromdate, todate, fromtime, totime).equals("ok"))
		List<vehiclesummary> data = new ArrayList<vehiclesummary>();
//		String retval = vehicleSummaryReport(deviceid, fromdate, todate, fromtime, totime);
//	//	System.out.println(retval+"\n"+"values  ="+deviceid+" "+fromdate+" "+todate+" "+totime+" "+fromtime);
		Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice ");
//			selectvstop
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectvstop', '"
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
           String sqlselect4="  Select MinDateReceived, MaxDateReceived, MinTimeReceived, MaxTimeReceived, TripID, Latitude, Longitude, "
           		+ " DATE_PART('hour'::text, MaxDateReceived::time - MinDateReceived::time) * 60 + "
           		+ " DATE_PART('minute'::text,  MaxDateReceived::time - MinDateReceived::time) AS Stop, "
           		+ " ntoWN, NCITY,(select max(distance) From dblocator.vstop2), "
           		+ " (select '[Village:'||name||'],[Block:'||blk_name01||'],[District:'||dist_name||']' from odisha_village "
           		+ " where st_contains(geom, (ST_MakePoint(Longitude::numeric, Latitude::numeric)))=true) as loc "
           		+ " From dblocator.vstop2 where Latitude>0 and Longitude>0 order by  MinDateReceived Desc;";
			
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			// livevehicle obj ;
			// recordid, datatimestamp, packettype, imeino, sequenceno,
			// gpsdatetime,
			// latitude, longitude, devicespeed, coarseoverground,
			// tracksatellite
			while (rs4.next()) {

//		   unitid, tripid, latitude, longitude, mindatereceived, mintimereceived, 
//	       maxdatereceived, maxtimereceived, datdiffer, timdiffer, stop, 
//	       ntown, nvillage, ncity, companyid
				
//		MinDateReceived, MaxDateReceived, MinTimeReceived, MaxTimeReceived, TripID, Latitude, Longitude,     
//        Stop, ntoWN, NCITY
				try{
				vehiclesummary obj = new vehiclesummary();
				obj.setMindatereceived(rs4.getString(1));
				obj.setMaxdatereceived(rs4.getString(2));
				obj.setMintimereceived(rs4.getString(3));
				obj.setMaxtimereceived(rs4.getString(4));
				obj.setTripid(rs4.getString(5));
				obj.setLatitude(rs4.getString(6));
				obj.setLongitude(rs4.getString(7));
				obj.setStop(rs4.getString(8));
				obj.setLocation(rs4.getString(12));
//				try{
//					obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitude()),
//						Double.parseDouble(obj.getLongitude())));
//				}catch(Exception e){
//				  System.out.println(e);
//				}
//				if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
//					obj.setLocation("Location Not Found");
//				}
//				obj.setNtown(rs4.getString(8+1));
//				obj.setNcity(rs4.getString(9+1));
				obj.setDistance(rs4.getString(11));
				data.add(obj);
				}catch(Exception e){
					System.out.println(e);
				}
				
			}
			if(data.isEmpty()){
				System.out.println("in if");
				return Response.status(404).entity("").build();
			}else{
				System.out.println("in else");
				System.out.println(data);
				return Response.status(200).entity(data).build();
			}
			
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	@GET
	@Path("/vehiclesummarydetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleSummaryReport(@QueryParam("deviceid") String deviceid,
			@QueryParam("todate") String todate,@QueryParam("fromtime") String fromtime,
			@QueryParam("fromdate") String fromdate,@QueryParam("totime") String totime) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice vehicle summary details");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// devicemapid,dealerid, dealername,
			// deviceid,devicename,salecost, credit_money, payement_mode

			String sqlselect4 = "select dblocator.latestfndaysummary('"+deviceid+"','"+fromdate+"',"
					+ "'"+fromtime+"','"+totime+"','"+fromdate+"')";
			System.out.println("latestfndsummary"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				System.out.println(" rs = "+rs);
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("error").build();
			}

		}

		catch (Exception e) {
		 // System.out.println(e);
			return Response.status(404).entity("error").build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
	//
	@GET
	@Path("/devicecount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectdeviceid(@QueryParam("count") String count,@QueryParam("modelid") String modelid,
			@QueryParam("dealerid") String dealerid) {
		List<device_details> data = new ArrayList<device_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdeviceCount', '"
						+ modelid + "', '"+dealerid+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" SELECT COUNT(md.deviceid) FROM dblocator.msttbldevice  as md "
					+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
					+ " where md.flag=0 and mdm.status='available' and  md.modelid ='"+modelid+"'::numeric and mdm.dealerid ='"+dealerid+"'::numeric; ";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
			
			
//			i
//				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
//				// remark, flag, companyid, assetid, available, vendorid
			while (rs4.next()) {
					try {
						device_details obj=new device_details();
						obj.setCountno(Integer.parseInt(rs4.getString(1)));
						data.add(obj);
						int devicecount= obj.getCountno();
					//	System.out.println("count devicecount"+devicecount);
						if(devicecount >=Integer.parseInt(count))
						{
					//	System.out.println("success model");
					
						// deviceiddetails(modelid);
						return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
						}
						else
						{
						//	System.out.println("not Avialiable");
							return Response.status(400).entity("{\"success\": false,\"error\": \"Devices Not Available\",\"error_code\": 404}").build();
						}
						
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
		}
			
			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  //System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/devicecountcustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectdeviceidcustomer(@QueryParam("count") String count,@QueryParam("modelid") String modelid,
			@QueryParam("loginid") String loginid) {
		List<device_details> data = new ArrayList<device_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdeviceCountcustomer', '"
						+ modelid + "', '"+loginid+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			    
			String sqlselect4=" SELECT COUNT(*) FROM dblocator.msttbldevice  where flag=0 and status_customer='available' and  modelid ='"+modelid+"'::numeric; ";
			     
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//	System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				
				boolean avail=false;;
			
			
//			i
//				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
//				// remark, flag, companyid, assetid, available, vendorid
			while (rs4.next()) {
					try {
						device_details obj=new device_details();
						obj.setCountno(Integer.parseInt(rs4.getString(1)));
						data.add(obj);
						int devicecount= obj.getCountno();
					//	System.out.println("count devicecount"+devicecount);
						if(devicecount >=Integer.parseInt(count))
						{
					//	System.out.println("success model");
					
						// deviceiddetails(modelid);
						return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
						}
						else
						{
						//	System.out.println("not Avialiable");
							return Response.status(400).entity("{\"success\": false,\"error\": \"Devices Not Available\",\"error_code\": 404}").build();
						}
						
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
		}
			
			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		 // System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/devicemanager")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicemanager() {
		List<devicemanager> data = new ArrayList<devicemanager>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectdevicemanager', '"
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			//	System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
			
			
//			i
//				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
//				// remark, flag, companyid, assetid, available, vendorid
			while (rs4.next()) {
					try {
						devicemanager obj=new devicemanager();
						obj.setImeino(rs4.getString(1));
						obj.setReactorkey(rs4.getString(2));
						obj.setClientkey(rs4.getString(3));
						obj.setTimestmp(rs4.getString(4));
						data.add(obj);
						
						
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
		}
			
			return Response.status(200).entity(data).build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("/rawdata")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectRawData(@QueryParam("pageno") String pageno,
			@QueryParam("itemsPerPage") String itemsPerPage,@QueryParam("imeino") String imeino,@QueryParam("date")String date) {
		List<rawdata> data = new ArrayList<rawdata>();
		Connection con = null;
		int start=0,end=0,count=0;
		try {
			
			if(Integer.parseInt(pageno)!=1){
				start = Integer.parseInt(itemsPerPage) * (Integer.parseInt(pageno)-1);
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}else{
				start = 0;
				end = start + Integer.parseInt(itemsPerPage);
				start++;
			}
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectRawData', '"
						+  start+"', '"+end+"', '"+imeino+"', '', '', '"+date+"', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/ 
			   String sqlselect4=" SELECT count(*)  "
			   		+ " FROM devicedata_logs where datamessage like '%'||'"+imeino+"'||'%' and datatimestamp::date='"+date+"'::date; ";
			   int cnt = 0;
			   Statement ST=con.createStatement();
			   ResultSet RS1=ST.executeQuery(sqlselect4);
				while(RS1.next()){
					cnt = RS1.getInt(1);
				}
			   sqlselect4 =	 " select * from "
			   				+ " ( SELECT recordid, datatimestamp, datamessage, clientfqdn, clientport, "+cnt
			   				+ ", row_number() over (ORDER BY datatimestamp desc)as rownum "
			   				+ " FROM devicedata_logs_ where datamessage like '%'||'"+imeino+"'||'%'  and datatimestamp::date='"+date+"'::date) as a "
			   				+ " where a.rownum between '" + start + "'::integer and '"+end+"'::integer and  datatimestamp::date='"+date+"'::date;  "; 
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a");
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
			
			
//			i
//				recordid, datatimestamp, datamessage, clientfqdn, clientport
				while (rs4.next()) {
					try {
						rawdata obj = new rawdata();
						obj.setRecordid(rs4.getString(1));
						
						java.util.Date parseTimestamp = sdf.parse(rs4.getString(2));
						obj.setDatatimestamp(sdfnew.format(parseTimestamp).toString());
						obj.setDatamessage(rs4.getString(3));
						obj.setClientfqdn(rs4.getString(4));
						obj.setClientport(rs4.getString(5));
						count = Integer.parseInt(rs4.getString(6));
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
				rawdet rr =new rawdet();
				rr.setData(data);
				rr.setTotal_count(count);
		//	System.out.println("values"+data);
			return Response.status(200).entity(rr).build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/deviceIddetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deviceiddetails(@QueryParam("modelid") String modelid) {
		List<device_details> data = new ArrayList<device_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			 System.out.println("Connected in websservice deviceIDdetails ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectdeviceid', '"
						+ modelid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				
			
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					try {
						device_details obj = new device_details();
						obj.setDeviceid(Long.parseLong(rs4.getString(1)));
						obj.setUniqueid(rs4.getString(2));
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
		//	System.out.println("values"+data);
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  //System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/tripdet")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tripdet(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<tripdet> data = new ArrayList<tripdet>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selecttripdetmap', '"+vehicleno+"', '"+todate+"', '"+fromdate+"', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
//			routename, source, destination, starttime,
//		     passvalid, minename, quantity, tripid, transportername
			
			while (rs4.next()) {
				
				try {
					tripdet obj = new tripdet();
					obj.setEtpno(rs4.getString(1));
					obj.setRoutename(rs4.getString(2));
					obj.setSource(rs4.getString(3));
					obj.setDestination(rs4.getString(4));
					obj.setStarttime(rs4.getString(5));
					obj.setPassvalid(rs4.getString(6));
					obj.setMinename(rs4.getString(7));
					obj.setQuantity(rs4.getString(8));
					obj.setTripid(rs4.getString(9));
					obj.setTransportername(rs4.getString(10));
					data.add(obj);
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	@GET
	@Path("/minereport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minereport(@QueryParam("mine") String mine,
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<minereport_det> data = new ArrayList<minereport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.getdistrictwise('getLocation', '"+mine+"', '"+fromdate+"', '"+todate+"', '', '')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			"L11160000111/12";"35";"OD21D7455";"ESSEL MINING, KASHIA";"Route1";"2017-01-02 21:57:47";"2017-01-15 21:57:47";"287"
			
			while (rs4.next()) {
				
				try {
					minereport_det obj = new minereport_det();
					obj.setEtpno(rs4.getString(1));
					obj.setTrips(rs4.getString(2));
					obj.setVehicleno(rs4.getString(3));
					obj.setDestination(rs4.getString(4));
					obj.setRoutename(rs4.getString(5));
					obj.setSource(mine);
					obj.setStarttime(rs4.getString(6));
					obj.setEndtime(rs4.getString(7));
					obj.setRdev(rs4.getString(8));
					obj.setIndex(index);
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/getroutetime")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getroutetime(@QueryParam("route") String route,
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<minereport_det> data = new ArrayList<minereport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.getroutetime('"+route+"', '"+fromdate+"', '"+todate+"', '')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			vehicleno1 text, outtype text, outtime timestamp without time zone, intype text, 
//			intime timestamp without time zone, time2 numeric, etpno text, source text, destination text, starttime text, endtime text
			
			while (rs4.next()) {
				int ind = 1;
				try {
					minereport_det obj = new minereport_det();
					obj.setVehicleno(rs4.getString(ind++));
					obj.setOuttype(rs4.getString(ind++));
					obj.setOuttime(rs4.getString(ind++));
					obj.setIntype(rs4.getString(ind++));
					obj.setIntime(rs4.getString(ind++));
					obj.setTime(rs4.getString(ind++));
					obj.setEtpno(rs4.getString(ind++));
					obj.setSource(rs4.getString(ind++));
					obj.setDestination(rs4.getString(ind++));
					obj.setStarttime(rs4.getString(ind++));
					obj.setEndtime(rs4.getString(ind++));
					obj.setTtime(rs4.getString(ind++));
					obj.setTripcount(rs4.getString(ind++));
					obj.setIndex(index);
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/etptripdet")
	@Produces(MediaType.APPLICATION_JSON)
	public Response etptripdet(
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<minereport_det> data = new ArrayList<minereport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectTripDetailsReport', '"
					+ fromdate+ " 00:00:00', '"+todate+" 23:00:00', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select etpno, vehicleno, routename, source, destination, starttime, passvalid, mt.datetimestamp from dblocator.msttbltripdetails as mt "
					+ " inner join dblocator.msttblvehicle as mv on mv.vehicleregno = mt.vehicleno "
			        + " where starttime between '"+ fromdate+ " 00:00:00'::timestamp and '"+todate+" 23:00:00'::timestamp; ";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			"L11160000111/12";"35";"OD21D7455";"ESSEL MINING, KASHIA";"Route1";"2017-01-02 21:57:47";"2017-01-15 21:57:47";"287"
//			etpno, vehicleno, routename, source, destination, starttime, passvalid, datetimestamp
			while (rs4.next()) {
				
				try {
					minereport_det obj = new minereport_det();
					obj.setEtpno(rs4.getString(1));
					obj.setVehicleno(rs4.getString(2));
					obj.setRoutename(rs4.getString(3));
					obj.setSource(rs4.getString(4));
					obj.setDestination(rs4.getString(5));
					obj.setStarttime(rs4.getString(6));
					obj.setEndtime(rs4.getString(7));
					obj.setRectime(rs4.getString(8));
					obj.setIndex(index);
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/tripveh")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tripveh() {
		List<tripveh> data = new ArrayList<tripveh>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selectTripDetailsDash', '"
					+ " ', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			"L11160000111/12";"35";"OD21D7455";"ESSEL MINING, KASHIA";"Route1";"2017-01-02 21:57:47";"2017-01-15 21:57:47";"287"
//			etpno, vehicleno, routename, source, destination, starttime, passvalid, datetimestamp
			while (rs4.next()) {
				try {
					tripveh obj = new tripveh();
					obj.setCount(rs4.getString(1));
					obj.setVehicleno(rs4.getString(2));
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/minereporttruck")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minereporttruck(@QueryParam("mine") String mine,
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<minereport_det> data = new ArrayList<minereport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.getdistrictwisetruck('getLocation', '"+mine+"', '"+fromdate+"', '"+todate+"', '', '')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			"L11160000111/12";"35";"OD21D7455";"ESSEL MINING, KASHIA";"Route1";"2017-01-02 21:57:47";"2017-01-15 21:57:47";"287"
			
			while (rs4.next()) {
				
				try {
					minereport_det obj = new minereport_det();
					obj.setTripcount(rs4.getString(1));
					obj.setVehcount(rs4.getString(2));
					obj.setEtpno(rs4.getString(3));
					obj.setTrips(rs4.getString(4));
					obj.setVehicleno(rs4.getString(5));
					obj.setDestination(rs4.getString(6));
					obj.setRoutename(rs4.getString(7));
					obj.setSource(mine);
					obj.setStarttime(rs4.getString(8));
					obj.setEndtime(rs4.getString(9));
					obj.setRdev(rs4.getString(10));
					obj.setIndex(index);
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}
			System.out.println("length = "+data.size());
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/minereportrdev")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minereportrdev(@QueryParam("mine") String mine,
			@QueryParam("todate") String todate, @QueryParam("fromdate") String fromdate) {
		List<minereport_det> data = new ArrayList<minereport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.getdistrictwiserdev('getLocation', '"+mine+"', '"+fromdate+"', '"+todate+"', '', '')";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int index = 1;
//			"L11160000111/12";"35";"OD21D7455";"ESSEL MINING, KASHIA";"Route1";"2017-01-02 21:57:47";"2017-01-15 21:57:47";"287"
			
			while (rs4.next()) {
				
				try {
					minereport_det obj = new minereport_det();
					obj.setTripcount(rs4.getString(1));
					obj.setVehcount(rs4.getString(2));
					obj.setEtpno(rs4.getString(3));
					obj.setTrips(rs4.getString(4));
					obj.setVehicleno(rs4.getString(5));
					obj.setDestination(rs4.getString(6));
					obj.setRoutename(rs4.getString(7));
					obj.setSource(mine);
					obj.setStarttime(rs4.getString(8));
					obj.setEndtime(rs4.getString(9));
					obj.setRdevtime(rs4.getString(10));
					obj.setIndex(index);
					data.add(obj);
					index++;
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/getdealerreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getdealerreport(@QueryParam("loginid") String loginid,
			@QueryParam("companyid") String companyid) {
		List<dealerreport_det> data = new ArrayList<dealerreport_det>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdealerreport', '"+loginid+"', '"+companyid+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select dealername, total, installed, polling, (total::integer)-(polling::integer), companyname from "
					+ " (select companyname, dealername, count(md.deviceid) as total, count(veh.vehicleid) as installed, count(cdm.imeino) as polling "
					+ " from dblocator.msttbldevice as md "
					+ "	left join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
					+ " left join connected_device_master as cdm on cdm.imeino = md.uniqueid and cdm.updatedtimestamp::date=current_date  "
					+ "	inner join dblocator.msttblmappingdevices_toall as mdm on mdm.deviceid = md.deviceid "
					+ "	inner join dblocator.msttbluserlogin as ml on ml.loginid = mdm.loginid "
					+ " inner join dblocator.msttbldealer as mdd on mdd.dealerid = ml.ownersid "
					+ "	left join dblocator.msttbluserlogin as ml2 on ml2.loginid = mdd.loginid "
					+ " left join dblocator.msttblcompany as mc on mc.companyid = ml2.ownersid "
					+ "	inner join dblocator.msttblmappingdealers_toall as dm on dm.dealerid = mdd.dealerid "
					+ " where dm.loginid = '"+loginid+"'::numeric group by mdd.dealername, mc.companyname) as a; ";
			System.out.println(sqlselect4);
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
//			routename, source, destination, starttime,
//		     passvalid, minename, quantity, tripid, transportername
			
			while (rs4.next()) {
				
				try {
					dealerreport_det obj = new dealerreport_det();
					obj.setDealer(rs4.getString(1));
					obj.setTotal(rs4.getString(2));
					obj.setInstalled(rs4.getString(3));
					obj.setPolling(rs4.getString(4));
					obj.setNotpolling(rs4.getString(5));
					obj.setCompany(rs4.getString(6));
					data.add(obj);
				} catch (Exception e) {
					System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	@GET
	@Path("/getallvehicles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response aalVehicleDetails(@QueryParam("ownersid") String ownersid) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("ownersid = "+ownersid);
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectallvehicles', '"
						+ ownersid+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11
							
						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
							}
//							 mt.etpno, mt.minename, mt.quantity, mt.source, mt.destination,
//							 mt.routename, mt.starttime, mt.endtime, mt.transportername
							try{
								dd.setEtpno(rs4.getString(18));
								dd.setMinename(rs4.getString(19));
								dd.setQuantity(rs4.getString(20));
								dd.setSource(rs4.getString(21));
								dd.setDestination(rs4.getString(22));
								dd.setRoutename(rs4.getString(23));
								dd.setStarttime(rs4.getString(24));
								dd.setEndtime(rs4.getString(25));
								dd.setTransportername(rs4.getString(26));
								}catch(Exception e){
									//dd.setDistrict("");
								}
							dd.setCompany(rs4.getString(27));
							dd.setStatus(true);
							
							//dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
				
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/getallvehiclesbycircle")
	@Produces(MediaType.APPLICATION_JSON)
	public Response aalVehicleDetailsByCircle(@QueryParam("ownersid") String ownersid, @QueryParam("circle") String circle) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("ownersid = "+ownersid);
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectallvehiclesbycircle', '"
						+ ownersid+ "', '"+circle+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				//System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11
							
						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
							}
//							 mt.etpno, mt.minename, mt.quantity, mt.source, mt.destination,
//							 mt.routename, mt.starttime, mt.endtime, mt.transportername
							try{
								dd.setEtpno(rs4.getString(18));
								dd.setMinename(rs4.getString(19));
								dd.setQuantity(rs4.getString(20));
								dd.setSource(rs4.getString(21));
								dd.setDestination(rs4.getString(22));
								dd.setRoutename(rs4.getString(23));
								dd.setStarttime(rs4.getString(24));
								dd.setEndtime(rs4.getString(25));
								dd.setTransportername(rs4.getString(26));
								}catch(Exception e){
									//dd.setDistrict("");
								}
							dd.setCompany(rs4.getString(27));
							dd.setStatus(true);
							
							//dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
				
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/selectambulance_details")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectambulance_details() {
		List<districtambulance_details> data = new ArrayList<districtambulance_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
				String sqlselect4 = "select * from dblocator.selectprocedure('selectAmbulanceDistrict_details', '" 
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println("selecrambulancedetails"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
			
				String amb_id;
				String ambbyno;
				String district;
				String amb_location;
				String vehicle_mobno;
				String eme;
				String eme_mobileno;
				String vehicleregno;
				String vehicleid;		
				
				
				while (rs4.next()) {
					districtambulance_details obj = new districtambulance_details();
					obj.setAmb_id(rs4.getString(1));
					obj.setAmbbyno(rs4.getString(2));
					obj.setDistrict(rs4.getString(3));
					obj.setAmb_location(rs4.getString(4));
					obj.setVehicle_mobno(rs4.getString(5));
					obj.setEme(rs4.getString(6));
					obj.setEme_mobileno(rs4.getString(7));
					obj.setVehicleregno(rs4.getString(8));
					obj.setVehicleid(rs4.getString(9));
					obj.setRowno(indexno);
					indexno++;

					
					data.add(obj);
					//System.out.println("lenght"+data.size());

				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/assignvehicle_district")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assignvehicle_district(@QueryParam("ambby_no") String ambby_no, @QueryParam("district") String district,
			@QueryParam("ambul_loctaion") String ambul_loctaion, @QueryParam("vehicle_mobno") String vehicle_mobno,@QueryParam("eme")String eme,@QueryParam("eme_mobno")String eme_mobno) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice amb insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// roleid, rolename, companyid, loginid, datetimestamp, remarks,
			// flag
			
			 
			String sqlselect4 = "select * from dblocator.insertprocedure('insertAmbulance_details', '0', '" +ambby_no+ "', " + "'"
					+ district + "', '" +ambul_loctaion+ "', '" +vehicle_mobno+ "', '" + eme + "', "
					+ "'"+eme_mobno+"', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		
			System.out.println("ambulance insert"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			ResultSet rs = ps.executeQuery();
			
			return Response.status(200).entity("{\"success\": false,\"error\": \"ok\",\"success_code\": 202}").build();

		} catch (Exception e) {
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			System.out.println(e);
			return Response.status(404).entity(det)
					.build();
		} finally {
			try {
				con.close(); // c
			} catch (SQLException e) {
				System.out.println("errrr");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	@GET
	@Path("/editassignvehicle_district")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editassignvehicle_district(@QueryParam("amb_id")String amb_id,@QueryParam("ambby_no") String ambby_no, @QueryParam("district") String district,
			@QueryParam("ambul_loctaion") String ambul_loctaion, @QueryParam("vehicle_mobno") String vehicle_mobno,@QueryParam("eme")String eme,@QueryParam("eme_mobno")String eme_mobno) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice amb insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// roleid, rolename, companyid, loginid, datetimestamp, remarks,
			// flag
			
			 
			String sqlselect4 = "select * from dblocator.insertprocedure('insertAmbulance_details', '"+amb_id+"', '" +ambby_no+ "', " + "'"
					+ district + "', '" +ambul_loctaion+ "', '" +vehicle_mobno+ "', '" + eme + "', "
					+ "'"+eme_mobno+"', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		
			System.out.println("ambulance update"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			ResultSet rs = ps.executeQuery();
			
			return Response.status(200).entity("{\"success\": false,\"error\": \"ok\",\"success_code\": 202}").build();

		} catch (Exception e) {
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			System.out.println(e);
			return Response.status(404).entity(det)
					.build();
		} finally {
			try {
				con.close(); // c
			} catch (SQLException e) {
				System.out.println("errrr");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@GET
	@Path("/selectdistrict")
	@Produces(MediaType.APPLICATION_JSON)
	public Response selectdistric() {
		List<district_details> data = new ArrayList<district_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledetails 1");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectdistrict', '" 
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println("veh"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				while (rs4.next()) {
					district_details obj = new district_details();
					obj.setDistrict(rs4.getString(1));
					obj.setRowno(indexno);
					indexno++;

					
					data.add(obj);
					//System.out.println("lenght"+data.size());

				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/getallvehiclesbydist")
	@Produces(MediaType.APPLICATION_JSON)
	public Response aalVehicleDetailsByDist(@QueryParam("ownersid") String ownersid, @QueryParam("district") String district) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("ownersid = "+ownersid);
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectallvehiclesbydist', '"
						+ ownersid+ "', '"+district+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				System.out.println(sqlselect4);
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11
							
						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
							}
							//dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
				
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public String getIGN(String val){
		if(val.equals("1")){
			return "ON";
		}else{
			return "OFF";
		}
	}
	
	public String getGPS(String val){
		if(val.equals("1")){
			return "VALID";
		}else{
			return "INVALID";
		}
	}
	
	public String getDir(int head){
		  String dir;
		  if(head>=345 && head<15){
		      dir="North";
		  }
		  else if(head>=15 && head<45){
		      dir="North-West";
		  }
		  else if(head>=45 && head<75){
		      dir="North-West";
		  }
		  else if(head>=75 && head<150){
		      dir="West";
		  }
		  else if(head>=105 && head<135){
		       dir="South-West";
		  }
		  else if(head>=135 && head<165){
		      dir="South-West";
		  }
		  else if(head>=165 && head<195){
		      dir="South";
		  }
		  else if(head>=195 && head<225){
		      dir="South-East";
		  }
		  else if(head>=225 && head<255){
		      dir="South-East";
		  }
		  else if(head>=255 && head<285){
		      dir="East";
		  }
		  else if(head>=285 && head<315){
		      dir="North-East";
		  }
		  else if(head>=315 && head<345){
		      dir="North-East";
		  }
		  else {
		      dir="North";
		  }
		  return(dir);
		}

	
	
	@GET
	@Path("/getlivevehicles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response liveVehicleDetails(@QueryParam("vehicleno") String vehicleno) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice device "+vehicleno);
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectvehiclelive', '"
						+ vehicleno+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				
//			//	System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11

						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
							}
							try{
							dd.setEtpno(rs4.getString(18));
							dd.setMinename(rs4.getString(19));
							dd.setQuantity(rs4.getString(20));
							dd.setSource(rs4.getString(21));
							dd.setDestination(rs4.getString(22));
							dd.setRoutename(rs4.getString(23));
							dd.setStarttime(rs4.getString(24));
							dd.setEndtime(rs4.getString(25));
							dd.setTransportername(rs4.getString(26));
							}catch(Exception e){
								dd.setDistrict("");
							}
							dd.setCompany(rs4.getString(27));
							//dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
				
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  //System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/gethistoryvehicles")
	@Produces(MediaType.APPLICATION_JSON)
	public Response historyVehicleDetails(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("date") String date, @QueryParam("time1") String time1,
			@QueryParam("time2") String time2) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat(
		            "dd-MMM-yyyy HH:mm:ss");
			
			SimpleDateFormat dateFormat2 = new SimpleDateFormat(
		            "yyyy-MM-dd HH:mm:ss");

		    Date parsedTimeStamp = dateFormat.parse(date+" "+time1);
		    Date parsedTimeStamp1 = dateFormat.parse(date+" "+time2);

		    Timestamp timest1 = new Timestamp(parsedTimeStamp.getTime());
		    Timestamp timest2 = new Timestamp(parsedTimeStamp1.getTime());
			
			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice device ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectvehiclehistory', '"
						+ vehicleno+ "', '"+timest1+"', '"+timest2+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11

						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0 && rs4.getDouble(8+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
								System.out.println("e = " + e);
							}
							try{
								dd.setEtpno(rs4.getString(18));
								dd.setMinename(rs4.getString(19));
								dd.setQuantity(rs4.getString(20));
								dd.setSource(rs4.getString(21));
								dd.setDestination(rs4.getString(22));
								dd.setRoutename(rs4.getString(23));
								dd.setStarttime(rs4.getString(24));
								dd.setEndtime(rs4.getString(25));
								dd.setTransportername(rs4.getString(26));
								}catch(Exception e){
									dd.setDistrict("");
									System.out.println("e = " + e);
								}
							dd.setHistoryicon(reticon(rs4.getInt(13)));
//							dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
				System.out.println(data.size());
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/gethistorytrip")
	@Produces(MediaType.APPLICATION_JSON)
	public Response gethistorytrip(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("fromdate") String fromdate, @QueryParam("todate") String todate) {
		List<DataReceived_Dump1> data = new ArrayList<DataReceived_Dump1>();
		Connection con = null;
		try {
			
			SimpleDateFormat dateFormat2 = new SimpleDateFormat(
		            "yyyy-MM-dd HH:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice device ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectvehiclehistory', '"
						+ vehicleno+ "', '"+fromdate+"', '"+todate+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
//						lpp.recordid, lpp.datatimestamp, lpp.packettype, lpp.imeino, lpp.packetdate, lpp.packettime, 
//							0				1					2				3				4				5
//						lpp.latitude, lpp.longitude, lpp.vehiclespeed, lpp.temperstatus, lpp.checksum,mv.vehicleregno 
//							6					7			8					9					10					11

						if (rs4.getDouble(6+1) > 0 && rs4.getDouble(7+1) > 0 && rs4.getDouble(8+1) > 0) {
							DataReceived_Dump1 dd = new DataReceived_Dump1();
							dd.setUnitid(rs4.getString(3+1));
							dd.setVehical_no(rs4.getString(11+1));
							SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss a");
							java.util.Date parseTimestamp = sdf1.parse(rs4.getString(1+1));
							dd.setDatereceived(sdfnew.format(parseTimestamp));
							dd.setTrackdate(parseTimestamp.toLocaleString());
							dd.setCheckdate(dateFormat2.format(parseTimestamp));
//							System.out.println(dd.getCheckdate());
							dd.setLatitude(rs4.getString(6+1));
							dd.setLongitude(rs4.getString(7+1));
							dd.setSpeed(rs4.getDouble(8+1));
							dd.setHeadings(getDir(rs4.getInt(13)));
							dd.setGpsstatus(getGPS(rs4.getString(14)));
							dd.setIgnnumber(getIGN(rs4.getString(15)));
							if(rs4.getString(15).equals("0") && dd.getSpeed() > 0.00){
								dd.setIgnnumber(getIGN("1"));
							}
							dd.setVtype(rs4.getString(16));
							try{
								dd.setDistrict(rs4.getString(17));
							}catch(Exception e){
								dd.setDistrict("");
							}
							try{
								dd.setEtpno(rs4.getString(18));
								dd.setMinename(rs4.getString(19));
								dd.setQuantity(rs4.getString(20));
								dd.setSource(rs4.getString(21));
								dd.setDestination(rs4.getString(22));
								dd.setRoutename(rs4.getString(23));
								dd.setStarttime(rs4.getString(24));
								dd.setEndtime(rs4.getString(25));
								dd.setTransportername(rs4.getString(26));
								}catch(Exception e){
									dd.setDistrict("");
								}
							dd.setHistoryicon(reticon(rs4.getInt(13)));
//							dd.setLocation(getLocationClass.getLoc(Double.parseDouble(dd.getLatitude()), Double.parseDouble(dd.getLongitude())));
							data.add(dd);
						}
						
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
				System.out.println(data.size());
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/companywisedevicedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companywisedevicedetails(){
		
			List<total_devices> data = new ArrayList<total_devices>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//				 System.out.println("Connected in websservice deviceIDdetails count ");
				
					/*String sqlselect4 = "select * from dblocator.selectprocedure('companywisedevicedetailstotal', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
					
				String sqlselect4 =" select comp, total, polling,  (installed - polling) as non, installed, tp, sold "
						+ " from (select mc.companyname as comp, count(md.deviceid) as total,"
						+ " (select count(*) from  dblocator.msttbldevice as md1 "
						+ " inner join connected_device_master as cmd1 on cmd1.imeino = md1.uniqueid and cmd1.updatedtimestamp::date=current_date "
						+ " inner join dblocator.msttblvehicleassigngps as veh1 on veh1.deviceid = md1.deviceid "
						+ " inner join dblocator.msttbluserlogin as ml2 on ml2.loginid = md1.loginid "
						+ " where ml2.companyid = mc.companyid ) as polling,"
						+ " count(veh.deviceid) as installed, "
						+ "(select count(*) from dblocator.msttbltripdetails as mt "
						+ " inner join dblocator.msttblvehicle as mv on mv.vehicleregno = mt.vehicleno "
						+ " inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid "
						+ " inner join dblocator.msttbldevice as md on md.deviceid = veh.deviceid "
						+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = md.loginid "
						+ " where ml.ownersid = mc.companyid and mt.starttime::date=current_date) as tp,"
						+ " (select count(mfm.deviceid) from dblocator.msttbldevice as kk "
						+ " left join dblocator.msttbldealerdevice_assign as mfm on mfm.deviceid = kk.deviceid and mfm.dealerid != 610011 "
						+ " and mfm.dealerid not in (select mj.companyid from dblocator.msttblcompany as mj)"
						+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = kk.loginid"
						+ " where ml.companyid = mc.companyid )as sold "
						+ " from dblocator.msttbldevice as md "
						+ " left join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
						+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = md.loginid "
						+ " inner join dblocator.msttblcompany as mc on mc.companyid = ml.ownersid "
						+ " left join connected_device_master as cmd on cmd.imeino = md.uniqueid and cmd.updatedtimestamp::date=current_date "
						+ " left join dblocator.msttblvehicle as mv on mv.vehicleid = veh.vehicleid"
//						+ " left join dblocator.msttbldealerdevice_assign as mfm on mfm.deviceid = md.deviceid and mfm.dealerid != 610011 "
//						+ " and mfm.dealerid not in (select mj.companyid from dblocator.msttblcompany as mj) "
						+ " group by mc.companyid) as k ;";
						
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					System.out.println("in companywise device"+sqlselect4);
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
				
				while (rs4.next()) {
						try {
							total_devices obj=new total_devices();	
							obj.setCompanyName(rs4.getString(1));
							obj.setTotalDevices(rs4.getString(2));
							obj.setPollingDevices(rs4.getString(3));	
							obj.setNonPolllingDevices(rs4.getString(4));
							obj.setInstalledDevices(rs4.getString(5));
							obj.setAvailabledevices(String.valueOf((Integer.parseInt(rs4.getString(2)))-(Integer.parseInt(rs4.getString(5)))));
							obj.setTripcount(rs4.getString(6));
							obj.setSold(rs4.getString(7));
							//obj.setAvailabledevices(rs4.getString(7));
							//obj.setInactiveDevices(rs4.getString(8));
						
							data.add(obj);
						
						} catch (Exception e) {
							System.out.println("e = " + e);
						}
			}
				return Response.status(200).entity(data).build();
//				return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
				
			
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	
	
	
	@GET
	@Path("/companywisecount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companywisecount(@QueryParam("companyid") String companyid){
		
			List<total_devices> data = new ArrayList<total_devices>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//				 System.out.println("Connected in websservice deviceIDdetails count ");
				
					/*String sqlselect4 = "select * from dblocator.selectprocedure('companywisecount', '"+companyid+"', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
				
				String sqlselect4="  select compname, total, polling, (total::integer)-(polling::integer), installed from "
						+ " (select "
						+ " (count(md.deviceid)) as total, "
						+ " count(cmd.imeino) as polling, "
						+ " mc.companyname as compname, count(veh.deviceid) as installed "
						+ " from dblocator.msttbldevice as md "
						+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = md.loginid "
						+ " inner join dblocator.msttblcompany as mc on mc.companyid =ml.ownersid "
						+ " left  join connected_device_master as cmd on cmd.imeino=md.uniqueid "
						+ " left  join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
						+ " where mc.companyid = '"+companyid+"'::numeric "
						+ " group by mc.companyid order by mc.companyid) as a;	 ";
						
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
					System.out.println("in companywise device"+sqlselect4);
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
				
				while (rs4.next()) {
						try {
							total_devices obj=new total_devices();	
							obj.setCompanyName(rs4.getString(1));
							obj.setTotalDevices(rs4.getString(2));
							obj.setPollingDevices(rs4.getString(3));	
							obj.setNonPolllingDevices(rs4.getString(4));
							obj.setInstalledDevices(rs4.getString(5));
						
							
						
							data.add(obj);
						
						} catch (Exception e) {
							System.out.println("e = " + e);
						}
			}
				return Response.status(200).entity(data).build();
//				return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
				
			
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	}
	
	
	
	
	@GET
	@Path("/companywisedealercount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companywisedealercount(@QueryParam("controlid") String controlid, @QueryParam("companyid") String companyid) {
		List<dealer_count> data = new ArrayList<dealer_count>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice device ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
			
				String sqlselect4 = "select * from dblocator.selectprocedure('selectcompanydetcount', '"
						+ controlid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println(sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=0, dealercnt=0, custcnt=0;
				String compname = null;

				while (rs4.next()) {
					 if(indexno==0){
						 compname = rs4.getString(1);
					 }
					 if(!compname.equals(rs4.getString(1))){
						 dealer_count obj = new dealer_count();
						 obj.setCompanyname(compname);
						 obj.setDealercount(String.valueOf(dealercnt));
						 obj.setCustomercount(String.valueOf(custcnt));
						 //System.out.println(compname+" "+dealercnt+" "+custcnt);
						 data.add(obj);
						 dealercnt = 0;
						 custcnt = 0;
					 }
						 dealercnt = dealercnt + Integer.parseInt(rs4.getString(2));
						 custcnt = custcnt + Integer.parseInt(rs4.getString(3));
					 compname = rs4.getString(1);
					 indexno++;
				}
				
				 dealer_count obj = new dealer_count();
				 obj.setCompanyname(compname);
				 obj.setDealercount(String.valueOf(dealercnt));
				 obj.setCustomercount(String.valueOf(custcnt));
				 data.add(obj);
				// System.out.println(compname+" "+dealercnt+" "+custcnt);
				
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
		 	return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/companywisedevicecount")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companywisedevicecount(@QueryParam("controlid") String controlid, @QueryParam("companyid") String companyid) {
		List<device_count> data = new ArrayList<device_count>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice device ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectcompanydevicecount', '"
						+ controlid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			
			String sqlselect4=" select * from (select mc.companyname, count(md.deviceid) as total,count(veh.deviceid),0 "
					+ " from dblocator.msttblcompany as mc "
					+ " inner join dblocator.msttbluserlogin as ml on ml.ownersid = mc.companyid "
					+ " left join dblocator.msttbldevice as md on md.loginid = ml.loginid "
					+ " left join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
					+ " group by mc.companyname,ml.ownersid)as a where total>0;";
				
				System.out.println(sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				

				while (rs4.next()) {
						 device_count obj = new device_count();
						 obj.setCompanyname(rs4.getString(1));
						 obj.setTotaldevices(rs4.getString(2));
						 obj.setSolddevices(rs4.getString(3));
						 obj.setAvaildevices(String.valueOf((Integer.parseInt(rs4.getString(2)))-(Integer.parseInt(rs4.getString(3)))));
						 data.add(obj);
				}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
		 	return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	@GET
	@Path("/devicedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deviceDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchBydeviceno")String searchBydeviceno) {
		List<device_details> data = new ArrayList<device_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice device ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
			if(searchBydeviceno==null)
			{
				searchBydeviceno="";
				
			}
				/*String sqlselect4 = "select * from dblocator.selectbcudevice('selectBCUDevice', '"
						+ loginid + "', '"+searchBydeviceno+"', '', '', '', '', '', '', "
						+ "'')";*/
			String sqlselect4=" select * from(select distinct(d.deviceid), d.makeid,d.modelid,d.uniqueid, d.loginid, d.datetimestamp, d.remark, d.flag, "
					+ " m.makename,mk.modelname,d.status_sim,d.status_veh, mda.status, d.status_customer, "
					+ " mpd.flag, "
					+ " (select ((DATE_PART('day', current_timestamp::timestamp - updatedtimestamp::timestamp) * 24 + "
					+ "  DATE_PART('hour',current_timestamp::timestamp - updatedtimestamp::timestamp)) * 60 + "
					+ " DATE_PART('minute', current_timestamp::timestamp - updatedtimestamp::timestamp)) from connected_device_master where imeino = d.uniqueid) as min, "
					+ " (select mc1.customername from dblocator.msttbldevice as md1 "
					+ " inner join dblocator.msttblmappingdevices_toall as mdm1 on md1.deviceid = mdm1.deviceid "
					+ "	inner join dblocator.msttbluserlogin c1 on mdm1.loginid = c1.loginid "
					+ " inner join dblocator.msttblcustomer as mc1 on mc1.customerid = c1.ownersid "
					+ "	where md1.deviceid = d.deviceid and md1.flag=0  limit 1) as customer, "
					+ "	(select mc1.dealername from dblocator.msttbldevice as md1 "
					+ "	inner join dblocator.msttblmappingdevices_toall as mdm1 on md1.deviceid = mdm1.deviceid "
					+ "	inner join dblocator.msttbluserlogin c1 on mdm1.loginid = c1.loginid "
					+ "	inner join dblocator.msttbldealer as mc1 on mc1.dealerid = c1.ownersid "
					+ "	where md1.deviceid = d.deviceid and md1.flag=0 and mc1.dealerid != 610011 order by mdm1.datetimestamp desc limit 1) as dealer, "
					+ " case when mv.isapproved is null then 'false' else mv.isapproved end, "
					+ "	case when ms.simnumber::text is null then 'NA' else ms.simnumber::text end, "
					+ " case when mv.vehicleregno is null then 'NA' else mv.vehicleregno end , "
					+ " case when mv.chasisnumber is null then 'NA' else mv.chasisnumber end , mcc.companyname, mv.receiptno ,d.loginid "
					+ " from dblocator.msttbldevice as d "
					+ " inner join dblocator.msttblmappingdevices_toall as mpd on d.deviceid = mpd.deviceid "
					+ " inner join dblocator.msttbldealerdevice_assign as mda on d.deviceid = mda.deviceid "
					+ " inner join dblocator.msttblmake m on d.makeid = m.makeid "
					+ " inner join dblocator.msttblmodel mk  on d.modelid = mk.modelid "
					+ " inner join dblocator.msttbluserlogin c on mpd.loginid=c.loginid "
					+ "	left join dblocator.msttblvehicleassigngps as veh  on d.deviceid = veh.deviceid "
					+ "	left join dblocator.msttblvehicle as mv on mv.vehicleid= veh.vehicleid "
					+ "	left join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = d.deviceid "
					+ "	left join dblocator.msttblsim as ms on ms.simid = dsm.simid "
					+ "	inner join dblocator.msttbluserlogin as mf on mf.loginid = d.loginid "
					+ "	inner join dblocator.msttblcompany as mcc on mcc.companyid = mf.ownersid "
					+ " where mpd.loginid='" + loginid + "'::numeric and mda.dealerid=(select k.ownersid from dblocator.msttbluserlogin as k where k.loginid='" + loginid + "'::numeric) "
					+ " and  case when('"+searchBydeviceno+"'!='')  then d.uniqueid::text like '%'||'"+searchBydeviceno+"'||'%' or d.deviceid::text like '%'||'"+searchBydeviceno+"'||'%' else mpd.loginid='" + loginid + "'::numeric  end "
					+ " ) as k order by min; ";
				
				System.out.println("====>"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
//			//	System.out.println("flag = " + flag);
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				
//				d.deviceid, d.makeid,d.modelid,d.uniqueid, d.loginid, d.datetimestamp, d.remark, d.flag,
//		        m.makename,mk.modelname,d.status_sim,d.status_veh, mda.status, d.status_customer,mpd.flag
				while (rs4.next()) {
					
					try {
//						System.out.println("in rs");
						device_details obj = new device_details();
						obj.setDeviceid(Long.parseLong(rs4.getString(1)));
						obj.setMakeid(Long.parseLong(rs4.getString(2)));
						obj.setModelid(Long.parseLong(rs4.getString(3)));
						obj.setUniqueid(rs4.getString(4));
						obj.setLoginid(Long.parseLong(rs4.getString(5)));
						obj.setDatetimestamp(sdf1.format(sdf.parse(rs4.getString(6))));
						obj.setRemark(rs4.getString(7));
						obj.setCustomername(rs4.getString(17));
						obj.setDealername(rs4.getString(18));
						obj.setReceiptno(rs4.getString(24));
						// obj.setFlag(Integer.parseInt(rs4.getString(7)));
						int flag1 = Integer.parseInt(rs4.getString(8));
//						System.out.println("in flag"+flag1);
						if(flag1 == 0)
						{
						obj.setDevice_status("Deactivate");	
						}
						else
						{
							obj.setDevice_status("Activate");
						}
						obj.setMakename(rs4.getString(9));
						obj.setModelname(rs4.getString(10));
						String status = rs4.getString(11);
						obj.setRowno(indexno);
						indexno++;
						if (status.equals("1")) {
							obj.setStatussim("Assigned");
						} else {
							obj.setStatussim("UnAssigned");
						}

						status = rs4.getString(12);
						if (status.equals("1")) {
							obj.setStatusvehicle("Assigned");
						} else {
							obj.setStatusvehicle("UnAssigned");
						}
						obj.setStatus(rs4.getString(13));
						obj.setStatus_customer(rs4.getString(14));
						int flag = Integer.parseInt(rs4.getString(15));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						try
						{
						String poling=rs4.getString(16);
						if(poling!=null)
						{
							if(Integer.parseInt(poling)<240){
								obj.setPolling_status("Polling");
							}else{
								obj.setPolling_status("Pollingor");
							}
							
						}else
						{
							//System.out.println("in else");
							obj.setPolling_status("NotPolling");
						}
						}catch(Exception er){}
						try
						{
						obj.setIsapproved(rs4.getString(19));
						}catch(Exception er){}
						try
						{
						obj.setSimno(rs4.getString(20));
						}catch(Exception er){}
						try
						{
						obj.setVehicleno(rs4.getString(21));
						}catch(Exception er){}
						try
						{
						obj.setChasisno(rs4.getString(22));
						}catch(Exception er){}
						try{
							obj.setCid(rs4.getString(23));
						}catch(Exception er){}
						try{
							obj.setCloginid(rs4.getString(25));
						}catch(Exception er){}
						data.add(obj);
					} catch (Exception e) {
						System.out.println("err = " + e);
					}
				}
				
				
				
				 
				
				
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
		 	return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	
	@GET
	@Path("/devicedetailsbydealer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicedetailsbydealer(@QueryParam("dealerid") String dealerid, @QueryParam("companyid") String companyid) {
		List<device_details> data = new ArrayList<device_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice device ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
			
				String sqlselect4 = "select md.uniqueid, md.deviceid from dblocator.msttbldevice as md "+
					" inner join dblocator.msttblmappingdevices_toall as mdm on mdm.deviceid = md.deviceid "+
					" inner join dblocator.msttbluserlogin as ml on ml.loginid = mdm.loginid "+
					" where ml.ownersid = "+dealerid+" and md.deviceid not in (select deviceid from dblocator.msttblcustomerdevice_assign)";
				
				System.out.println(sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
//			//	System.out.println("flag = " + flag);
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				
//				d.deviceid, d.makeid,d.modelid,d.uniqueid, d.loginid, d.datetimestamp, d.remark, d.flag,
//		        m.makename,mk.modelname,d.status_sim,d.status_veh, mda.status, d.status_customer,mpd.flag
				while (rs4.next()) {
					
					try {
//						System.out.println("in rs");
						device_details obj = new device_details();
						obj.setDeviceid(Long.parseLong(rs4.getString(2)));
						obj.setUniqueid(rs4.getString(1));
						data.add(obj);
					} catch (Exception e) {
						System.out.println("err = " + e);
					}
				}
				
				
				
				 
				
				
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
		 	return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/searchByDeviceid")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchByDeviceid(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("deviceid")String deviceid) {
		List<device_details> data = new ArrayList<device_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			 System.out.println("Connected in websservice serchbydevice ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				String sqlselect4 = "select * from dblocator.selectprocedure('selectSearchByDeviceid', '"
						+ loginid + "', '"+deviceid+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			
				System.out.println("query  "+sqlselect4);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
//			//	System.out.println("flag = " + flag);
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				
//				d.deviceid, d.makeid,d.modelid,d.uniqueid, d.loginid, d.datetimestamp, d.remark, d.flag,
//		        m.makename,mk.modelname,d.status_sim,d.status_veh, mda.status, d.status_customer,mpd.flag
				while (rs4.next()) {
					
					try {
						device_details obj = new device_details();
						obj.setDeviceid(Long.parseLong(rs4.getString(1)));
						obj.setMakeid(Long.parseLong(rs4.getString(2)));
						obj.setModelid(Long.parseLong(rs4.getString(3)));
						obj.setUniqueid(rs4.getString(4));
						obj.setLoginid(Long.parseLong(rs4.getString(5)));
						obj.setDatetimestamp(rs4.getString(6));
						obj.setRemark(rs4.getString(7));
						// obj.setFlag(Integer.parseInt(rs4.getString(7)));
						int flag1 = Integer.parseInt(rs4.getString(8));
//						System.out.println("in flag"+flag1);
						if(flag1 == 0)
						{
						obj.setDevice_status("Active");	
						}
						else
						{
							obj.setDevice_status("InActive");
						}
						obj.setMakename(rs4.getString(9));
						obj.setModelname(rs4.getString(10));
						String status = rs4.getString(11);
						obj.setRowno(indexno);
						indexno++;
						if (status.equals("1")) {
							obj.setStatussim("Sim Assigned");
						} else {
							obj.setStatussim("Sim Not Assigned");
						}

						status = rs4.getString(12);
						if (status.equals("1")) {
							obj.setStatusvehicle("Assigned to Vehicle");
						} else {
							obj.setStatusvehicle("Not  Assigned to Vehicle");
						}
						obj.setStatus(rs4.getString(13));
						obj.setStatus_customer(rs4.getString(14));
						int flag = Integer.parseInt(rs4.getString(15));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
				
				
				
				 
				
				
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
		 	return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	public String getControlId(String ownerid) {
		Connection con = null;
		String controlid = null;
		try{
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			String sqlselect4 = "select * from dblocator.selectprocedure('selectControlid', '" + ownerid
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			while (rs4.next()) {
				
				try {
					
					controlid = rs4.getString(1);
//					 System.out.println(controlid);

				} catch (Exception e) {
				//	System.out.println("e = " + e);
				}
			}

		}catch(Exception e){
		  System.out.println(e);
		}
		return controlid;
	}

	public List<parentcomp_details> getLoginId(String companyid) {
		List<parentcomp_details> data = new ArrayList<parentcomp_details>();
		Connection con = null;
		long pid, cid = 0;
		String kid =null;
		pid = Long.parseLong(companyid);
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//		//	System.out.println("Connected in websservice sim");
			String tblnamecdet="selectCompanydet", tblnamepdet="selectParentCompanydet";
			if(companyid.startsWith("7")){
				companyid = getControlId(companyid);
				pid = Long.parseLong(companyid);
//			//	System.out.println(companyid+"hello");
			}
			if(companyid.startsWith("3")){
				tblnamecdet = "selectCompanydet";
				tblnamepdet = "selectParentCompanydet";
			}else if(companyid.startsWith("6")){
//			//	System.out.println("in dealer 6");
				tblnamecdet = "selectDealerdet";
				tblnamepdet = "selectParentDealerdet";
			}
			
			// String sqlselect4="select sim_creation()";
			while (pid != 0) {
//			//	System.out.println("in first loop");
				String sqlselect4 = "select * from dblocator.selectprocedure('"+tblnamecdet+"', '" + pid
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				
				
					pid = 0;
				
				
				while (rs4.next()) {
					
					try {
						//
						pid = Long.parseLong(rs4.getString(2));
						cid = Long.parseLong(rs4.getString(1));
						// cid=pid;
						// System.out.println("cid= "+cid+" "+pid);
						if (pid == 0) {
							
							parentcomp_details obj = new parentcomp_details();
							obj.setCompanyid(rs4.getString(1));
							obj.setParentcompid(rs4.getString(2));
//							obj.setCompanyname(rs4.getString(3));
							obj.setLoginid(rs4.getString(3));
							kid = rs4.getString(1);
//							obj.setOrderno(orderno);
							 data.add(obj);
							
							 System.out.println("breaked "+obj.getLoginid()+" comp "+obj.getCompanyid()+" parent comp "+obj.getParentcompid());
							break;
						}
						// data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
			}
			// System.out.println("cid = "+cid);
			if (cid != 0) {
				int orderno = 3;
			//	System.out.println("in pid of result = "+cid);
				pid = cid;
				cid = 1;
				Pattern delhiLivePattern = null;
				String pattn = kid;
				while (cid == 1) {
				//	System.out.println("loop comp id= " + pid);
					String sqlselect4 = "select * from dblocator.selectprocedure('"+tblnamepdet+"', '" + pid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					// System.out.println("rs = "+rs4);
					cid = 0;
					while (rs4.next()) {
						
						try {
							

								parentcomp_details obj = new parentcomp_details();
								obj.setCompanyid(rs4.getString(1));
								obj.setParentcompid(rs4.getString(2));
//								obj.setCompanyname(rs4.getString(3));
								obj.setLoginid(rs4.getString(3));
								obj.setOrderno(orderno);
								pid = Long.parseLong(rs4.getString(1));
								cid = Long.parseLong(rs4.getString(1));
								delhiLivePattern = Pattern.compile(pattn);
								if (!(delhiLivePattern.matcher(obj.getCompanyid()).find())) {
//								//	System.out.println("pattern= " + pattn + "in true");
									data.add(obj);
									pattn = pattn + "|" + obj.getCompanyid();
								}
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						cid = 1;
					}
					orderno++;
				}
				// System.out.println("out of while loop"+data.size());
			}
		} catch (Exception e) {
		  System.out.println(e);
		} finally {
			try {
				con.close();
				// System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//	//	System.out.println(data.size());
		return data;
	}

	@GET
	@Path("/getids")
	@Produces(MediaType.APPLICATION_JSON)
	public List<parentcomp_details> getLoginIdWeb(@QueryParam("companyid") String companyid) {
		List<parentcomp_details> data = new ArrayList<parentcomp_details>();
		Connection con = null;
		long pid = 1, cid = 0;
		cid = Long.parseLong(companyid);
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice sim/n"+cid);

			// String sqlselect4="select sim_creation()";
			while (pid != 0) {
			//	System.out.println("in first loop");
				String sqlselect4 = "select * from dblocator.selectprocedure('selectParentCompanydet', '" + cid
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				pid = 0;
				while (rs4.next()) {
					
					try {

						pid = Long.parseLong(rs4.getString(2));
						cid = Long.parseLong(rs4.getString(1));
						// System.out.println(cid);

					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
			}
			pid = 1;
			while (pid != 2) {
			//	System.out.println("in first loop");
				String sqlselect4 = "select * from dblocator.selectprocedure('selectCompanydet', '" + cid
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				pid = 2;
				while (rs4.next()) {
				
					try {
						parentcomp_details obj = new parentcomp_details();
						obj.setCompanyid(rs4.getString(0+1));
						obj.setParentcompid(rs4.getString(1+1));
						obj.setCompanyname(rs4.getString(2+1));
						obj.setLoginid(rs4.getString(3+1));
						// obj.setOrderno(orderno);
						pid = Long.parseLong(rs4.getString(0+1));
						cid = Long.parseLong(rs4.getString(0+1));
						data.add(obj);
						pid = Long.parseLong(rs4.getString(1+1));
						cid = Long.parseLong(rs4.getString(1+1));
					//	System.out.println(cid);

					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
			}
			// System.out.println("cid = "+cid);

		} catch (Exception e) {
		 // System.out.println(e);
		} finally {
			try {
				con.close();
				// System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return data;
	}

	@GET
	@Path("/simdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchBySimno")String searchBySimno) {
		List<sim_details> data = new ArrayList<sim_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice sim details");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select sim_creation()";
			if(searchBySimno==null)
			{
				searchBySimno="";
			}
			/*	String sqlselect4 = "select * from dblocator.selectprocedure('selectsim', '" + loginid
						+ "', '"+searchBySimno+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4 =" select s.simid,s.networkid,s.assettypeid,s.simnumber,s.mobilenumber,s.vendorid,s.receiptdt,s.loginid,s.datetimestamp,s.remarks,s.flag, "
					+ " a.assetname,n.networkname,v.vendorfirmname,s.status,mdm.flag,0,s.mobilenumber2 "
					+ "	from  dblocator.msttblsim s "
					+ " inner join dblocator.msttblmappingsims_toall as mdm on mdm.simid = s.simid "
					+ "	inner join dblocator.msttbluserlogin c on mdm.loginid=c.loginid "
					+ " inner join dblocator.msttblasset a on s.assettypeid=a.assettypeid "
					+ "	inner join dblocator.msttblnetwork n on s.networkid=n.networkid "
					+ " inner join dblocator.msttblvendor v on s.vendorid=v.vendorid where "
					+ " mdm.loginid ='" + loginid + "'::numeric "
					+ " and  case when('"+searchBySimno+"'!='')  then s.simnumber::text like '%'||'"+searchBySimno+"'||'%' else mdm.loginid ='" + loginid + "'::numeric  end "
					+ " order by s.simnumber asc; ";
				
				System.out.println("simdetails"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}

				while (rs4.next()) {
				
					try {
						
//						 s.simid,s.networkid,s.assettypeid,s.simnumber,s.mobilenumber,s.vendorid,s.receiptdt,s.loginid,s.datetimestamp,s.remarks,s.flag,
//							a.assetname,n.networkname,v.vendorfirmname,s.status,mdm.flag
						sim_details obj = new sim_details();
						obj.setSimid(Long.parseLong(rs4.getString(0+1)));
						obj.setNetworkid(Long.parseLong(rs4.getString(1+1)));
						obj.setAssettypeid(Long.parseLong(rs4.getString(2+1)));
						obj.setSimnumber(rs4.getString(3+1));
						obj.setMobilenumber(Long.parseLong(rs4.getString(4+1)));
						obj.setVendorid(Long.parseLong(rs4.getString(5+1)));
						obj.setReceiptdt(rs4.getString(6+1));
						obj.setLoginid(Long.parseLong(rs4.getString(7+1)));
						obj.setDatetimestamp(rs4.getString(8+1));
						obj.setRemarks(rs4.getString(9+1));
						try{
							obj.setMobilenumber2(rs4.getString(17+1));
						}catch(Exception e){
//							System.out.println(e);
						}
						int flag1 = Integer.parseInt(rs4.getString(10+1));
						if(flag1 == 0)
						{
							obj.setSim_status("Deactivate");
						}
						else
						{
							obj.setSim_status("Activate");
						}
						// obj.setFlag(Integer.parseInt(rs4.getString(10+1)));
						obj.setAssetname(rs4.getString(11+1));
						obj.setNetworkname(rs4.getString(12+1));
						obj.setVendorname(rs4.getString(13+1));
						String status = rs4.getString(14+1);
						obj.setRowno(indexno);
						indexno++;
						if (status.equals("1")) {
							obj.setStatus("Assigned");
						} else {
							obj.setStatus("UnAssigned");
						}
						int flag = Integer.parseInt(rs4.getString(16));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						// System.out.println("vendor name =
						// "+obj.getVendorname());
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
	@GET
	@Path("/searchBySimnumber")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBySimnumber(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("simno")String simno) {
		List<sim_details> data = new ArrayList<sim_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice sim");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select sim_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchbysimnumber', '" + loginid
						+ "', '"+simno+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}

				while (rs4.next()) {
				
					try {
//						 s.simid,s.networkid,s.assettypeid,s.simnumber,s.mobilenumber,s.vendorid,s.receiptdt,s.loginid,s.datetimestamp,s.remarks,s.flag,
//							a.assetname,n.networkname,v.vendorfirmname,s.status,mdm.flag
						sim_details obj = new sim_details();
						obj.setSimid(Long.parseLong(rs4.getString(0+1)));
						obj.setNetworkid(Long.parseLong(rs4.getString(1+1)));
						obj.setAssettypeid(Long.parseLong(rs4.getString(2+1)));
						obj.setSimnumber(rs4.getString(3+1));
						obj.setMobilenumber(Long.parseLong(rs4.getString(4+1)));
						obj.setVendorid(Long.parseLong(rs4.getString(5+1)));
						obj.setReceiptdt(rs4.getString(6+1));
						obj.setLoginid(Long.parseLong(rs4.getString(7+1)));
						obj.setDatetimestamp(rs4.getString(8+1));
						obj.setRemarks(rs4.getString(9+1));
						int flag1 = Integer.parseInt(rs4.getString(10+1));
						if(flag1 == 0)
						{
							obj.setSim_status("Active");
						}
						else
						{
							obj.setSim_status("InActive");
						}
						// obj.setFlag(Integer.parseInt(rs4.getString(10+1)));
						obj.setAssetname(rs4.getString(11+1));
						obj.setNetworkname(rs4.getString(12+1));
						obj.setVendorname(rs4.getString(13+1));
						String status = rs4.getString(14+1);
						obj.setRowno(indexno);
						indexno++;
						if (status.equals("1")) {
							obj.setStatus("Assigned");
						} else {
							obj.setStatus("UnAssigned");
						}
						int flag = Integer.parseInt(rs4.getString(16));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						// System.out.println("vendor name =
						// "+obj.getVendorname());
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/companydetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companyDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid) {
System.out.println("loginId:"+loginid+ " "+companyid);
		List<company_details> data = new ArrayList<company_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {
//			//	System.out.println("cid of company = " + list.get(k).getCompanyid());
//				String sqlselect4 = "select * from dblocator.selectprocedure('selectCompany', '" + loginid
//						+ "', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				
				String sqlselect4 = "select mc.companyid, mc.companyname, mc.shortname, mc.companyaddress, mc.contactpersonname, mc.lanldlinenumber1, "+
					   " mc.lanldlinenumber2, mc.faxnumber, mc.emailid, mc.registeredaddress, mu.loginid, mc.datetimestamp, mc.remarks, mc.parentcompid,mc.flag,mc.city, "+
					   " mc.state,mc.zip,mc.flag "+
					   " from dblocator.msttblcompany as mc  "+
					   " inner join dblocator.msttbluserlogin as ml on ml.loginid=mc.loginid "+
					   " left join dblocator.msttbluserlogin as mu  on mu.ownersid = mc.companyid "+
					   "  where ml.loginid='"+loginid+"'::numeric ;";
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				int indexno = 1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}

				while (rs4.next()) {
					
					try {
						company_details obj = new company_details();
						// companyid, companyname, shortname, companyaddress,
						// contactpersonname, lanldlinenumber1,
						// lanldlinenumber2, faxnumber, emailid,
						// registeredaddress, loginid, datetimestamp,
						// remarks, parentcompid

						obj.setCompanyid(Long.parseLong(rs4.getString(0+1)));
						obj.setCompanyname(rs4.getString(1+1));
						obj.setShortname(rs4.getString(2+1));
						obj.setCompanyaddress(rs4.getString(3+1));
						obj.setContactpersonname(rs4.getString(4+1));
						obj.setLanldlinenumber1(Long.parseLong(rs4.getString(5+1)));
						obj.setLanldlinenumber2(Long.parseLong(rs4.getString(6+1)));
						obj.setFaxnumber(Long.parseLong(rs4.getString(7+1)));
						obj.setEmailid(rs4.getString(8+1));
						obj.setRegisteredaddress(rs4.getString(9+1));
						obj.setLoginid(Long.parseLong(rs4.getString(10+1)));

						java.util.Date parseTimestamp = sdf.parse(rs4.getString(11+1));

						// obj.setDatetimestamp(new
						// Timestamp(parseTimestamp.getTime()));
						obj.setDatetimestamp(rs4.getString(11+1));
						obj.setRemarks(rs4.getString(12+1));
						obj.setParentcompid(rs4.getString(13+1));
						obj.setFlag(rs4.getString(14+1));
						obj.setCity(rs4.getString(15+1));
						obj.setState(rs4.getString(16+1));
						obj.setZip(rs4.getString(17+1));
						obj.setRowno(indexno);
						
						indexno++;
						
//						if (flag == 1) {
//							obj.setFlag("false");
//							obj.setStatus("Inactive");
//							
//						} else {
							obj.setFlag("true");
							obj.setStatus("Active");
//						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
					
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/Approvaldetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response Approvaldetails(@QueryParam("controlid") String controlid, @QueryParam("companyid") String companyid
			, @QueryParam("searchid") String searchid) {
//		System.out.println("loginId:"+controlid+ " "+companyid);
		List<approval_det> data = new ArrayList<approval_det>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			if(searchid==null){
				searchid = "";
			}
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {
//			//	System.out.println("cid of company = " + list.get(k).getCompanyid());
				String sqlselect4 = "select mc.companyname, mdd.dealername, mcu.customername, mv.vehicleregno, "+
							 " mv.isapproved, md.uniqueid, ms.simnumber,mv.chasisnumber, "+
							 " mv.enginenumber,md.deviceid,mv.receiptno, mv.datetimestamp::date,  "+
							 " (select updatedtimestamp::timestamp without time zone from connected_device_master where imeino = md.uniqueid) "+
							 " from dblocator.msttblvehicle as mv  "+
							 " inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid  "+
							 " inner join dblocator.msttbldevice as md on md.deviceid = veh.deviceid  "+
							 " left join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = md.deviceid  "+
							 " left join dblocator.msttblsim as ms on ms.simid = dsm.simid  "+
							 " inner  join dblocator.msttbluserlogin as mld on mld.loginid = mv.loginid "+
							 " inner join dblocator.msttblcustomer as mcu on mcu.customerid = mld.ownersid  "+
							 " inner join dblocator.msttbluserlogin as mc2 on mcu.loginid = mc2.loginid  "+
							 " inner join dblocator.msttbldealer as mdd on mdd.dealerid = mc2.ownersid  "+
							 " inner join dblocator.msttblcompany as mc on mc.companyid = mc2.controlid and"+
							 " case when '"+searchid+"' != '' then (mc.companyname like '%"+searchid+"%' or "
							 + " mv.vehicleregno like '%"+searchid+"%') else mv.flag = 0 end"+
							 " order by mv.datetimestamp::date desc;";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//				System.out.println(sqlselect4);
				int indexno = 1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}

				while (rs4.next()) {
					
					try {
//						mc.companyname, dealername, mcu.customername, mv.vehicleregno, mv.isapproved, 
//						md.uniqueid,ms.simnumber,mv.chasisnumber,mv.enginenumber,md.deviceid
						approval_det obj = new approval_det();
						obj.setCompanyname(rs4.getString(1));
						obj.setDealername(rs4.getString(2));
						obj.setCustomername(rs4.getString(3));
						obj.setVehicleno(rs4.getString(4));
						obj.setImeino(rs4.getString(6));
						obj.setSimno(rs4.getString(7));
						obj.setChasisno(rs4.getString(8));
						obj.setEngineno(rs4.getString(9));
						obj.setDeviceid(rs4.getString(10));
						obj.setReceiptno(rs4.getString(11));
						//System.out.println("inn date"+rs4.getString(2));
						obj.setDate(sdf.format(new java.util.Date()));
//						try{
							if(rs4.getString(5).equals("t")){
								obj.setStatus("Approved");
							}else{
								obj.setStatus("Pending");
							}
//						System.out.println(obj.getStatus());
//						}catch(Exception e){
//							System.out.println(e);
//						}
						obj.setRdate(rs4.getString(12));
						try{
						obj.setPollingtime(rs4.getString(13));
						}catch(Exception e){}
						obj.setRowno(indexno);
						
						indexno++;
						
						data.add(obj);
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
					
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	@GET
	@Path("/vehicledelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicledelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledelete");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteVehicle'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			/*String sqlselect4="UPDATE dblocator.msttblvehicle "
					+ " SET flag = 1 where vehicleid='" + id + "'::numeric; "
					+ " RAISE NOTICE 'Process Procedure Type : % ', 'deleteVehicle';";*/
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/makedelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makedelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteMake");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteMake'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/makedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchByMakename")String searchByMakename) {
		List<make_details> data = new ArrayList<make_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			if(searchByMakename==null)
			{
				searchByMakename="";
			}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectMake', '" + companyid
						+ "', '"+searchByMakename+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			
			    String sqlselect4=" select m.makeid, m.makename, a.assettypeid ,a.assetname, m.loginid, "
			    		+ " m.datetimestamp, m.remarks, m.flag "
			    		+ " from dblocator.msttblmake m inner join dblocator.msttblasset a on a.assettypeid = m.assetid "
			    		+ " where "
			    		+ " m.vendorid = '" + companyid + "' ::numeric and  "
			    		+ " m.flag=0 and case when('" + searchByMakename + "'!='')  then m.makename like '%'||'" + searchByMakename + "'||'%' else m.flag=0  end "
			    		+ " order by  m.makename asc; "; 
			    
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
////				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// m.makeid, m.makename, a.assettypeid ,a.assetname,
					// m.vendorid,v.vendorfirmname,
					// m.loginid, m.datetimestamp, m.remarks,
					// m.flag,c.Companyname,m.maketype,m.companyid
					make_details obj = new make_details();
					obj.setMakeid(rs4.getString(0+1));
					obj.setMakename(rs4.getString(1+1));
					obj.setAssettypeid(rs4.getString(2+1));
					obj.setAssetname(rs4.getString(3+1));
//					obj.setVendorid(rs4.getString(4+1));
//					obj.setVendorfirmname(rs4.getString(5+1));
					obj.setLoginid(rs4.getString(4+1));
					obj.setDatetimestamp(rs4.getString(5+1));
					obj.setRemarks(rs4.getString(6+1));
					obj.setRowno(indexno);
					indexno++;
					// obj.setFlag(rs4.getString(9+1));
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					// obj.setCompanyname(rs4.getString(10+1));
					// obj.setMaketype(rs4.getString(11+1));
					// obj.setCompanyid(rs4.getString(12+1));
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("/searchBymakename")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBymakename(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("makename")String makename ) {
		List<make_details> data = new ArrayList<make_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice make ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchByMakename', '" + loginid
						+ "', '"+makename+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				System.out.println("in make"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
////				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// m.makeid, m.makename, a.assettypeid ,a.assetname,
					// m.vendorid,v.vendorfirmname,
					// m.loginid, m.datetimestamp, m.remarks,
					// m.flag,c.Companyname,m.maketype,m.companyid
					make_details obj = new make_details();
					obj.setMakeid(rs4.getString(0+1));
					obj.setMakename(rs4.getString(1+1));
					obj.setAssettypeid(rs4.getString(2+1));
					obj.setAssetname(rs4.getString(3+1));
//					obj.setVendorid(rs4.getString(4+1));
//					obj.setVendorfirmname(rs4.getString(5+1));
					obj.setLoginid(rs4.getString(4+1));
					obj.setDatetimestamp(rs4.getString(5+1));
					obj.setRemarks(rs4.getString(6+1));
					obj.setRowno(indexno);
					indexno++;
					// obj.setFlag(rs4.getString(9+1));
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					// obj.setCompanyname(rs4.getString(10+1));
					// obj.setMaketype(rs4.getString(11+1));
					// obj.setCompanyid(rs4.getString(12+1));
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	
	
	
	@GET
	@Path("/assetdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assetDetails(@QueryParam("companyid") String companyid) {
		List<asset_details> data = new ArrayList<asset_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice asset");

			// String sqlselect4="select vehicle_creation()";
			/*String sqlselect4 = "select * from dblocator.selectprocedure('selectAsset', '" + companyid
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4="  select a.companyid,a.assettypeid,a.assetname,a.loginid,a.datetimestamp,a.remarks "
					+ " from dblocator.msttblasset a inner join dblocator.msttblcompany c on a.companyid = c.companyid "
					+ " where case when(c.companyid!= '-1')then  a.companyid= '"+companyid+"'::numeric else a.companyid!='"+companyid+"'::numeric end " ;
				
			
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			while (rs4.next()) {
				
				// a.companyid,a.assettypeid,a.assetname,a.loginid,a.datetimestamp,a.remarks
				asset_details obj = new asset_details();
				obj.setCompanyid(rs4.getString(0+1));
				obj.setAssettypeid(rs4.getString(1+1));
				obj.setAssetname(rs4.getString(2+1));
				obj.setLoginid(rs4.getString(3+1));
				obj.setDatetimestamp(rs4.getString(4+1));
				obj.setRemarks(rs4.getString(5+1));
				data.add(obj);
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/modeldetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modelDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchbymodelname")String searchbymodelname) {
		List<model_details> data = new ArrayList<model_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice models");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			if(searchbymodelname==null)
			{
				searchbymodelname="";
			}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectModel', '" + companyid
						+ "', '"+searchbymodelname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			   String sqlselect4=" select mo.modelid, mo.modelname, mo.makeid, mo.loginid, mo.datetimestamp, mo.remarks, mo.flag,mk.Makename,ms.assetname,mo.cost "
			   		+ " from dblocator.msttblmodel mo inner join dblocator.msttblmake mk on mo.makeid=mk.makeid "
			   		+ " inner join dblocator.msttblasset ms on ms.assettypeid=mk.assetid "
			   		+ " where mk.vendorid = '" + companyid + "'::numeric and "
			   		+ " mo.flag=0 and  case when('" + searchbymodelname + "'!='')  then mo.modelname like '%'||'" + searchbymodelname + "'||'%' else mo.flag=0  end "
			   		+ " order by mo.modelname asc ; ";
			
				System.out.println("model "+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// mo.modelid, mo.modelname, mo.makeid, mo.loginid,
					// mo.datetimestamp,
					// mo.remarks, mo.flag,mk.Makename
try
{
					model_details obj = new model_details();
					obj.setModelid(rs4.getString(0+1));
					obj.setModelname(rs4.getString(1+1));
					obj.setMakeid(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					// obj.setFlag(rs4.getString(6+1));
					obj.setMakename(rs4.getString(7+1));
					obj.setAssetname(rs4.getString(8+1));
					obj.setCost(rs4.getString(9+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					data.add(obj);
}catch(Exception e){System.out.println("exception"+e);}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/searchbyModelname")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbyModelname(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("modelname")String modelname) {
		List<model_details> data = new ArrayList<model_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice vehde ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchbymodelname', '" + loginid
						+ "', '"+modelname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// mo.modelid, mo.modelname, mo.makeid, mo.loginid,
					// mo.datetimestamp,
					// mo.remarks, mo.flag,mk.Makename
try
{
					model_details obj = new model_details();
					obj.setModelid(rs4.getString(0+1));
					obj.setModelname(rs4.getString(1+1));
					obj.setMakeid(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					// obj.setFlag(rs4.getString(6+1));
					obj.setMakename(rs4.getString(7+1));
					obj.setAssetname(rs4.getString(8+1));
					obj.setCost(rs4.getString(9+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					data.add(obj);
}catch(Exception e){System.out.println("exception"+e);}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
	
	
	
	
	@GET
	@Path("/menudetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuDetails(@QueryParam("roleid") String roleid, @QueryParam("parentid") String parentid) {
		List<menu_details> data = new ArrayList<menu_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice menus");
			String sqlselect4;
			if (Integer.parseInt(parentid) == 0) {
				//names = "selectmenu";
				
				 sqlselect4 = " select m.menutext,m.navigateurl,m.parentid,m.mid,m.orderno,m.menuid,m.description, "
						+ " (select mm.menutext  from dblocator.msttblmenu as mm where mm.menuid=m.parentid) as parentname "
						+ " from dblocator.msttblmenu m where m.flag=0; ";
			} else {
				//names = "selectmenuall";
				
				 sqlselect4 = " select m.menutext,m.navigateurl,m.parentid,mr.id,m.orderno,m.menuid,m.description, "
						+ "r.rolename,mc.companyname from dblocator.msttblmenu m "
						+ " inner join dblocator.msttblmenurole as mr on mr.menuid = m.menuid "
						+ " inner join dblocator.msttblrole as r on r.roleid= mr.roleid "
						+ " inner join dblocator.msttblcompany as mc on mc.companyid=r.companyid "
						+ " where mr.flag='0' and m.flag=0 " ;
					//	+ "case when($3!= '-1')then mr.roleid=$3::numeric else mr.roleid!=$3::numeric end; ";
			}
			// String sqlselect4="select vehicle_creation()";
		/*	String sqlselect4 = "select * from dblocator.selectprocedure('" + names + "', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int indexno=1;
			while (rs4.next()) {
				// System.out.println("res= "+rs4.getString(1));
				
				// m.menutext,m.navigateurl,m.parentid,m.mid,m.orderno,m.menuid,parentname

				menu_details obj = new menu_details();
				obj.setMenutext(rs4.getString(0+1));
				obj.setNavigateurl(rs4.getString(1+1));
				obj.setParentid(rs4.getString(2+1));
				obj.setMid(rs4.getString(3+1));
				obj.setOrderno(rs4.getString(4+1));
				obj.setMenuid(rs4.getString(5+1));
				obj.setDescription(rs4.getString(6+1));
				obj.setRowno(indexno);
				indexno++;
				try {
					obj.setParentname(rs4.getString(7+1));
				} catch (Exception e) {

				}
				
				data.add(obj);
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/menudalletails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuallDetails(@QueryParam("companyid") String companyid, @QueryParam("roleid") String roleid) {
		List<menu_details> data = new ArrayList<menu_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice vehde ");
int indexno=1;
			// String sqlselect4="select vehicle_creation()";
			String sqlselect4;
			if (roleid != null) {
				/*sqlselect4 = "select * from dblocator.selectprocedure('selectmenuall', '" + companyid + "', '" + roleid
						+ "', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
				 sqlselect4=" select m.menutext,m.navigateurl,m.parentid,mr.id,m.orderno,m.menuid,m.description, "
						+ " r.rolename,mc.companyname from dblocator.msttblmenu m "
						+ " inner join dblocator.msttblmenurole as mr on mr.menuid = m.menuid "
						+ " inner join dblocator.msttblrole as r on r.roleid= mr.roleid "
						+ "	inner join dblocator.msttblcompany as mc on mc.companyid=r.companyid "
						+ " where mr.flag='0' and m.flag=0 and "
						+ "	case when('" + roleid + "'!= '-1')then mr.roleid='" + roleid + "'::numeric else mr.roleid!='" + roleid + "'::numeric end; ";   
				
			} else {
				/*sqlselect4 = "select * from dblocator.selectprocedure('selectmenuall', '" + companyid
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
	              sqlselect4=" select m.menutext,m.navigateurl,m.parentid,mr.id,m.orderno,m.menuid,m.description, "
						+ " r.rolename,mc.companyname from dblocator.msttblmenu m "
						+ " inner join dblocator.msttblmenurole as mr on mr.menuid = m.menuid "
						+ " inner join dblocator.msttblrole as r on r.roleid= mr.roleid "
						+ " inner join dblocator.msttblcompany as mc on mc.companyid=r.companyid " 
						+ " where mr.flag='0' and m.flag=0 " ;
						//+ " case when($3!= '-1')then mr.roleid=$3::numeric else mr.roleid!=$3::numeric end; ";
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			while (rs4.next()) {
				// System.out.println("res= "+rs4.getString(1));
				
				// m.menutext,m.navigateurl,m.parentid,m.mid,m.orderno,m.menuid

				menu_details obj = new menu_details();
				obj.setMenutext(rs4.getString(0+1));
				obj.setNavigateurl(rs4.getString(1+1));
				obj.setParentid(rs4.getString(2+1));
				obj.setMid(rs4.getString(3+1));
				obj.setOrderno(rs4.getString(4+1));
				obj.setMenuid(rs4.getString(5+1));
				obj.setDescription(rs4.getString(6+1));
				obj.setRolename(rs4.getString(7+1));
//				obj.setParentname(rs4.getString(9+1));
				obj.setRowno(indexno);
				indexno++;
				data.add(obj);
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/menupage")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menupage(@QueryParam("companyid") String companyid, @QueryParam("roleid") String roleid,@QueryParam("searchBymenuname")String searchBymenuname) {
		List<menu_details> data = new ArrayList<menu_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			// System.out.println("Connected in websservice vehde ");
int indexno=1;
			// String sqlselect4="select vehicle_creation()";
			String sqlselect4;
			if(searchBymenuname== null)
			{
				searchBymenuname="";
			}
			if (roleid != null) {
				/*sqlselect4 = "select * from dblocator.selectprocedure('selectmenupage', '" + companyid + "', '" + roleid
						+ "', '"+searchBymenuname+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			   sqlselect4=" select m.menutext,m.navigateurl,m.parentid,mr.id,m.orderno,m.menuid,m.description, "
			   		+ "	r.rolename,mc.companyname,mu.menutext from dblocator.msttblmenu m "
			   		+ "	inner join dblocator.msttblmenurole as mr on mr.menuid = m.menuid "
			   		+ "	inner join dblocator.msttblmenu as mu on mu.menuid = m.parentid "
			   		+ " inner join dblocator.msttblrole as r on r.roleid= mr.roleid  "
			   		+ " inner join dblocator.msttblcompany as mc on mc.companyid=r.companyid "
			   		+ " where mc.companyid='" + companyid + "'::numeric and mr.flag='0' and m.flag=0 and "
			   		+ " case when('" + roleid + "'!= '-1')then mr.roleid='" + roleid + "'::numeric else mr.roleid!='" + roleid + "'::numeric end "
			   		+ "	and  case when('"+searchBymenuname+"'!='') then m.menutext like '%'||'"+searchBymenuname+"'||'%' else m.flag=0 end "
			   		+ "	order by mu.menutext asc; "; 
			   } else { 
				   
				   
				  /* sqlselect4 = "select * from dblocator.selectprocedure('selectmenupage', '" + companyid
						+ "', '', '"+searchBymenuname+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
				   sqlselect4=" select m.menutext,m.navigateurl,m.parentid,mr.id,m.orderno,m.menuid,m.description, "
				   		+ "	r.rolename,mc.companyname,mu.menutext from dblocator.msttblmenu m "
				   		+ " inner join dblocator.msttblmenurole as mr on mr.menuid = m.menuid "
				   		+ "	inner join dblocator.msttblmenu as mu on mu.menuid = m.parentid "
				   		+ " inner join dblocator.msttblrole as r on r.roleid= mr.roleid "
				   		+ " inner join dblocator.msttblcompany as mc on mc.companyid=r.companyid  "
				   		+ "	where mc.companyid='" + companyid + "'::numeric and mr.flag='0' and m.flag=0 "
				   	//	+ " case when($3!= '-1')then mr.roleid=$3::numeric else mr.roleid!=$3::numeric end "
				   		+ "	and  case when('"+searchBymenuname+"'!='') then m.menutext like '%'||'"+searchBymenuname+"'||'%' else m.flag=0 end "
				   		+ "	order by mu.menutext asc; ";	
				   }
			 System.out.println("menupage"+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println(sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			while (rs4.next()) {
				// System.out.println("res= "+rs4.getString(1));
				
				// m.menutext,m.navigateurl,m.parentid,m.mid,m.orderno,m.menuid

				menu_details obj = new menu_details();
				obj.setMenutext(rs4.getString(0+1));
				obj.setNavigateurl(rs4.getString(1+1));
				obj.setParentid(rs4.getString(2+1));
				obj.setMid(rs4.getString(3+1));
				obj.setOrderno(rs4.getString(4+1));
				obj.setMenuid(rs4.getString(5+1));
				obj.setDescription(rs4.getString(6+1));
				obj.setRolename(rs4.getString(7+1));
				obj.setParentname(rs4.getString(9+1));
				obj.setRowno(indexno);
				indexno++;
				data.add(obj);
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/networkdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response networkDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchbyNetworkname")String searchbyNetworkname) {
		List<network_details> data = new ArrayList<network_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			if(searchbyNetworkname==null)
			{
				searchbyNetworkname="";
			}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectnetwork', '" + loginid
						+ "', '"+searchbyNetworkname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select n.networkid, n.networkname, n.networkapn, n.loginid, n.datetimestamp, n.remarks,n.flag "
					+ " from   dblocator.msttblnetwork n inner join dblocator.msttbluserlogin c on n.loginid=c.loginid "
					+ " where "
					+ " n.flag=0 and  case when('"+searchbyNetworkname+"'!='')  then n.networkname like '%'||'"+searchbyNetworkname+"'||'%' else n.flag=0  end "
					+ " order by n.networkname asc; "; 
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// n.networkid, n.networkname, n.networkapn, n.loginid,
					// n.datetimestamp, n.remarks,c.companyname

					network_details obj = new network_details();
					obj.setNetworkid(rs4.getString(0+1));
					obj.setNetworkname(rs4.getString(1+1));
					obj.setNetworkapn(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					// obj.setCompanyname(rs4.getString(6+1));
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	@GET
	@Path("/searchbynetworkName")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchbynetworkName(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("networkname")String networkname) {
		List<network_details> data = new ArrayList<network_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchBynetworkname', '" + loginid
						+ "', '"+networkname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// n.networkid, n.networkname, n.networkapn, n.loginid,
					// n.datetimestamp, n.remarks,c.companyname

					network_details obj = new network_details();
					obj.setNetworkid(rs4.getString(0+1));
					obj.setNetworkname(rs4.getString(1+1));
					obj.setNetworkapn(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					// obj.setCompanyname(rs4.getString(6+1));
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	@GET
	@Path("/vendordetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vendorDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("vendorname")String vendorname,@QueryParam("searchbyVendorname")String searchbyVendorname) {
		List<vendor_details> data = new ArrayList<vendor_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice vendorde ");
//			int k = 0, flag = 1;
//		//	System.out.println("size = "+list.size());
//			while (k < list.size()) {
//			//	System.out.println("loginid = "+list.get(k).getLoginid());
				// String sqlselect4="select vehicle_creation()";
			if(searchbyVendorname==null)
			{
				searchbyVendorname="";
			}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectVendor', '" + loginid
						+ "', '"+searchbyVendorname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
						
						String sqlselect4="  select v.vendorid , v.vendorfirmname, v.vendoraddress, v.city, v.statename, v.landlinenumber, v.contactperson, v.designation, v.mobilenumber, "
								+ " v.emailid, v.assettypeid, v.servicestationaddr, v.servstationcontactno, v.creditdays, v.loginid, v.datetimestamp , v.paymentdate, v.remarks, "
								+ " v.flag,a.assetname,mm.makeid,mm.makename "
								+ " from dblocator.msttblvendor v inner join dblocator.msttblasset a on v.assettypeid=a.assettypeid "
								+ " inner join dblocator.msttblmake as mm on mm.makeid=v.makeid "
								+ " inner join dblocator.msttbluserlogin c on v.loginid=c.loginid "
								+ " where "
								+ " v.flag=0 "
								+ " and  case when('"+searchbyVendorname+"'!='')  then v.vendorfirmname like '%'||'"+searchbyVendorname+"'||'%' else v.flag=0  end "
								+ " order by v.vendorfirmname asc ; ";
						
				System.out.println("q  "+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// v.vendorid , v.vendorfirmname, v.vendoraddress, v.city,
					// v.statename, v.landlinenumber,
					// v.contactperson, v.designation, v.mobilenumber,
					// v.emailid, v.assettypeid, v.servicestationaddr,
					// v.servstationcontactno, v.creditdays,
					// v.loginid, v.datetimestamp , v.paymentdate, v.remarks
					// ,v.flag,a.assetname

					vendor_details obj = new vendor_details();
					obj.setVendorid(rs4.getString(0+1));
					obj.setVendorfirmname(rs4.getString(1+1));
					obj.setVendoraddress(rs4.getString(2+1));
					obj.setCity(rs4.getString(3+1));
					obj.setStatename(rs4.getString(4+1));
					obj.setLandlinenumber(rs4.getString(5+1));
					obj.setContactperson(rs4.getString(6+1));
					obj.setDesignation(rs4.getString(7+1));
					obj.setMobilenumber(rs4.getString(8+1));
					obj.setEmailid(rs4.getString(9+1));
					obj.setAssettypeid(rs4.getString(10+1));
					obj.setServicestationaddr(rs4.getString(11+1));
					obj.setServstationcontactno(rs4.getString(12+1));
					obj.setCreditdays(rs4.getString(13+1));
					obj.setLoginid(rs4.getString(14+1));
					obj.setDatetimestamp(rs4.getString(15+1));
					obj.setPaymentdate(rs4.getString(16+1));
					obj.setRemarks(rs4.getString(17+1));
					// obj.setFlag(rs4.getString(18+1));
					obj.setAssetname(rs4.getString(19+1));
					obj.setMakeid(rs4.getString(20+1));
					obj.setMakename(rs4.getString(21+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/searchByVendorName")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchByVendorName(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("vendorname")String vendorname) {
		List<vendor_details> data = new ArrayList<vendor_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice vendorde ");
//			int k = 0, flag = 1;
//		//	System.out.println("size = "+list.size());
//			while (k < list.size()) {
//			//	System.out.println("loginid = "+list.get(k).getLoginid());
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchbyvendorname', '" + loginid
						+ "', '"+vendorname+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				System.out.println("vendorquery"+sqlselect4);
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
					// v.vendorid , v.vendorfirmname, v.vendoraddress, v.city,
					// v.statename, v.landlinenumber,
					// v.contactperson, v.designation, v.mobilenumber,
					// v.emailid, v.assettypeid, v.servicestationaddr,
					// v.servstationcontactno, v.creditdays,
					// v.loginid, v.datetimestamp , v.paymentdate, v.remarks
					// ,v.flag,a.assetname

					vendor_details obj = new vendor_details();
					obj.setVendorid(rs4.getString(0+1));
					obj.setVendorfirmname(rs4.getString(1+1));
					obj.setVendoraddress(rs4.getString(2+1));
					obj.setCity(rs4.getString(3+1));
					obj.setStatename(rs4.getString(4+1));
					obj.setLandlinenumber(rs4.getString(5+1));
					obj.setContactperson(rs4.getString(6+1));
					obj.setDesignation(rs4.getString(7+1));
					obj.setMobilenumber(rs4.getString(8+1));
					obj.setEmailid(rs4.getString(9+1));
					obj.setAssettypeid(rs4.getString(10+1));
					obj.setServicestationaddr(rs4.getString(11+1));
					obj.setServstationcontactno(rs4.getString(12+1));
					obj.setCreditdays(rs4.getString(13+1));
					obj.setLoginid(rs4.getString(14+1));
					obj.setDatetimestamp(rs4.getString(15+1));
					obj.setPaymentdate(rs4.getString(16+1));
					obj.setRemarks(rs4.getString(17+1));
					// obj.setFlag(rs4.getString(18+1));
					obj.setAssetname(rs4.getString(19+1));
					obj.setMakeid(rs4.getString(20+1));
					obj.setMakename(rs4.getString(21+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					data.add(obj);
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/roledetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response roleDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchbyrolename")String searchbyrolename) {
		List<role_details> data = new ArrayList<role_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		System.out.println("userdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");
//			int k = 0, flag = 1;
//		//	System.out.println("size = "+list.size());
//			while (k < list.size()) {
			// String sqlselect4="select vehicle_creation()";
			if(searchbyrolename==null)
			{
				searchbyrolename="";
			}
			String sqlselect4;
//			if(rolename.contains(null))
//			{
//				sqlselect4 = "select * from dblocator.selectprocedure('selectRole',"
//						+ " '"+loginid+"', '''', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
//			}
//			else
//			{
	/*		sqlselect4 = "select * from dblocator.selectprocedure('selectRole',"
					+ " '"+loginid+"', '"+searchbyrolename+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			
			
			 sqlselect4= " select mr.roleid, mr.rolename, mr.companyid, mr.loginid, mr.datetimestamp, mr.remarks, mr.flag, mc.parentcompid "
					+ " from dblocator.msttblrole as mr inner join dblocator.msttblcompany as mc on mc.companyid=mr.companyid "
					+ " where mr.loginid!=0 and mr.flag=0 and "
					+ " case when('"+searchbyrolename+"'!='') then mr.rolename like '%'||'"+searchbyrolename+"'||'%' else mr.flag=0  end "
					+ " order by mr.rolename asc; ";
			//}
			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			int indexno=1;
//			if (list.get(k).getLoginid().equals(loginid)) {
//				flag = 0;
//			}
			while (rs4.next()) {
				
				// System.out.println("values ="+rs4.getString(3+1));
				// roleid, rolename, companyid, loginid, datetimestamp, remarks,
				// flag
				
					role_details obj = new role_details();
					obj.setRoleid(rs4.getString(0+1));
					obj.setRolename(rs4.getString(1+1));
					obj.setCompanyid(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					obj.setParentcompid(rs4.getString(7+1));
					obj.setRowno(indexno);
					indexno++;
					
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					data.add(obj);
				}
//			k++;
//			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/simassigndetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simAssignDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchbydeviceno")String searchbydeviceno) {
		List<simassign_details> data = new ArrayList<simassign_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice simassigndetails");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
		if(searchbydeviceno==null)
		{
			searchbydeviceno="";
		}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectsimassign', '"
						+ loginid + "', '"+searchbydeviceno+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
		           String sqlselect4=" select d.deviceid,d.uniqueid,da.simid,s.simnumber,n.networkname,s.networkid,da.devicesimid,da.remarks,s.mobilenumber,mdm.flag, 0 "
		           		+ " from dblocator.msttbldevice d "
		           		+ " inner join dblocator.msttbldevicesimmap da on d.deviceid=da.deviceid "
		           		+ " inner join dblocator.msttblsim s on da.simid=s.simid "
		           		+ " inner join dblocator.msttblnetwork n on s.networkid=n.networkid "
		           		+ " inner join dblocator.msttblmappingdevicesims_toall as mdm on mdm.devicesimmapid = da.devicesimid "
		           		+ " inner join dblocator.msttbluserlogin c on mdm.loginid=c.loginid where mdm.loginid='"+ loginid +"'::numeric "
		           		+ " and da.flag=0 and  case when('"+searchbydeviceno+"'!='')  then s.simnumber::text like '%'||'"+searchbydeviceno+"'||'%' else da.flag=0 end "
		           		+ " order by  s.simnumber asc; ";
				
				System.out.println("simass"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				int indexno=1;
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
				
					// select d.deviceid,d.uniqueid,da.simid,s.simnumber,n.networkname,s.networkid,da.devicesimid,da.remarks,s.mobilenumber,mdm.flag
					try {
						//System.out.println("in rs simass");
						simassign_details obj = new simassign_details();
						obj.setDeviceid(rs4.getString(0+1));
						obj.setUniqueid(rs4.getString(1+1));
						obj.setSimid(rs4.getString(2+1));
						obj.setSimnumber(rs4.getString(3+1));
						obj.setNetworkname(rs4.getString(4+1));
						obj.setNetworkid(rs4.getString(5+1));
						obj.setDevicesimid(rs4.getString(6+1));
						obj.setRemarks(rs4.getString(7+1));
						obj.setMobileno(rs4.getString(8+1));
						obj.setRowno(indexno);
						indexno++;
						int flag = Integer.parseInt(rs4.getString(10));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						int flag1=Integer.parseInt(rs4.getString(11));
						if(flag1 == 0)
						{
							obj.setSimassign_status("Active");
						}
						else
						{
							obj.setSimassign_status("InActive");
						}
						data.add(obj);
					} catch (Exception e) {
						System.out.println("e " + e);
					}
//					k++;
//				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/searchsimassignByDeviceno")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchsimassignByDeviceno(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("deviceno")String deviceno) {
		List<simassign_details> data = new ArrayList<simassign_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice simassigndetails");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchsimssByDeviceno', '"
						+ loginid + "', '"+deviceno+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println("simass"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				int indexno=1;
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
				
					// select d.deviceid,d.uniqueid,da.simid,s.simnumber,n.networkname,s.networkid,da.devicesimid,da.remarks,s.mobilenumber,mdm.flag
					try {
						simassign_details obj = new simassign_details();
						obj.setDeviceid(rs4.getString(0+1));
						obj.setUniqueid(rs4.getString(1+1));
						obj.setSimid(rs4.getString(2+1));
						obj.setSimnumber(rs4.getString(3+1));
						obj.setNetworkname(rs4.getString(4+1));
						obj.setNetworkid(rs4.getString(5+1));
						obj.setDevicesimid(rs4.getString(6+1));
						obj.setRemarks(rs4.getString(7+1));
						obj.setMobileno(rs4.getString(8+1));
						obj.setRowno(indexno);
						indexno++;
						int flag = Integer.parseInt(rs4.getString(10));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						int flag1=Integer.parseInt(rs4.getString(11));
						if(flag1 == 0)
						{
							obj.setSimassign_status("Active");
						}
						else
						{
							obj.setSimassign_status("InActive");
						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
//					k++;
//				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/deviceassigndetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response DeviceAssignDetails(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid,@QueryParam("searchByDeviceVehicle")String searchByDeviceVehicle) {
		List<deviceassign_details> data = new ArrayList<deviceassign_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice deviceass ");

//			int k = 0, flag = 1;
			int indexno=1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectvehassigngps', '"
						+ loginid + "', '', '"+searchByDeviceVehicle+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select va.id, v.vehicleid,v.vehicleregno,va.deviceid,va.assigndate,d.uniqueid,va.remarks "
					+ " from dblocator.msttblvehicle v "
					+ "	inner join dblocator.msttblvehicleassigngps va on v.vehicleid=va.vehicleid and va.status=1 "
					+ "	inner join dblocator.msttblmappingvehicledevices_toall as mdm on mdm.vehicledevicemapid = va.id "
					+ "	inner join dblocator.msttbluserlogin c on mdm.loginid=c.loginid "
					+ "	inner join dblocator.msttbldevice d on va.deviceid=d.deviceid where va.flag=0 and "
					+ " case when('" + loginid + "' != '-1')then mdm.loginid = '" + loginid + "'::numeric else mdm.loginid!='" + loginid + "'::numeric end "
				//  + "	and case when($3!= '')then   v.vehicleregno ilike $3 else ( v.vehicleregno is null or v.vehicleregno=''or v.vehicleregno!='') end "
				 //   + " and case when($5!= '')then   d.uniqueid  ilike $5 else ( d.uniqueid  is null or d.uniqueid =''or d.uniqueid !='') end "
					+ "	and case when( '"+searchByDeviceVehicle+"'!='') then v.vehicleregno like '%'|| '"+searchByDeviceVehicle+"'||'%' or  d.uniqueid like '%'|| '"+searchByDeviceVehicle+"'||'%' else va.flag=0  end  "
					+ "	order by v.vehicleregno asc; ";

//				System.out.println(sqlselect4);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
				
					// va.id,
					// v.vehicleid,v.vehicleregno,va.deviceid,va.assigndate,d.uniqueid,va.remarks
					try {
						deviceassign_details obj = new deviceassign_details();
						obj.setId(rs4.getString(0+1));
			
						obj.setVehicleid(rs4.getString(1+1));
						obj.setVehicleregno(rs4.getString(2+1));
						//obj.setAssigndate(rs4.getString(4+1));
						
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy");
						java.util.Date parseTimestamp = sdf1.parse(rs4.getString(4+1));
//						dd.setDatereceived(sdfnew.format(parseTimestamp));
//						dd.setTrackdate(parseTimestamp.toLocaleString());
						obj.setAssigndate(sdfnew.format(parseTimestamp));
						
//						System.out.println(obj.getAssigndate()+""+parseTimestamp);
						obj.setUniqueid(rs4.getString(5+1));
						obj.setRemarks(rs4.getString(6+1));
						obj.setRowno(indexno);
						indexno++;
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
					} catch (Exception e) {
				System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	

	

	@GET
	@Path("/customerassigndetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerAssignDetails() {
		List<customerdevice_assigndetails> data = new ArrayList<customerdevice_assigndetails>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selectcustomerdeviceassign', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			while (rs4.next()) {
				
				// customerid, customername, deviceid, devicename
				try {
					customerdevice_assigndetails obj = new customerdevice_assigndetails();
					obj.setCustomerid(rs4.getString(0+1));
					obj.setCustomername(rs4.getString(1+1));
					obj.setDeviceid(rs4.getString(2+1));
					obj.setDevicename(rs4.getString(3+1));
					obj.setSalecost(rs4.getString(4+1));
					obj.setCredit_money(rs4.getString(5+1));
					obj.setPayement_mode(rs4.getString(6+1));
					obj.setDevicemapid(rs4.getString(7+1));
					obj.setDatetimesatmp(rs4.getString(8+1));
					obj.setCompanyname(rs4.getString(9+1));
					obj.setCompanyid(rs4.getString(10+1));
					obj.setDealerid(rs4.getString(11+1));
					obj.setDealername(rs4.getString(12+1));
					obj.setRowno(indexno);
					indexno++;
					data.add(obj);
				} catch (Exception e) {
				//	System.out.println("e " + e);
				}
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/vehicleassigndetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleAssigntoGPSDetails(@QueryParam("loginid") String loginid,
			@QueryParam("companyid") String companyid) {
		List<vehicleassigntogps_details> data = new ArrayList<vehicleassigntogps_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde");

			// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selectvehassigngps', '" + loginid
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
int indexno=1;
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			while (rs4.next()) {
			
				// v.vehicleid,v.vehicleregno,va.deviceid,va.assigndate,c.companyname,v.companyid,d.uniqueid
				vehicleassigntogps_details obj = new vehicleassigntogps_details();
				obj.setId(rs4.getString(0+1));
				obj.setVehicleid(rs4.getString(1+1));
				obj.setVehicleregno(rs4.getString(2+1));
				obj.setDeviceid(rs4.getString(3+1));
				obj.setAssigndate(rs4.getString(4+1));
				obj.setCompanyname(rs4.getString(5+1));
				obj.setCompanyid(rs4.getString(6+1));
				obj.setUniqueid(rs4.getString(7+1));
				obj.setRemarks(rs4.getString(8+1));
				obj.setRowno(indexno);
				indexno++;
				data.add(obj);
			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close();
				// System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/fuletypedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response fuletypedetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid) {
		List<fueltype_details> data = new ArrayList<fueltype_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledetails ");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectFuletype', '"
						+ loginid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
//			//	System.out.println("in vehicledetails " + list.get(k).getLoginid() + " " + loginid);

				while (rs4.next()) {
					
					// ft.fueltypeid, ft.fueltypedesc, ft.assettypeid,
					// ft.loginid, ft.datetimestamp, ft.remarks,
					// ft.flag,ma.assetname
					fueltype_details obj = new fueltype_details();
					obj.setFueltypeid(rs4.getString(0+1));
					obj.setFueltypedesc(rs4.getString(1+1));
					obj.setAssettypeid(rs4.getString(2+1));
					obj.setLoginid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setRemarks(rs4.getString(5+1));
					obj.setFlag(rs4.getString(6+1));
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					obj.setAssetname(rs4.getString(7+1));
					data.add(obj);

				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/geofencedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response geofenceDetails(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid) {
		List<geofence_details> data = new ArrayList<geofence_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectgeofence', '"
						+ loginid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4="  Select mg.id,mg.geofencename,ST_AsGeoJSON(mg.coord),mg.datatimestamp,mg.loginid From dblocator.msttblgeofence as mg "
					+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mg.loginid "
					+ " where mg.flag=0; ";

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				int m=0;
				while (rs4.next()) {
					
					// mg.id,mg.geofencename,mg.coord,mg.datatimestamp,mg.loginid
					try {
						
						geofence_details obj = new geofence_details();
						obj.setId(rs4.getString(1));
						obj.setGeofencename(rs4.getString(2));
						String fence = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":"+rs4.getString(3)+",\"properties\":null}]}";
					//	System.out.println("fence ="+fence);
						obj.setCoord(fence);
						obj.setDatatimestamp(rs4.getString(4));
						obj.setLoginid(rs4.getString(5));
						
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
						m++;
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
//	selectgeofencecoord
	
	
	

	@GET
	@Path("/vehicletypedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicletypeDetails(@QueryParam("loginid") String loginid,
			@QueryParam("companyid") String companyid) {
		List<vehicletype_details> data = new ArrayList<vehicletype_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  vehiceltypedetails");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select sim_creation()";
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectvehicletype', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" SELECT vehicletypeid, vehicletypename, assettypeid, loginid, datetimestamp,remarks, flag "
					+ " FROM dblocator.msttblvehicletype where flag=0; ";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}

				while (rs4.next()) {
					
					try {
						// vehicletypeid, vehicletypename, assettypeid, loginid,
						// datetimestamp,remarks, flag
						vehicletype_details obj = new vehicletype_details();
						obj.setVehicletypeid(rs4.getString(0+1));
						obj.setVehicletypename(rs4.getString(1+1));
						obj.setAssettypeid(rs4.getString(2+1));
						obj.setLoginid(rs4.getString(3+1));
						obj.setDatetimestamp(rs4.getString(4+1));
						obj.setRemarks(rs4.getString(5+1));
						obj.setFlag(rs4.getString(6+1));
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/geofencereport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response geofenceReport(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("fdate") String fdate,@QueryParam("tdate") String tdate,@QueryParam("type") String type) {
		List<geofencereportdet> data = new ArrayList<geofencereportdet>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  vehiceltypedetails");
//			int k = 0, flag = 1;
			String tblname= null;
			if(type.equals("geofence_wise")){
				tblname = "getgeofence_geofencewise";
			}else{
				tblname = "getgeofence";
			}
			
			String sqlselect4 = "select * from dblocator."+tblname+"('"+vehicleno+"','"+fdate+"','"+tdate+"')";
//		//	System.out.println()
//			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			/* sqlselect4 = "select * from dblocator.selectprocedure('selectGeofenceDetails', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			 sqlselect4=" SELECT vehicleno, indatatimestamp, outdatatimestamp, geofencename "
					+ " FROM dblocator.geofencedetails; ";
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm a");
				SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
				SimpleDateFormat sdf4 = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				java.util.Date dd2=null,dd1=null;

				while (rs4.next()) {
					
					try {
						// vehicletypeid, vehicletypename, assettypeid, loginid,
						// datetimestamp,remarks, flag
						geofencereportdet obj = new geofencereportdet();
						obj.setVehicleno(rs4.getString(1));
						
						dd1 = sdf.parse(rs4.getString(2));
						obj.setIntime(sdf3.format(dd1).toString());
						
						if(rs4.getString(3)!=null){
							 dd2 = sdf.parse(rs4.getString(3));
							 obj.setOuttime(sdf3.format(dd2).toString());
						}else{
							dd2 = sdf4.parse(tdate);
							obj.setOuttime("In geogence till end date");
						}
						
						
						obj.setGeofencename(rs4.getString(4));
						
						obj.setStoppage(String.valueOf((Math.abs(Integer.parseInt(String.valueOf(compareTwoTimeStamps(new Timestamp(dd2.getTime()), new Timestamp(dd1.getTime()))))))));
						data.add(obj);
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
				}
//				k++;
//				System.out.println(data.size());
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/userdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userDetails(@QueryParam("loginid") String loginid, @QueryParam("ownersid") String ownersid, 
			@QueryParam("roleid") String roleid,@QueryParam("searchusername")String searchusername ) {
		List<user_details> data = new ArrayList<user_details>();
		System.out.println("in userdetails");
		
//		List<parentcomp_details> list = getLoginId(ownersid);
		Connection con = null;
		int indexno=1;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//		//	System.out.println("Companyid = " + companyid);
//		//	System.out.println("size = "+list.size());
			int k = 0, flag = 1;
			for(int i=0; i<3 ; i++){
				if(i==0){
//				//	System.out.println("in if");
					roleid="1002";
				}else if(i==1){
//				//	System.out.println("in else");
					roleid="1003";
				}else if(i==2){
					roleid="1001";
				}
				k=0;
//			while (k < list.size()) {
//			//	System.out.println("ownersid = "+list.get(k).getCompanyid());
				
				if(searchusername==null)
				{
					searchusername="";
				}
				
			String sqlselect4 = "select * from dblocator.selectprocedure('selectUser', '" + ownersid
					+ "', '"+roleid+"', '"+searchusername+"', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			System.out.println("in userdet"+sqlselect4);
			
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//		//	System.out.print(sqlselect4);
//			if (list.get(k).getCompanyid().equals(ownersid)) {
//				flag = 0;
//			}
			while (rs4.next()) {
				
				try {

					//System.out.println("in rs");
//					ml.loginname, ml.password, ml.ownersid, 
//					ml.roleid, ml.datetimestamp, ml.loginid, ml.flag, ml.controlid, md.dealername, mr.rolename
					user_details obj = new user_details();
					obj.setLoginname(rs4.getString(0+1));
					obj.setPassword(rs4.getString(1+1));
					obj.setOwnersid(rs4.getString(2+1));
					obj.setRoleid(rs4.getString(3+1));
					obj.setDatetimestamp(rs4.getString(4+1));
					obj.setLoginid(rs4.getString(5+1));
					obj.setControlid(rs4.getString(7+1));
					obj.setDealername(rs4.getString(8+1));
					obj.setRolename(rs4.getString(9+1));
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}

					data.add(obj);
				} catch (Exception e) {
				//	System.out.println("e = " + e);
				}
			}
//			k++;
//		}
	}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/custoerwisedevice")
	@Produces(MediaType.APPLICATION_JSON)
	public Response custoerwisedevice(@QueryParam("customerid") String customerid) {
		List<customerwisedevice> data = new ArrayList<customerwisedevice>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			 System.out.println("Connected in websservice customerwisedevice ");
			 ///System.out.println("customerid"+customerid);
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				/*String sqlselect4 = "select * from dblocator.selectprocedure('customerwisedevice', '"
						+ customerid + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			 String sqlselect4=" select d.deviceid, d.makeid,d.modelid,d.uniqueid, d.loginid, d.datetimestamp, d.remark, d.flag, "
			 		+ " m.makename,mk.modelname,d.status_sim,d.status_veh, d.status, d.status_customer,1,md.customername "
			 		+ " from dblocator.msttblmappingdevices_toall as mpd "
			 		+ " inner join dblocator.msttbldevice as d on d.deviceid = mpd.deviceid "
			 		+ "	inner join dblocator.msttblcustomerdevice_assign as mda on d.deviceid = mda.deviceid "
			 		+ "	inner join dblocator.msttblmake m on d.makeid = m.makeid "
			 		+ "	inner join dblocator.msttblmodel mk  on d.modelid = mk.modelid "
			 		+ "	inner join dblocator.msttbluserlogin c on mpd.loginid=c.loginid "
			 		+ "	inner join dblocator.msttblcustomer as md on md.customerid=mda.customerid "
			 		+ "	where mda.customerid='"+ customerid + "'::numeric and d.flag=0; ";
			 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
//			//	System.out.println("flag = " + flag);
				// deviceid, makeid, modelid, uniqueid, loginid, datetimestamp,
				// remark, flag, companyid, assetid, available, vendorid
				while (rs4.next()) {
					
					try {
						customerwisedevice obj = new customerwisedevice();

						// d.deviceid, d.makeid,d.modelid,d.uniqueid, d.loginid,
						// d.datetimestamp, d.remark, d.flag,
						// m.makename,mk.modelname

						obj.setDeviceid(Long.parseLong(rs4.getString(1)));
						obj.setMakeid(Long.parseLong(rs4.getString(2)));
						obj.setModelid(Long.parseLong(rs4.getString(3)));
						obj.setUniqueid(rs4.getString(4));
						obj.setLoginid(Long.parseLong(rs4.getString(5)));
						obj.setDatetimestamp(rs4.getString(6));
						obj.setRemark(rs4.getString(7));
						// obj.setFlag(Integer.parseInt(rs4.getString(7)));
						obj.setMakename(rs4.getString(9));
						obj.setModelname(rs4.getString(10));
						String status = rs4.getString(11);
						obj.setRowno(indexno);
						indexno++;
						if (status.equals("1")) {
							obj.setStatussim("Sim Assigned");
						} else {
							obj.setStatussim("Sim Not Assigned");
						}

						status = rs4.getString(12);
						if (status.equals("1")) {
							obj.setStatusvehicle("Assigned to Vehicle");
						} else {
							obj.setStatusvehicle("Not  Assigned to Vehicle");
						}
						obj.setStatus(rs4.getString(13));
						obj.setStatus_customer(rs4.getString(14));
						int flag = Integer.parseInt(rs4.getString(15));
						if (flag == 1) {
							obj.setFlag("false");
						} else {
							obj.setFlag("true");
						}
						obj.setCustomername(rs4.getString(16));
						
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e = " + e);
					}
				}
				
				
				
				 
				
				
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	// --------------------------------------select
	// Function-------------------------------------

	// ---------------------------------InsertUpdate----------------------------------------

	@GET
	@Path("/menudelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menudelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteMenu");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deletemenu'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/userdelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response userdelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteUser");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteUser'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/userinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUser(@QueryParam("loginname") String loginname, @QueryParam("password") String password,
			@QueryParam("companyid") String companyid, @QueryParam("controlid") String controlid,
			@QueryParam("roleid") String roleid, @QueryParam("ownersid") String ownersid) {
		
		Connection con = null;
		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice  user insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			String sqlselect4 = null;
			// String sqlselect4=" select
			// company_creation_insert1('"+maxID+1+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"','"+dd+"',10001,'"+remark+"')";
//			loginid,loginname, password, roleid, datetimestamp,flag, controlid, ownersid,companyid
			try {
				// loginname, password, roleid, datetimestamp, flag, companyid
				sqlselect4 = "select * from dblocator.insertprocedure('insertUser', '0', '" + loginname + "', " + "'"
						+ password + "', '" + roleid + "', '" + tt.toLocaleString() + "', '0', '"+controlid+"'," + "'" + ownersid
						+ "', '"+companyid+"',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println(sqlselect4);
			} catch (Exception e) {
			System.out.println("in insert qer" + e);
			}
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean b = ps.execute();

			if (b) {
			//	System.out.println("success");

			} else {
			//	System.out.println("failedhhhhh");
			}

			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
			System.out.println("insert" + e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
			
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/userupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertUpdate(@QueryParam("loginname") String loginname, @QueryParam("password") String password,
			@QueryParam("companyid") String companyid, @QueryParam("controlid") String controlid,
			@QueryParam("roleid") String roleid, @QueryParam("ownersid") String ownersid,
			@QueryParam("loginid") String loginid) {
		
		Connection con = null;
		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  company insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			String sqlselect4 = null;
			// String sqlselect4=" select
			// company_creation_insert1('"+maxID+1+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"','"+dd+"',10001,'"+remark+"')";
//			loginid,loginname, password, roleid, datetimestamp,flag, controlid, ownersid,companyid
			try {
				// loginname, password, roleid, datetimestamp, flag, companyid
				sqlselect4 = "select * from dblocator.insertprocedure('insertUser', '"+loginid+"', '" + loginname + "', " + "'"
						+ password + "', '" + roleid + "', '" + tt.toLocaleString() + "', '0', '"+controlid+"'," + "'" + ownersid
						+ "', '"+companyid+"',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			} catch (Exception e) {
			//	System.out.println("in insert qer" + e);
			}
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean b = ps.execute();

			if (b) {
			//	System.out.println("success");

			} else {
			//	System.out.println("failedhhhhh");
			}

			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		//	System.out.println("insert" + e);
			
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	@GET
	@Path("/insertFlagDet")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertFlagDet(@QueryParam("vehicleid") String vehicleid) {
		
		Connection con = null;
		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  company insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			String sqlselect4 = null;
			// String sqlselect4=" select
			// company_creation_insert1('"+maxID+1+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"','"+dd+"',10001,'"+remark+"')";
//			loginid,loginname, password, roleid, datetimestamp,flag, controlid, ownersid,companyid
			try {
				// loginname, password, roleid, datetimestamp, flag, companyid
				sqlselect4 = "select * from dblocator.insertprocedure('insertFlagDet', '"+vehicleid+"', '', " + "'"
						+ "', '', '', '', ''," + "'"
						+ "', '',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			} catch (Exception e) {
				System.out.println("in insert qer" + e);
			}
			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean b = ps.execute();

			if (b) {
			//	System.out.println("success");

			} else {
			//	System.out.println("failedhhhhh");
			}

			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
			System.out.println("insert" + e);
			
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	
	@GET
	@Path("/insertFlagDetu")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertFlagDetu(@QueryParam("vehicleid") String vehicleid) {
		
		Connection con = null;
		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  company insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			String sqlselect4 = null;
			// String sqlselect4=" select
			// company_creation_insert1('"+maxID+1+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"','"+dd+"',10001,'"+remark+"')";
//			loginid,loginname, password, roleid, datetimestamp,flag, controlid, ownersid,companyid
			try {
				// loginname, password, roleid, datetimestamp, flag, companyid
				sqlselect4 = "select * from dblocator.insertprocedure('insertFlagDetu', '"+vehicleid+"', '', " + "'"
						+ "', '', '', '', ''," + "'"
						+ "', '',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			} catch (Exception e) {
				System.out.println("in insert qer" + e);
			}
			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean b = ps.execute();

			if (b) {
			//	System.out.println("success");

			} else {
			//	System.out.println("failedhhhhh");
			}

			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
			System.out.println("insert" + e);
			
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	

	@GET
	@Path("/companyinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertCom(@QueryParam("companyname") String companyname, @QueryParam("shortname") String shortname,
			@QueryParam("caddress") String caddress, @QueryParam("personname") String personname,
			@QueryParam("landlin1") String landlin1, @QueryParam("faxnumber") String faxnumber,
			@QueryParam("landlin2") String landlin2,
			@QueryParam("regisraddress") String regisraddress, @QueryParam("emailid") String emailid,
			@QueryParam("remark") String remark, @QueryParam("parentcompid") String parentcompid,
			@QueryParam("loginid") String loginid,@QueryParam("city") String city,@QueryParam("state") String state,@QueryParam("zip") String zip) {

		Connection con = null;
		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice  company insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			String sqlselect4 = null;
			// String sqlselect4=" select
			// company_creation_insert1('"+maxID+1+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"','"+dd+"',10001,'"+remark+"')";
			
			
			try {
//				companyid, companyname, shortname, companyaddress, contactpersonname, 
//				lanldlinenumber1, lanldlinenumber2, faxnumber, emailid, registeredaddress, loginid, datetimestamp, remarks,parentcompid,flag,city,state,zip
				sqlselect4 = "select * from dblocator.insertprocedure('insertCompany', '0', '" + companyname + "', " + "'"
						+ shortname + "', '" + caddress + "', '" + personname + "', '" + landlin1 + "', " + "'"
						+ landlin2 + "', '" + faxnumber + "', '" + emailid + "', ' " + regisraddress + "', '"
						+ loginid + "', '" + tt.toLocaleString() + "', '" + remark + "'," + "'" + parentcompid+"',"
						+ "'0', '"+city+"', '"+state+"', '"+zip+"', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//				System.out.println(sqlselect4);
			} catch (Exception e) {
			//	System.out.println("in insert qer" + e);
			}
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean b = ps.execute();

			if (b) {
			//	System.out.println("success");

			} else {
			//	System.out.println("failedhhhhh");
			}
			
			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		//	System.out.println("insert" + e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/companyedit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCom(@QueryParam("companyid") String companyid, @QueryParam("companyname") String companyname,
			@QueryParam("shortname") String shortname, @QueryParam("caddress") String caddress,
			@QueryParam("personname") String personname, @QueryParam("landlin1") long landlin1,
			@QueryParam("faxnumber") long faxnumber, @QueryParam("regisraddress") String regisraddress,
			@QueryParam("emailid") String emailid, @QueryParam("remark") String remark,
			@QueryParam("parentcompid") String parentcompid, @QueryParam("loginid") String loginid,@QueryParam("city") String city,@QueryParam("state") String state,@QueryParam("zip") String zip) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice editcom");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// String sqlselect4=" select
			// company_creation_update('"+maxID+"','"+companyname+"','"+shortname+"','"+caddress+"','"+personname+"','"+landlin1+"','"+landlin1+"','"+faxnumber+"','"+emailid+"','"+regisraddress+"',0,null,'"+remark+"')";
			// company_creation_insert(ncompanyid numeric,
			// ncompanyname character varying, nshortname character,
			// ncompanyaddress character varying, ncontactpersonname character
			// varying, nlanldlinenumber1 numeric, nlanldlinenumber2 numeric,
			// nfaxnumber numeric, nemailid character varying,
			// nregisteredaddress character varying, nloginid numeric,
			// ndatetimestamp timestamp without time zone, nremarks character
			// varying)

			String sqlselect4 = "select * from dblocator.insertprocedure('insertCompany', '" + companyid + "', '"
					+ companyname + "', " + "'" + shortname + "', '" + caddress + "', '" + personname + "', '"
					+ landlin1 + "', " + "'" + landlin1 + "', '" + faxnumber + "', '" + emailid + "',  " + "'"
					+ regisraddress + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remark + "'," + "'"
					+ parentcompid+"',"
					+ "'0', '"+city+"', '"+state+"', '"+zip+"', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			ResultSet rs = ps.executeQuery();

			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	// insertBCUDevice
	@GET
	@Path("/siminsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCom(@QueryParam("networkid") String networkid, @QueryParam("assettypeid") String assettypeid,
			@QueryParam("simno") String simno, @QueryParam("mobileno") String mobileno, @QueryParam("mobileno2") String mobileno2,
			@QueryParam("vendorid") String vendorid, @QueryParam("receiptdt") String receiptdt,
			@QueryParam("loginid") String loginid, @QueryParam("remark") String remark) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice simins insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// String sqlselect4=" select
			// sim_creation_insert('"+maxID+1+"','"+network+"',110010,'"+simno+"','"+mobileno+"','"+vendor+"',0,44001,null,'"+remark+"',0,'"+company+"',null,null)";

			// simid, networkid, assettypeid, simnumber, mobilenumber, vendorid,
			// receiptdt, loginid, datetimestamp, remarks, flag
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertsim'," + " '0', '" + networkid + "', "+ "'" + assettypeid + "', '" + simno + "', '" + mobileno + "', '" + vendorid + "', " + "'"
					+ receiptdt + "', '" + loginid + "', '" + tt.toLocaleString() + "',  " + "'" + remark
					+ "', '0', '" + mobileno2 + "', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
			System.out.println("siminsert"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			return Response.status(200).entity("ok").build();
		} catch (Exception e) {
		  System.out.println(e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		  
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/simupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simUpdate(@QueryParam("simid") String simid, @QueryParam("networkid") String networkid,
			@QueryParam("assettypeid") String assettypeid, @QueryParam("simno") String simno,
			@QueryParam("mobileno") String mobileno, @QueryParam("mobileno2") String mobileno2,@QueryParam("vendorid") String vendorid,
			@QueryParam("receiptdt") String receiptdt, @QueryParam("loginid") String loginid,
			@QueryParam("remark") String remark) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// String sqlselect4=" select
			// sim_creation_insert('"+maxID+1+"','"+network+"',110010,'"+simno+"','"+mobileno+"','"+vendor+"',0,44001,null,'"+remark+"',0,'"+company+"',null,null)";

			// simid, networkid, assettypeid, simnumber, mobilenumber, vendorid,
			// receiptdt, loginid, datetimestamp, remarks, flag

			String sqlselect4 = "select * from dblocator.insertprocedure('insertsim'," + " '" + simid + "', '" + networkid
					+ "', " + "'" + assettypeid + "', '" + simno + "', '" + mobileno + "', '" + vendorid + "', " + "'"
					+ receiptdt + "', '" + loginid + "', '" + tt.toLocaleString() + "',  " + "'" + remark
					+ "', '0', '" + mobileno2 + "', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


	@GET
	@Path("/simdelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simdelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deletesim'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/simactivate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simactivate(@QueryParam("id") String id) {
		Connection con = null;
	try {
		Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			 System.out.println("activatesim");
			String sqlselect4 = "select * from dblocator.insertprocedure('activatesim'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println("errr"+e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	@GET
	@Path("/deviceinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deviceInsert(@QueryParam("makeid") String makeid, @QueryParam("modelid") String modelid,
			@QueryParam("uniqueid") String uniqueid, @QueryParam("loginid") String loginid,
			@QueryParam("remark") String remark,@QueryParam("deviceid") String deviceid) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// deviceid, makeid, modelid, uniqueid, loginid,datetimestamp,
			// remark, flag,companyid,
			// assetid,available,vendorid
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertBCUDevice'," + " '0', '" + makeid + "', "
					+ "'" + modelid + "', '" + uniqueid + "', '" + loginid + "', '" + tt.toLocaleString() + "', " + "'"
					+ remark + "', '" + 0 + "', '"+deviceid+"',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//			System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			return Response.status(200).entity("ok").build();
		} catch (Exception e) {
		  System.out.println(e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		  
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	
	
	
	
	
	@GET
	@Path("/deviceupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deviceUpdate(@QueryParam("deviceid") String deviceid, @QueryParam("makeid") String makeid,
			@QueryParam("modelid") String modelid, @QueryParam("uniqueid") String uniqueid,
			@QueryParam("loginid") String loginid, @QueryParam("remark") String remark) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// deviceid, makeid, modelid, uniqueid, loginid,datetimestamp,
			// remark, flag,companyid,
			// assetid,available,vendorid
			String sqlselect4 = "select * from dblocator.insertprocedure('insertBCUDevice'," + " '" + deviceid + "', '"
					+ makeid + "', " + "'" + modelid + "', '" + uniqueid + "', '" + loginid + "', '"
					+ tt.toLocaleString() + "', " + "'" + remark + "', '" + 0 + "', '',  " + "'', '0', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/devicedelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicedelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteBCUDevice'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/activateDevice")
	@Produces(MediaType.APPLICATION_JSON)
	public Response activateDevice(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice activate  device");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('activateBCUDevice'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		System.out.println("activate"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	 @Path("/dealersaledetails")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerSaleDetails(@QueryParam("companyid") String companyid,@QueryParam("loginid") String loginid,@QueryParam("searchbyDealername")String searchbyDealername) {
	 List<devicesale>data=new ArrayList<devicesale>();
//	 List<parentcomp_details> list = getLoginId(companyid);
	 Connection con = null;
	 try{
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
  System.out.println("Connected in websservice dealersale");
//	 int k=0, flag=1;
	 int indexno=1;
	 int index2=1;
	 String dealer=null;
	 int count = 0, d = 0;
	 String dealername="p", paymentmode=null, dispatchby=null, payedby=null, orderedby=null;
	 
//	  while(k<list.size()){
//	 String sqlselect4="select vehicle_creation()";
		if(searchbyDealername==null)
		{
			searchbyDealername="";
		}
		  
		 /* String sqlselect4 = "select * from dblocator.selectprocedure('selectDeviceSale', '" +loginid
					+ "', '"+searchbyDealername+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
		String sqlselect4="  SELECT ds.transid, ds.dealerid, ds.no_devices, ds.purchaseorder_number, ds.purchaseorder_date, "
				+ " ds.invoice_number, ds.invoice_date, ds.total, ds.tax, ds.octroi, ds.vatt, ds.servicetax, "
				+ " ds.paymentmode, ds.chequeno, ds.chequedate, ds.creditdays, ds.creditamount, "
				+ " ds.payedby, ds.order_placedby, ds.emailidby, ds.phonenoby, ds.nameby, ds.dispatchnameby, "
				+ " ds.dispatchphoneby, ds.dispatchcourier, ds.dispatchpaketno, ds.datetimestamp, "
				+ " ds.loginid, ds.flag, md.dealername,ds.finaltotal,ds.dispatchby,ds.bank_name,ds.branch,ds.accountno,ds.bank_address,ds.ifscno "
				+ " FROM dblocator.msttbldevicesale as ds "
				+ " inner join dblocator.msttblmappingdevicesales_toall as mdm on mdm.devicesaleid = ds.transid "
				+ " inner join dblocator.msttbldealer as md on md.dealerid=ds.dealerid "
				+ " inner join dblocator.msttbluserlogin as mcp on mcp.loginid = mdm.loginid "
				+ " where mdm.loginid='"+loginid+"'::numeric and ds.flag=0 "
				+ " and case when('"+searchbyDealername+"'!='')  then md.dealername like '%'||'"+searchbyDealername+"'||'%'  else ds.flag=0  end "
				+ " order by md.dealername; ";
 
		  
	
	 System.out.println("dealersaledetails"+sqlselect4);

	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Statement st4=con.createStatement();
	 ResultSet rs4=st4.executeQuery(sqlselect4);
//	 if(list.get(k).getLoginid().equals(loginid)){
//			flag=0;
//		}
	 while(rs4.next()){
	
		  // transid, dealerid, no_devices, purchaseorder_number, purchaseorder_date, 
//	       invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//	       paymentmode, chequeno, chequedate, creditdays, creditamount, 
//	       payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//	       dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//	       loginid, flag, finaltotal, dispatchby
		 
		 try{
			 
			 if(index2==1)
			 {
				dealer = rs4.getString(29+1);
				// System.out.println("1rst dealer "+dealer);
			 }
			// System.out.println("Normal dealer "+ rs4.getString(29+1));
			 if(dealer.equals(rs4.getString(29+1))){
				 dealer=rs4.getString(29+1);
				 //System.out.println(dealer);
				 }
			 else
			{
			 devicesale obj = new devicesale();
			 obj.setDealerid(rs4.getString(1+1));
			 obj.setNo_devices(String.valueOf(count));
			 obj.setPaymentmode(rs4.getString(12+1));
			 obj.setPayedby(rs4.getString(17+1));
			 obj.setOrder_placedby(rs4.getString(18+1));
			 obj.setEmailidby(rs4.getString(19+1));
			 obj.setPhonenoby(rs4.getString(20+1));
			 obj.setNameby(rs4.getString(21+1));
			 obj.setDispatchnameby(rs4.getString(22+1));
			 obj.setDispatchphoneby(rs4.getString(23+1));
			 obj.setDispatchcourier(rs4.getString(24+1));
			 obj.setDispatchpaketno(rs4.getString(25+1));
			 obj.setDatetimestamp(rs4.getString(26+1));
			 obj.setLoginid(rs4.getString(27+1));
			 obj.setFlag("0");
			 obj.setDealername(dealer);
			 obj.setRowno(indexno);
			 dispatchby = rs4.getString(22+1);
			 payedby = rs4.getString(17+1);
			 orderedby = rs4.getString(18+1);
			 paymentmode = rs4.getString(12+1);
			 dealer=rs4.getString(29+1);
			 count = 0;
			 data.add(obj);
			 indexno++;
			}
			 
			 count=count+Integer.parseInt(rs4.getString(2+1));
			 index2++;
			
			
		 }catch(Exception e){
			 System.out.println("e "+e);
		 }
	 }
	 
	 devicesale obj = new devicesale();
	 obj.setDealername(dealer);
	 obj.setNo_devices(String.valueOf(count));
	 obj.setPaymentmode(paymentmode);
	 obj.setPayedby(payedby);
	 obj.setOrder_placedby(orderedby);
	 obj.setDispatchnameby(dispatchby);
	 obj.setFlag("0");
	 obj.setRowno(indexno);
	 
	 data.add(obj);
	
	 return Response.status(200).entity(data).build();
	 }
	 catch (Exception e)
	 {
	 System.out.println(e);
	 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		  System.out.println(e);
			e.printStackTrace();
		}
	 }
	}
	
		
	@GET
	 @Path("/customersaledetails")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response customerSaleDetails(@QueryParam("companyid") String companyid,@QueryParam("loginid") String loginid,@QueryParam("searchbyCustomer")String searchbyCustomer) {
	 List<devicesalecustomer>data=new ArrayList<devicesalecustomer>();
	 int indexno=1;
	 int index2=1;
	 String dealer=null;
	 int count = 0, d = 0;
	 String dealername="p", paymentmode=null, dispatchby=null, payedby=null, orderedby=null;
	 
	 Connection con = null;
	 try{
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	  System.out.println("Connected in websservice customersale");
 
		if(searchbyCustomer==null)
		{
			searchbyCustomer="";
		}
		  
		/*  String sqlselect4 = "select * from dblocator.selectprocedure('selectDeviceSalecustomer', '" +loginid
					+ "', '"+searchbyCustomer+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
		String sqlselect4=" SELECT ds.transid, ds.customerid, ds.no_devices, ds.purchaseorder_number, ds.purchaseorder_date, "
				+ " ds.invoice_number, ds.invoice_date, ds.total, ds.tax, ds.octroi, ds.vatt, ds.servicetax, "
				+ " ds.paymentmode, ds.chequeno, ds.chequedate, ds.creditdays, ds.creditamount, "
				+ " ds.payedby, ds.order_placedby, ds.emailidby, ds.phonenoby, ds.nameby, ds.dispatchnameby, "
				+ " ds.dispatchphoneby, ds.dispatchcourier, ds.dispatchpaketno, ds.datetimestamp, "
				+ " ds.loginid, ds.flag, md.customername,ds.finaltotal,ds.dispatchby,ds.bank_name,ds.branch,ds.bank_address,ds.account_no,ds.ifscno "
				+ " ,(select md.dealername from dblocator.msttbldealer as md "
				+ " inner join dblocator.msttbluserlogin as ml on ml.ownersid = md.dealerid "
				+ " where ml.loginid = ds.loginid::numeric) "
				+ " FROM dblocator.msttbldevicesalecustomer as ds "
				+ " inner join dblocator.msttblmappingdevicesalecustomers_toall as mdm on mdm.devicesaleid = ds.transid "
				+ " inner join dblocator.msttblcustomer as md on md.customerid=ds.customerid "
				+ " inner join dblocator.msttbluserlogin as mcp on mcp.loginid = mdm.loginid "
				+ " inner join dblocator.msttbluserlogin as mn on md.loginid = mn.loginid "
				+ " inner join dblocator.msttbldealer as d on d.dealerid=mn.ownersid "
				+ " where mdm.loginid='"+ loginid +"'::numeric and ds.flag=0 and case when('"+searchbyCustomer+"'!='') then md.customername like '%'||'"+searchbyCustomer+"'||'%'  else ds.flag=0  end "
				+ " order by  md.customername asc; ";

		  System.out.println("cust"+sqlselect4);

	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 Statement st4=con.createStatement();
	 ResultSet rs4=st4.executeQuery(sqlselect4);
	 while(rs4.next()){
		 
		 if(index2==1)
		 {
			dealer = rs4.getString(29+1);
		 }
		 //System.out.println("Normal Customer = "+rs4.getString(29+1));
		 if(dealer.equals(rs4.getString(29+1))){
			 dealer=rs4.getString(29+1);
		  }
		 else
		{
		 
		 try{
			 //System.out.println("Customer = "+dealer);
			 devicesalecustomer obj = new devicesalecustomer();
			 
			 obj.setTransid(rs4.getString(0+1));
			 obj.setCustomerid(rs4.getString(1+1));
			 obj.setNo_devices(String.valueOf(count));
			 obj.setPaymentmode(rs4.getString(12+1));
			 obj.setPayedby(rs4.getString(17+1));
			 obj.setOrder_placedby(rs4.getString(18+1));
			 obj.setDispatchnameby(rs4.getString(22+1));
			 obj.setFlag("0");
			 obj.setCustomername(dealer);
			 obj.setDispatchby(rs4.getString(31+1));
			 obj.setDealername(rs4.getString(38));
			 obj.setRowno(indexno);
			 dispatchby = rs4.getString(22+1);
			 payedby = rs4.getString(17+1);
			 orderedby = rs4.getString(18+1);
			 paymentmode = rs4.getString(12+1);
			 dealer = rs4.getString(29+1);
			 count = 0;
			 data.add(obj);
			 indexno++;
		 }catch(Exception e){
			 System.out.println("e "+e);
		 }
		}
		 count=count+Integer.parseInt(rs4.getString(2+1));
		 index2++;
			 
	 }
	 devicesalecustomer obj = new devicesalecustomer();
	 obj.setCustomername(dealer);
	 obj.setNo_devices(String.valueOf(count));
	 obj.setPaymentmode(paymentmode);
	 obj.setPayedby(payedby);
	 obj.setOrder_placedby(orderedby);
	 obj.setDispatchnameby(dispatchby);
	 obj.setFlag("0");
	 obj.setRowno(indexno);
	 
	 data.add(obj);
	 return Response.status(200).entity(data).build();
	 }
	 catch (Exception e)
	 {
	 System.out.println(e);
	 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		  System.out.println(e);
			e.printStackTrace();
		}
	 }
	}


	@GET
	@Path("/devicesaleinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicesaleInsert(@QueryParam("dealerid") String dealerid, @QueryParam("no_devices") String no_devices,
			@QueryParam("purchaseorder_number") String purchaseorder_number, @QueryParam("purchaseorder_date") String purchaseorder_date,
			@QueryParam("invoice_number") String invoice_number, @QueryParam("invoice_date") String invoice_date,
			@QueryParam("total") String total, @QueryParam("tax") String tax,
			@QueryParam("octroi") String octroi, @QueryParam("vatt") String vatt,
			@QueryParam("servicetax") String servicetax, @QueryParam("paymentmode") String paymentmode,
			@QueryParam("chequeno") String chequeno, @QueryParam("chequedate") String chequedate,
			@QueryParam("creditdays") String creditdays, @QueryParam("creditamount") String creditamount,
			@QueryParam("payedby") String payedby,@QueryParam("order_placedby") String order_placedby,
			@QueryParam("emailidby") String emailidby,@QueryParam("phonenoby") String phonenoby,
			@QueryParam("nameby") String nameby,@QueryParam("dispatchnameby") String dispatchnameby,
			@QueryParam("dispatchphoneby") String dispatchphoneby,@QueryParam("dispatchcourier") String dispatchcourier,
			@QueryParam("dispatchpaketno") String dispatchpaketno,@QueryParam("finaltotal") String finaltotal,
			@QueryParam("loginid") String loginid,@QueryParam("dispatchby") String dispatchby,
			@QueryParam("modelid") String modelid,@QueryParam("count") String count,
			@QueryParam("bank_name")String bank_name,@QueryParam("branch")String branch,
			@QueryParam("accoutno")String accoutno,@QueryParam("bank_address")String bank_address,@QueryParam("ifscno")String ifscno) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice dealersale insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
//			transid, dealerid, no_devices, purchaseorder_number, purchaseorder_date, 
//		    invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		    paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		    payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		    dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		    loginid, flag, finaltotal
		    
		
			
//			transid, dealerid, no_devices, purchaseorder_number, purchaseorder_date, 
//		       invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		       paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		       payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		       dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		       loginid, flag, finaltotal,dispatchby
//			
			
			
			String sqlselect4 = " select * from dblocator.insertprocedure('insertDevicesale'," + " '0', '" + dealerid + "', "
					+ "'" + no_devices + "', '" + purchaseorder_number + "', '" + purchaseorder_date + "', '" + invoice_number + "', " + "'"
					+ invoice_date + "', '" + total + "', '" + tax + "',  " + "'" + octroi + "', '"
					+ vatt + "', '" + servicetax + "', '" + paymentmode + "'," + "'" + chequeno + "', '"
					+ chequedate + "', '" + creditdays+ "', '" + creditamount + "', " + "'"+payedby+"',"
					+ "'"+order_placedby+"', '"+emailidby+"', '"+phonenoby+"', '"+nameby+"','"+dispatchnameby+"', '"+dispatchphoneby+"',"
					+ " '"+dispatchcourier+"', '"+dispatchpaketno+"','"+tt.toLocaleString()+"', '"+loginid+"', "
					+ "'0', '"+finaltotal+"','"+dispatchby+"', '"+bank_name+"', "
					+ "'"+branch+"', '"+accoutno+"','"+bank_address+"', '"+ifscno+"', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	System.out.println("query insert"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//			sqlselect4 = "select * from dblocator.insertprocedure('insertdevicetodealer'," + " '" + modelid + "', '" + dealerid
//						+ "', " + "'" + purchaseorder_number + "', '" + tt.toLocaleString() + "', '" + loginid
//						+ "', " + "'"+count+"', '', '',  " + "'', '', '', '',"
//						+ "'', '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//			//	System.out.println("query"+sqlselect4);
//				ps = con.prepareStatement(sqlselect4);
//				rs = ps.execute();
//				
//				if (rs) {
//				return Response.status(200).entity("ok").build();
//				} else {
//					return Response.status(404).entity("Not ok").build();
//				}
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/devicesaleinsertcustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicesaleInsertcustomer(@QueryParam("customerid") String customerid, @QueryParam("no_devices") String no_devices,
			@QueryParam("purchaseorder_number") String purchaseorder_number, @QueryParam("purchaseorder_date") String purchaseorder_date,
			@QueryParam("invoice_number") String invoice_number, @QueryParam("invoice_date") String invoice_date,
			@QueryParam("total") String total, @QueryParam("tax") String tax,
			@QueryParam("octroi") String octroi, @QueryParam("vatt") String vatt,
			@QueryParam("servicetax") String servicetax, @QueryParam("paymentmode") String paymentmode,
			@QueryParam("chequeno") String chequeno, @QueryParam("chequedate") String chequedate,
			@QueryParam("creditdays") String creditdays, @QueryParam("creditamount") String creditamount,
			@QueryParam("payedby") String payedby,@QueryParam("order_placedby") String order_placedby,
			@QueryParam("emailidby") String emailidby,@QueryParam("phonenoby") String phonenoby,
			@QueryParam("nameby") String nameby,@QueryParam("dispatchnameby") String dispatchnameby,
			@QueryParam("dispatchphoneby") String dispatchphoneby,@QueryParam("dispatchcourier") String dispatchcourier,
			@QueryParam("dispatchpaketno") String dispatchpaketno,@QueryParam("finaltotal") String finaltotal,
			@QueryParam("loginid") String loginid,@QueryParam("dispatchby") String dispatchby,
			@QueryParam("modelid") String modelid,@QueryParam("count") String count,@QueryParam("parentloginid") String parentloginid,
			@QueryParam("bank_name")String bank_name,@QueryParam("branch")String branch,@QueryParam("accountno")String accountno,@QueryParam("ifscno")String ifscno,@QueryParam("bank_address")String bank_address) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice dealersale insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
//			transid, customer, no_devices, purchaseorder_number, purchaseorder_date, 
//		    invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		    paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		    payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		    dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		    loginid, flag, finaltotal
		    
		
			
//			transid, customer, no_devices, purchaseorder_number, purchaseorder_date, 
//		       invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		       paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		       payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		       dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		       loginid, flag, finaltotal,dispatchby
//					
			String sqlselect4 = " select * from dblocator.insertprocedure('insertDevicesaleCustomer'," + " '0', '" + customerid + "', "
					+ "'" + no_devices + "', '" + purchaseorder_number + "', '" + purchaseorder_date + "', '" + invoice_number + "', " + "'"
					+ invoice_date + "', '" + total + "', '" + tax + "',  " + "'" + octroi + "', '"
					+ vatt + "', '" + servicetax + "', '" + paymentmode + "'," + "'" + chequeno + "', '"
					+ chequedate + "', '" + creditdays+ "', '" + creditamount + "', " + "'"+payedby+"',"
					+ "'"+order_placedby+"', '"+emailidby+"', '"+phonenoby+"', '"+nameby+"','"+dispatchnameby+"', '"+dispatchphoneby+"',"
					+ " '"+dispatchcourier+"', '"+dispatchpaketno+"','"+tt.toLocaleString()+"', '"+loginid+"', "
					+ "'0', '"+finaltotal+"','"+dispatchby+"', '"+parentloginid+"', "
					+ "'"+bank_name+"', '"+branch+"','"+bank_address+"', '"+accountno+"', '"+ifscno+"', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println("query cust"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//			sqlselect4 = "select * from dblocator.insertprocedure('insertdevicetodealer'," + " '" + modelid + "', '" + customer
//						+ "', " + "'" + purchaseorder_number + "', '" + tt.toLocaleString() + "', '" + loginid
//						+ "', " + "'"+count+"', '', '',  " + "'', '', '', '',"
//						+ "'', '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//			//	System.out.println("query"+sqlselect4);
//				ps = con.prepareStatement(sqlselect4);
//				rs = ps.execute();
//				
//				if (rs) {
//				return Response.status(200).entity("ok").build();
//				} else {
//					return Response.status(404).entity("Not ok").build();
//				}
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/idlereportDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response idlereportDetails(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("fdate") String fdate,@QueryParam("tdate") String tdate,
			@QueryParam("loginid1") String loginid1) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice idelreport summary");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// devicemapid,dealerid, dealername,
			// deviceid,devicename,salecost, credit_money, payement_mode
			String sqlselect4 = "select * from dblocator.getidlehoursreport1('"+vehicleno+"','"+fdate+"',"
					+ "'"+tdate+"','"+loginid1+"')";
			System.out.println(sqlselect4);
			System.out.println("fdate"+fdate+"tdate"+tdate);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//			//	System.out.println(" rs = "+rs);
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("error").build();
			}

		}

		catch (Exception e) {
		System.out.println("indeat"+e);
			return Response.status(404).entity("error").build();
		} finally {
			try {
				con.close(); //System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	

	@GET
	@Path("/idlereport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response idlereport() {
	List<idleReport_details> data = new ArrayList<idleReport_details>();
	Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice ");
//			selectvstop
		/*	String sqlselect4 = "select * from dblocator.selectprocedure('selectidlereport', '"
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" SELECT dv.vehicleno, dv.tdate, dv.hours, (SELECT sum(hours) FROM dblocator.idelreport1 where vehicleno = dv.vehicleno group by vehicleno ) "
					+ " FROM dblocator.idelreport1 as dv; "
					+ " SELECT mv.vehicleno, mv.tdate, "
					+ " mv.switchon_duration, "
					+ " (SELECT sum(switchon_duration) FROM dblocator.iginitionreport where vehicleno = mv.vehicleno group by vehicleno ), "
					+ " mv.switchoff_duration,(SELECT sum(switchoff_duration) FROM dblocator.iginitionreport where vehicleno = mv.vehicleno group by vehicleno ) "
					+ " FROM dblocator.iginitionreport as mv; ";

			System.out.println("query"+sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			String vno = "h";  int i =0;int k=0;
			String dist="";
			String dist1="";
			while (rs4.next()) {
try
{
	idleReport_details  obj = new idleReport_details ();
				if(!vno.equals(rs4.getString(1))&& !dist.equals(rs4.getString(4))){
					obj.setVehicleno(rs4.getString(1));
					//obj.setTotalhours((rs4.getString(4)));
					obj.setTotalhours(rs4.getTime(4).toString());

					}else{
					obj.setVehicleno("");
					//obj.setTotalhours("");
				}
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date parseTimestamp = sdf1.parse(rs4.getString(2));
//				dd.setDatereceived(sdfnew.format(parseTimestamp));
//				dd.setTrackdate(parseTimestamp.toLocaleString());
				obj.setTodate(sdfnew.format(parseTimestamp));
				
				//obj.setTodate(rs4.getString(2));
				obj.setHours(rs4.getString(3));
				data.add(obj);
				vno=rs4.getString(1);
				//dist=rs4.getString(4);
				dist=String.valueOf(Math.round(Double.parseDouble(rs4.getString(4)) * 100.0) / 100.0);
				//dist1=rs4.getString(2);
				i++;
}catch(Exception ex){
//	System.out.println(ex);
	}
}
			if(data.isEmpty()){
				System.out.println("empty data");
				return Response.status(404).entity("").build();
			}else{
				System.out.println("data = "+data);
				return Response.status(200).entity(data).build();
			}
			
		} catch (Exception e) {
			System.out.println("e1"+e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/overspeedReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response overspeedReport(@QueryParam("vehicleno") String vehicleno,@QueryParam("fromdate")String fromdate,@QueryParam("todate")String todate,@QueryParam("overspeedlimit")String overspeedlimit) {
		List<overspeed_details > data = new ArrayList<overspeed_details >();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
 System.out.println("Connected in websservice overspeed report ");
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('overspeedReport1', '"
						+ fromdate + "', '"+todate+"', '"+overspeedlimit+"', '"+vehicleno+"', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
   
                String sqlselect4=" SELECT  mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno, "
                		+ " mstass.deviceid,deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp, "
                		+ " deviceparse.gpsstatus,deviceparse.packettime,deviceparse.vehiclespeed,deviceparse.ignumber "
                		+ "	FROM dblocator.msttblvehicle as mstvehicle "
                		+ "	inner  join dblocator.msttblvehicleassigngps as mstass on mstass.vehicleid=mstvehicle.vehicleid "
                		+ " inner join  dblocator.msttbldevice as mstdev on mstdev.deviceid = mstass.deviceid "
                		+ " inner join  parsed_loc_device_record_'||'"+fromdate+"'::date||'  as deviceparse on deviceparse.imeino= mstdev.uniqueid "
                		+ " where deviceparse.latitude>0 and deviceparse.longitude>0 and deviceparse.latitude<100 and deviceparse.longitude<100 "
                		+ " and   deviceparse.vehiclespeed > '||'"+overspeedlimit+"'::numeric||' and mstvehicle.vehicleregno = '||quote_literal('"+vehicleno+"'::text); "
                		+ " '"+fromdate+"':='"+fromdate+"'::date + interval '1 day'; "
                		+ "End Loop;  ";
 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				System.out.println("in  log"+sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				 System.out.println("in overlint"+overspeedlimit);
				boolean avail=false;;
		while (rs4.next()) {
					try {
						overspeed_details  obj=new overspeed_details ();
						obj.setVehicleid(rs4.getString(1));
						obj.setVehicletypeid(rs4.getString(2));
						obj.setVehicleregno(rs4.getString(3));
						obj.setDeviceid(rs4.getString(4));
						obj.setImeino(rs4.getString(5));
						obj.setLatitude(rs4.getString(6));
						obj.setLongitude(rs4.getString(7));
						
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
						java.util.Date parseTimestamp = sdf1.parse(rs4.getString(8));

						obj.setDatatimestamp(sdfnew.format(parseTimestamp));
						
						if(rs4.getString(9).equals("0"))
						{
						obj.setGpsstatus("Invalid");
						}
						else
						{
							obj.setGpsstatus("Valid");
						}
						obj.setPackettime(rs4.getString(10));
						
						obj.setVehiclespeed(String.valueOf(Math.round(Double.parseDouble(rs4.getString(11)) * 100.0) / 100.0));

						
						//obj.setVehiclespeed(rs4.getString(11));
						if(rs4.getInt(12)==0)
						{
							obj.setIgnumber("ON");
						}
						else
						{
							obj.setIgnumber("OFF");
						}
						
						try{
							obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitude()),
								Double.parseDouble(obj.getLongitude())));
							}catch(Exception e){
							  System.out.println(e);
							}
						if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
							obj.setLocation("Location Not Found");
						}
						//System.out.println("speed"+rs4.getString(11)+"location"+obj.getLocation());
						data.add(obj);
					
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	
	
	
	
	@GET
	@Path("/ignitionReportDetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ignitionReport(@QueryParam("vehicleno") String vehicleno,
			@QueryParam("fdate") String fdate,@QueryParam("tdate") String tdate,
			@QueryParam("loginid1") String loginid1) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice iginitdetais summary");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// devicemapid,dealerid, dealername,
			// deviceid,devicename,salecost, credit_money, payement_mode
			String sqlselect4 = "select * from dblocator.getignitonreport('"+vehicleno+"','"+fdate+"',"
					+ "'"+tdate+"','"+loginid1+"')";
			System.out.println(sqlselect4);
			System.out.println("fdate"+fdate+"tdate"+tdate);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//			//	System.out.println(" rs = "+rs);
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("error").build();
			}

		}

		catch (Exception e) {
		System.out.println("indeat"+e);
			return Response.status(404).entity("error").build();
		} finally {
			try {
				con.close(); //System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/ignitionreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ignitionreport() {
	List<iginition_details> data = new ArrayList<iginition_details>();
	Connection con = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice igintio report ");
//			selectvstop
			String sqlselect4 = "select * from dblocator.selectprocedure('selectIgnitionReport', '"
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

			System.out.println("query"+sqlselect4);
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
			String vno = "h";  int i =0;int k=0;
			String dist="";
			String dist1="";
			while (rs4.next()) {
try
{
		iginition_details  obj = new iginition_details ();
//				if(!vno.equals(rs4.getString(1))&& !dist.equals(rs4.getString(4))){
//					obj.setVehicleno(rs4.getString(1));
//					//obj.setTotalhours((rs4.getString(4)));
//					obj.setTotal_switchonhours(rs4.getTime(4).toString());
//					obj.setTotal_switchoffhours(rs4.getTime(6).toString());
//
//					}else{
//					obj.setVehicleno("");
//					//obj.setTotalhours("");
//				}
		
				obj.setVehicleno(rs4.getString(1));
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy");
				java.util.Date parseTimestamp = sdf1.parse(rs4.getString(2));
				obj.setTdate(sdfnew.format(parseTimestamp));
				obj.setSwitchon_duration(rs4.getString(3));
				obj.setTotal_switchonhours(rs4.getString(4));
				obj.setSwitchoff_duration(rs4.getString(5));
				obj.setTotal_switchoffhours(rs4.getString(6));
				
				data.add(obj);
		
}catch(Exception ex){
	System.out.println(ex);
	}
}
			if(data.isEmpty()){
				System.out.println("empty data");
				return Response.status(404).entity("").build();
			}else{
				System.out.println("data = "+data);
				return Response.status(200).entity(data).build();
			}
			
		} catch (Exception e) {
			System.out.println("e1"+e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	@GET
	@Path("/devicesaleupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicesaleUpdate(@QueryParam("transid") String transid,
			@QueryParam("dealerid") String dealerid, @QueryParam("no_devices") String no_devices,
			@QueryParam("purchaseorder_number") String purchaseorder_number, @QueryParam("purchaseorder_date") String purchaseorder_date,
			@QueryParam("invoice_number") String invoice_number, @QueryParam("invoice_date") String invoice_date,
			@QueryParam("total") String total, @QueryParam("tax") String tax,
			@QueryParam("octroi") String octroi, @QueryParam("vatt") String vatt,
			@QueryParam("servicetax") String servicetax, @QueryParam("paymentmode") String paymentmode,
			@QueryParam("chequeno") String chequeno, @QueryParam("chequedate") String chequedate,
			@QueryParam("creditdays") String creditdays, @QueryParam("creditamount") String creditamount,
			@QueryParam("payedby") String payedby,@QueryParam("order_placedby") String order_placedby,
			@QueryParam("emailidby") String emailidby,@QueryParam("phonenoby") String phonenoby,
			@QueryParam("nameby") String nameby,@QueryParam("dispatchnameby") String dispatchnameby,
			@QueryParam("dispatchphoneby") String dispatchphoneby,@QueryParam("dispatchcourier") String dispatchcourier,
			@QueryParam("dispatchpaketno") String dispatchpaketno,@QueryParam("finaltotal") String finaltotal,
			@QueryParam("loginid") String loginid,@QueryParam("dispatchby") String dispatchby,
			@QueryParam("bank_name")String bank_name,@QueryParam("branch")String branch,
			@QueryParam("accoutno")String accoutno,@QueryParam("bank_address")String bank_address,@QueryParam("ifscno")String ifscno
			) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice dealersaleupdate");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
//			transid, dealerid, no_devices, purchaseorder_number, purchaseorder_date, 
//		       invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		       paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		       payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		       dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		       loginid, flag, finaltotal, dispatchby
		       
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertDevicesale'," + " '"+transid+"', '" + dealerid + "', "
					+ "'" + no_devices + "','" + purchaseorder_number + "', '" + purchaseorder_date + "', '" + invoice_number + "', " + "'"
					+ invoice_date + "', '" + total + "', '" + tax + "',  " + "'" + octroi + "', '"
					+ vatt + "', '" + servicetax + "', '" + paymentmode + "'," + "'" + chequeno + "', '"
					+ chequedate + "', '" + creditdays+ "', '" + creditamount + "', " + "'"+payedby+"',"
					+ "'"+order_placedby+"', '"+emailidby+"', '"+phonenoby+"', '"+nameby+"','"+dispatchnameby+"', '"+dispatchphoneby+"',"
					+ "'"+dispatchcourier+"', '"+dispatchpaketno+"','"+tt.toLocaleString()+"', '"+loginid+"', "
					+ "'0', '"+finaltotal+"','"+dispatchby+"', '"+bank_name+"', "
					+ "'"+branch+"', '"+accoutno+"','"+bank_address+"', '"+ifscno+"', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
		//	System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
	}
	
	
		@GET
		@Path("daywiseallvehicle")
		@Produces(MediaType.APPLICATION_JSON)
		public Response daywiseallvehicle(@QueryParam("day") String day,@QueryParam("loginid")String loginid) {
			List<daywisevehiclesReport> data = new ArrayList<daywisevehiclesReport>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice vehde ");
				
				
				
					String sqlselect4 = "select * from dblocator.selectprocedure('daywiseAllVehiclereport', '" +day
							+ "', '"+loginid+"', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
				//	System.out.println("query"+sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int indexno=1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					
//					mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//				       mstass.deviceid,mstass.assigndate,
//					deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp
//				       FROM dblocator.msttblvehicle as mstvehicle inner  join dblocator.msttblvehicleassigngps as mstass on mstass.vehicleid=mstvehicle.vehicleid
//				       inner join  dblocator.msttbldevice as mstdev on mstdev.deviceid =mstass.deviceid  inner join  devicedata_parsed  as deviceparse 
//				       on deviceparse.imeino= mstdev.uniqueid where deviceparse.datatimestamp::date=$2::date;
//					
					
					while (rs4.next()) {
						daywisevehiclesReport obj=new daywisevehiclesReport();
							obj.setVehicleid(rs4.getString(1));
							obj.setVehicletypeid(rs4.getString(2));
							obj.setVehicleregno(rs4.getString(3));
							obj.setDeviceid(rs4.getString(4));
							obj.setAssigndate(rs4.getString(5));
							obj.setUniqueid(rs4.getString(6));
							obj.setLatitude(rs4.getString(7));
							obj.setLongitude(rs4.getString(8));
							obj.setDatetimestamp(rs4.getString(9));
						//	System.out.println(obj.getVehicleregno()+" "+obj.getLatitude()+" "+obj.getLongitude());
							data.add(obj);
					}
					
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		@GET
		@Path("daterangewiseallvehicle")
		@Produces(MediaType.APPLICATION_JSON)
		public Response daterangewiseallvehicle(@QueryParam("date1") String date1,@QueryParam("date2")String date2,@QueryParam("loginid")String loginid) {
			List<daywisevehiclesReport> data = new ArrayList<daywisevehiclesReport>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice daterangewiseAllVehiclereport ");
				
				 String sqlselect4=" select * from dblocator.selectprocedure('daterangewiseAllVehiclereport', '"+date1+"', '"+date2+"', '"+loginid+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				
//				String sqlselect4 = "select * from dblocator.selectprocedure('daterangewiseAllVehiclereport', '" +date1
//							+ "'"+date2+"'', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
//					
				//	System.out.println("query"+sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					
//					mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//				       mstass.deviceid,mstass.assigndate,
//					deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp
//				       FROM dblocator.msttblvehicle as mstvehicle inner  join dblocator.msttblvehicleassigngps as mstass on mstass.vehicleid=mstvehicle.vehicleid
//				       inner join  dblocator.msttbldevice as mstdev on mstdev.deviceid =mstass.deviceid  inner join  devicedata_parsed  as deviceparse 
//				       on deviceparse.imeino= mstdev.uniqueid where deviceparse.datatimestamp::date=$2::date;
//					
					
					while (rs4.next()) {
						daywisevehiclesReport obj=new daywisevehiclesReport();
							obj.setVehicleid(rs4.getString(1));
							obj.setVehicletypeid(rs4.getString(2));
							obj.setVehicleregno(rs4.getString(3));
							obj.setDeviceid(rs4.getString(4));
							obj.setAssigndate(rs4.getString(5));
							obj.setUniqueid(rs4.getString(6));
							obj.setLatitude(rs4.getString(7));
							obj.setLongitude(rs4.getString(8));
							obj.setDatetimestamp(rs4.getString(9));
							data.add(obj);
							 
					
					}
					
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		@GET
		@Path("daywiseaparticularvehicle")
		@Produces(MediaType.APPLICATION_JSON)
		public Response daywiseaparticularvehicle(@QueryParam("day") String day,@QueryParam("vehicleno")String vehicleno,@QueryParam("loginid")String loginid) {
			List<daywisevehiclesReport> data = new ArrayList<daywisevehiclesReport>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice vehde ");
				
		String sqlselect4=" select * from dblocator.selectprocedure('DailyVehiclereportdaywise', '"+day+"', '"+vehicleno+"', '"+loginid+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				
				//	System.out.println("query"+sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int indexno=1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					
					while (rs4.next()) {
						daywisevehiclesReport obj=new daywisevehiclesReport();
							obj.setVehicleid(rs4.getString(1));
							obj.setVehicletypeid(rs4.getString(2));
							obj.setVehicleregno(rs4.getString(3));
							obj.setDeviceid(rs4.getString(4));
							obj.setAssigndate(rs4.getString(5));
							obj.setUniqueid(rs4.getString(6));
							obj.setLatitude(rs4.getString(7));
							obj.setLongitude(rs4.getString(8));
							obj.setDatetimestamp(rs4.getString(9));
//						//	System.out.println("lat long = "+obj.getLatitude()+ " "+obj.getLongitude());
							data.add(obj);
						}
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		@GET
		@Path("daterangeaparticularvehicle")
		@Produces(MediaType.APPLICATION_JSON)
		public Response daterangeaparticularvehicle(@QueryParam("date1") String date1,@QueryParam("date2") String date2,@QueryParam("vehicleno")String vehicleno,@QueryParam("loginid")String loginid) {
			List<daywisevehiclesReport> data = new ArrayList<daywisevehiclesReport>();
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice daterangeparticu;ar vehicle ");
				
		String sqlselect4=" select * from dblocator.selectprocedure('DailyVehiclereportdatewise', '"+date1+"', '"+date2+"', '"+vehicleno+"', '"+loginid+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				
				//	System.out.println("query"+sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int indexno=1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					
					while (rs4.next()) {
						daywisevehiclesReport obj=new daywisevehiclesReport();
					
							obj.setVehicleid(rs4.getString(1));
							obj.setVehicletypeid(rs4.getString(2));
							obj.setVehicleregno(rs4.getString(3));
							obj.setDeviceid(rs4.getString(4));
							obj.setAssigndate(rs4.getString(5));
							obj.setUniqueid(rs4.getString(6));
							obj.setLatitude(rs4.getString(7));
							obj.setLongitude(rs4.getString(8));
							obj.setDatetimestamp(rs4.getString(9));
//						//	System.out.println("uniqueid"+rs4.getString(6));
//						//	System.out.println("latitude"+rs4.getString(7));
//						//	System.out.println("longitude"+rs4.getString(8));
							data.add(obj);
							 
					
					}
					
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		@GET
		@Path("/companiwisedevice")
		@Produces(MediaType.APPLICATION_JSON)
		public Response companiwisedevice(@QueryParam("controlid") String controlid) {
			List<companywisedevice_details> data = new ArrayList<companywisedevice_details>();
		
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice vehde ");
				
					
					String sqlselect4 = "select * from dblocator.selectprocedure('companywisedevice', '" + controlid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
					System.out.println("dealercount"+sqlselect4);
					int cnt = 0;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					int indexno=1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						companywisedevice_details obj=new companywisedevice_details();
						obj.setCompanyname(rs4.getString(1));
						obj.setDevicecount(rs4.getInt(2));
						cnt = cnt + rs4.getInt(2);
						//System.out.println("count:"+cnt);
						obj.setTotalcount(cnt+1);
						data.add(obj);
						}
				//System.out.println(data.size());
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
			
			@GET
			@Path("/dealerdashvalues")
			@Produces(MediaType.APPLICATION_JSON)
			public Response dealerdashvalues(@QueryParam("loginid") String loginid) {
				dealerdash obj = new dealerdash();
			
				Connection con = null;
				try {
					Class.forName("org.postgresql.Driver");
					con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
				//	System.out.println("Connected in websservice vehde ");
					
						
						/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdealerdashvalues', '" + loginid + "', '', '', '', '', '', '', '', "
								+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
								+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
					
					String sqlselect4="  select (select count(md.deviceid) from dblocator.msttbldevice as md "
							+ " where md.loginid = '" + loginid + "'::numeric) as total, "
							+ " (select count(md.deviceid) from dblocator.msttbldevice as md "
							+ " inner join dblocator.msttblvehicleassigngps as veh on veh.deviceid = md.deviceid "
							+ "	where md.loginid = '" + loginid + "'::numeric) as sold, "
							+ " (select count(md.deviceid) from dblocator.msttbldevice as md "
							+ " where md.loginid = '" + loginid + "'::numeric and md.status_veh=0) as available, "
							+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
							+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
							+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
							+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
							+ " where md.flag=0 and mdm.status='damaged' and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric)) as damaged, "
							+ " (select count(mc.customerid) from dblocator.msttblcustomer as mc "
							+ " inner join dblocator.msttblmappingcustomers_toall as mca on mc.customerid = mca.customerid "
							+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mca.loginid "
							+ " where mca.loginid = '" + loginid + "'::numeric and mc.flag=0 ) as customers, "
							+ " (select count(mc.dealerid) from dblocator.msttbldealer as mc "
							+ " inner join dblocator.msttblmappingdealers_toall as mca on mc.dealerid = mca.dealerid "
							+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mca.loginid "
							+ " where mca.loginid = '" + loginid + "'::numeric and mc.flag=0 ) as dealers, "
							+ " (select count(comp.companyname) from dblocator.msttblcompany as comp "
							+ " inner join dblocator.msttbluserlogin  as ur on ur.loginid=comp.loginid "
							+ " where  ur.loginid='" + loginid + "'::numeric)as company "
							+ " from dblocator.msttbldevice as md "
							+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
							+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
							+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
							+ " where md.flag=0 and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric);  ";
						
						System.out.println("dealercount"+sqlselect4);
						
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

						SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						int indexno=1;
						Statement st4 = con.createStatement();
						ResultSet rs4 = st4.executeQuery(sqlselect4);
						while (rs4.next()) {
							
							obj.setTotaldevice(rs4.getString(1));
							obj.setSolddevice(rs4.getString(2));
							obj.setAvailabledevice(rs4.getString(3));
							obj.setDamageddevice(rs4.getString(4));
							obj.setCustomers(rs4.getString(5));
							obj.setDealers(rs4.getString(6));
						    obj.setCompanycount(rs4.getString(7));
							
							}
						
					
					return Response.status(200).entity(obj).build();
				} catch (Exception e) {
				  System.out.println(e);
					return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
							.build();
				} finally {
					try {
						con.close(); // System.out.println("connection closed");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		
		
		
//		@GET
//		@Path("/dealerdashsimvehicledetails")
//		@Produces(MediaType.APPLICATION_JSON)
//		public Response dealerdashsimvehicledetails(@QueryParam("loginid") String loginid) {
//			List <dealerassign_details>  data = new  ArrayList<dealerassign_details>();
//		
//			Connection con = null;
//			try {
//				Class.forName("org.postgresql.Driver");
//				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			System.out.println("Connected in websservice in dealersimvehi ");
//				
//					
//					/*String sqlselect4 = "select * from dblocator.selectprocedure('selectsimandvehicleassigned', '" + loginid
//							+ "', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
//			      
//			         String sqlselect4=" if '" + loginid + "'::numeric != 10001 and '" + loginid + "'::numeric != 10002 THEN "
//			         		+ " select count(md.uniqueid) as total, "
//			         		+ " (select mdd.dealername from dblocator.msttbldealer as mdd inner join  dblocator.msttbluserlogin as mll on mll.ownersid=mdd.dealerid where mll.loginid='" + loginid + "'::numeric)as dealername, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " where md.flag=0 and md.status_veh=0 and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric)) as vehiclesnotassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " where md.flag=0 and md.status_veh=1 and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric)) as vehicleassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ "  where md.flag=0 and md.status_sim=0  and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric)) as simnotassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " where md.flag=0 and md.status_sim=1  and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric)) as simassigned "
//			         		+ " from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " where md.flag=0 and mda.loginid='" + loginid + "'::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric); "
//			         		+ "  End if; "
//			         		+ " FOR md_g IN SELECT dealerid FROM  dblocator.msttblmappingdealers_toall where loginid='" + loginid + "'::numeric LOOP "
//			         		+ " select loginid into did from dblocator.msttbluserlogin where ownersid = md_g.dealerid; "
//			         		
//			         		+ " select count(md.uniqueid) as total,"
//			         		+ " (select mdd.dealername from dblocator.msttbldealer as mdd inner join  dblocator.msttbluserlogin as mll on mll.ownersid=mdd.dealerid where mll.loginid=did::numeric)as dealername, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " where md.flag=0 and md.status_veh=0 and mda.loginid=did::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = did::numeric)) as vehiclesnotassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " where md.flag=0 and md.status_veh=1 and mda.loginid=did::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = did::numeric)) as vehicleassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " where md.flag=0 and md.status_sim=0  and mda.loginid=did::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = did::numeric)) as simnotassigned, "
//			         		+ " (select count(md.uniqueid) from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbluserlogin as ml on ml.loginid = mda.loginid "
//			         		+ " where md.flag=0 and md.status_sim=1  and mda.loginid=did::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = did::numeric)) as simassigned "
//			         		+ " from dblocator.msttbldevice as md "
//			         		+ " inner join dblocator.msttblmappingdevices_toall as mda on mda.deviceid = md.deviceid "
//			         		+ " inner join dblocator.msttbldealerdevice_assign as mdm on mdm.deviceid = md.deviceid "
//			         		+ " where md.flag=0 and mda.loginid=did::numeric and mdm.dealerid = (select ownersid from dblocator.msttbluserlogin where loginid = did::numeric); "
//			         		+ " END LOOP; ";
//			
//					System.out.println(sqlselect4);
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//					int indexno=1;
//					Statement st4 = con.createStatement();
//					ResultSet rs4 = st4.executeQuery(sqlselect4);
//					while (rs4.next()) {
//						dealerassign_details obj=new dealerassign_details();
//						obj.setTotal(rs4.getString(1));
//						obj.setDealername(rs4.getString(2));
//						obj.setVehiclenotassigned(rs4.getString(3));
//						obj.setVehicleassigned(rs4.getString(4));
//						obj.setSimnotassigned(rs4.getString(5));
//						obj.setSimassigned(rs4.getString(6));
//						data.add(obj);
//						}
//					
//				
//				return Response.status(200).entity(data).build();
//			} catch (Exception e) {
//				System.out.println(e);
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
//			} finally {
//				try {
//					con.close(); // System.out.println("connection closed");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		
		@GET
		@Path("/dealercustomerdetails")
		@Produces(MediaType.APPLICATION_JSON)
		public Response dealercustomerdetails(@QueryParam("loginid") String loginid) {
			List<dealer_details> data = new ArrayList<dealer_details>();
		
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice vehde ");
				
					String sqlselect4 = "select * from dblocator.selectprocedure('selectdealercust', '" + loginid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//	System.out.println(sqlselect4);
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);

					
					int count=0;
					while (rs4.next()) {
							 count=rs4.getInt(1);
						}
					
				
				return Response.status(200).entity(count).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@GET
		@Path("/dealersolddevice")
		@Produces(MediaType.APPLICATION_JSON)
		public Response dealersolddevice() {
			List<TotalDevice> data = new ArrayList<TotalDevice>();
		
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice dealersold ");
				
					String sqlselect4 = "select * from dblocator.selectprocedure('dealersolddevice', '" 
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			//	System.out.println(sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//	System.out.println(sqlselect4);
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						TotalDevice totaldev=new TotalDevice();
						
						totaldev.setId(rs4.getString(1));
						totaldev.setTotal_devices(rs4.getString(2));
						totaldev.setAvailable(rs4.getString(3));
						totaldev.setDamaged(rs4.getString(4));
						totaldev.setSaled(rs4.getString(5));
						totaldev.setDatetimestamp(rs4.getString(6));
						data.add(totaldev);
							
						}
					
				
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		@GET
		@Path("/getSubdealers")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getSubdealers(@QueryParam("loginid") String loginid) {

			List<subdealers> data = new ArrayList<subdealers>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice ");

//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					String sqlselect4 = "select * from dblocator.selectprocedure('mappingdealer', '" + loginid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

					int indexno = 1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
//					if (list.get(k).getLoginid().equals(loginid)) {
//						flag = 0;
//					}

					while (rs4.next()) {
						
						try {
						
							subdealers sb=new subdealers();
							sb.setDealerid(rs4.getString(1));
							data.add(sb);
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		@GET
		@Path("/getSubdealersname")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getSubdealersname(@QueryParam("loginid")String loginid) {

			List<subdealersold> data = new ArrayList<subdealersold>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice subdealername ");
//				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					String sqlselect4 = "select * from dblocator.selectprocedure('subdealername', '" + loginid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
			//	System.out.println(sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

					int indexno = 1;
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						
						try {
							subdealersold sb=new subdealersold();
							sb.setDealername(rs4.getString(1));
							data.add(sb);
					
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		@GET
		@Path("/getsolddeviceNumbers")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getsolddeviceNumbers() {

			List<device_details> data = new ArrayList<device_details>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice subdealername ");
//				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					String sqlselect4 = "select * from dblocator.selectprocedure('solddevicesNumber', '" 
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
			//	System.out.println(sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						
						try {
							device_details sb=new device_details();
							sb.setUniqueid(rs4.getString(1));
							data.add(sb);
					
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		
		@GET
		@Path("/avialdevicesNumber")
		@Produces(MediaType.APPLICATION_JSON)
		public Response avialdevicesNumber() {

			List<device_details> data = new ArrayList<device_details>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice avialdevicesNumber ");
//				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					String sqlselect4 = "select * from dblocator.selectprocedure('avialdevicesNumber', '" 
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
			//	System.out.println(sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						
						try {
							device_details sb=new device_details();
							sb.setUniqueid(rs4.getString(1));
							data.add(sb);
					
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		
		
		@GET
		@Path("/getcustomername")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getselectdealercustomername(@QueryParam("loginid") String loginid) {

			List<customer_details> data = new ArrayList<customer_details>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice selectdealercustomername ");
//				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					String sqlselect4 = "select * from dblocator.selectprocedure('selectdealercustomername', '" +loginid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
					
			//	System.out.println(sqlselect4);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
					Statement st4 = con.createStatement();
					ResultSet rs4 = st4.executeQuery(sqlselect4);
					while (rs4.next()) {
						try {
							customer_details sb=new customer_details();
							sb.setCustomername(rs4.getString(1));
							data.add(sb);
					
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		@GET
		@Path("/imenosearch")
		@Produces(MediaType.APPLICATION_JSON)
		public Response imenosearch(@QueryParam("deviceid") String deviceid
				) {
			Connection con = null;

			try {

				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//System.out.println("Connected in websservice daily details summary");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());

				// devicemapid,dealerid, dealername,
				// deviceid,devicename,salecost, credit_money, payement_mode
				String sqlselect4 = "select * from dblocator.getdailyreport('"+deviceid+"','',"
						+ "'','')";
				System.out.println(sqlselect4);
				PreparedStatement ps = con.prepareStatement(sqlselect4);
				boolean rs = ps.execute();
				if (rs) {
//				//	System.out.println(" rs = "+rs);
					return Response.status(200).entity("ok").build();
				} else {
					return Response.status(404).entity("error").build();
				}

			}

			catch (Exception e) {
			//System.out.println("indeat"+e);
				return Response.status(404).entity("error").build();
			} finally {
				try {
					con.close(); //System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		
		
		@POST
		@Path("/getsoldimeis")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getSoldimeis(@QueryParam("dealerid") String dealerid, subdealersold obj) {

			List<Devicedash_sold> data = new ArrayList<Devicedash_sold>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			int i = 0;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
				
				
				 /*String sqlselect = "select * from dblocator.selectprocedure('selectimeidealer', '" +obj.getDealerid()
					+ "', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
				String sqlselect4=" select d.uniqueid, m.makename, mk.modelname, ms.mobilenumber, d.deviceid "
						+ " from dblocator.msttblmappingdevices_toall as mpd "
						+ " inner join dblocator.msttbldevice as d on d.deviceid = mpd.deviceid "
						+ " inner join dblocator.msttbldealerdevice_assign as mda on d.deviceid = mda.deviceid "
						+ " inner join dblocator.msttblmake m on d.makeid = m.makeid "
						+ " inner join dblocator.msttblmodel mk  on d.modelid = mk.modelid "
						+ " inner join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = d.deviceid "
						+ " inner join dblocator.msttblsim as ms on ms.simid = dsm.simid "
						+ " inner join dblocator.msttbluserlogin c on mpd.loginid=c.loginid "
						+ " where mpd.loginid=(select loginid from dblocator.msttbluserlogin where ownersid='" +obj.getDealerid()+ "'::numeric) and mda.dealerid='" +obj.getDealerid()+ "'::numeric; ";

					//System.out.println(sqlselect);
					Statement st=con.createStatement();
					ResultSet rs=st.executeQuery(sqlselect4);
					int h=0;
					while(rs.next()){
						 Devicedash_sold dd = new Devicedash_sold();
						 dd.setImeino(rs.getString(1));
						 dd.setSimno(rs.getString(4));
						 dd.setDeviceid(rs.getString(5));
						 data.add(dd);
					}
				System.out.println(data.size());
			}catch(Exception e){
				System.out.println(e);
			}
			
			return Response.status(200).entity(data).build();
		}
		
//		@GET
//		@Path("/getSubdealerssolddeatils")
//		@Produces(MediaType.APPLICATION_JSON)
//		public Response getSubdealerssolddeatils(@QueryParam("loginid") String loginid) {
//
//			List<subdealersold> data = new ArrayList<subdealersold>();
////			List<parentcomp_details> list = getLoginId(companyid);
//			Connection con = null;
//			try {
//				Class.forName("org.postgresql.Driver");
//				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			//	System.out.println("Connected in websservice ");
////				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
////				int k = 0, flag = 1;
////				while (k < list.size()) {
////				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
//					/*String sqlselect4 = "select * from dblocator.selectprocedure('selectdealerssaledetails', '" + loginid
//							+ "', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
////				String sqlselect4=" select ownersid into did from dblocator.msttbluserlogin where loginid = '" + loginid + "'::numeric; "
////						+ " FOR g IN select * from dblocator.msttblmappingdealers_toall where loginid = '" + loginid + "'::numeric LOOP "
////						+ " select loginid into did from dblocator.msttbluserlogin where ownersid = g.dealerid; "
////						+ " select  count(d.deviceid) as devino, mdd.dealername, "
////						+ " (select dealername from dblocator.msttbldealer where dealerid=g.dealerid::numeric) as dealer, '4752542', mdd.dealerid "
////						+ " from dblocator.msttblmappingdevices_toall as mpd "
////						+ " inner join dblocator.msttbldevice as d on d.deviceid = mpd.deviceid "
////						+ " inner join dblocator.msttbldealerdevice_assign as mda on d.deviceid = mda.deviceid "
////						+ " inner join dblocator.msttbldealer as mdd on mda.dealerid = mdd.dealerid "
////						+ " inner join dblocator.msttblmake m on d.makeid = m.makeid "
////						+ " inner join dblocator.msttblmodel mk  on d.modelid = mk.modelid "
////						+ " inner join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = d.deviceid "
////						+ " inner join dblocator.msttblsim as ms on ms.simid = dsm.simid "
////						+ " inner join dblocator.msttbluserlogin c on mpd.loginid=c.loginid "
////						+ " where mpd.loginid=did::numeric and mda.dealerid=(select ownersid from dblocator.msttbluserlogin where loginid=did::numeric) "
////						+ " group by mdd.dealername, dealer, mdd.dealerid; "
////						+ " END LOOP; ";
//				        
//					
//				System.out.println("selectdealerssaledetails"+sqlselect4);
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//					int indexno = 1;
//					Statement st4 = con.createStatement();
//					ResultSet rs4 = st4.executeQuery(sqlselect4);
//					String dealer = null,subdealer=null;
//					int count = 0, d = 0;
//					String dealername="p";
//					String subdealername="";
//					String dealerid = null;
//					List<String> ponum = new ArrayList<String>();
//					while (rs4.next()) {
//						
//						try {
//							if(indexno==1){
//								dealer = rs4.getString(3);
//								subdealer = rs4.getString(2);
//							}
//							if(!dealer.equals(rs4.getString(3)) || !subdealer.equals(rs4.getString(2))){
//								subdealersold sb=new subdealersold();
//								sb.setNo_devices(String.valueOf(count));
//								sb.setSubdealername(subdealer);
//								if(d==0){
//									sb.setDealername(dealer);
//								}else{
//									if(!dealername.equals(dealer)){
//										sb.setDealername(dealer);
//									}
//								}
//								dealername = dealer;
//								sb.setPurchaseorder(ponum);
//								sb.setDealerid(dealerid);
//								//System.out.println(ponum.toString());
//								data.add(sb);
//								count = 0;
//								d++;
//								ponum = new ArrayList<String>();
//							}
//								dealer = rs4.getString(3);
//								dealerid = rs4.getString(5);
//								//dealername=rs4.getString(3);
//								subdealer = rs4.getString(2);
//								count = count + Integer.parseInt(rs4.getString(1));
//								ponum.add(rs4.getString(4));
//								
//							indexno++;
//					
//						} catch (Exception e) {
//							System.out.println("e = " + e);
//						}
//						
//					}
//					try{
//						subdealersold sb=new subdealersold();
//						sb.setNo_devices(String.valueOf(count));
//						sb.setSubdealername(subdealer);
////						sb.setDealername(dealer);
//						sb.setPurchaseorder(ponum);
//						sb.setDealerid(dealerid);
//						data.add(sb);
//					}catch(Exception e){
//						System.out.println(e);
//					}
////					k++;
////				}
//				return Response.status(200).entity(data).build();
//			} catch (Exception e) {
//			  System.out.println(e);
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
//			} finally {
//				try {
//					con.close(); // System.out.println("connection closed");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
		

		
		
		@GET
		@Path("/getdealercustdetails")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getdealercustdetails(@QueryParam("loginid") String loginid) {

			List<dealercustomers_details> data = new ArrayList<dealercustomers_details>();
//			List<parentcomp_details> list = getLoginId(companyid);
			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice getdealercustdetails ");
//				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
//				int k = 0, flag = 1;
//				while (k < list.size()) {
//				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
					
				System.out.println("customer graph list");
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectcustomerssaledetails', '" +loginid
							+ "', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
				String sqlselect4=" select ownersid from dblocator.msttbluserlogin where loginid = '" +loginid+ "'::numeric ";
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				String did = null;
				while (rs4.next()) {
					did = rs4.getString(1);
				}
				
				
				sqlselect4  =  "  select  mds.no_devices, md.customername, "
						+ " (select dealername from dblocator.msttbldealer where dealerid="+did+"::numeric) as dealer "
						+ " from dblocator.msttblcustomer as md "
						+ " inner join dblocator.msttbldevicesalecustomer as mds on mds.customerid = md.customerid "
						+ " where mds.loginid='"+loginid+"'::numeric and mds.flag = 0 order by md.customername,dealer; ";
				
				
			//System.out.println("gra[ph customerlist"+sqlselect4);
				String dealrid;
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		            int indexno = 1;
					 st4 = con.createStatement();
					 rs4 = st4.executeQuery(sqlselect4);
					String dealer = null,customer=null;
					String  dealername="h";
					int count = 0;
					while (rs4.next()) {
						try {
							if(indexno==1){
								dealer = rs4.getString(3);
								customer = rs4.getString(2);
							}
							if(!dealer.equals(rs4.getString(3)) || !customer.equals(rs4.getString(2))){
								dealercustomers_details sb=new dealercustomers_details();
								sb.setNo_devices(String.valueOf(count));
								sb.setCustomername(customer);
								sb.setDealername(dealer);
								//System.out.println(dealer+" "+customer);
								data.add(sb);
								count = 0;
							}
							
							dealer = rs4.getString(3);
							customer = rs4.getString(2);
							count = count + Integer.parseInt(rs4.getString(1));
							
							indexno++;
					
						} catch (Exception e) {
						//	System.out.println("e = " + e);
						}
						
					}
					try{
						dealercustomers_details sb=new dealercustomers_details();
						//System.out.println(dealer+" "+customer);
						sb.setNo_devices(String.valueOf(count));
						sb.setCustomername(customer);
						sb.setDealername(dealer);
						data.add(sb);
					}catch(Exception e){
						System.out.println(e);
					}
//					k++;
//				}
				return Response.status(200).entity(data).build();
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


//		@GET
//		@Path("/getdealercustdetailsList")
//		@Produces(MediaType.APPLICATION_JSON)
//		public Response getdealercustdetailsList(@QueryParam("loginid") String loginid) {
//
//			List<dealercustomers_details> data = new ArrayList<dealercustomers_details>();
////			List<parentcomp_details> list = getLoginId(companyid);
//			Connection con = null;
//			try {
//				Class.forName("org.postgresql.Driver");
//				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			//	System.out.println("Connected in websservice getdealercustdetails ");
////				SELECT mapdealer.dealerid, mapdealer.loginid,dealer.dealername,sale.no_devices
////				int k = 0, flag = 1;
////				while (k < list.size()) {
////				//	System.out.println("cid of company = " + list.get(k).getCompanyid());
//					/*String sqlselect4 = "select * from dblocator.selectprocedure('selectcustomerssaledetails', '" +loginid
//							+ "', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//							+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
//				String sqlselect4=" select ownersid from dblocator.msttbluserlogin where loginid = '" +loginid+ "'::numeric; ";
//				Statement st4 = con.createStatement();
//				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				String did;
//				while (rs4.next()) {
//					did = rs4.getString(1);
//				}
//				
//				sqlselect4= " select  mds.no_devices, md.customername, "
//						+ " (select dealername from dblocator.msttbldealer where dealerid="+did+"::numeric) as dealer "
//						+ " from dblocator.msttblcustomer as md "
//						+ " inner join dblocator.msttbldevicesalecustomer as mds on mds.customerid = md.customerid "
//						+ " where mds.loginid='" +loginid+ "'::numeric and mds.flag = 0 order by md.customername,dealer; "
//						+ " FOR g IN select * from dblocator.msttblmappingdealers_toall where loginid = '" +loginid+ "'::numeric LOOP "
//						+ " select loginid into did from dblocator.msttbluserlogin where ownersid = g.dealerid; "
//						+ " select  mds.no_devices, md.customername, "
//						+ " (select dealername from dblocator.msttbldealer where dealerid=g.dealerid::numeric) as dealer "
//						+ " from dblocator.msttblcustomer as md "
//						+ " inner join dblocator.msttbldevicesalecustomer as mds on mds.customerid = md.customerid "
//						+ " where mds.loginid="+did+"::numeric and mds.flag = 0 order by md.customername,dealer; "
//						+ " END LOOP; ";
//					
//				//System.out.println("custmerdetails"+sqlselect4);
//				String dealrid;
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		            int indexno = 1;
//					Statement st4 = con.createStatement();
//					ResultSet rs4 = st4.executeQuery(sqlselect4);
//					String dealer = null,customer=null;
//					String  dealername="h";
//					int count = 0, d = 0;
//					while (rs4.next()) {
//						try {
//							if(indexno==1){
//								dealer = rs4.getString(3);
//								customer = rs4.getString(2);
//							}
//							if(!dealer.equals(rs4.getString(3)) ||!customer.equals(rs4.getString(2))){
//								dealercustomers_details sb=new dealercustomers_details();
//								sb.setNo_devices(String.valueOf(count));
//								sb.setCustomername(customer);
//								if(d == 0){
//									sb.setDealername(dealer);
//								}
//								if(d==0){
//									sb.setDealername(dealer);
//								}else{
//									if(!dealername.equals(dealer)){
//										sb.setDealername(dealer);
//									}
//								}
//								dealername = dealer;
//								data.add(sb);
//								count = 0;
//								d++;
//							}
//							
//							dealer = rs4.getString(3);
//							customer = rs4.getString(2);
//							count = count + Integer.parseInt(rs4.getString(1));
//							indexno++;
//					
//						} catch (Exception e) {
//						//	System.out.println("e = " + e);
//						}
//						
//					}
//					try{
//						dealercustomers_details sb=new dealercustomers_details();
//						sb.setNo_devices(String.valueOf(count));
//						sb.setCustomername(customer);
//						sb.setDealername(dealer);
//						data.add(sb);
//					}catch(Exception e){
//						System.out.println(e);
//					}
////					k++;
////				}
//				return Response.status(200).entity(data).build();
//			} catch (Exception e) {
//			  System.out.println(e);
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
//			} finally {
//				try {
//					con.close(); // System.out.println("connection closed");
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}


		
		
	
	@GET
	@Path("/devicesaleupdatecustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response devicesaleUpdateCustomer(@QueryParam("transid") String transid,
			@QueryParam("customerid") String customerid, @QueryParam("no_devices") String no_devices,
			@QueryParam("purchaseorder_number") String purchaseorder_number, @QueryParam("purchaseorder_date") String purchaseorder_date,
			@QueryParam("invoice_number") String invoice_number, @QueryParam("invoice_date") String invoice_date,
			@QueryParam("total") String total, @QueryParam("tax") String tax,
			@QueryParam("octroi") String octroi, @QueryParam("vatt") String vatt,
			@QueryParam("servicetax") String servicetax, @QueryParam("paymentmode") String paymentmode,
			@QueryParam("chequeno") String chequeno, @QueryParam("chequedate") String chequedate,
			@QueryParam("creditdays") String creditdays, @QueryParam("creditamount") String creditamount,
			@QueryParam("payedby") String payedby,@QueryParam("order_placedby") String order_placedby,
			@QueryParam("emailidby") String emailidby,@QueryParam("phonenoby") String phonenoby,
			@QueryParam("nameby") String nameby,@QueryParam("dispatchnameby") String dispatchnameby,
			@QueryParam("dispatchphoneby") String dispatchphoneby,@QueryParam("dispatchcourier") String dispatchcourier,
			@QueryParam("dispatchpaketno") String dispatchpaketno,@QueryParam("finaltotal") String finaltotal,
			@QueryParam("loginid") String loginid,@QueryParam("dispatchby") String dispatchby,
			@QueryParam("bank_name")String bank_name,@QueryParam("branch")String branch,@QueryParam("accountno")String accountno,@QueryParam("ifscno")String ifscno,@QueryParam("bank_address")String bank_address
			) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice customersaleupdate");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
//			transid, customerid, no_devices, purchaseorder_number, purchaseorder_date, 
//		       invoice_number, invoice_date, total, tax, octroi, vatt, servicetax, 
//		       paymentmode, chequeno, chequedate, creditdays, creditamount, 
//		       payedby, order_placedby, emailidby, phonenoby, nameby, dispatchnameby, 
//		       dispatchphoneby, dispatchcourier, dispatchpaketno, datetimestamp, 
//		       loginid, flag, finaltotal, dispatchby
		       
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertDevicesaleCustomer'," + " '"+transid+"', '" + customerid + "', "
					+ "'" + no_devices + "','" + purchaseorder_number + "', '" + purchaseorder_date + "', '" + invoice_number + "', " + "'"
					+ invoice_date + "', '" + total + "', '" + tax + "',  " + "'" + octroi + "', '"
					+ vatt + "', '" + servicetax + "', '" + paymentmode + "'," + "'" + chequeno + "', '"
					+ chequedate + "', '" + creditdays+ "', '" + creditamount + "', " + "'"+payedby+"',"
					+ "'"+order_placedby+"', '"+emailidby+"', '"+phonenoby+"', '"+nameby+"','"+dispatchnameby+"', '"+dispatchphoneby+"',"
					+ "'"+dispatchcourier+"', '"+dispatchpaketno+"','"+tt.toLocaleString()+"', '"+loginid+"', "
					+ "'0', '"+finaltotal+"','"+dispatchby+"', '"+bank_name+"', "
					+ "'"+branch+"', '"+bank_address+"','"+accountno+"', '"+ifscno+"', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
		//	System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block 
				e.printStackTrace();
			}
		}
	}


	
	@GET
	@Path("/dealersaledelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response dealersaledelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteDevicesale");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteDevicesale'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/deleteDevicesalecustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customersaledelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteDevicesale");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteDevicesalecustomer'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	


	@GET
	@Path("/vehicledetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleDetails(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("searchbyvehicleno")String searchbyvehicleno) {
		List<vehicle_details> data = new ArrayList<vehicle_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledetails 1");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			if(searchbyvehicleno==null)
			{
			searchbyvehicleno="";
			}
			
				/*String sqlselect4 = "select * from dblocator.selectvehicle('selectvehicle', '" + loginid
						+ "', '"+searchbyvehicleno+"', '', '', '', '', '', '','');";*/
			String sqlselect4 =" SELECT  mv.vehicleid,  mv.makeid, mv.modelid, mv.vehicletypeid,  mv.vehicleregno, mv.regdate, "
					+ " mv.chasisnumber, mv.enginenumber, mv.bodycolor, mv.fueltypeid, mv.regvaliddate, "
					+ " mv.insurancevaliddate, mv.pucdate, mv.tankcapacity, mv.loginid, mv.datetimestamp, "
					+ " mv.remarks, mv.flag, mv.insurancecomp_name, "
					+ " mk.makename,mm.modelname,mvt.vehicletypename,mv.status,md.customername, "
					+ " case when ad.district IS NULL then 'Not Assigned' else ad.district end, "
					+ " case when mv.isapproved is null then 'false' else mv.isapproved end, "
					+ " case when ms.simnumber::text is null then 'NA' else ms.simnumber::text end, "
					+ " case when mdd.deviceid::text is null then 'NA' else mdd.deviceid::text end, "
					+ " case when mdd.uniqueid is null then 'NA' else mdd.uniqueid end, mv.receiptno, "
					+ " mv.fitnessvalidity,mv.insurancevalidity, "
					+ " ( select mc1.dealername from dblocator.msttbldevice as md1 "
					+ " inner join dblocator.msttblmappingdevices_toall as mdm1 on md1.deviceid = mdm1.deviceid "
					+ " inner join dblocator.msttbluserlogin c1 on mdm1.loginid = c1.loginid "
					+ " inner join dblocator.msttbldealer as mc1 on mc1.dealerid = c1.ownersid "
					+ " where md1.deviceid = mdd.deviceid and md1.flag=0 order by mdm1.datetimestamp desc limit 1) as dealer, "
					+ " case when ms.mobilenumber::text is null then 'NA' else ms.mobilenumber::text end, "
					+ " case when ms.mobilenumber2::text is null then 'NA' else ms.mobilenumber2::text end,mc2.loginid "
					+ " FROM dblocator.msttblvehicle as mv "
					+ " inner join dblocator.msttblmappingvehicles_toall as mdm on mdm.vehicleid = mv.vehicleid "
					+ " inner join  dblocator.msttblmake as mk on mk.makeid=mv.makeid "
					+ " inner join dblocator.msttblmodel as mm on mm.modelid=mv.modelid "
					+ " inner join dblocator.msttbluserlogin as mcp on mcp.loginid = mdm.loginid "
					+ " inner join dblocator.msttbluserlogin as mcp1 on mcp1.loginid = mv.loginid "
					+ " inner join dblocator.msttblcustomer as md on md.customerid=mcp1.ownersid "
					+ " inner join dblocator.msttblvehicletype as mvt on mvt.vehicletypeid = mv.vehicletypeid "
					+ " left join dblocator.msttblambulance_details as ad on ad.ambbyno = mv.vehicleregno "
					+ " left join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid "
					+ " left join dblocator.msttbldevice as mdd on mdd.deviceid = veh.deviceid "
					+ " left join dblocator.msttbldevicesimmap as dsm on dsm.deviceid = mdd.deviceid "
					+ " left join dblocator.msttblsim as ms on ms.simid = dsm.simid"
					+ " inner join dblocator.msttbluserlogin as mc2 on mc2.ownersid = mcp1.companyid "
					+ " where mdm.loginid='"+loginid+"'::numeric "
					+ " and  mv.flag=0 and case when('"+searchbyvehicleno+"'!='') then mv.vehicleregno like '%'||'"+searchbyvehicleno+"'||'%'  else mv.flag=0  end "
					+ " order by    mv.vehicleregno  asc; ";
				
				System.out.println("veh"+sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				while (rs4.next()) {
					vehicle_details obj = new vehicle_details();
					obj.setVehicleid(Long.parseLong(rs4.getString(0+1)));
					obj.setMakeid(Long.parseLong(rs4.getString(1+1)));
					obj.setModelid(Long.parseLong(rs4.getString(2+1)));
					obj.setVehicletypeid(Long.parseLong(rs4.getString(3+1)));
					obj.setVehicleregno(rs4.getString(4+1));

					// obj.setRegdate(sdf.parse(rs4.getString(5+1)));
					obj.setRegdate(rs4.getString(5+1));
					obj.setChasisnumber(rs4.getString(6+1));
					obj.setEnginenumber(rs4.getString(7+1));
					obj.setBodycolor(rs4.getString(8+1));
					obj.setFueltypeid(Long.parseLong(rs4.getString(9+1)));
					// obj.setRegvaliddate(sdf.parse(rs4.getString(10+1)));
					obj.setRegvaliddate(rs4.getString(10+1));
					obj.setInsurancevaliddate(rs4.getString(11+1));
					obj.setPucdate(rs4.getString(12+1));
					obj.setTankcapacity(Integer.parseInt(rs4.getString(13+1)));
					obj.setLoginid(Long.parseLong(rs4.getString(14+1)));

					java.util.Date parseTimestamp = sdf2.parse(rs4.getString(15+1));

					// obj.setDatetimestamp(new
					// Timestamp(parseTimestamp.getTime()));
					obj.setDatetimestamp((sdf.format(parseTimestamp)));
					obj.setRemarks(rs4.getString(16+1));
					obj.setFlag(rs4.getString(17+1));
					obj.setInsurancecompany(rs4.getString(18+1));
					
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					obj.setMakename(rs4.getString(19+1));
					obj.setModelname(rs4.getString(20+1));

					obj.setVehicletypename(rs4.getString(21+1));
					// obj.setStatus(rs4.getString(21+1));
					String status = rs4.getString(22+1);
					if (status.equals("1")) {
						obj.setStatus("Assigned");
					} else {
						obj.setStatus("Not Assigned");
					}
					obj.setCustomername(rs4.getString(24));
					try{
						obj.setDistrict(rs4.getString(25));
					}catch(Exception e){
						
					}
					obj.setIsapproved(rs4.getString(26));
					obj.setSimno(rs4.getString(27));
					obj.setDeviceid(rs4.getString(28));
					obj.setImeino(rs4.getString(29));
					obj.setReceiptno(rs4.getString(30));
					obj.setFitnessvalidity(rs4.getString(31));
					obj.setInsurancevalidity(rs4.getString(32));
					obj.setDealername(rs4.getString(33));
					obj.setMobilenumber(rs4.getString(34));
					obj.setMobilenumber2(rs4.getString(35));
					obj.setCompanyid(Long.parseLong(rs4.getString(36)));
//			        if(obj.getCompanyid().equals("10011"))
//			        	{
//			        	System.out.println(obj.getVehicleregno());
//			        	}
					data.add(obj);
					//System.out.println("lenght"+data.size());
					

				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	

	@GET
	@Path("/searchByVehicleno")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchByVehicleno(@QueryParam("loginid") String loginid, @QueryParam("companyid") String companyid,@QueryParam("vehicleno")String vehicleno) {
		List<vehicle_details> data = new ArrayList<vehicle_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledetails 1");
//			int k = 0, flag = 1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selectsearchByvehicleno', '" + loginid
					+ "', '"+vehicleno+"', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println(sqlselect4);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				int indexno=1;
				while (rs4.next()) {
					vehicle_details obj = new vehicle_details();
					obj.setVehicleid(Long.parseLong(rs4.getString(0+1)));
					obj.setMakeid(Long.parseLong(rs4.getString(1+1)));
					obj.setModelid(Long.parseLong(rs4.getString(2+1)));
					obj.setVehicletypeid(Long.parseLong(rs4.getString(3+1)));
					obj.setVehicleregno(rs4.getString(4+1));

					// obj.setRegdate(sdf.parse(rs4.getString(5+1)));
					obj.setRegdate(rs4.getString(5+1));
					obj.setChasisnumber(rs4.getString(6+1));
					obj.setEnginenumber(rs4.getString(7+1));
					obj.setBodycolor(rs4.getString(8+1));
					obj.setFueltypeid(Long.parseLong(rs4.getString(9+1)));
					// obj.setRegvaliddate(sdf.parse(rs4.getString(10+1)));
					obj.setRegvaliddate(rs4.getString(10+1));
					obj.setInsurancevaliddate(rs4.getString(11+1));
					obj.setPucdate(rs4.getString(12+1));
					obj.setTankcapacity(Integer.parseInt(rs4.getString(13+1)));
					obj.setLoginid(Long.parseLong(rs4.getString(14+1)));

					java.util.Date parseTimestamp = sdf2.parse(rs4.getString(15+1));

					// obj.setDatetimestamp(new
					// Timestamp(parseTimestamp.getTime()));
					obj.setDatetimestamp(parseTimestamp.toLocaleString());
					obj.setRemarks(rs4.getString(16+1));
					obj.setFlag(rs4.getString(17+1));
					obj.setInsurancecompany(rs4.getString(18+1));
					
					obj.setRowno(indexno);
					indexno++;
//					if (flag == 1) {
//						obj.setFlag("false");
//					} else {
						obj.setFlag("true");
//					}
					obj.setMakename(rs4.getString(19+1));
					obj.setModelname(rs4.getString(20+1));

					obj.setVehicletypename(rs4.getString(21+1));
					// obj.setStatus(rs4.getString(21+1));
					String status = rs4.getString(22+1);
					if (status.equals("1")) {
						obj.setStatus("Assigned");
					} else {
						obj.setStatus("Not Assigned");
					}
					obj.setCustomername(rs4.getString(24));
					data.add(obj);

				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
			System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	

	@GET
	@Path("/vehicleinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleInsert(@QueryParam("makeid") String makeid, @QueryParam("modelid") String modelid,
			@QueryParam("vehicletypeid") String vehicletypeid, @QueryParam("vehicleregno") String vehicleregno,
			@QueryParam("chasisnumber") String chasisnumber, @QueryParam("enginenumber") String enginenumber,
			@QueryParam("bodycolor") String bodycolor, @QueryParam("fueltypeid") String fueltypeid,
			@QueryParam("regdate") String regdate, @QueryParam("regvaliddate") String regvaliddate,
			@QueryParam("insurancevaliddate") String insurancevaliddate, @QueryParam("pucdate") String pucdate,
			@QueryParam("tankcapacity") String tankcapacity, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks,@QueryParam("inusrancecompanyname") String inusrancecompanyname,
			@QueryParam("customerid") String customerid,@QueryParam("fitnessvalidity") String fitnessvalidity,
			@QueryParam("insurancevalidity") String insurancevalidity,@QueryParam("circle") String circle,
            @QueryParam("district") String district){

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice vehicleinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// vehicleid, makeid, modelid, vehicletypeid, vehicleregno, regdate,
			// chasisnumber, enginenumber, bodycolor, fueltypeid, regvaliddate,
			// permitdate, permitvaliddate, insurancedate, insurancevaliddate,
			// pucdate, pucvaliddate, tankcapacity, loginid, datetimestamp,
			// remarks, flag
			
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertVehicle'," + " '0', '" + makeid + "', "
					+ "'" + modelid + "', '" + vehicletypeid + "', '" + ((vehicleregno.toUpperCase()).replace(" ", "")).replace("-", "") + "', '" + regdate + "', " + "'"
					+ chasisnumber + "', '" + enginenumber + "', '" + bodycolor + "',  " + "'" + fueltypeid + "', '"
					+ regvaliddate + "', '" + insurancevaliddate + "', '" + pucdate + "'," + "'" + tankcapacity + "', '"
					+ loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', " + "'0','"+inusrancecompanyname+"', '"+customerid+"', '"+fitnessvalidity+"', '"+insurancevalidity+"','"+circle+"', '"+district+"',"
					+ " '', '','', '', '', '','', '', "
					+ "'', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
           
			
			
			System.out.println("vehinsert "+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
			return Response.status(200).entity("ok").build();
			} else {
			return Response.status(404).entity("Not ok").build();
			}
			
			//return Response.status(200).entity("ok").build();
		} catch (Exception e) {
		  System.out.println("vehicleinsert"+e);
		  System.out.println("catch"+e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("/vehicleupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleUpdate(@QueryParam("vehicleid") String vehicleid, @QueryParam("makeid") String makeid,
			@QueryParam("modelid") String modelid, @QueryParam("vehicletypeid") String vehicletypeid,
			@QueryParam("vehicleregno") String vehicleregno, @QueryParam("chasisnumber") String chasisnumber,
			@QueryParam("enginenumber") String enginenumber, @QueryParam("bodycolor") String bodycolor,
			@QueryParam("fueltypeid") String fueltypeid, @QueryParam("regdate") String regdate,
			@QueryParam("regvaliddate") String regvaliddate,
			@QueryParam("insurancevaliddate") String insurancevaliddate, @QueryParam("pucdate") String pucdate,
			@QueryParam("tankcapacity") String tankcapacity, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks,@QueryParam("inusrancecompanyname")String inusrancecompanyname,
			@QueryParam("fitnessvalidity") String fitnessvalidity, @QueryParam("insurancevalidity") String insurancevalidity
			) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice vehicleupdate");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// vehicleid, makeid, modelid, vehicletypeid, vehicleregno, regdate,
			// chasisnumber, enginenumber, bodycolor, fueltypeid, regvaliddate,
			// permitdate, permitvaliddate, insurancedate, insurancevaliddate,
			// pucdate, pucvaliddate, tankcapacity, loginid, datetimestamp,
			// remarks, flag
			String sqlselect4 = "select * from dblocator.insertprocedure('insertVehicle'," + " '" + vehicleid + "', '" 
					+ makeid + "', " + "'" + modelid + "', '" + vehicletypeid + "', '" + ((vehicleregno.toUpperCase()).replace("-", "")).replace(" ", "") + "' , '" + regvaliddate + "', " + "'" + chasisnumber + "', '" + enginenumber + "', '" + bodycolor + "',  " + "'"
					+ fueltypeid + "', '" + regvaliddate + "', '" + insurancevaliddate + "', '" + pucdate + "'," + "'"
					+ tankcapacity + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', "
					+ "'0','"+inusrancecompanyname+"', '"+fitnessvalidity+"', '"+insurancevalidity+"', '','', ''," + " '', '','', '', '', '','', '', "
					+ "'', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
			System.out.println("update"+sqlselect4);
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@GET
	@Path("/makeinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeInsert(@QueryParam("makename") String makename, @QueryParam("assetid") String assetid,
			 @QueryParam("loginid") String loginid,
		 @QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			
		//	System.out.println("Connected in websservice makeinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			//makeid, makename, assetid, loginid, datetimestamp, remarks, flag
			
			
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertMake'," + " '0', '" + makename + "',  '"
					+ assetid + "',  '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', '0',  "
					+ "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
			
			
			
//			String sqlselect4 = "select * from dblocator.insertprocedure('insertMake'," + " '0', '" + makename + "', "
//					+ "'" + assetid + "', '" + loginid + "', '" + tt.toLocaleString() + "', " + "'"
//					+ remarks + "', '0', '',  " + "'', '', '', '', '', "
//					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
		//	System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			return Response.status(200).entity("ok").build();
		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		}

		finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/makeupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response makeUpdate(@QueryParam("makeid") String makeid, @QueryParam("makename") String makename,
			@QueryParam("assetid") String assetid,
			@QueryParam("loginid") String loginid, @QueryParam("datetimestamp") String datetimestamp,
			@QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// makeid, makename, assetid, vendorid, loginid, datetimestamp,
			// remarks, flag,companyid,maketype
			String sqlselect4 = "select * from dblocator.insertprocedure('insertMake'," + " '" + makeid + "', '" + makename
					+ "', " + "'" + assetid + "', '" + loginid + "', '" + tt.toLocaleString()
					+ "', " + "'" + remarks + "', '" + 0 + "', '" + loginid + "',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/simassigninsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simAssignInsert(@QueryParam("deviceid") String deviceid, @QueryParam("simid") String simid,
			@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks) {
		Connection con = null;
		// devicesimid, deviceid, simid, loginid, datetimestamp, remarks, flag
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice simins assign");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// System.out.println("values = "+deviceid+" "+simid+" "+loginid+"
			// "+status+" "+assigndate+" "+remarks);
			// devicesimid, deviceid, simid, loginid, datetimestamp, remarks,
			// flag
			
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertsimassign', '0', '" + deviceid + "', "
					+ "'" + simid + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', "
					+ "'0', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println("quer assugn"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			
			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/simassignupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simAssignUpdate(@QueryParam("devicesimid") String devicesimid,
			@QueryParam("deviceid") String deviceid, @QueryParam("simid") String simid,
			@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks) {
		Connection con = null;
		// devicesimid, deviceid, simid, loginid, datetimestamp, remarks, flag
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// System.out.println("values = "+deviceid+" "+simid+" "+loginid+"
			// "+status+" "+assigndate+" "+remarks);
			// devicesimid, deviceid, simid, loginid, datetimestamp, remarks,
			// flag
			String sqlselect4 = "select * from dblocator.insertprocedure('insertsimassign', '" + devicesimid + "', '"
					+ deviceid + "', " + "'" + simid + "', '" + loginid + "', '" + tt.toLocaleString() + "', '"
					+ remarks + "', " + "'0', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		//	System.out.println(sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/customerdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerDetails(@QueryParam("companyid") String companyid, @QueryParam("loginid") String loginid,
			@QueryParam("searchbycustomername")String searchbycustomername, @QueryParam("dealerid")String dealerid) {
		List<customer_details> data = new ArrayList<customer_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//		//	System.out.println("Connected in websservice customerdetails ");
//			int k = 0, flag = 1; 
			int indexno=1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			//	System.out.println("loginid "+loginid);
			if(searchbycustomername==null)
			{
				searchbycustomername="";
			}
			if(dealerid==null){
				dealerid="";
			}if(loginid==null){
				loginid="";
			}
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectCustomer', '"
						+ loginid + "', '"+searchbycustomername+"', '"+dealerid+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
                  String sqlselect4=" SELECT mco.customerid, mco.customername, mco.customertype, mco.contactperson, mco.emailid, "
                  		+ "mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, "
                  		+ " mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, "
                  		+ " mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag, md.dealername "
                  		+ " FROM  dblocator.msttblcustomer as mco "
                  		+ " inner join dblocator.msttblmappingcustomers_toall as mdm on mdm.customerid = mco.customerid "
                  		+ " left join dblocator.msttbluserlogin as mcp on mcp.loginid = mco.loginid "
                  		+ " left join dblocator.msttbldealer as md on md.dealerid = mcp.ownersid "
                  		+ " where mdm.loginid='"+loginid+"'::numeric and mco.flag=0 and mco.flag=0 and "
                  		+ " case when('"+searchbycustomername+"'!='')  then mco.customername like '%'||'"+searchbycustomername+"'||'%'  else mco.flag=0  end "
                  		+ " order by  mco.customername asc; ";

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				System.out.println("query = "+sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
//			mco.customerid, mco.customername, mco.customertype, mco.contactperson, mco.emailid, 
//			mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, 
//			mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, 
//			mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag
					try {
						customer_details obj = new customer_details();
						obj.setCustomerid(rs4.getString(1));
						obj.setCustomername(rs4.getString(2));
						obj.setCustomertype(rs4.getString(3));
						obj.setContactperson(rs4.getString(4));
						obj.setEmailid(rs4.getString(5));
						obj.setMobilenumber(rs4.getString(6));
						obj.setAlternatecontnumber(rs4.getString(7));
						obj.setAddress(rs4.getString(8));
						obj.setCity(rs4.getString(9));
						obj.setPincode(rs4.getString(10));
						obj.setIscredit(rs4.getString(11));
						obj.setCreditdays(rs4.getString(12));
						obj.setCreditamount(rs4.getString(13));
						obj.setPaymentmode(rs4.getString(14));
						obj.setBank_name(rs4.getString(15));
						obj.setBranch(rs4.getString(16));
						obj.setAccountno(rs4.getString(17));
						obj.setBankaddress(rs4.getString(18));
						obj.setIfscno(rs4.getString(19));
						obj.setLoginid(rs4.getString(20));
						obj.setDatetimestamp(rs4.getString(21));
						obj.setRemarks(rs4.getString(22));
						obj.setFlag(rs4.getString(23));
						obj.setDealername(rs4.getString(24));
						obj.setRowno(indexno);
						indexno++;
						// obj.setFlag(rs4.getString(14+1));
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
//				k++;
//			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close();
			//	System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	@GET
	@Path("/searchcustomerByName")
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchcustomerByName(@QueryParam("companyid") String companyid, @QueryParam("loginid") String loginid,@QueryParam("customername")String customername) {
		List<customer_details> data = new ArrayList<customer_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice customerdetails byname ");
//			int k = 0, flag = 1; 
			int indexno=1;
//			while (k < list.size()) {
				// String sqlselect4="select vehicle_creation()";
			//	System.out.println("loginid "+loginid);
				String sqlselect4 = "select * from dblocator.selectprocedure('selectCustomerByName', '"
						+ loginid + "', '"+customername+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				
				System.out.println("cust"+sqlselect4);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				while (rs4.next()) {
					
//			mco.customerid, mco.customername, mco.customertype, mco.contactperson, mco.emailid, 
//			mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, 
//			mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, 
//			mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag
					try {
						customer_details obj = new customer_details();
						obj.setCustomerid(rs4.getString(1));
						obj.setCustomername(rs4.getString(2));
						obj.setCustomertype(rs4.getString(3));
						obj.setContactperson(rs4.getString(4));
						obj.setEmailid(rs4.getString(5));
						obj.setMobilenumber(rs4.getString(6));
						obj.setAlternatecontnumber(rs4.getString(7));
						obj.setAddress(rs4.getString(8));
						obj.setCity(rs4.getString(9));
						obj.setPincode(rs4.getString(10));
						obj.setIscredit(rs4.getString(11));
						obj.setCreditdays(rs4.getString(12));
						obj.setCreditamount(rs4.getString(13));
						obj.setPaymentmode(rs4.getString(14));
						obj.setBank_name(rs4.getString(15));
						obj.setBranch(rs4.getString(16));
						obj.setAccountno(rs4.getString(17));
						obj.setBankaddress(rs4.getString(18));
						obj.setIfscno(rs4.getString(19));
						obj.setLoginid(rs4.getString(20));
						obj.setDatetimestamp(rs4.getString(21));
						obj.setRemarks(rs4.getString(22));
						obj.setFlag(rs4.getString(23));
						obj.setDealername(rs4.getString(24));
						obj.setRowno(indexno);
						indexno++;
						// obj.setFlag(rs4.getString(14+1));
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
//				k++;
//			}

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close();
				System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	
	
	
	@GET
	@Path("/customerinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerInsert(@QueryParam("customername") String customername,
			@QueryParam("customertype") String customertype, @QueryParam("contactperson") String contactperson,
			@QueryParam("emailid") String emailid, @QueryParam("mobilenumber") String mobilenumber,
			@QueryParam("alternatecontnumber") String alternatecontnumber,@QueryParam("address") String address,
			@QueryParam("city") String city, @QueryParam("pincode") String pincode,
			@QueryParam("iscredit") String iscredit, @QueryParam("creditdays") String creditdays,
			@QueryParam("creditamount") String creditamount,@QueryParam("paymentmode") String paymentmode,
			@QueryParam("bank_name") String bank_name,@QueryParam("branch") String branch,
			@QueryParam("accountno") String accountno,@QueryParam("bankaddress") String bankaddress,
			@QueryParam("ifscno") String ifscno,@QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks,@QueryParam("dealerid")String dealerid) {
		Connection con = null;

		try {

//			mco.customerid, mco.customername, mco.customertype, mco.contactperson,
//			mco.emailid, mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, 
//			mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, 
//			mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag
			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//		//	System.out.println("Connected in websservice customerinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
			
				String sqlselect4 = "select * from dblocator.insertprocedure('insertCustomer', '" 
						+ "0', '"+customername.toUpperCase()+"', '"+customertype+"', '"+contactperson+"', '"+emailid+"',"
						+ " '"+mobilenumber+"', '"+alternatecontnumber+"', '"+address+"', "
						+ "'"+city+"', '"+pincode+"', '"+iscredit+"', '"+creditdays+"', '"+creditamount+"', "
						+ "'"+paymentmode+"', '"+bank_name+"', '"+branch+"', '"+accountno+"', "
						+ "'"+bankaddress+"', '"+ifscno+"', '"+loginid+"', '"+tt.toLocaleString()+"',"
						+ " '"+remarks+"', '0', '"+dealerid+"', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
	//		//	System.out.println()
				System.out.println(sqlselect4);
				PreparedStatement ps = con.prepareStatement(sqlselect4);
				boolean rs = ps.execute();
				
			return Response.status(200).entity("ok").build();
		}

		catch (Exception e) {
			System.out.println(e);
			 livedet det = new livedet();
				det.setExc(String.valueOf(e));
			 // System.out.println(e);
				return Response.status(404).entity(det)
						.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close();
			//	System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	@GET
	@Path("/customerupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerUpdate(@QueryParam("customerid") String customerid,@QueryParam("customername") String customername,
			@QueryParam("customertype") String customertype, @QueryParam("contactperson") String contactperson,
			@QueryParam("emailid") String emailid, @QueryParam("mobilenumber") String mobilenumber,
			@QueryParam("alternatecontnumber") String alternatecontnumber,@QueryParam("address") String address,
			@QueryParam("city") String city, @QueryParam("pincode") String pincode,
			@QueryParam("iscredit") String iscredit, @QueryParam("creditdays") String creditdays,
			@QueryParam("creditamount") String creditamount,@QueryParam("paymentmode") String paymentmode,
			@QueryParam("bank_name") String bank_name,@QueryParam("branch") String branch,
			@QueryParam("accountno") String accountno,@QueryParam("bankaddress") String bankaddress,
			@QueryParam("ifscno") String ifscno,@QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks) {
		Connection con = null;

		try {

//			mco.customerid, mco.customername, mco.customertype, mco.contactperson,
//			mco.emailid, mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, 
//			mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, 
//			mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag
			
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice update cust");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// devicesimid,deviceid,simid,loginid,status,assigndate,datetimestamp,remarks,flag
			String sqlselect4 = "select * from dblocator.insertprocedure('insertCustomer', '" 
					+ customerid +"', '"+customername+"', '"+customertype+"', '"+contactperson+"', '"+emailid+"',"
					+ " '"+mobilenumber+"', '"+alternatecontnumber+"', '"+address+"', "
					+ "'"+city+"', '"+pincode+"', '"+iscredit+"', '"+creditdays+"', '"+creditamount+"', "
					+ "'"+paymentmode+"', '"+bank_name+"', '"+branch+"', '"+accountno+"', "
					+ "'"+bankaddress+"', '"+ifscno+"', '"+loginid+"', '"+tt.toLocaleString()+"',"
					+ " '"+remarks+"', '0', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
			
			System.out.println("in custome "+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close();
			//	System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/customerdelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerdelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteCustomer");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteCustomer'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {

			try {
				con.close();
			//	System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	 
	@GET
	 @Path("/dealerdetails")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerDetails(@QueryParam("companyid") String companyid,@QueryParam("loginid") String loginid,@QueryParam("searchbydealername")String searchbydealername) {
	 List<dealer_details>data=new ArrayList<dealer_details>();
//	 List<parentcomp_details> list = getLoginId(companyid);

	 Connection con = null;
	 try{
	 Class.forName("org.postgresql.Driver");
	  con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	 System.out.println("Connected in websservice vehde ");
//	 int k=0, flag=1;
//	  while(k<list.size()){
//	 String sqlselect4="select vehicle_creation()";
	 if(searchbydealername==null)
	 {
		 searchbydealername=""; 
	 }
	 /*String sqlselect4="select * from dblocator.selectprocedure('selectDealer', '"+loginid+"', '"+searchbydealername+"', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
	 String sqlselect4="  SELECT  mco.dealerid, mco.dealername, mco.dealertype, mco.mobilenumber,mco.alternatecontnumber,mco.loginid,mco.datetimestamp, "
	 		+ " mco.remarks,mco.flag,mco.address,mco.city,mco.pincode,mco.bank_name,mco.branch,mco.accountno,mco.bankaddress,mco.ifscno, "
	 		+ " mco.creditdays, mco.creditamount, mco.contactperson, mco.iscredit, mco.paymentmode, mco.emailid, "
	 		+ " case when md.dealername IS NULL then md2.companyname else md.dealername end,mco.state "
	 		+ " FROM dblocator.msttbldealer as mco "
	 		+ " inner join dblocator.msttblmappingdealers_toall as mdm on mdm.dealerid = mco.dealerid "
	 		+ " inner join dblocator.msttbluserlogin as mcp on mcp.loginid = mdm.loginid "
	 		+ " inner join dblocator.msttbluserlogin as mcp1 on mcp1.loginid=mco.loginid "
	 		+ " left join dblocator.msttbldealer as md on mcp1.ownersid=md.dealerid "
	 		+ " left join dblocator.msttblcompany as md2 on mcp1.ownersid=md2.companyid "
	 		+ " where mdm.loginid='"+loginid+"'::numeric and mco.flag=0 and  case when('"+searchbydealername+"'!='')  then  mco.dealername like '%'||'"+searchbydealername+"'||'%' else mco.flag=0  end "
	 		+ " order by mco.dealername asc ; ";
	 
	 System.out.println(sqlselect4);
	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 int indexno=1;
	Statement st4=con.createStatement();
	 ResultSet rs4=st4.executeQuery(sqlselect4);
//	 if(list.get(k).getLoginid().equals(loginid)){
//			flag=0;
//		}
	 while(rs4.next()){
		
		 try{
			 
			 
//			 mco.dealerid, mco.dealername, mco.dealertype, mco.mobilenumber,mco.alternatecontnumber,mco.loginid,mco.datetimestamp,
//			 mco.remarks,mco.flag,mco.address,mco.city,mco.pincode,mco.bank_name,mco.branch,mco.accountno,mco.bankaddress,mco.ifscno,
//			 mco.creditdays, mco.creditamount, mco.contactperson, mco.iscredit, mco.paymentmode, mco.emailid
			 
			dealer_details obj = new dealer_details();
			obj.setDealerid(rs4.getString(0+1));
			obj.setDealername(rs4.getString(1+1));
			obj.setDealertype(rs4.getString(2+1));
			obj.setMobilenumber(rs4.getString(3+1));
			obj.setAlternatecontnumber(rs4.getString(4+1));
			obj.setLoginid(rs4.getString(5+1));
			obj.setDatetimestamp(rs4.getString(6+1));
			obj.setRemarks(rs4.getString(7+1));
			obj.setFlag(rs4.getString(8+1));
			obj.setAddress(rs4.getString(10+1));
			obj.setCity(rs4.getString(9+1));
			obj.setPincode(rs4.getString(11+1));
			obj.setBank_name(rs4.getString(12+1));
			obj.setBranch(rs4.getString(13+1));
			obj.setAccountno(rs4.getString(14+1));
			obj.setBankaddress(rs4.getString(15+1));
			obj.setIfscno(rs4.getString(16+1));
			obj.setCreditdays(rs4.getString(17+1));
			obj.setCreditamount(rs4.getString(18+1));
			obj.setContactperson(rs4.getString(19+1));
			obj.setIscredit(rs4.getString(20+1));
			obj.setPaymentmode(rs4.getString(21+1));
			obj.setEmailid(rs4.getString(22+1));
			obj.setParentdealer(rs4.getString(23+1));
			obj.setState(rs4.getString(24+1));
			
			obj.setRowno(indexno);
			indexno++;
//			  if(flag==1){
//				 obj.setFlag("false");
//			 } else{
				 obj.setFlag("true");
//			 }
//			 if(rs4.getString(23+1).equals("0")){
//				 obj.setFlag("false");
//			 }
			 data.add(obj);
		 }catch(Exception e){
			 System.out.println("e "+e);
		 }
	 }
//	 k++;
//	 }
	
	 return Response.status(200).entity(data).build();
	 }
	 catch (Exception e)
	 {
	 System.out.println(e);
	 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	}

	
	@GET
	 @Path("/searchdealerbyname")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response searchdealerbyname(@QueryParam("companyid") String companyid,@QueryParam("loginid") String loginid,@QueryParam("dealername")String dealername) {
	 List<dealer_details>data=new ArrayList<dealer_details>();
//	 List<parentcomp_details> list = getLoginId(companyid);

	 Connection con = null;
	 try{
	 Class.forName("org.postgresql.Driver");
	  con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	 System.out.println("Connected in websservice vehde ");
//	 int k=0, flag=1;
//	  while(k<list.size()){
//	 String sqlselect4="select vehicle_creation()";
	 String sqlselect4="select * from dblocator.selectprocedure('selectDealerByName', '"+loginid+"', '"+dealername+"', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
	 System.out.println(sqlselect4);
	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 int indexno=1;
	Statement st4=con.createStatement();
	 ResultSet rs4=st4.executeQuery(sqlselect4);
//	 if(list.get(k).getLoginid().equals(loginid)){
//			flag=0;
//		}
	 while(rs4.next()){
		
		 try{
			 
			 
//			 mco.dealerid, mco.dealername, mco.dealertype, mco.mobilenumber,mco.alternatecontnumber,mco.loginid,mco.datetimestamp,
//			 mco.remarks,mco.flag,mco.address,mco.city,mco.pincode,mco.bank_name,mco.branch,mco.accountno,mco.bankaddress,mco.ifscno,
//			 mco.creditdays, mco.creditamount, mco.contactperson, mco.iscredit, mco.paymentmode, mco.emailid
			 
			dealer_details obj = new dealer_details();
			obj.setDealerid(rs4.getString(0+1));
			obj.setDealername(rs4.getString(1+1));
			obj.setDealertype(rs4.getString(2+1));
			obj.setMobilenumber(rs4.getString(3+1));
			obj.setAlternatecontnumber(rs4.getString(4+1));
			obj.setLoginid(rs4.getString(5+1));
			obj.setDatetimestamp(rs4.getString(6+1));
			obj.setRemarks(rs4.getString(7+1));
			obj.setFlag(rs4.getString(8+1));
			obj.setAddress(rs4.getString(10+1));
			obj.setCity(rs4.getString(9+1));
			obj.setPincode(rs4.getString(11+1));
			obj.setBank_name(rs4.getString(12+1));
			obj.setBranch(rs4.getString(13+1));
			obj.setAccountno(rs4.getString(14+1));
			obj.setBankaddress(rs4.getString(15+1));
			obj.setIfscno(rs4.getString(16+1));
			obj.setCreditdays(rs4.getString(17+1));
			obj.setCreditamount(rs4.getString(18+1));
			obj.setContactperson(rs4.getString(19+1));
			obj.setIscredit(rs4.getString(20+1));
			obj.setPaymentmode(rs4.getString(21+1));
			obj.setEmailid(rs4.getString(22+1));
			obj.setParentdealer(rs4.getString(23+1));
			obj.setState(rs4.getString(24+1));
			
			obj.setRowno(indexno);
			indexno++;
//			  if(flag==1){
//				 obj.setFlag("false");
//			 } else{
				 obj.setFlag("true");
//			 }
//			 if(rs4.getString(23+1).equals("0")){
//				 obj.setFlag("false");
//			 }
			 data.add(obj);
		 }catch(Exception e){
			 System.out.println("e "+e);
		 }
	 }
//	 k++;
//	 }
	
	 return Response.status(200).entity(data).build();
	 }
	 catch (Exception e)
	 {
	 System.out.println(e);
	 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	}

	 
	 
	 
	 public String vehicletype(String vehicletype){
		 
		    String vehicon;
		      if(vehicletype.contains("Bus")){
		    	  vehicon="resources/images/SchoolBus.png";
		      }
		      else if(vehicletype.contains("Car"))
		      {
		    	 vehicon="resources/images/Car.png";
		      }
		      else if(vehicletype.contains("Truck"))
		      {
		    	  vehicon="resources/images/truck.gif";  
		      }
		      else if(vehicletype.contains("Ambulance"))
		      {
		    	  vehicon="resources/images/ambulance.png";  
		      }
		     else 
		     {
		    	  vehicon="resources/images/notass1.png";
		     }
		      return(vehicon);
		  } 
	 
	 
	 public String vehiclestatus(String stateofvehicle){
		 
		    String status;
		      if(stateofvehicle.equals("Halt")){
		    	  status="resources/images/hand.png";
		      }
		      else if(stateofvehicle.equals("Stop"))
		      {
		    	  status="resources/imagenew/stop.png";
		      }
		     else if(stateofvehicle.equals("Running"))
		     {
		    	 status="resources/images/run.png";
		     }
		     else
		     {
		    	 status="not found";
		     }
		      return(status);
		  } 
	 

	public String directionname(int head){
	    //alert("head"+head);
		 String dir;
		  if(head>=345 && head<15){
		      dir="North";
		  }
		  else if(head>=15 && head<45){
		      dir="North-West";
		  }
		  else if(head>=45 && head<75){
		      dir="North-West";
		  }
		  else if(head>=75 && head<150){
		      dir="West";
		  }
		  else if(head>=105 && head<135){
		       dir="South-West";
		  }
		  else if(head>=135 && head<165){
		      dir="South-West";
		  }
		  else if(head>=165 && head<195){
		      dir="South";
		  }
		  else if(head>=195 && head<225){
		      dir="South-East";
		  }
		  else if(head>=225 && head<255){
		      dir="South-East";
		  }
		  else if(head>=255 && head<285){
		      dir="East";
		  }
		  else if(head>=285 && head<315){
		      dir="North-East";
		  }
		  else if(head>=315 && head<345){
		      dir="North-East";
		  }
		  else {
		      dir="North";
		  }
		  return(dir);
	  }

	
	
	
	
	
	
	
	
	
	

	public String iginition(int ignumber){
		String igiIcon=null;
		 if(ignumber==1){
			 igiIcon="resources/imagenew/IGINIOn.png";
	      }
		 else{
			 igiIcon="resources/imagenew/IGINOff.png";
	      }
		return(igiIcon);
	}
	
	public String GPS(String gpsvalue){
		String gpsstatus=null;
		 if(gpsvalue.equals("0")){
			 gpsstatus="resources/images/GPSOFF.png";
	      }
		 else{
			 gpsstatus="resources/images/GPSON.png";
	      }
		return(gpsstatus);
	}

	 
	 


	 @GET
	 @Path("/dealerinsert")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerInsert(@QueryParam("dealername") String dealername,@QueryParam("dealertype") String dealertype,
			 @QueryParam("address")String address,@QueryParam("city")String city,
			 @QueryParam("pincode")String pincode,@QueryParam("mobilenumber")String mobilenumber,
			 @QueryParam("alternatecontnumber")String alternatecontnumber,
			 @QueryParam("bankname")String bankname,@QueryParam("branch") String branch,@QueryParam("accountno")String accountno,
			 @QueryParam("bankaddress")String bankaddress , @QueryParam("ifsc")String ifsc, @QueryParam("loginid")String loginid,
			 @QueryParam("remarks")String remarks, @QueryParam("creditdays")String creditdays,@QueryParam("creditamount")String creditamount,
			 @QueryParam("iscredit")String iscredit,@QueryParam("paymentmode")String paymentmode,
			 @QueryParam("contactperson")String contactperson,@QueryParam("emailid")String emailid,@QueryParam("state")String state) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	 System.out.println("Connected in websservice insertDealer");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
	 //dealerid, dealername, dealertype,mobilenumber,alternatecontnumber,loginid,datetimestamp,
		//remarks,flag,address,city,pincode,bank_name,branch,accountno,bankaddress,ifscno
	 
//    ,contactperson,iscredit, paymentmode, emailid
    
   
    String sqlselect4 =null;
    	sqlselect4 = "select * from dblocator.insertprocedure('insertDealer', '0'"
				+ ", '"+dealername+"', '"+dealertype+"', '"+mobilenumber+"', '"+alternatecontnumber+"',"
				+ " '"+loginid+"', '"+tt.toLocaleString()+"', '"+remarks+"', "
				+ "'0', '"+address+"', '"+city+"', "
				+ "'"+pincode+"', '"+bankname+"', '"+branch+"', '"+accountno+"',"
				+ " '"+bankaddress+"', '"+ifsc+"', '"+creditdays+"', '"+creditamount+"', "
				+ "'"+contactperson+"', '"+iscredit+"', '"+paymentmode+"', '"+emailid+"', '"+state+"', '', '', '', '', "
				+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
//    }
    System.out.println(sqlselect4);
    PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
//		 return Response.status(200).entity("ok").build();
	 }else{
//		 return Response.status(404).entity("Not ok").build();
	 }
	 
	return Response.status(200).entity("ok").build();
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		// return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 @GET
	 @Path("/dealerupdate")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerUpdate(@QueryParam("dealerid") String dealerid,
			 @QueryParam("dealername") String dealername,@QueryParam("dealertype") String dealertype,
			 @QueryParam("address")String address,@QueryParam("city")String city,
			 @QueryParam("pincode")String pincode,@QueryParam("mobilenumber")String mobilenumber,
			 @QueryParam("alternatecontnumber")String alternatecontnumber,
			 @QueryParam("bankname")String bankname,@QueryParam("branch") String branch,@QueryParam("accountno")String accountno,
			 @QueryParam("bankaddress")String bankaddress , @QueryParam("ifsc")String ifsc, @QueryParam("loginid")String loginid,
			 @QueryParam("remarks")String remarks,@QueryParam("creditdays")String creditdays,@QueryParam("creditamount")String creditamount,
			 @QueryParam("iscredit")String iscredit,@QueryParam("paymentmode")String paymentmode,
			 @QueryParam("contactperson")String contactperson,@QueryParam("emailid")String emailid,@QueryParam("state")String state) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	 System.out.println("Connected in websservice upate dealer");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());

    String sqlselect4 =null;
    //dealerid, dealername, dealertype,mobilenumber,alternatecontnumber,loginid,datetimestamp,
	//remarks,flag,address,city,pincode,bank_name,branch,accountno,bankaddress,ifscno
    
    
    
	sqlselect4 = "select * from dblocator.insertprocedure('insertDealer', '"+dealerid+"'"
			+ ", '"+dealername+"', '"+dealertype+"', '"+mobilenumber+"', '"+alternatecontnumber+"',"
			+ " '"+loginid+"', '"+tt.toLocaleString()+"', '"+remarks+"', "
			+ "'0', '"+address+"', '"+city+"', "
			+ "'"+pincode+"', '"+bankname+"', '"+branch+"', '"+accountno+"',"
			+ " '"+bankaddress+"', '"+ifsc+"', '"+creditdays+"', '"+creditamount+"', "
			+ "'"+contactperson+"', '"+iscredit+"', '"+paymentmode+"', '"+emailid+"', '"+state+"', '', '', '', '', "
			+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
	System.out.println("update dealer"+sqlselect4);
//    }
    PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		// return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 
	 
	 @GET
	 @Path("/dealerdelete")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerdelete(@QueryParam("id") String id) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	 System.out.println("Connected in websservice dealerdelete");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
//	 networkid, networkname, networkapn, loginid, datetimestamp, remarks,companyid
    
//   System.out.println("payment mode = "+payment_mode);
    String sqlselect4 = "select * from dblocator.insertprocedure('deleteDealer',"
 	  		+ " '"+id+"', '', "+
   			" '',  '', "+
   			"'', '', '',  "+
   			"'', '', '', '', '', '',"+
   			"'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 @GET
		@Path("/checkuser")
		@Produces(MediaType.APPLICATION_JSON)
		public Response checkuser(@QueryParam("param") String param,@QueryParam("username") String username) {
			List<forget_passDetails> data = new ArrayList<forget_passDetails>();
			generateOTP otpgen=new generateOTP();
			String otp=null;
			Connection con = null;
			try {
			//	System.out.println("in chkser");
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice check user");

				// String sqlselect4="select vehicle_creation()";
//				String sqlselect4 = "select * from db_nrda_dborissa.dblocator.selectprocedure('selectForgotpassword', "
//						+ "'"+param+"', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

				String sqlselect4 = "select * from dblocator.selectprocedure('selectForgotpassword', '"
						+ param + "', '"+username+"', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				
				System.out.println("query"+sqlselect4);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			//	System.out.println("query"+sqlselect4);
				forget_passDetails obj = null;
				String ownerid = null;
				String mailid=null;
				String mobileno = null;
				while (rs4.next()) {
					
//					mco.customerid, mco.customername, mco.customertype, mco.contactperson, mco.emailid, 
//					mco.mobilenumber, mco.alternatecontnumber, mco.address, mco.city, mco.pincode, mco.iscredit, 
//					mco.creditdays, mco.creditamount, mco.paymentmode, mco.bank_name, mco.branch, mco.accountno, 
//					mco.bankaddress, mco.ifscno, mco.loginid, mco.datetimestamp, mco.remarks, mco.flag
					try {
						obj = new forget_passDetails();
						System.out.println("mobile"+rs4.getString(6));
						obj.setOwnersid(rs4.getString(1));
						obj.setOwnersname(rs4.getString(2));
						obj.setOwnerstype(rs4.getString(3));
						obj.setContactperson(rs4.getString(4));
						obj.setEmailid(rs4.getString(5));
						obj.setMobilenumber(rs4.getString(6));
						obj.setAlternatecontnumber(rs4.getString(7));
						obj.setAddress(rs4.getString(8));
						obj.setCity(rs4.getString(9));
						obj.setPincode(rs4.getString(10));
						obj.setIscredit(rs4.getString(11));
						obj.setCreditdays(rs4.getString(12));
						obj.setCreditamount(rs4.getString(13));
						obj.setPaymentmode(rs4.getString(14));
						obj.setBank_name(rs4.getString(15));
						obj.setBranch(rs4.getString(16));
						obj.setAccountno(rs4.getString(17));
						obj.setBankaddress(rs4.getString(18));
						obj.setIfscno(rs4.getString(19));
						obj.setLoginid(rs4.getString(20));
						obj.setDatetimestamp(rs4.getString(21));
						obj.setRemarks(rs4.getString(22));
						obj.setFlag(rs4.getString(23));
						obj.setRowno(indexno);
						indexno++;
						data.add(obj);
					} catch (Exception e) {
					System.out.println("e " + e);
					}
				}
				
		
	if(!(data.isEmpty()))
	{
		String mobile=obj.getMobilenumber();	
		String emailid=obj.getEmailid();
			
//		System.out.println("mobile"+mobile);
//		System.out.println("emailid"+emailid);
//	System.out.println("in if");
	try
	{
if(param.equals(mobile))
{
	//System.out.println("in mobileno");
	otp=otpgen.generateotp();
	SearchBloxREST.sendSMS(param, otp);
//System.out.println("otp"+otp);
	ownerid=obj.getOwnersid();
	//System.out.println("owernerid"+ownerid);
	OTPInsert(ownerid,otp,"ok");
}
else if(param.equals(emailid))
{
	try{
	//System.out.println("in email");
		EmailSend email=new EmailSend();
		email.sendHtmlEmail(param,ownerid);
	}catch(Exception ex){System.out.println("iternet Exception"+ex);}
	
}
	}
	catch(Exception er){System.out.println("in er"+er);}
		
	return Response.status(200).entity(data).build();
	}
	else
	{
		//System.out.println("in elseif");
		return Response.status(404).entity("").build();
	}
			} catch (Exception e) {
				System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		
		@GET
		@Path("/checkotp")
		@Produces(MediaType.APPLICATION_JSON)
		public Response checkotp(@QueryParam("otp") String otp,@QueryParam("ownersid") String ownersid) {
			Connection con = null;
			int k=0;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice checkotp ");

				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "select * from dblocator.selectprocedure('selectotp', "
						+ "'"+ownersid+"', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

			//	System.out.println("query"+sqlselect4);
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				int indexno=1;
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);

				while (rs4.next()) {
					

					try {
						//System.out.println(rs4.getString(2));
						if(otp.equals(rs4.getString(2))){
							k++;
						}
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
				
				if(k!=0){
					return Response.status(200).entity("true").build();
				}else{
					return Response.status(404).entity("false").build();
				}

				
			} catch (Exception e) {
			  System.out.println(e);
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

		@GET
		 @Path("/passwordinsert")
		 @Produces(MediaType.APPLICATION_JSON)
		 public Response PasswordInsert(@QueryParam("ownersid") String ownersid,
				 @QueryParam("password") String password) {
			 Connection con = null;
		
		 try{
			 
		 Class.forName("org.postgresql.Driver");
		  con =
		 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
						"rsrtc@2017");
		 System.out.println("Connected in websservice password insert");
		 java.util.Date dd=new Date();
	   Timestamp tt=new Timestamp(dd.getTime());
	   
	//   ownersid, otp, datetimestamp, remarks
	 
	//   String sqlselect4 = "select * from dblocator.insertprocedure('insertPassword',"
//		  		+ " '"+password+"', '"+ownersid+"', "+
//	  			" '', '', "+
//	  			"'', '', '',"+
//	  			"'', '', '', '', '', '',"+
//	  			 "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		 
	   String sqlselect4 = "select * from dblocator.insertprocedure('insertPassword', '"+password+"', '" + ownersid + "', " + "'"
				+  "', '', '', '', "
				+ "'', '', '',  " + "'', '', '', '',"
				+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	   
	   
	   PreparedStatement	ps = con.prepareStatement(sqlselect4);
		 boolean rs = ps.execute();
		 if(rs){
			 return Response.status(200).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
		 }else{
			 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
		 }
		 
		
		 }
		 
		 catch (Exception e)
		 {
			 System.out.println(e);
			 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
		 }
		 finally{
			 try {
				con.close(); System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 }
//		
//		
			
		@POST
		 @Path("/exceptioninsert")
		 @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
		 public Response ExceptionInsert(@QueryParam("formname") String formname,
				livedet inputJsonObj) {
			 Connection con = null;
		
		 try{
			 
		 Class.forName("org.postgresql.Driver");
		  con =
		 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
						"rsrtc@2017");
		 System.out.println("Connected in websservice inseretexception insert");
		 java.util.Date dd=new Date();
	 Timestamp tt=new Timestamp(dd.getTime());
	 
	// ownersid, otp, datetimestamp, remarks

	 String sqlselect4="select * from dblocator.insertprocedure('inseretexception', '"+formname+"', '"+tt.toLocaleString()+"', '"+inputJsonObj.getExc()+"', '', '', '', '', '', '',  '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '')";
		 
	 System.out.println("qq"+sqlselect4);
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
		 boolean rs = ps.execute();
		 if(rs){
			 return Response.status(200).entity("ok").build();
		 }else{
			 return Response.status(404).entity("Not ok").build();
		 }
		 
	 
		 }
		 
		 catch (Exception e)
		 {
			 System.out.println(e);
			 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
		 }
		 finally{
			 try {
				con.close(); System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 }
		 
		
		
		
		
		
		
		
	 
	 @GET
	 @Path("/otpinsert")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response OTPInsert(@QueryParam("ownersid") String ownersid,
			 @QueryParam("otp") String otp, @QueryParam("remarks")String remarks) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassign insert");
	 java.util.Date dd=new Date();
 Timestamp tt=new Timestamp(dd.getTime());
 
// ownersid, otp, datetimestamp, remarks

 String sqlselect4 = "select * from dblocator.insertprocedure('insertOtp',"
	  		+ " '"+ownersid+"', '"+otp+"', "+
			" '"+tt.toLocaleString()+"', '"+remarks+"', "+
			"'', '', '',"+
			"'', '', '', '', '', '',"+
			 "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 
	 @GET
	 @Path("/dealerassigninsert")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerAssignInsert(@QueryParam("dealerid") String dealerid,
			 @QueryParam("salecost")String salecost,@QueryParam("credit_money")String credit_money,
			 @QueryParam("payment_mode")String payment_mode,
			 @QueryParam("deviceid")String deviceid,
			 @QueryParam("loginid")String loginid) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassign insert");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
//devicemapid,
	//dealerid, salecost, credit_money, payment_mode, datetimestamp, deviceid, loginid, flag
   
    String sqlselect4 = "select * from dblocator.insertprocedure('insertdealerAssign',"
 	  		+ " '0', '"+dealerid+"', "+
   			" '"+salecost+"', '"+credit_money+"', "+
   			"'"+payment_mode+"', '"+tt.toLocaleString()+"', '"+deviceid+"',  "+
   			"'"+loginid+"', '0', '', '', '', '',"+
   			 "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
//		 return Response.status(200).entity("ok").build();
	 }else{
//		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
   	return Response.status(200).entity("ok").build();
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 @GET
	 @Path("/dealerassignupdate")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerAssignUpdate(@QueryParam("devicemapid") String devicemapid,
			 @QueryParam("dealerid") String dealerid,
			 @QueryParam("salecost")String salecost,@QueryParam("credit_money")String credit_money,
			 @QueryParam("payment_mode")String payment_mode,
			@QueryParam("deviceid")String deviceid,
			 @QueryParam("loginid")String loginid) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassignupdate");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
///devicemapid,
 	//dealerid, salecost, credit_money, payment_mode, datetimestamp, deviceid, loginid, flag
    
    
   System.out.println("payment mode = "+payment_mode);
    String sqlselect4 = "select * from dblocator.insertprocedure('insertdealerAssign',"
 	  		+ " '"+devicemapid+"', '"+dealerid+"', "+
   			" '"+salecost+"', '"+credit_money+"', "+
   			"'"+payment_mode+"', '"+tt.toLocaleString()+"', '"+deviceid+"',  "+
   			"'"+loginid+"', '0', '', '', '', '',"+
   			 "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 @POST
	 @Path("/getimei")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerAssignUpdate(imeilist inputJsonObj, @QueryParam("dealerid") String dealerid,
			 @QueryParam("ponum") String ponum,@QueryParam("loginid") String loginid) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassignupdate");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
///devicemapid,
 	//dealerid, salecost, credit_money, payment_mode, datetimestamp, deviceid, loginid, flag
    
    
//   System.out.println("payment mode = "+payment_mode);
    String arr = "{";
    String[] val = inputJsonObj.getImeino();
    for(int i = 0 ;i<=inputJsonObj.getImeino().length-1; i++){
    	if(i == (inputJsonObj.getImeino().length)-1){
    		arr = arr + ""+val[i]+"";
    	}else{
    		arr = arr + ""+val[i]+",";
    	}
    }
    arr = arr + "}";
    System.out.println("length"+arr+"\n"+inputJsonObj.getImeino().length);
    String sqlselect4 = "select * from dblocator.saveimei('"+arr+"', '"+dealerid+"', '"+ponum+"', '"+tt.toLocaleString()+"', '"+loginid+"')";
//	System.out.println("sqlselect = "+sqlselect4);
    PreparedStatement	ps = con.prepareStatement(sqlselect4);
  
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 
	
	 
//	 @POST
//	 @Path("/getimei")
//	 @Produces(MediaType.APPLICATION_JSON)
////	 @Consumes(MediaType.APPLICATION_JSON)
//	 public Response getIMEINO(imeilist inputJsonObj) {
//		 Connection con = null;
//	
//	 try{
//		 
////	 Class.forName("org.postgresql.Driver");
////	  con =
////	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
////					"rsrtc@2017");
//	 
//	 java.util.Date dd=new Date();
//    Timestamp tt=new Timestamp(dd.getTime());
//    
/////devicemapid,
// 	//dealerid, salecost, credit_money, payment_mode, datetimestamp, deviceid, loginid, flag
//    
////    String input = (String) inputJsonObj.get("input");
////    String output = "The input you sent is :" + input;
////    JSONObject outputJsonObj = new JSONObject();
////    outputJsonObj.put("output", output);
//    System.out.println("Connected in websservice dealerassignupdate"+inputJsonObj.getImeino().length);
////   System.out.println("payment mode = "+payment_mode);
////    String sqlselect4 = "select * from dblocator.insertprocedure('insertdealerAssign',"
//// 	  		+ " '"+devicemapid+"', '"+dealerid+"', "+
////   			" '"+salecost+"', '"+credit_money+"', "+
////   			"'"+payment_mode+"', '"+tt.toLocaleString()+"', '"+deviceid+"',  "+
////   			"'"+loginid+"', '0', '', '', '', '',"+
////   			 "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
////	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
////	 boolean rs = ps.execute();
////	 if(rs){
////		 return Response.status(200).entity("ok").build();
////	 }else{
////		 return Response.status(404).entity("Not ok").build();
////	 }
//	 
//    return Response.status(200).entity("ok").build();
//	 }
//	 
//	 catch (Exception e)
//	 {
//		 System.out.println(e);
//		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
//	 }
//	 finally{
//		 try {
//			con.close(); System.out.println("connection closed");
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	 }
//	 }
	 
	 @POST
	 @Path("/getimeicustomer")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response customerAssignUpdate(imeilist inputJsonObj, @QueryParam("customerid") String customerid,
			 @QueryParam("ponum") String ponum,@QueryParam("loginid") String loginid) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice customerassignupdate");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
///devicemapid,
 	//customerid, salecost, credit_money, payment_mode, datetimestamp, deviceid, loginid, flag
    
    
//   System.out.println("payment mode = "+payment_mode);
    String arr = "{";
    String[] val = inputJsonObj.getImeino();
    for(int i = 0 ;i<=inputJsonObj.getImeino().length-1; i++){
    	if(i == (inputJsonObj.getImeino().length)-1){
    		arr = arr + ""+val[i]+"";
    	}else{
    		arr = arr + ""+val[i]+",";
    	}
    }
    arr = arr + "}";
    System.out.println("length"+arr+"\n"+inputJsonObj.getImeino().length);
    String sqlselect4 = "select * from dblocator.salecustomer('"+arr+"', '"+customerid+"', '"+ponum+"', '"+tt.toLocaleString()+"', '"+loginid+"')";
//	System.out.println("sqlselect = "+sqlselect4);
    PreparedStatement	ps = con.prepareStatement(sqlselect4);
  
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	 
	 
	 
	 @GET
	 @Path("/dealerassigndetails")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerAssignDetails(@QueryParam("companyid") String companyid,@QueryParam("loginid") String loginid) {
	 List<dealerdevice_assigndetails>data=new ArrayList<dealerdevice_assigndetails>();
//	 List<parentcomp_details> list = getLoginId(companyid);
	 Connection con = null;
	 try{
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassign ");
//	 int k=0, flag=1; 
	 int indexno=1;
//	  while(k<list.size()){
//	 String sqlselect4="select vehicle_creation()";
	 String sqlselect4="select * from dblocator.selectprocedure('selectdealerdeviceassign', '"+loginid+"', '', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "+
			  "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";

	 SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	 Statement st4=con.createStatement();
	 ResultSet rs4=st4.executeQuery(sqlselect4);
//	 if(list.get(k).getLoginid().equals(loginid)){
//			flag=0;
//		}
	 while(rs4.next()){
		
//		 mca.dealerid,mca.salecost, mca.credit_money, 
//		   mca.payment_mode,mca.devicemapid,mca.datetimestamp,md.deviceid,md.uniqueid,mco.dealername,mca.loginid,mca.flag
		 try{
			 dealerdevice_assigndetails obj = new dealerdevice_assigndetails();
			 obj.setDealerid(rs4.getString(0+1));
			 obj.setSalecost(rs4.getString(1+1));
			 obj.setCredit_money(rs4.getString(2+1));
			 obj.setPayment_mode(rs4.getString(3+1));
			 obj.setDevicemapid(rs4.getString(4+1));
			 obj.setDatetimestamp(rs4.getString(5+1));
			 obj.setDeviceid(rs4.getString(6+1));
			 obj.setDeviceno(rs4.getString(7+1));
			 obj.setDealername(rs4.getString(8+1));
			 obj.setLoginid(rs4.getString(9+1));
			 obj.setFlag(rs4.getString(10+1));
			 obj.setRowno(indexno);
				indexno++;
//			 if(flag==1){
//				 obj.setFlag("false");
//			 } else{
				 obj.setFlag("true");
//			 }
			 data.add(obj);
		 }catch(Exception e){
			 System.out.println("e "+e);
		 }
	 }
//	 k++;
//	 }
	
	 return Response.status(200).entity(data).build();
	 }
	 catch (Exception e)
	 {
	 System.out.println(e);
	 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	}
	 

	 
	 @GET
	 @Path("/dealerassigndelete")
	 @Produces(MediaType.APPLICATION_JSON)
	 public Response dealerassigndelete(@QueryParam("id") String id) {
		 Connection con = null;
	
	 try{
		 
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	 System.out.println("Connected in websservice dealerassigndelete");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
    
//	 networkid, networkname, networkapn, loginid, datetimestamp, remarks,companyid
    
//   System.out.println("payment mode = "+payment_mode);
    String sqlselect4 = "select * from dblocator.insertprocedure('deleteDealerAssign',"
 	  		+ " '"+id+"', '', "+
   			" '',  '', "+
   			"'', '', '',  "+
   			"'', '', '', '', '', '',"+
   			"'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	 PreparedStatement	ps = con.prepareStatement(sqlselect4);
	 boolean rs = ps.execute();
	 if(rs){
		 return Response.status(200).entity("ok").build();
	 }else{
		 return Response.status(404).entity("Not ok").build();
	 }
	 
	
	 }
	 
	 catch (Exception e)
	 {
		 System.out.println(e);
		 return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); //System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 } 



	 @GET
		@Path("/customerassigninsert")
		@Produces(MediaType.APPLICATION_JSON)
		public Response customerAssignInsert(@QueryParam("customerid") String customerid,
				@QueryParam("deviceid") String deviceid, @QueryParam("salecost") String salecost,
				@QueryParam("credit_money") String credit_money, @QueryParam("payment_mode") String payment_mode,
				@QueryParam("datetimestamp") String datetimestamp, @QueryParam("companyid") String companyid,
				@QueryParam("dealerid") String dealerid) {
			Connection con = null;

			try {

				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			//	System.out.println("Connected in websservice simins");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());

				// devicemapid,dealerid, dealername,
				// deviceid,devicename,salecost, credit_money, payement_mode

			//	System.out.println("payment mode = " + payment_mode);
				String sqlselect4 = "select * from dblocator.insertprocedure('insertcustomerAssign'," + " '0', '" + customerid
						+ "', " + " '" + deviceid + "',  '" + salecost + "', " + "'" + credit_money + "', '" + payment_mode
						+ "', '" + datetimestamp + "',  " + "'" + companyid + "', '" + dealerid + "', '', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
				PreparedStatement ps = con.prepareStatement(sqlselect4);
				boolean rs = ps.execute();
				if (rs) {
					return Response.status(200).entity("ok").build();
				} else {
					return Response.status(404).entity("Not ok").build();
				}

			}
			catch (Exception e) {
			  System.out.println(e);
			  livedet det = new livedet();
				det.setExc(String.valueOf(e));
			  //System.out.println(e);
				return Response.status(404).entity(det)
						.build();
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	@GET
	@Path("/customerassignupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerAssignUpdate(@QueryParam("devicemapid") String devicemapid,
			@QueryParam("customerid") String customerid, @QueryParam("deviceid") String deviceid,
			@QueryParam("salecost") String salecost, @QueryParam("credit_money") String credit_money,
			@QueryParam("payment_mode") String payment_mode, @QueryParam("datetimestamp") String datetimestamp,
			@QueryParam("companyid") String companyid, @QueryParam("dealerid") String dealerid) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// devicemapid,dealerid, dealername,
			// deviceid,devicename,salecost, credit_money, payement_mode

		//	System.out.println("payment mode = " + payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('insertcustomerAssign'," + " '" + devicemapid
					+ "', '" + customerid + "', " + "'" + deviceid + "', '" + salecost + "', " + "'" + credit_money
					+ "', '" + payment_mode + "', '" + datetimestamp + "',  " + "'" + companyid + "', '" + dealerid
					+ "', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/networkinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response networkInsert(@QueryParam("networkname") String networkname,
			@QueryParam("networkapn") String networkapn, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice insert network");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertnetwork'," + " '0', '" + networkname
					+ "', " + " '" + networkapn + "',  '" + loginid + "', " + "'" + tt.toLocaleString() + "', '"
					+ remarks + "', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			   	return Response.status(200).entity("ok").build();
		}

		catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/networkupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response networkUpdate(@QueryParam("networkid") String networkid,
			@QueryParam("networkname") String networkname, @QueryParam("networkapn") String networkapn,
			@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice updte");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('insertnetwork'," + " '" + networkid + "', '"
					+ networkname + "', " + " '" + networkapn + "',  '" + loginid + "', " + "'" + tt.toLocaleString()
					+ "', '" + remarks + "', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		
			System.out.println("in update query"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/networkdelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response networkdelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteNetwork");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteNetwork'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/companydelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companydelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteCompany'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/simassigndelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simassignUpdate(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simass delete");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			
			String sqlselect4 = "select * from dblocator.insertprocedure('deletesimassign'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/simassignactivate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response simassignactivate(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simass delete");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid
		String sqlselect4 = "select * from dblocator.insertprocedure('activatesimassign'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	@GET
	@Path("/vehicleassigndelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleassignUpdate(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteVehicleToDevice'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/vehicleassignupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleAssignUpdate(@QueryParam("id") String id, @QueryParam("vehicleid") String vehicleid,
			@QueryParam("deviceid") String deviceid, @QueryParam("status") String status,
			@QueryParam("assigndate") String assigndate, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// id,vehicleid,deviceid,status,assigndate,companyid,loginid,remarks,datetimestamp
			String sqlselect4 = "select * from dblocator.insertprocedure('insertVehicleToDevice'," + " '" + id + "', '"
					+ vehicleid + "',  '" + deviceid + "',  '" + status + "', '" + assigndate + "', " + "'" + loginid
					+ "', '" + remarks + "',  " + "'" + tt.toLocaleString() + "', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	 @POST
	 @Path("getmultipledeviceassign")
	 @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON})
	// @Consumes(MediaType.APPLICATION_JSON)
	 public Response getmultipledeviceassign(@QueryParam("status") String status,
				@QueryParam("assigndate") String assigndate, @QueryParam("loginid") String loginid,
				@QueryParam("remarks") String remarks,
				@QueryParam("customerid") String customerid,List<Multipledeviceassng_jsonlist> inputJsonObj) {
		 Connection con = null;
	 try{
		 System.out.println("Connected in websservice multipledevi1   "+inputJsonObj);
	 Class.forName("org.postgresql.Driver");
	  con =
	 DriverManager.getConnection("jdbc:postgresql://192.168.1.120:5432/dbOrissa", "postgres",
					"rsrtc@2017");
	// System.out.println("Connected in websservice multipledevi");
	 java.util.Date dd=new Date();
    Timestamp tt=new Timestamp(dd.getTime());
//    String arr = "{";
////    String[] val = inputJsonObj.getVehicleid();
////    for(int i = 0 ;i<=inputJsonObj.size(); i++){
////    	
////    	Multipledeviceassng_jsonlist deviceid=inputJsonObj.get(i);
////    	System.out.println("deviceid");
//    	
////    	if(i ==(inputJsonObj.getImeino().length)-1){
////    		arr = arr + ""+val[i]+"";
////    	}else{
////    		arr = arr + ""+val[i]+",";
////    	}
////    }
//    arr = arr + "}";
    //System.out.println("length"+arr+"\n"+inputJsonObj.getImeino().length);
    //String sqlselect4 = "select * from salecustomer('"+arr+"', '"+customerid+"', '"+ponum+"', '"+tt.toLocaleString()+"', '"+loginid+"')";
  
    System.out.println("size of lust"+inputJsonObj.size());
    for(int i=0;i<inputJsonObj.size();i++)
   {
    System.out.println("in for");

    String sqlselect4="select * from dblocator.insertprocedure('insertVehicleToDevice'," + " '0', '" + inputJsonObj.get(i).getVehicleid()
					+ "',  '"+inputJsonObj.get(i).getDeviceid() +"',  '" + status + "', '" + assigndate + "', " + "'" + loginid + "', '"
					+ remarks + "',  " + "'" + tt.toLocaleString() + "', '"+customerid+"', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
  
    System.out.println("sqlselect4"+ sqlselect4);
    PreparedStatement	ps = con.prepareStatement(sqlselect4);
   
	boolean rs = ps.execute();
	if (rs) {
	//return Response.status(200).entity("ok").build();
	} else {
		//return Response.status(404).entity("Not ok").build();
	}   
   }
	// boolean rs = ps.execute();
	// if(rs){
		 return Response.status(200).entity("ok").build();
	// }else{
		// return Response.status(404).entity("Not ok").build();
	// }
	 
	
	 }
	 
	 catch (Exception e)
	 { System.out.println(e);
		 livedet det = new livedet();
			det.setExc(String.valueOf(e));
		 	return Response.status(404).entity(det)
					.build();
		 //return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
	 }
	 finally{
		 try {
			con.close(); System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 }
	 
	
	 @POST
		@Path("/getMultipeDeviceInsert")
		@Produces(MediaType.APPLICATION_JSON)
		public Response getMultipeDeviceInsert(@QueryParam("makeid") String makeid, @QueryParam("modelid") String modelid,
				@QueryParam("uniqueid") String uniqueid, @QueryParam("loginid") String loginid,
				@QueryParam("remark") String remark,@QueryParam("deviceid") String deviceid,List<MultipleDeviceCreation> inputjsonlist) {

			Connection con = null;
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
				System.out.println("Connected in websservice multipledevicecretion");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());
				// deviceid, makeid, modelid, uniqueid, loginid,datetimestamp,
				// remark, flag,companyid,
				// assetid,available,vendorid
				//System.out.println("json"+inputjsonlist.size());
				 for(int i=0;i<inputjsonlist.size();i++)
				   {
				String sqlselect4 = "select * from dblocator.insertprocedure('insertBCUDevice'," + " '0', '" + makeid + "', "
						+ "'" + modelid + "', '" + inputjsonlist.get(i).getDeviceno() + "', '" + loginid + "', '" + tt.toLocaleString() + "', " + "'"
						+ remark + "', '" + 0 + "', '"+inputjsonlist.get(i).getDeviceid()+"',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		System.out.println("multi"+sqlselect4);
				PreparedStatement ps = con.prepareStatement(sqlselect4);
				boolean rs = ps.execute();
				if (rs) {
//					return Response.status(200).entity("ok").build();
				} else {
//					return Response.status(404).entity("Not ok").build();
				}
				   }
				return Response.status(200).entity("ok").build();
			} catch (Exception e) {
			  System.out.println("err in insert "+e);
				livedet det = new livedet();
				det.setExc(String.valueOf(e));
				return Response.status(404).entity(det)
						.build();
			  
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	 
	 	@POST
		@Path("/mutileplesimassstodevice")
		@Produces(MediaType.APPLICATION_JSON)
		public Response mutileplesimassstodevice(@QueryParam("deviceid") String deviceid, @QueryParam("simid") String simid,
				@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks,List<MultipleDeviceCreation> inputjsonlist)
	 		{
			Connection con = null;
			// devicesimid, deviceid, simid, loginid, datetimestamp, remarks, flag
			try {
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
				System.out.println("Connected in websservice simins assign");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());
				// System.out.println("values = "+deviceid+" "+simid+" "+loginid+"
				// "+status+" "+assigndate+" "+remarks);
				// devicesimid, deviceid, simid, loginid, datetimestamp, remarks,
				// flag
				
				for(int i=0;i<inputjsonlist.size();i++)
				{
					System.out.println("in  for");
				String sqlselect4 = "select * from dblocator.insertprocedure('insertsimassign', '0', '" +inputjsonlist.get(i).getDeviceid() + "', "
						+ "'" +inputjsonlist.get(i).getSimid() + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', "
						+ "'0', '', '',  " + "'', '', '', '',"
						+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
				System.out.println("quer assugn"+sqlselect4);
				PreparedStatement ps = con.prepareStatement(sqlselect4);
				boolean rs = ps.execute();
				if (rs) {
//					return Response.status(200).entity("ok").build();
				} else {
//					return Response.status(404).entity("Not ok").build();
				}
				
				}
				return Response.status(200).entity("ok").build();

			} catch (Exception e) {
			  System.out.println("errr"+e);
			  livedet det = new livedet();
				det.setExc(String.valueOf(e));
				return Response.status(404).entity(det)
						.build();
//				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//						.build();
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}


	 
	 
	 
	 
	
	
	
	
	@GET
	@Path("vehicleassigninsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleAssignInsert(@QueryParam("vehicleid") String vehicleid,
			@QueryParam("deviceid") String deviceid, @QueryParam("status") String status,
			@QueryParam("assigndate") String assigndate, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks,@QueryParam("customerid") String customerid) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice vehicleassigninsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// id,vehicleid,deviceid,status,assigndate,loginid,remarks,datetimestamp,flag
			
			
			String sqlselect4 = "select * from dblocator.insertprocedure('insertVehicleToDevice'," + " '0', '" + vehicleid
					+ "',  '" + deviceid + "',  '" + status + "', '" + assigndate + "', " + "'" + loginid + "', '"
					+ remarks + "',  " + "'" + tt.toLocaleString() + "', '"+customerid+"', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
			/*String sqlselect4=" select max(id) into  DptID  from dblocator.msttblvehicleassigngps; "
					+ " IF DptID IS NULL THEN "
					+ "	DptID = 21000; "
					+ "	END IF; "
					+ "	DptID :=DptID + 1; "
					+ " if exists(select id from dblocator.msttblvehicleassigngps where id='0'::numeric) then "
					+ "	update dblocator.msttblvehicleassigngps set id='0'::numeric, vehicleid='" + vehicleid
					+ "'::numeric, deviceid='" + deviceid + "'::numeric, "
					+ "	status='" + status + "'::numeric, assigndate='" + assigndate + "'::date,loginid='" + loginid + "'::numeric, "
					+ "	remarks='"+ remarks + "', datetimestamp='" + tt.toLocaleString() + "'::timestamp "
					+ "	where id='0'::numeric; "
					+ "	update dblocator.msttbldevice SET status_veh=0::integer where deviceid= "
					+ " (select deviceid from dblocator.msttblvehicleassigngps where id='" + deviceid + "'::numeric)::numeric; "
					+ " update dblocator.msttblvehicle SET status=0::integer where vehicleid= "
					+ " (select vehicleid from dblocator.msttblvehicleassigngps where id='" + vehicleid
					+ "'::numeric)::numeric; "
					+ " update dblocator.msttbldevice SET status_veh=1::integer where deviceid='" + deviceid + "'::numeric; "
					+ " update dblocator.msttblvehicle SET status=1::integer where vehicleid='" + vehicleid
					+ "'::numeric; "
					+ " else "
					+ " insert into dblocator.msttblvehicleassigngps (id,vehicleid,deviceid,status,assigndate,loginid,remarks,datetimestamp,flag,parentloginid) "
					+ "	values(DptID,'" + vehicleid+ "'::numeric,'" + deviceid + "'::numeric,'" + status + "'::numeric,'" + assigndate + "'::date, "
					+ "	(select loginid from dblocator.msttbluserlogin where ownersid='"+customerid+"'::numeric)::numeric,'"+ remarks + "','" + tt.toLocaleString() + "'::timestamp,0::integer,'"+customerid+"'::numeric); "
					+ "	update dblocator.msttbldevice SET status_veh=1::integer where deviceid='" + deviceid + "'::numeric; "
					+ "	update dblocator.msttblvehicle SET status=1::integer where vehicleid='" + vehicleid + "'::numeric; "
					+ "	did := (select loginid from dblocator.msttbluserlogin where ownersid='"+customerid+"'::numeric)::numeric; "
					+ "	 WHILE did > 0  "
					+ " LOOP "
					+ "	RAISE NOTICE 'Value : % ', did; "
					+ "	INSERT INTO dblocator.msttblmappingvehicledevices_toall(vehicledevicemapid, loginid, datetimestamp, flag) "
					+ "	VALUES (DptID, did, current_timestamp, 0::integer); "
					+ "	SELECT column1 into did FROM dblocator.selectprocedure('selectLoginid', did::character varying, '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '',''); "
					+ "	END LOOP; "
					+ " end if; ";*/
			
			System.out.println("deviceassigntovehcle "+sqlselect4);
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			
			   	return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("modelinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modelInsert(@QueryParam("modelname") String modelname, @QueryParam("makeid") String makeid,
			@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice modelinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// modelid, modelname, makeid, loginid, datetimestamp, remarks,flag
			
			

			String sqlselect4 = "select * from dblocator.insertprocedure('insertModel'," + " '0', '" + modelname + "',  '"
					+ makeid + "',  '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', '0',  "
					+ "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			
			   	return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("modelUpdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modelUpdate(@QueryParam("modelid") String modelid, @QueryParam("modelname") String modelname,
			@QueryParam("makeid") String makeid, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice modelupdatet");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// modelname=$3, makeid=$4::numeric, loginid=$5::numeric,
			// datetimestamp=$6::timestamp, remarks=$7,
			// flag=$8::integer,companyid=$9::numeric
			// WHERE modelid

			String sqlselect4 = "select * from dblocator.insertprocedure('insertModel'," + "'" + modelid + "','"
					+ modelname + "', '" + makeid + "',  '" + loginid + "',  '" + tt.toLocaleString() + "', '" + remarks
					+ "', '0', '',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println("moedlupadte  "+sqlselect4);
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}
		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/vendorinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vendorInsert(@QueryParam("vendorfirmname") String vendorfirmname,
			@QueryParam("vendoraddress") String vendoraddress, @QueryParam("city") String city,
			@QueryParam("statename") String statename, @QueryParam("landlinenumber") String landlinenumber,
			@QueryParam("contactperson") String contactperson, @QueryParam("designation") String designation,
			@QueryParam("mobilenumber") String mobilenumber, @QueryParam("emailid") String emailid,
			@QueryParam("assettypeid") String assettypeid, @QueryParam("servicestationaddr") String servicestationaddr,
			@QueryParam("servstationcontactno") String servstationcontactno, @QueryParam("makeid") String makeid,
			@QueryParam("creditdays") String creditdays, @QueryParam("loginid") String loginid,
			@QueryParam("paymentdate") String paymentdate, @QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vendor insert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

//			vendorid, vendorfirmname, vendoraddress, city,statename,landlinenumber,contactperson,designation,
//		      mobilenumber,emailid,assettypeid,servicestationaddr,servstationcontactno,creditdays,
//		      loginid,datetimestamp,paymentdate,remarks,flag, makeid
			
			
			/*String sqlselect4 = "select * from dblocator.insertprocedure('insertVendor'," + " '0', '" + vendorfirmname
					+ "',  '" + vendoraddress + "', '" + city + "', '" + statename + "'," + " '" + landlinenumber
					+ "', '" + contactperson + "',  " + "'" + designation + "', '" + mobilenumber + "', '" + emailid
					+ "', '" + assettypeid + "', '" + servicestationaddr + "', '" + servstationcontactno + "'," + "'"
					+ creditdays + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + paymentdate + "', " + "'"
					+ remarks
					+ "','0', '"+makeid+"', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";*/
			
//			String sqlselect4= " select max(vendorid) into VendID from  dblocator.msttblvendor; "
//					+ " IF VendID IS NULL THEN "
//					+ "	VendID = 5000; "
//					+ "	END IF; "
//					+ "	VendID := VendID + 1; "
//					+ "	RAISE NOTICE 'Vendore ID % ', VendID; "
//					+ " IF EXISTS(SELECT vendorid FROM dblocator.msttblvendor WHERE vendorid = " + " '0'::numeric)THEN "
//					+ "	UPDATE dblocator.msttblvendor "
//					+ "	SET  vendorfirmname='" + vendorfirmname + "', vendoraddress='" + vendoraddress + "',city='" + city + "',statename='" + statename + "',landlinenumber='" + emailid+ "'::numeric, "
//					+ "	contactperson='" + contactperson + "',designation='" + designation + "',mobilenumber='" + mobilenumber + "'::numeric, "
//					+ "	emailid='" + emailid + "',assettypeid='" + assettypeid + "'::numeric,servicestationaddr='" + servicestationaddr + "',servstationcontactno='" + servstationcontactno + "'::numeric, "
//					+ "	creditdays='"+ creditdays + "'::integer, datetimestamp='" + tt.toLocaleString() + "'::timestamp,paymentdate='" + paymentdate + "'::timestamp, remarks='"+ remarks + "',flag='0'::numeric,makeid='"+makeid+"'::numeric "
//					+ "	WHERE vendorid=" + " '0'::numeric; "
//					+ " ELSE "
//					+ "	INSERT INTO  dblocator.msttblvendor(vendorid, vendorfirmname, vendoraddress, city,statename,landlinenumber,contactperson,designation, "
//					+ " mobilenumber,emailid,assettypeid,servicestationaddr,servstationcontactno,creditdays, "
//					+ " loginid,datetimestamp,paymentdate,remarks,flag, makeid) "
//					+ "	VALUES (VendID, '" + vendorfirmname + "','" + vendoraddress + "','" + city + "','" + statename + "','" + emailid+ "'::numeric,'" + contactperson + "','" + designation + "','" + mobilenumber + "'::numeric,'" + emailid+ "','" + assettypeid + "'::numeric,'" + servicestationaddr + "', '" + servstationcontactno + "'::numeric, "
//					+ " '"+ creditdays + "'::integer,'" + loginid + "'::numeric,'" + tt.toLocaleString() + "'::timestamp,'" + paymentdate + "'::timestamp, '"+ remarks + "', '0'::numeric, '"+makeid+"'::numeric); "
//					+ " END IF;";

//			
			String sqlselect4 = " select * from dblocator.insertprocedure('insertVendor'," + " '0', '" + vendorfirmname + "', "
					+ "'" + vendoraddress + "', '" + city + "', '" + statename + "', '" + landlinenumber + "', " + "'"
					+ contactperson + "', '" + designation + "', '" + mobilenumber + "',  " + "'" + emailid + "', '"
					+ assettypeid + "', '" + servicestationaddr + "', '" + servstationcontactno + "'," + "'" + creditdays + "', '"
					+ loginid + "', '" + creditdays+ "', '" +  tt.toLocaleString() + "', " + "'"+paymentdate+"',"
					+ "'"+remarks+"', '0', '"+makeid+"', '','', '',"
					+ " '', '','', '', "
					+ "'', '','', '', "
					+ "'', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//			
			
			System.out.println("vendor"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
//				return Response.status(200).entity("ok").build();
			} else {
//				return Response.status(404).entity("Not ok").build();
			}
			
			
			   	return Response.status(200).entity("ok").build();

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/modeldelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response modeldelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteModel");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteModel'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("vendorupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vendorUpdate(@QueryParam("vendorid") String vendorid,
			@QueryParam("vendorfirmname") String vendorfirmname, @QueryParam("vendoraddress") String vendoraddress,
			@QueryParam("city") String city, @QueryParam("statename") String statename,
			@QueryParam("landlinenumber") String landlinenumber, @QueryParam("contactperson") String contactperson,
			@QueryParam("designation") String designation, @QueryParam("mobilenumber") String mobilenumber,
			@QueryParam("emailid") String emailid, @QueryParam("assettypeid") String assettypeid,
			@QueryParam("servicestationaddr") String servicestationaddr,
			@QueryParam("servstationcontactno") String servstationcontactno,@QueryParam("makeid") String makeid,
			@QueryParam("creditdays") String creditdays, @QueryParam("loginid") String loginid,
			@QueryParam("paymentdate") String paymentdate, @QueryParam("remarks") String remarks) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vendor updatet");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// vendorid, vendorfirmname, vendoraddress, city, statename,
			// landlinenumber,
			// contactperson, designation, mobilenumber, emailid, assettypeid,
			// servicestationaddr, servstationcontactno, creditdays, loginid,
			// datetimestamp, paymentdate, remarks, flag

			String sqlselect4 = "select * from dblocator.insertprocedure('insertVendor'," + " '" + vendorid + "', '"
					+ vendorfirmname + "',  '" + vendoraddress + "', '" + city + "', '" + statename + "'," + " '"
					+ landlinenumber + "', '" + contactperson + "',  " + "'" + designation + "', '" + mobilenumber
					+ "', '" + emailid + "', '" + assettypeid + "', '" + servicestationaddr + "', '"
					+ servstationcontactno + "'," + "'" + creditdays + "', '" + loginid + "', '" + tt.toLocaleString()
					+ "', '" + paymentdate + "', " + "'" + remarks
					+ "','0', '"+makeid+"', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
                  
//			String sqlselect4= " select max(vendorid) into VendID from  dblocator.msttblvendor; "
//					+ " IF VendID IS NULL THEN "
//					+ "	VendID = 5000; "
//					+ "	END IF; "
//					+ "	VendID := VendID + 1; "
//					+ "	RAISE NOTICE 'Vendore ID % ', VendID; "
//					+ " IF EXISTS(SELECT vendorid FROM dblocator.msttblvendor WHERE vendorid = " + " '0'::numeric)THEN "
//					+ "	UPDATE dblocator.msttblvendor "
//					+ "	SET  vendorfirmname='" + vendorfirmname + "', vendoraddress='" + vendoraddress + "',city='" + city + "',statename='" + statename + "',landlinenumber='" + emailid+ "'::numeric, "
//					+ "	contactperson='" + contactperson + "',designation='" + designation + "',mobilenumber='" + mobilenumber + "'::numeric, "
//					+ "	emailid='" + emailid + "',assettypeid='" + assettypeid + "'::numeric,servicestationaddr='" + servicestationaddr + "',servstationcontactno='" + servstationcontactno + "'::numeric, "
//					+ "	creditdays='"+ creditdays + "'::integer, datetimestamp='" + tt.toLocaleString() + "'::timestamp,paymentdate='" + paymentdate + "'::timestamp, remarks='"+ remarks + "',flag='0'::numeric,makeid='"+makeid+"'::numeric "
//					+ "	WHERE vendorid=" + " '0'::numeric; "
//					+ " ELSE "
//					+ "	INSERT INTO  dblocator.msttblvendor(vendorid, vendorfirmname, vendoraddress, city,statename,landlinenumber,contactperson,designation, "
//					+ " mobilenumber,emailid,assettypeid,servicestationaddr,servstationcontactno,creditdays, "
//					+ " loginid,datetimestamp,paymentdate,remarks,flag, makeid) "
//					+ "	VALUES (VendID, '" + vendorfirmname + "','" + vendoraddress + "','" + city + "','" + statename + "','" + emailid+ "'::numeric,'" + contactperson + "','" + designation + "','" + mobilenumber + "'::numeric,'" + emailid+ "','" + assettypeid + "'::numeric,'" + servicestationaddr + "', '" + servstationcontactno + "'::numeric, "
//					+ " '"+ creditdays + "'::integer,'" + loginid + "'::numeric,'" + tt.toLocaleString() + "'::timestamp,'" + paymentdate + "'::timestamp, '"+ remarks + "', '0'::numeric, '"+makeid+"'::numeric); "
//					+ " END IF;";
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/vendordelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vendordelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vendordelete");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			/*String sqlselect4 = "select * from dblocator.insertprocedure('deleteVendor'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";*/
			
			String sqlselect4 = " UPDATE  dblocator.msttblvendor "
					+ " SET flag = 1 where vendorid='"+id+"'::numeric;	RAISE NOTICE 'Process Procedure Type : % ', $1; ";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("vehiclesummary")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehicleSummary(@QueryParam("imeino") String imeino, @QueryParam("fromdate") String fromdate,
			@QueryParam("todate") String todate, @QueryParam("fromtime") String fromtime,
			@QueryParam("totime") String totime) {
		List<vehiclesummary_report> data = new ArrayList<vehiclesummary_report>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vendor updatet");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// modelname=$3, makeid=$4::numeric, loginid=$5::numeric,
			// datetimestamp=$6::timestamp, remarks=$7,
			// flag=$8::integer,companyid=$9::numeric
			// WHERE modelid

			String sqlselect4 = "select vehiclesummary('" + imeino + "','" + fromdate + "','" + fromtime
					+ "','" + totime + "','" + todate + "');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);

			boolean rs = ps.execute();
			if (rs) {
				sqlselect4 = "select * from dblocator.selectprocedure('selectVehicleSummary', '" + imeino
						+ "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				// SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd
				// HH:mm:ss");

				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);

				while (rs4.next()) {
				

					// unitid, MinDateReceived, MaxDateReceived,
					// MinTimeReceived, MaxTimeReceived, TripID, Latitude,
					// Longitude,
					// DATE_PART('day', MaxDateReceived::timestamp -
					// MinDateReceived::timestamp) * 24 * 60 + DATE_PART('hour',
					// '08:56:10'::time - '08:54:55'::time) * 60 +
					// DATE_PART('minute', MaxTimeReceived::time -
					// MinTimeReceived::time) AS Stop,
					// ntoWN, NCITY
					try {
						vehiclesummary_report obj = new vehiclesummary_report();
						obj.setUnitid(rs4.getString(0+1));
						obj.setMindatereceived(rs4.getString(1+1));
						obj.setMaxdatereceived(rs4.getString(2+1));
						obj.setMintimereceived(rs4.getString(3+1));
						obj.setMaxtimereceived(rs4.getString(4+1));
						obj.setTripid(rs4.getString(5+1));
						obj.setLatitude(rs4.getString(6+1));
						obj.setLongitude(rs4.getString(7+1));
						obj.setStop(rs4.getString(8+1));
						// obj.setNtown(rs4.getString(9+1));
						// obj.setNcity(rs4.getString(10+1));
						data.add(obj);
					} catch (Exception e) {
					//	System.out.println("e " + e);

					}
				}

				return Response.status(200).entity(data).build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/roleinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response insertRole(@QueryParam("rolename") String rolename, @QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid, @QueryParam("remarks") String remarks) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
	System.out.println("Connected in websservice roleinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// roleid, rolename, companyid, loginid, datetimestamp, remarks,
			// flag
			
			 
			String sqlselect4 = "select * from dblocator.insertprocedure('insertRole', '0', '" + rolename + "', " + "'"
					+ companyid + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', "
					+ "'0', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		
			System.out.println("role edit "+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			ResultSet rs = ps.executeQuery();
			
			

			return Response.status(200).entity("{\"success\": false,\"error\": \"ok\",\"success_code\": 202}").build();

		} catch (Exception e) {
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			System.out.println(e);
			return Response.status(404).entity(det)
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/roleedit")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editRole(@QueryParam("roleid") String roleid, @QueryParam("rolename") String rolename,
			@QueryParam("companyid") String companyid, @QueryParam("loginid") String loginid,
			@QueryParam("remarks") String remarks) {
		Connection con = null;

		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice role edit");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// roleid, rolename, companyid, loginid, datetimestamp, remarks,
			// flag

			String sqlselect4 = "select * from dblocator.insertprocedure('insertRole', '" + roleid + "', '" + rolename
					+ "', " + "'" + companyid + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks
					+ "', " + "'0', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			System.out.println("role"+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			ResultSet rs = ps.executeQuery();

			
			return Response.status(200).entity("ok").build();

		} catch (Exception e) {
			System.out.println("error "+e);
			livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@GET
	@Path("/roledelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response roledelete(@QueryParam("id") String id) {
		Connection con = null;
	try {
		Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice deleteRole");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteRole'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			
			System.out.println("query"+sqlselect4);
			
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				System.out.println("ok");
				return Response.status(200).entity("ok").build();
			} else {
				System.out.println(" not ok");
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/menuassigninsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuassignInsert(@QueryParam("menuid") String menuid, @QueryParam("roleid") String roleid,
			@QueryParam("loginid") String loginid) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// id, menuid, roleid, loginid, flag
			String sqlselect4 = "select * from dblocator.insertprocedure('insertmenuassign'," + " '0', '" + menuid + "', "
					+ "'" + roleid + "', '" + loginid + "', '0', '', " + "'', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		}

		finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/menuassignupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuassignUpdate(@QueryParam("id") String id, @QueryParam("menuid") String menuid,
			@QueryParam("roleid") String roleid, @QueryParam("loginid") String loginid) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// id, menuid, roleid, loginid, flag
			String sqlselect4 = "select * from dblocator.insertprocedure('insertmenuassign'," + " '" + id + "', '" + menuid
					+ "', " + "'" + roleid + "', '" + loginid + "', '0', '', " + "'', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		}

		finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	
	
	@GET
	@Path("/menuassigndelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuassigndelete(@QueryParam("id") String id) {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice deleteMenuAssign");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.insertprocedure('deleteMenuAssign'," + " '" + id + "', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		}

		catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/assetinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assetInsert(@QueryParam("companyid") String companyid, @QueryParam("remarks") String remarks,
			@QueryParam("assetname") String assetname, @QueryParam("loginid") String loginid) {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// assettypeid, companyid,assetname,loginid,datetimestamp,remarks
			String sqlselect4 = "select * from dblocator.insertprocedure('insertAsset'," + " '0', '" + companyid + "', "
					+ "'" + assetname + "', '" + loginid + "', '" + tt.toLocaleString() + "', '" + remarks + "', "
					+ "'', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		}

		finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("/assetupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response assetUpdate(@QueryParam("companyid") String companyid, @QueryParam("remarks") String remarks,
			@QueryParam("assetname") String assetname, @QueryParam("loginid") String loginid,
			@QueryParam("assettypeid") String assettypeid) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// assettypeid, companyid,assetname,loginid,datetimestamp,remarks
			String sqlselect4 = "select * from dblocator.insertprocedure('insertAsset'," + " '" + assettypeid + "', '"
					+ companyid + "', " + "'" + assetname + "', '" + loginid + "', '" + tt.toLocaleString() + "', '"
					+ remarks + "', " + "'', '', '',  " + "'', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		}

		finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("menuinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuInsert(@QueryParam("menutext") String menutext, @QueryParam("description") String description,
			@QueryParam("navigateurl") String navigateurl, @QueryParam("parentid") String parentid,
			@QueryParam("orderno") String orderno) {
		navigateurl = "#/" + navigateurl;
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			System.out.println("Connected in websservice menuinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
		//	System.out.println("parentid = " + parentid);
			// menuid,menutext, description, navigateurl, parentid, orderno, mid
			String sqlselect4 = "select * from dblocator.insertprocedure('insertmenu'," + " '0', '" + menutext + "',  '"
					+ description + "',  '" + navigateurl + "', '" + parentid + "', " + "'" + orderno + "', '"
					+ parentid + "',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		System.out.println("insert "+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@GET
	@Path("menuupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response menuUpdate(@QueryParam("menutext") String menutext, @QueryParam("menuid") String menuid,
			@QueryParam("description") String description, @QueryParam("navigateurl") String navigateurl,
			@QueryParam("parentid") String parentid, @QueryParam("orderno") String orderno) {
		navigateurl = "#/" + navigateurl;
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// menuid,menutext, description, navigateurl, parentid, orderno, mid
			String sqlselect4 = "select * from dblocator.insertprocedure('insertmenu'," + " '" + menuid + "', '" + menutext
					+ "',  '" + description + "',  '" + navigateurl + "', '" + parentid + "', " + "'" + orderno + "', '"
					+ parentid + "',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

		//	System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("ok").build();
			} else {
				return Response.status(404).entity("Not ok").build();
			}

		} catch (Exception e) {
		  System.out.println(e);
		  
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

@GET
	@Path("/shelterdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shelterdetails(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid,@QueryParam("routename") String routename) {
		List<shelter_det> data = new ArrayList<shelter_det>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
			String sqlselect4 ="select shelterid, sheltercode, sheltername, latitude, longitude,"
					+ " loginid, datetimestamp,c.circleid,circlename from dblocator.msttblrouteshelter as md "
					+ " inner join dblocator.msttblcircle as c on c.circleid = md.circleid";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				int m = 0;
				while (rs4.next()) {
				// shelterid, sheltercode, sheltername, latitude, longitude, loginid, datetimestamp
					try {
						shelter_det obj = new shelter_det();
						obj.setShelterid(rs4.getString(1));
						obj.setSheltercode(rs4.getString(2));
						obj.setSheltername(rs4.getString(3));
						obj.setLatitude(rs4.getString(4));
						obj.setLongitude(rs4.getString(5));
						obj.setLoginid(rs4.getString(6));
						obj.setDatetimestamp(rs4.getString(7));
						obj.setCircleid(rs4.getString(8));
						obj.setCirclename(rs4.getString(9));
						
						data.add(obj);
						m++;
					} catch (Exception e) {
						System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/shelterdetailsreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response shelterdetailsreport(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid,@QueryParam("routename") String routename) {
		List<shelter_det> data = new ArrayList<shelter_det>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select shelterid, sheltercode, sheltername, latitude, longitude, loginid, datetimestamp from dblocator.msttblrouteshelter";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				int m = 0;
				while (rs4.next()) {
				// shelterid, sheltercode, sheltername, latitude, longitude, loginid, datetimestamp
					try {
						shelter_det obj = new shelter_det();
						obj.setShelterid(rs4.getString(1));
						obj.setSheltercode(rs4.getString(2));
						obj.setSheltername(rs4.getString(3));
						obj.setLatitude(rs4.getString(4));
						obj.setLongitude(rs4.getString(5));
						obj.setLoginid(rs4.getString(6));
						obj.setDatetimestamp(rs4.getString(7));
						
						data.add(obj);
						m++;
					} catch (Exception e) {
						System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/historylogdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response historylogdetails(@QueryParam("vehicleno") String vehicleno,@QueryParam("fromdate")String fromdate,@QueryParam("todate")String todate) {
		List<historylog_details> data = new ArrayList<historylog_details>();
		Connection con = null;
		try {
			System.out.println("in history log");
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			//sdf.parse(fromdate);
				/*String sqlselect4 = "select * from dblocator.selectprocedure('historyLogreport', '"
						+ fromdate + "', '"+todate+"', '"+vehicleno+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			   String sqlselect4=" SELECT  mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno, "
			   		+ " mstass.deviceid,deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp, "
			   		+ " deviceparse.gpsstatus,deviceparse.packettime,deviceparse.vehiclespeed,deviceparse.ignumber,m.district "
			   		+ " FROM dblocator.msttblvehicle as mstvehicle "
			   		+ " inner  join dblocator.msttblvehicleassigngps as mstass on mstass.vehicleid = mstvehicle.vehicleid "
			   		+ " inner join  dblocator.msttbldevice as mstdev on mstdev.deviceid = mstass.deviceid "
			   		+ " inner join  \"parsed_loc_device_record_"+sdf1.format(sdf.parse(fromdate))+ "\"   as deviceparse on deviceparse.imeino= mstdev.uniqueid "
			   		+ " left join dblocator.msttblambulance_details as m on m.ambbyno = mstvehicle.vehicleregno"
			   		+ " where deviceparse.latitude>0 and deviceparse.longitude>0 and deviceparse.latitude<100 and deviceparse.longitude<100 and mstvehicle.vehicleregno='"+vehicleno+"'::text and "
			   		+ " deviceparse.packetdate = '"+ sdf.parse(fromdate) + "'::date ";
			
				//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				System.out.println("in history log"+sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
//			
//				mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//		        mstass.deviceid,
//		      deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp,deviceparse.gpsstatus,deviceparse.packettime,
//		      deviceparse.vehiclespeed,deviceparse.ignumber
				
				
			while (rs4.next()) {
					try {
						historylog_details obj=new historylog_details();
						obj.setVehicleid(rs4.getString(1));
						obj.setVehicletypeid(rs4.getString(2));
						obj.setVehicleregno(rs4.getString(3));
						obj.setDeviceid(rs4.getString(4));
						obj.setImeino(rs4.getString(5));
						obj.setLatitude(rs4.getString(6));
						obj.setLongitude(rs4.getString(7));
						
						SimpleDateFormat sdf12 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
						java.util.Date parseTimestamp = sdf12.parse(rs4.getString(8));
//						System.out.println(rs4.getString(8));
//						dd.setDatereceived(sdfnew.format(parseTimestamp));
//						dd.setTrackdate(parseTimestamp.toLocaleString());
					
						obj.setDatatimestamp(sdfnew.format(parseTimestamp));
						
						if(rs4.getString(9).equals("0"))
						{
						obj.setGpsstatus("Invalid");
						}
						else
						{
							obj.setGpsstatus("Valid");
						}
						obj.setPackettime(rs4.getString(10));
						obj.setVehiclespeed(rs4.getString(11));
						if(rs4.getInt(12)==0)
						{
							obj.setIgnumber("OFF");
						}
						else
						{
							obj.setIgnumber("ON");
						}
						
						obj.setDistrict(rs4.getString(13));
						data.add(obj);
					
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/alertlogreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response alertlogreport(@QueryParam("vehicleno") String vehicleno,@QueryParam("fromdate")String fromdate,
			@QueryParam("todate")String todate,@QueryParam("typeof_alerts")String typeof_alerts,@QueryParam("overspeedlimit")int overspeedlimit ) {
		List<alert_log_details> data = new ArrayList<alert_log_details>();
		System.out.println("overspeed value"+overspeedlimit+"vehicleno ="+vehicleno+" fromdate  "+fromdate+" typeof_alerts"+typeof_alerts);
		Connection con = null;
		String tamperstatus=null;
		int Overspeed=0;
		
		try {
			fromdate = fromdate +" 00:00:00";
			todate = todate +" 23:59:00";
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('alertLogreport', '"
						+ fromdate + "', '"+todate+"', '"+vehicleno+"', '"+typeof_alerts+"', '"+overspeedlimit+"', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
//			
//				mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//		        mstass.deviceid,
//		      deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp,deviceparse.gpsstatus,deviceparse.packettime,
//		      deviceparse.vehiclespeed,deviceparse.ignumber
				
				
			while (rs4.next()) {
				//System.out.println(rs4);
					try {
						alert_log_details obj=new alert_log_details();
						obj.setVehicleregno(rs4.getString(3));
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
						java.util.Date parseTimestamp = sdf1.parse(rs4.getString(8));
//						System.out.println(rs4.getString(8));
//						dd.setDatereceived(sdfnew.format(parseTimestamp));
//						dd.setTrackdate(parseTimestamp.toLocaleString());
						obj.setDatatimestamp(sdfnew.format(parseTimestamp));
						obj.setType_of_alerts(typeof_alerts);
						obj.setLatitute(rs4.getString(6));
						obj.setLongitute(rs4.getString(7));
						try{
							obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitute()),
							Double.parseDouble(obj.getLongitute())));
						}catch(Exception e){
						  //System.out.println(e);
						}
////					//	System.out.println("location = "+obj.getLocation()+"hello");
						if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
							
							 String message="Location Not Found"+" "+"Lat-"+obj.getLatitute()+" "+"Long-"+obj.getLongitute();
							obj.setLocation(message);
						}
						data.add(obj);
					
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	@GET
	@Path("/tamperreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response tamperreport(@QueryParam("vehicleno") String vehicleno,@QueryParam("fromdate")String fromdate,
			@QueryParam("todate")String todate) {
		List<alert_log_details> data = new ArrayList<alert_log_details>();
		//System.out.println("overspeed value"+overspeedlimit+"vehicleno ="+vehicleno+" fromdate  "+fromdate+" typeof_alerts"+typeof_alerts);
		Connection con = null;
		String tamperstatus="NNN";
		int Overspeed=0;
		
		try {
			fromdate = fromdate +" 00:00:00";
			todate = todate +" 23:59:00";
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('tamperreport', '"
						+ fromdate + "', '"+todate+"', '"+vehicleno+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
//			
//				mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//		        mstass.deviceid,
//		      deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp,deviceparse.gpsstatus,deviceparse.packettime,
//		      deviceparse.vehiclespeed,deviceparse.ignumber
				
				
			while (rs4.next()) {
				//System.out.println(rs4);
					try {
						alert_log_details obj=new alert_log_details();
						obj.setVehicleregno(rs4.getString(3));
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
						java.util.Date parseTimestamp = sdf1.parse(rs4.getString(8));
//						System.out.println(rs4.getString(8));
//						dd.setDatereceived(sdfnew.format(parseTimestamp));
//						dd.setTrackdate(parseTimestamp.toLocaleString());
						obj.setDatatimestamp(sdfnew.format(parseTimestamp));
						obj.setType_of_alerts(gettemperStatus(rs4.getString(1)));
						obj.setLatitute(rs4.getString(6));
						obj.setLongitute(rs4.getString(7));
//						try{
//							obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitute()),
//							Double.parseDouble(obj.getLongitute())));
//						}catch(Exception e){
//						  //System.out.println(e);
//						}
//////					//	System.out.println("location = "+obj.getLocation()+"hello");
//						if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
//							
//							 String message="Location Not Found"+" "+"Lat-"+obj.getLatitute()+" "+"Long-"+obj.getLongitute();
//							obj.setLocation(message);
//						}
						data.add(obj);
					
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/disconnectreport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response disconnectreport(@QueryParam("vehicleno") String vehicleno,@QueryParam("fromdate")String fromdate,
			@QueryParam("todate")String todate) {
		List<alert_log_details> data = new ArrayList<alert_log_details>();
		//System.out.println("overspeed value"+overspeedlimit+"vehicleno ="+vehicleno+" fromdate  "+fromdate+" typeof_alerts"+typeof_alerts);
		Connection con = null;
		String tamperstatus=null;
		int Overspeed=0,ign=1,i=0;
		
		try {
			fromdate = fromdate +" 00:00:00";
			todate = todate +" 23:59:00";
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				String sqlselect4 = "select * from dblocator.selectprocedure('disconnectreport', '"
						+ fromdate + "', '"+todate+"', '"+vehicleno+"', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;;
//			
//				mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//		        mstass.deviceid,
//		      deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp,deviceparse.gpsstatus,deviceparse.packettime,
//		      deviceparse.vehiclespeed,deviceparse.ignumber
				
				
			while (rs4.next()) {
				//System.out.println(Integer.parseInt(rs4.getString(1)));
					try {
						
						if(Integer.parseInt(rs4.getString(1))==0){
							if(ign==1 || i==0){
								alert_log_details obj=new alert_log_details();
								obj.setVehicleregno(rs4.getString(3));
								SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
								java.util.Date parseTimestamp = sdf1.parse(rs4.getString(8));
		//						System.out.println(rs4.getString(8));
		//						dd.setDatereceived(sdfnew.format(parseTimestamp));
		//						dd.setTrackdate(parseTimestamp.toLocaleString());
								obj.setDatatimestamp(sdfnew.format(parseTimestamp));
								obj.setType_of_alerts(rs4.getString(1));
								obj.setLatitute(rs4.getString(6));
								obj.setLongitute(rs4.getString(7));
								try{
									obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitute()),
									Double.parseDouble(obj.getLongitute())));
								}catch(Exception e){
								  //System.out.println(e);
								}
////							//	System.out.println("location = "+obj.getLocation()+"hello");
								if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
									
									 String message="Location Not Found"+" "+"Lat-"+obj.getLatitute()+" "+"Long-"+obj.getLongitute();
									obj.setLocation(message);
								}
								data.add(obj);
							}
						}
					
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
					ign=Integer.parseInt(rs4.getString(1));
					i++;
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/bangimeidetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response bangimeidetails(@QueryParam("imei") String imei) {
		List<bang_imei> data = new ArrayList<bang_imei>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
//			 System.out.println("Connected in websservice deviceIDdetails count ");
			
				/*String sqlselect4 = "select * from dblocator.selectprocedure('selectbangimei', '"
						+ imei + "', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/ 
			String sqlselect4=" SELECT md.deviceid,cdm.imeino, cdm.packetdate, cdm.packettime, cdm.updatedtimestamp, "
					+ " cdm.vehicleid, cdm.latitude, cdm.latitudedirection, cdm.longitude, cdm.longitudedirection, "
					+ " cdm.vehiclespeed, cdm.vehicledirection, cdm.gpsstatus, cdm.ignumber,msd.devicesimid,mss.mobilenumber, "
					+ " (select mc1.customername from dblocator.msttbldevice as md1 "
					+ " inner join dblocator.msttblmappingdevices_toall as mdm1 on md1.deviceid = mdm1.deviceid "
					+ " inner join dblocator.msttbluserlogin c1 on mdm1.loginid = c1.loginid "
					+ " inner join dblocator.msttblcustomer as mc1 on mc1.customerid = c1.ownersid "
					+ " where md1.deviceid = md.deviceid and md1.flag=0  limit 1) as customer "
					+ " FROM connected_device_master as cdm  "
					+ " inner join dblocator.msttbldevice as md on md.uniqueid = cdm.imeino "
					+ " inner join dblocator.msttbldevicesimmap as msd on msd.deviceid= md.deviceid "
					+ " inner join dblocator.msttblsim as mss on mss.simid=msd.simid "
					+ " where md.uniqueid = '"+imei+"' or md.deviceid = '"+imei+"'::numeric;";
			
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfnew = new SimpleDateFormat("dd-MMM-yyyy");
				
				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
			
				boolean avail=false;
			
			
//			   md.deviceid,cdm.imeino, cdm.packetdate, cdm.packettime, cdm.updatedtimestamp, 
//		       cdm.vehicleid, cdm.latitude, cdm.latitudedirection, cdm.longitude, cdm.longitudedirection, 
//		       cdm.vehiclespeed, cdm.vehicledirection, cdm.gpsstatus, cdm.ignumber
			while (rs4.next()) {
					try {
						bang_imei obj=new bang_imei();
						obj.setDeviceid(rs4.getString(1));
						obj.setImeino(rs4.getString(2));
						java.util.Date parseTimestamp = sdf.parse(rs4.getString(3));
						//System.out.println("inn date"+rs4.getString(2));
						//obj.setDatatimestamp(sdfnew.format(parseTimestamp));
						obj.setPacketdate(sdfnew.format(parseTimestamp));
						obj.setPackettime(rs4.getString(4));
						obj.setUpdatedtimestamp(rs4.getString(5));
						obj.setVehicleid(rs4.getString(6));
						obj.setLatitude(rs4.getString(7));
						obj.setLatitudedirection(rs4.getString(8));
						obj.setLongitude(rs4.getString(9));
						obj.setLongitudedirection(rs4.getString(10));
						obj.setVehiclespeed(rs4.getString(11));
						obj.setVehicledirection(rs4.getString(12));
						if(rs4.getString(13).equals("0"))
						{
							obj.setGpsstatus("Invalid");
						}
						else
						{
							obj.setGpsstatus("Valid");
						}
						if(rs4.getInt(14)==0)
						{
						obj.setIgnumber("OFF");
						}
						else
						{
							obj.setIgnumber("ON");
						}
						obj.setMobileno(rs4.getString(16));
						try{
							obj.setLocation(getLocationClass.getLoc(Double.parseDouble(obj.getLatitude()),
							Double.parseDouble(obj.getLongitude())));
						}catch(Exception e){
						}
						if(obj.getLocation().equals("") || obj.getLocation().startsWith("Object")){
							obj.setLocation("Location Not Found");
						}
						try{
							obj.setCustomername(rs4.getString(17));
						}catch(Exception e){
							obj.setCustomername("Not Availiable");
						}
						data.add(obj);
						
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("geofenceinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response geofenceInsert(@QueryParam("geofencename") String geofencename, 
			@QueryParam("coord") String coord,
			@QueryParam("loginid") String loginid) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice simins");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
//			String json = new Gson().toJson(coord);
			coord = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":{\"type\":\"Polygon\",\"coordinates\":[[[9500916.295587467,2523345.447975215],[9501061.854381563,2523279.3120308197],[9501059.615015501,2523231.3895970704],[9501058.719269074,2523202.4271293217],[9501058.719269074,2523188.3937686603],[9501068.124606542,2523105.8358064694],[9501078.127108289,2523029.697360326],[9501086.039535046,2522951.7674213317],[9501092.906924305,2522890.856664417],[9501098.132111786,2522851.7424038495],[9501142.620850902,2522780.2319809026],[9501198.45571141,2522700.5105490587],[9501262.35228974,2522610.9359065364],[9501273.549120056,2522595.8575083786],[9501301.317259239,2522569.4329888355],[9501375.21633932,2522533.3045496847],[9501456.729264015,2522494.339580188],[9501488.52826211,2522483.4413320147],[9501530.479053024,2522494.339580188],[9501629.757615149,2522572.1202281113],[9501733.514909407,2522651.8416599547],[9501810.698393043,2522711.558088303],[9501955.062858576,2522823.526391455],[9501986.264692387,2522848.3087092196],[9501992.833499506,2522869.806623425],[9501971.484876372,2522916.3854375356],[9501988.20547631,2522965.5021998514],[9502000.596635193,2522976.8483212376],[9502127.941918643,2522962.3670873637],[9502199.303050518,2522954.6039516786],[9502251.853507465,2522972.220298041],[9502276.934407368,2522989.9859354747],[9502253.645000314,2522973.1160444664],[9502309.62915189,2522994.4646676006],[9502368.897707026,2523020.1427317895],[9502481.612465532,2523068.662329823],[9502589.549909772,2523114.4946885793],[9502637.323052451,2523134.0518188635],[9502780.642480485,2523158.5355544863],[9502833.939392785,2523164.2086151796],[9502911.869331779,2523182.123543684],[9503047.72420627,2523204.218622172],[9503192.984418225,2523180.3320508334],[9503290.322196433,2523163.163577683],[9503429.760056626,2523167.343727668],[9503568.451461462,2523170.6281312266],[9503660.414761117,2523231.538888141],[9503818.066131957,2523332.1610699072],[9503836.578224745,2523344.4029377187],[9503862.853453217,2523348.2845055615],[9503958.847611787,2523383.8157804282],[9504073.503154213,2523381.427123294],[9504175.170373477,2523377.9934286643],[9504318.788383652,2523357.6898430255],[9504407.467279749,2523378.590592948],[9504472.856768789,2523375.1568983183],[9504575.718316618,2523376.201935814],[9504783.382196197,2523381.427123294],[9504858.774186987,2523248.856652362],[9504878.032735132,2523216.161907842],[9504937.599872408,2523191.081007936],[9504934.315468848,2523085.681511902],[9504941.63073132,2522965.352908781],[9504949.99103129,2522871.4488252043],[9504959.993533036,2522803.9692611713],[9504973.429729415,2522745.4471613904],[9505000.899286456,2522629.8958725375],[9505029.861754203,2522518.67402474],[9505061.212879088,2522365.352094957],[9505076.589859387,2522271.4480113797],[9505080.322136156,2522188.7407581178],[9505082.710793292,2522121.261194085],[9505082.561502222,2522072.5923049813],[9505081.964337938,2521999.4396802555],[9505081.815046865,2521903.2962306156],[9505080.770009369,2521868.8099932447],[9505079.724971874,2521750.5714651155],[9505078.082770094,2521653.830851192],[9505077.933479022,2521582.693655923],[9505077.037732597,2521526.1869856],[9505076.440568317,2521383.4647218483],[9505080.6207183,2521172.6657297798],[9505080.919300443,2521109.366315732],[9505087.786689702,2521026.957644612],[9505127.49811455,2520861.84172023],[9505129.886771685,2520821.2345489534],[9505143.024385922,2520795.556484764],[9505174.375510804,2520682.692435187],[9505177.659914363,2520497.87008945],[9505158.849239433,2520369.1811863612],[9505141.531475214,2520265.5731831775],[9505146.308789482,2520211.8283976647],[9505221.252907056,2520024.3188126516],[9505261.56149619,2519929.3696915796],[9505292.912621073,2519846.9610204594],[9505342.477256604,2519729.9168208973],[9505409.956820637,2519561.516492957],[9505491.171163188,2519352.210411598],[9505515.35631667,2519293.3897296754],[9505523.71661664,2519230.388897768],[9505506.996016702,2519030.9360270863],[9505498.03855245,2518942.5557131316],[9505484.30377393,2518809.089495775],[9505475.943473961,2518712.6474639927],[9505460.41720259,2518546.0386289023],[9505442.502274087,2518364.202104584],[9505534.764155885,2518247.7550693057],[9505675.396344643,2518085.6249663406],[9505774.22703356,2517918.7175491084],[9505791.246215638,2517884.9777670926],[9505894.55563668,2517799.881856697],[9506008.016850539,2517709.710049892],[9506225.98181401,2517660.742578647],[9506342.428849287,2517643.126232284],[9506575.920084128,2517611.7751074014],[9506805.828333266,2517573.258011117],[9506930.635668514,2517551.760096912],[9507278.185281496,2517450.242168721],[9507433.447995203,2517400.9761153334],[9507682.465501413,2517397.094547491],[9507778.60895105,2517384.5540975383],[9508004.635632351,2517298.8610228584],[9508083.75989991,2517268.7042265437],[9508188.26364952,2517194.058691109],[9508246.188585017,2517160.617491234],[9508279.928367032,2517153.451519832],[9508304.412102655,2517152.854355549],[9508320.535538308,2517149.8685341314],[9508348.005095348,2517126.280544934],[9508388.910848767,2516962.3589491197],[9508407.124359414,2516880.8460244243],[9508448.328694975,2516770.967796264],[9508479.381237715,2516750.0670463424],[9508534.917516079,2516742.005328516],[9508627.179397875,2516746.1854785],[9508661.217762034,2516574.799329142],[9508677.938361973,2516469.996997392],[9508678.535526255,2516457.4565474386],[9508666.293658445,2516387.8869084134],[9508672.56388342,2516361.014515657],[9508695.853290476,2516341.308094302],[9508791.399575831,2516330.5591371995],[9508823.049282856,2516321.900255089],[9508880.675636211,2516285.174651655],[9508938.301989567,2516238.894419685],[9508980.103489412,2516178.580827054],[9509005.184389317,2516070.4940917445],[9509005.482971456,2515933.7434708285],[9509004.587225031,2515788.0353856604],[9509005.781553598,2515764.148814321],[9509016.530510701,2515645.6117040506],[9509082.218581885,2515392.7126299976],[9509121.03426031,2515264.32230905],[9509124.915828153,2515172.3590093944],[9509118.944185318,2515096.817727534],[9509092.370374706,2514891.691796159],[9509075.94835691,2514751.955353826],[9509090.280299712,2514708.3623611317],[9509097.744853256,2514696.717657604],[9509118.048438894,2514645.0629470833],[9509135.664785258,2514519.359865411],[9509185.528002927,2514381.7134980694],[9509172.09180655,2514316.9211733122],[9509060.123503396,2513650.784415092],[9509031.45961779,2513494.3273728206],[9508956.216918072,2512737.1230613706],[9508839.17271851,2512015.7486069296],[9508839.17271851,2511479.4950803667],[9508875.002575519,2511160.6093529896],[9508903.666461125,2511174.941295793],[9508864.253618415,2511543.988822982],[9508869.030932683,2511903.4817216354],[9508867.836604116,2512028.8862211662],[9508958.605575206,2512579.4716905328],[9509042.208574891,2513257.8503165636],[9509103.119331807,2513764.245628953],[9509196.276960028,2514283.7785555786],[9509202.248602863,2514412.7660408095],[9509154.475460187,2514536.976211773],[9509138.949188815,2514663.5750398706],[9509097.147688972,2514753.149682392],[9509144.920831649,2515165.1930379923],[9509141.33784595,2515267.905294751],[9509038.625589192,2515639.3414790737],[9509020.710660687,2515768.328964305],[9509019.51633212,2516091.9920059503],[9508996.824089348,2516185.1496341727],[9508956.216918072,2516242.4774053865],[9508846.338689912,2516330.8577193413],[9508783.337858004,2516343.9953335775],[9508703.019261876,2516349.6683942713],[9508678.834108396,2516372.9578013266],[9508675.549704837,2516397.7401190912],[9508689.284483356,2516465.518265266],[9508662.4120906,2516623.766800387],[9508638.525519261,2516760.218839162],[9508538.201919638,2516756.9344356023],[9508495.80325551,2516759.92025702],[9508459.376234218,2516785.0011569257],[9508380.5505488,2517061.7868023184],[9508356.066813176,2517142.1053984454],[9508320.53553831,2517159.423162666],[9508269.627283143,2517165.544096573],[9508243.949218953,2517171.9636126203],[9508203.342047675,2517195.99947503],[9508173.185251359,2517217.0495160227],[9508087.790758824,2517279.303892575],[9507997.469660947,2517312.1479281667],[9507835.339557983,2517373.9544315063],[9507779.95257069,2517395.452345712],[9507680.076844279,2517407.395631381],[9507545.416298354,2517406.0520117427],[9507484.80412358,2517407.694213522],[9507435.388779124,2517411.277199223],[9507294.159426082,2517454.720900847],[9507178.309555087,2517491.29721321],[9507032.153596707,2517534.890205904],[9506917.199472135,2517568.6299879197],[9506798.811652938,2517586.246334283],[9506696.547269393,2517601.17544137],[9506549.794146728,2517624.614139496],[9506400.801657997,2517645.96276263],[9506125.658214385,2517701.9469142067],[9506005.031029122,2517736.5824426482],[9505797.217858473,2517907.371427723],[9505761.388001464,2517967.087856071],[9505692.11694458,2518093.6866841684],[9505459.222874025,2518365.993597434],[9505530.88258804,2519122.0035803174],[9505538.048559442,2519270.10032262],[9505299.182846053,2519883.985206035],[9505160.640732285,2520242.283776121],[9505196.470589293,2520481.149489513],[9505191.693275025,2520703.294602967],[9505106.89594677,2521020.9860017765],[9505094.952661103,2521782.967627495],[9505097.341318237,2522281.002639916],[9504963.576518739,2522899.664837598],[9504956.410547337,2523205.412950739],[9504888.33381902,2523241.2428077483],[9504789.204547964,2523403.671492854],[9504411.796720803,2523397.6998500195],[9504316.250435447,2523378.5905929483],[9504141.87846467,2523397.6998500195],[9503977.06112243,2523403.671492854],[9503814.632437326,2523361.8699930105],[9503556.657466864,2523194.6639936366],[9503302.265482102,2523185.109365101],[9503061.011111578,2523230.4938506456],[9502627.469841773,2523157.639808061],[9502214.232157605,2522983.267837286],[9502196.317229101,2522976.101865884],[9501978.949429914,2522995.211122955],[9501949.09121574,2522912.8024518355],[9501967.006144246,2522859.057666322],[9501519.132931637,2522519.868353307],[9501480.914417494,2522507.925067637],[9501307.736775286,2522586.7507530563],[9501125.004504543,2522860.251994889],[9501076.037033295,2523203.0242936052],[9501079.620018996,2523289.015950426],[9500911.219691057,2523373.8132786797],[9500916.295587467,2523345.447975215]]]},\"properties\":null}]}";
			JSONObject json = new JSONObject(coord);
			
			JSONArray jsonArray = json.getJSONArray("features");
			
			json = jsonArray.getJSONObject(0);
			json = json.getJSONObject("geometry");
		//	System.out.println("jsonArray = "+json);
			//id, geofencename, coord, datatimestamp, loginid, flag
			String sqlselect4 = "select dblocator.insertprocedure('insertgeofence','0'," + " '" + geofencename + "', '" + json
					+ "',  '" + tt.toLocaleString() + "',  '" + loginid + "', '0', " + "'', '"
					+ "',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

		//	System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("{\"success\": true,\"error\": \"Invalid input\",\"error_code\": 200}")
				.build();
			} else {
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/poidetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response poidetails(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid) {
		List<poidet> data = new ArrayList<poidet>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
			String sqlselect4 = "select * from dblocator.selectprocedure('selectpoi', '',"
					+ " '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				int m=0;
				while (rs4.next()) {
				// shelterid, sheltercode, sheltername, latitude, longitude, loginid, datetimestamp
					try {
						poidet obj = new poidet();
						obj.setPoiid(rs4.getString(1));
						obj.setPoiname(rs4.getString(2));
						obj.setLatitude(rs4.getString(3));
						obj.setLongitude(rs4.getString(4));
						obj.setPoitype(rs4.getString(5));
						data.add(obj);
						m++;
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("poiinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response poiInsert(@QueryParam("poiname") String poiname, @QueryParam("loginid") String loginid,
			@QueryParam("latitude") String latitude,@QueryParam("longitude") String longitude,
			@QueryParam("poitype") String poitype) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			
			
			//routeid, routename, routetype, depotid, companyid, loginid, datetimestamp,remarks, flag, routecords
			String sqlselect4 = "select dblocator.insertprocedure('insertPoi','"+poiname+"'," + " '"+latitude+"', '"
					+ longitude + "',  '"+poitype+"',  '', '', '', '"
					+ "',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

			System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("{\"success\": true,\"error\": \"Invalid input\",\"error_code\": 200}")
				.build();
			} else {
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("poiupdate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response poiUpdate(@QueryParam("poiname") String poiname, @QueryParam("loginid") String loginid,
			@QueryParam("id") String id, @QueryParam("poitype") String poitype) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			
			
			//routeid, routename, routetype, depotid, companyid, loginid, datetimestamp,remarks, flag, routecords
			String sqlselect4 = "select dblocator.insertprocedure('updatePoi', '"+id+"', '"+poiname+"'," + " '"+poitype+"', '"
					+ "', '', '', '', '"
					+ "',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

			System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("{\"success\": true,\"error\": \"Invalid input\",\"error_code\": 200}")
				.build();
			} else {
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	@GET
	@Path("poidelete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response poiDelete(@QueryParam("loginid") String loginid, @QueryParam("id") String id) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			
			
			//routeid, routename, routetype, depotid, companyid, loginid, datetimestamp,remarks, flag, routecords
			String sqlselect4 = "select dblocator.insertprocedure('deletePoi', '"+id+"', ''," + " '', '"
					+ "', '', '', '', '"
					+ "',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

			System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("{\"success\": true,\"error\": \"Invalid input\",\"error_code\": 200}")
				.build();
			} else {
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
//	imagesave
	@POST
	@Path("/imagesave")
	@Produces(MediaType.APPLICATION_JSON)
	public Response routeDetails(byte[] imageInByte) {
		try{
			
//			String[] splitimg = imgdata.split(",");
//			System.out.println(splitimg[1]);
////			byte[] imageInByte;
//			byte[] imageInByte = imgdata.getBytes();
			// convert BufferedImage to byte array
			System.out.println(imageInByte);
			InputStream in = new ByteArrayInputStream(imageInByte);
			BufferedImage bImageFromConvert = ImageIO.read(in);
			ImageIO.write(bImageFromConvert, "png", new File(
					"/home/nrdadb/new-darksouls.png"));
		}catch(Exception e){
			System.out.println(e);
		}
		return Response.status(200).entity("").build(); 
	}
	
	@GET
	@Path("/routedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response routeDetails(@QueryParam("companyid") String companyid,
			@QueryParam("loginid") String loginid) {
		List<geofence_details> data = new ArrayList<geofence_details>();
//		List<parentcomp_details> list = getLoginId(companyid);
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehde ");

//			int k = 0, flag = 1;
//			while (k < list.size()) {

				// String sqlselect4="select vehicle_creation()";
				String sqlselect4 = "Select mg.routeid,mg.routename,ST_AsGeoJSON(mg.coord),mg.datatimestamp,"
						+ " mg.routetype From dblocator.msttblroute as mg where mg.flag=0";

				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
//				System.out.println(sqlselect4);
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
//				if (list.get(k).getLoginid().equals(loginid)) {
//					flag = 0;
//				}
				int m=0;
				while (rs4.next()) {
					
					// mg.id,mg.geofencename,mg.coord,mg.datatimestamp,mg.loginid
					try {
						
						geofence_details obj = new geofence_details();
						obj.setId(rs4.getString(1));
						obj.setGeofencename(rs4.getString(2));
						String fence = "{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"geometry\":"+rs4.getString(3)+",\"properties\":null}]}";
						//System.out.println("fence ="+fence);
						obj.setCoord(fence);
						obj.setDatatimestamp(rs4.getString(4));
//						obj.setLoginid(rs4.getString(5));
						//System.out.println(obj.getRcolor()+" "+obj.getRwidth()+" "+obj.getRdashcolor()+" "+obj.getRlabelcolor()+" "+obj.getRlabelfont());
//						if (flag == 1) {
//							obj.setFlag("false");
//						} else {
							obj.setFlag("true");
//						}
						data.add(obj);
						m++;
					} catch (Exception e) {
					//	System.out.println("e " + e);
					}
				}
//				k++;
//			}
			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@GET
	@Path("/inserttripdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response inserttripdetails(@QueryParam("vehicleno") String vehicleno, @QueryParam("etpno") String etpno,
			@QueryParam("routename") String routename, @QueryParam("source") String source,
			@QueryParam("destination") String destination, @QueryParam("starttime") String starttime,
			@QueryParam("endtime") String endtime, @QueryParam("column1") String column1,
			@QueryParam("column2") String column2, @QueryParam("column3") String column3)
			 {

		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		System.out.println("Connected in websservice vehicleinsert");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			// vehicleid, makeid, modelid, vehicletypeid, vehicleregno, regdate,
			// chasisnumber, enginenumber, bodycolor, fueltypeid, regvaliddate,
			// permitdate, permitvaliddate, insurancedate, insurancevaliddate,
			// pucdate, pucvaliddate, tankcapacity, loginid, datetimestamp,
			// remarks, flag
			
			
			String sqlselect4 = "INSERT INTO dblocator.msttbltripdetails(vehicleno, etpno, routename, "
					+ "source, destination, starttime,  endtime) "+
					" VALUES ('"+vehicleno+"', '"+etpno+"', '"+routename+"', '"+source+"', '"+destination+"', '"+starttime+"', '"+endtime+"');";
			System.out.println("vehinsert "+sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			int rs = ps.executeUpdate();
			if (rs==1) {
			return Response.status(200).entity("{\"success\": true}").build();
			} else {
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}").build();
			}
			
			//return Response.status(200).entity("ok").build();
		} catch (Exception e) {
		  System.out.println("vehicleinsert"+e);
		  System.out.println("catch"+e);
		  livedet det = new livedet();
			det.setExc(String.valueOf(e));
		  //System.out.println(e);
			return Response.status(404).entity(det)
					.build();
//			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
//					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
	@GET
	@Path("routeinsert")
	@Produces(MediaType.APPLICATION_JSON)
	public Response routeInsert(@QueryParam("routename") String routename, @QueryParam("routetype") String rtype,
			@QueryParam("coord") String coord, @QueryParam("loginid") String loginid) {
		Connection con = null;
		System.out.println(coord);
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
			
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			JSONObject json = new JSONObject(coord);
			JSONArray jsonArray = json.getJSONArray("features");
				
			json = jsonArray.getJSONObject(0);
			json = json.getJSONObject("geometry");	
			System.out.println("jsonArray = "+json);
			if(rtype.equals("UP")){
				rtype = "1";
			}else{
				rtype = "2";
			}
//			 INSERT INTO dblocator.msttblroute(routeid, routename, routetype, coord, loginid, companyid, flag, datatimestamp)
//			 VALUES (roid, $2, $3, (SELECT ST_AsText(ST_GeomFromGeoJSON($8)) As wkt), $5::numeric, $6::numeric, 0, current_timestamp );
//			width, routecolor, dashcolor, labelcolor, labelfont
			//routeid, routename, routetype, depotid, companyid, loginid, datetimestamp,remarks, flag, routecords
			String sqlselect4 = "select dblocator.insertprocedure('insertroute','"+routename+"'," + " '"+rtype+"', '"
					+ "1001',  '50001',  '30002', 'OK', '"+json+"', '"
					+ "',  " + " '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

			System.out.println("script=" + sqlselect4);
			PreparedStatement ps = con.prepareStatement(sqlselect4);
			boolean rs = ps.execute();
			if (rs) {
				return Response.status(200).entity("{\"success\": true,\"error\": \"Invalid input\",\"error_code\": 200}")
				.build();
			} else {
				return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
						.build();
			}

		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public String reticondir(int head){
	    //alert("head"+head);
	      String icon;
	      if(head>=345 && head<15){
	          icon="resources/assets/images/directionimage/0.png";
	      }
	      else if(head>=15 && head<45){
	          icon="resources/assets/images/directionimage/30.png";
	      }
	      else if(head>=45 && head<75){
	          icon="resources/assets/images/directionimage/60.png";
	      }
	      else if(head>=75 && head<150){
	          icon="resources/assets/images/directionimage/90.png";
	      }
	      else if(head>=105 && head<135){
	          icon="resources/assets/images/directionimage/120.png";
	      }
	      else if(head>=135 && head<165){
	          icon="resources/assets/images/directionimage/150.png";
	      }
	      else if(head>=165 && head<195){
	          icon="resources/assets/images/directionimage/180.png";
	      }
	      else if(head>=195 && head<225){
	          icon="resources/assets/images/directionimage/210.png";
	      }
	      else if(head>=225 && head<255){
	          icon="resources/assets/images/directionimage/240.png";
	      }
	      else if(head>=255 && head<285){
	          icon="resources/assets/images/directionimage/270.png";
	      }
	      else if(head>=285 && head<315){
	          icon="resources/assets/images/directionimage/300.png";
	      }
	      else if(head>=315 && head<345){
	          icon="resources/assets/images/directionimage/330.png";
	      }
	      else {
	          icon="resources/assets/images/directionimage/0.png";
	      }
	      return(icon);
	  }
	
	
	
	public String reticon(int head){
	    //alert("head"+head);
	      String icon;
	      if(head>=345 && head<15){
	          icon="0.png";
	      }
	      else if(head>=15 && head<45){
	          icon="30.png";
	      }
	      else if(head>=45 && head<75){
	          icon="60.png";
	      }
	      else if(head>=75 && head<150){
	          icon="90.png";
	      }
	      else if(head>=105 && head<135){
	          icon="120.png";
	      }
	      else if(head>=135 && head<165){
	          icon="150.png";
	      }
	      else if(head>=165 && head<195){
	          icon="180.png";
	      }
	      else if(head>=195 && head<225){
	          icon="210.png";
	      }
	      else if(head>=225 && head<255){
	          icon="240.png";
	      }
	      else if(head>=255 && head<285){
	          icon="270.png";
	      }
	      else if(head>=285 && head<315){
	          icon="300.png";
	      }
	      else if(head>=315 && head<345){
	          icon="330.png";
	      }
	      else {
	          icon="0.png";
	      }
	      return(icon);
	  }
	
	
	@GET
	@Path("/minedetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response minedetails() {
		Connection con = null;

		try {

			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		//	System.out.println("Connected in websservice vehicledelete");
			java.util.Date dd = new Date();
			Timestamp tt = new Timestamp(dd.getTime());
			
		    List<shelter_details> data= new ArrayList<shelter_details>();
		    int indexno=1;

			// networkid, networkname, networkapn, loginid, datetimestamp,
			// remarks,companyid

			// System.out.println("payment mode = "+payment_mode);
			String sqlselect4 = "select * from dblocator.selectprocedure('shelterdetails'," + " '', '', "
					+ " '',  '', " + "'', '', '',  " + "'', '', '', '', '', '',"
					+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
        
			////			if (list.get(k).getLoginid().equals(loginid)) {
//				flag = 0;
//			}
              while (rs4.next()) {
				
				// m.makeid, m.makename, a.assettypeid ,a.assetname,
				// m.vendorid,v.vendorfirmname,
				// m.loginid, m.datetimestamp, m.remarks,
				// m.flag,c.Companyname,m.maketype,m.companyid
				shelter_details obj = new shelter_details();
				obj.setShelterid(rs4.getString(1));
				//System.out.println(rs4.getString(1));
				obj.setSheltername(rs4.getString(2));
				//System.out.println(rs4.getString(2));
				obj.setSheltercode(rs4.getString(3));
				//System.out.println(rs4.getString(3));
				obj.setRowno(indexno);
				indexno++;
				
				
				
				// obj.setFlag(rs4.getString(9+1));
//				if (flag == 1) {
//					obj.setFlag("false");
//				} else {
				
//				}
				// obj.setCompanyname(rs4.getString(10+1));
				// obj.setMaketype(rs4.getString(11+1));
				// obj.setCompanyid(rs4.getString(12+1));
				data.add(obj);
				
				 System.out.println("ShelterDetails"+data);
				
			}
//			k++;
//		}
		return Response.status(200).entity(data).build();
	} catch (Exception e) {
	  System.out.println(e);
		return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
				.build();
	} finally {
		try {
			con.close(); // System.out.println("connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

	
	
	@GET
	@Path("/lustatusReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response lustatusReport(@QueryParam("fromdate")String fromdate,@QueryParam("todate")String todate) {
		List<lustatus_details> data = new ArrayList<lustatus_details>();
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
 System.out.println("Connected in websservice lustatus report ");
			
//				String sqlselect4 = "select * from dblocator.selectprocedure('selectLUReport', '"
//						+ fromdate + "', '"+todate+"', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
//						+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
 				String sqlselect4 = "select * from dblocator.getLUReport('selectLUReport', '"+fromdate+"', '"+todate+"')";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
				Statement st4 = con.createStatement();
				ResultSet rs4 = st4.executeQuery(sqlselect4);
				 //System.out.println("in overlint"+overspeedlimit);
				boolean avail=false;
				int index = 1;
		while (rs4.next()) {
					try {
						lustatus_details  obj=new lustatus_details ();
						obj.setVehicleregno(rs4.getString(1));
						obj.setTotallu(rs4.getString(2));
						obj.setTotallivelu(rs4.getString(3));
						obj.setTotalhistorylu(rs4.getString(4));
						obj.setLuonprimary(rs4.getString(5));
						obj.setLuonsecondary(rs4.getString(6));
						obj.setImeino(rs4.getString(7));
						obj.setDate(rs4.getString(8));
						obj.setSrno(index++);
						data.add(obj);
					} catch (Exception e) {
						System.out.println("e = " + e);
					}
		}
			return Response.status(200).entity(data).build();
//			return Response.status(200).entity("{\"success\": true,\"error_code\": 200}").build();
			
		
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	

	@GET
	@Path("/circlewisetrip")
	@Produces(MediaType.APPLICATION_JSON)
	public Response circletripdetails() {
		List<circlewisetrip> data = new ArrayList<circlewisetrip>();
//		System.out.println("circeltripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			/*String sqlselect4 = "select * from dblocator.selectprocedure('circlewisetrip',"
					+ " '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
		 String sqlselect3=" select circle, count(*) from dblocator.msttbltripdetails "
							+ " where starttime::date=current_date group by circle; " ; 
		 Statement st4 = con.createStatement();
				 
		 ResultSet rs3=st4.executeQuery(sqlselect3);
		
		while (rs3.next()) {
			circlewisetrip obj = new circlewisetrip();
				obj.setCount(rs3.getInt(2));
				obj.setCirclename(rs3.getString(1));
//				System.out.println(obj.getCount());
//				System.out.println(obj.getDistrict_name());
				data.add(obj);
			}
		
		String sqlselect4="select circlename,0 from dblocator.msttblcircle where circlename "
							+ " not in ( select circle from dblocator.msttbltripdetails "
							+ " where starttime::date=current_date group by circle ); "; 
		                   
			
			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			ResultSet rs4 = st4.executeQuery(sqlselect4);
			
			while (rs4.next()) {
				circlewisetrip obj = new circlewisetrip();
					obj.setCount(rs4.getInt(2));
					obj.setCirclename(rs4.getString(1));
//					System.out.println(obj.getCount());
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/circlewisetripdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response circlewisetripdetails(@QueryParam("circlename") String circlename) {
		List<circlewisetripdetails> data = new ArrayList<circlewisetripdetails>();
		System.out.println("circlewisetripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			/*String sqlselect4 = "select * from dblocator.selectprocedure('circlewisetripdetails',"
					+ " '"+circlename+"', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			String sqlselect4=" select vehicleno,routename,source,destination,starttime,endtime,minename,quantity,"
					+ "passvalid,tripid,latitude,transportername,etpno,code,circle,datetimestamp  "
					+ "from dblocator.msttbltripdetails where circle like '%'||'"+ circlename +"'::text||'%' and "
							+ "starttime::date=current_date; ";
			
			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//vehicleno,routename,source,destination,starttime,endtime,minename,quantity,passvalid,tripid,latitude,transportername,etpno,code,circle,datetimestamp;
			int index = 1;
			while (rs4.next()) {
				circlewisetripdetails obj = new circlewisetripdetails();
				
					obj.setVehicleno(rs4.getString(1));
					obj.setRoutename(rs4.getString(2));
					obj.setSource(rs4.getString(3));
					obj.setDestination(rs4.getString(4));
					obj.setStarttime(rs4.getString(5));
					obj.setEndtime(rs4.getString(6));
					obj.setMinename(rs4.getString(7));
					obj.setQuantity(rs4.getString(8));
					obj.setPassvalid(rs4.getString(9));
					obj.setTripid(rs4.getString(10));
					obj.setLatitude(rs4.getString(11));
					obj.setTransportername(rs4.getString(12));
					obj.setEtpno(rs4.getString(13));
					obj.setCode(rs4.getString(14));
					obj.setCircle(rs4.getString(15));
					obj.setDatetimestamp(rs4.getString(16));
					obj.setRowno(index++);
//					System.out.println(obj.getCount());
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/vehiclewisetripdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response vehiclewisetripdetails(@QueryParam("circlename") String circlename) {
		List<circlewisetripdetails> data = new ArrayList<circlewisetripdetails>();
		System.out.println("circlewisetripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			String sqlselect4 = "select source, count(*)  "
					+ "from dblocator.msttbltripdetails where circle like '" + circlename + "' and "
							+ " starttime::date=current_date group by source;";
			
//			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			int index = 1;
			while (rs4.next()) {
				circlewisetripdetails obj = new circlewisetripdetails();
				
					obj.setVehicleno(rs4.getString(1));
					obj.setRowno(Integer.parseInt(rs4.getString(2)));
					if(index%2==0)
						obj.setCode("#008ee4");
					else
						obj.setCode("#6baa01");
//					System.out.println(obj.getCount());#e44a00
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
					index++;
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/mineralwisetripdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response mineralwisetripdetails(@QueryParam("circlename") String circlename) {
		List<circlewisetripdetails> data = new ArrayList<circlewisetripdetails>();
		System.out.println("circlewisetripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			String sqlselect4 = "select minename, sum(quantity::double precision) "
					+ " from dblocator.msttbltripdetails where circle like '%"+circlename+"%' and  "
							+ " starttime::date=current_date group by minename;";
			
//			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			int index = 1;
			while (rs4.next()) {
				circlewisetripdetails obj = new circlewisetripdetails();
				
					obj.setMinename(rs4.getString(1));
					obj.setTonnage(Double.parseDouble(rs4.getString(2)));
//					System.out.println(obj.getCount());#e44a00
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
					index++;
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	@GET
	@Path("/circledetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response circledetails() {
		List<circledetails> data = new ArrayList<circledetails>();
		System.out.println("circlewisetripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		
			String sqlselect4 = "select circleid, circlename from dblocator.msttblcircle where flag = 0";
			
//			System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);

			int index = 1;
			while (rs4.next()) {
					circledetails obj = new circledetails();
						obj.setCirclecode(rs4.getString(1));
						obj.setCirclename(rs4.getString(2));
					data.add(obj);
					index++;
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@GET
	@Path("/companywisetripdetails")
	@Produces(MediaType.APPLICATION_JSON)
	public Response companywisetripdetails(@QueryParam("companyname") String companyname) {
		List<circlewisetripdetails> data = new ArrayList<circlewisetripdetails>();
		System.out.println("circlewisetripdetails");
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa","postgres","rsrtc@2017");
		/*
			String sqlselect4 = "select * from dblocator.selectprocedure('companywisetripdetails',"
					+ " '"+companyname+"', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', "
					+ "'', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";*/
			
			 String sqlselect4=" select vehicleno,routename,source,destination,starttime,endtime,minename,quantity,passvalid,tripid,latitude, "
			 		+ " transportername,etpno,code,mt.circle,mt.datetimestamp "
			 		+ "from dblocator.msttbltripdetails as mt "
			 		+ "inner join dblocator.msttblvehicle as mv on mv.vehicleregno = mt.vehicleno "
			 		+ "inner join dblocator.msttblvehicleassigngps as veh on veh.vehicleid = mv.vehicleid "
			 		+ "inner join dblocator.msttbldevice as md on md.deviceid = veh.deviceid "
			 		+ "inner join dblocator.msttbluserlogin as ml on ml.loginid = md.loginid "
			 		+ "inner join dblocator.msttblcompany as mc on mc.companyid = ml.ownersid "
			 		+ " where mc.companyname = '"+companyname+"'::text and mt.starttime::date=current_date; ";
			
			//System.out.println("role "+sqlselect4);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			Statement st4 = con.createStatement();
			ResultSet rs4 = st4.executeQuery(sqlselect4);
//vehicleno,routename,source,destination,starttime,endtime,minename,quantity,passvalid,tripid,latitude,transportername,etpno,code,circle,datetimestamp;
	int ind = 1;
			while (rs4.next()) {
				circlewisetripdetails obj = new circlewisetripdetails();
					obj.setVehicleno(rs4.getString(1));
					obj.setRoutename(rs4.getString(2));
					obj.setSource(rs4.getString(3));
					obj.setDestination(rs4.getString(4));
					obj.setStarttime(rs4.getString(5));
					obj.setEndtime(rs4.getString(6));
					obj.setMinename(rs4.getString(7));
					obj.setQuantity(rs4.getString(8));
					obj.setPassvalid(rs4.getString(9));
					obj.setTripid(rs4.getString(10));
					obj.setLatitude(rs4.getString(11));
					obj.setTransportername(rs4.getString(12));
					obj.setEtpno(rs4.getString(13));
					obj.setCode(rs4.getString(14));
					obj.setCircle(rs4.getString(15));
					obj.setDatetimestamp(rs4.getString(16));
					obj.setRowno(ind++);
//					System.out.println(obj.getCount());
//					System.out.println(obj.getDistrict_name());
					data.add(obj);
				}
	

			return Response.status(200).entity(data).build();
		} catch (Exception e) {
		  System.out.println(e);
			return Response.status(404).entity("{\"success\": false,\"error\": \"Invalid input\",\"error_code\": 404}")
					.build();
		} finally {
			try {
				con.close(); // System.out.println("connection closed");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	

	
	
	// ---------------------------------InsertUpdate----------------------------------------

}
