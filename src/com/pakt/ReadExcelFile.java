package com.pakt;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.pakt.pojos.ExcelPojo;
import com.pakt.pojos.device_details;

public class ReadExcelFile {
	
//	public static void main(String args[]){
//		ReadExcelFile.read("Upload.xlsx", "10009", "10007", "110006", "66006");
//	}
	
	 public static void read(String name, String loginid, String customerid, String makeid, String modelid) 
	    {
	        try
	        {
	            FileInputStream file = new FileInputStream(new File(name));
	            
	            //Create Workbook instance holding reference to .xlsx file
	            org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
	            
	            //Get first/desired sheet from the workbook
	            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            List<ExcelPojo> listpojo = new ArrayList<ExcelPojo>();
	            while (rowIterator.hasNext()) 
	            {
	            	ExcelPojo obj=new ExcelPojo();
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Iterator<Cell> cellIterator = row.cellIterator();
	                 int k=0;
	                while (cellIterator.hasNext()) 
	                {
	                	
	                    Cell cell = cellIterator.next();
	                    //Check the cell type and format accordingly
	                 
	                    switch (cell.getCellType()) 
	                    {
	                        case Cell.CELL_TYPE_NUMERIC:
	                        	if(k==0)
	                            {
	                            	String did = String.valueOf(cell.getNumericCellValue());
	                            	did = did.replace(".0", "");
		                            obj.setSno(did);
		                            System.out.print("0  "+obj.getSno()+ "\t");
	                            }
	                        	if(k==1)
	                            {	 
	                        		String did = String.valueOf(cell.getNumericCellValue());
	                            	did = did.replace("N", "");
	                            	did = did.replace(".0", "");
	                            	Double dd = Double.parseDouble(did);
	                            	obj.setDeviceid(String.valueOf(dd.intValue()));
	                            	System.out.print("1"+did+ "\t");
	                            }
	                        	if(k==2)
	                            {
	                            	String cellv = String.valueOf(cell.getNumericCellValue());
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E14", "");
	                            	if(cellv.length()<15){
	                            		int l = 15 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setImeino(cellv);
	                            	System.out.print("2  "+obj.getImeino()+ "\t");
	                            }
	                        	if(k==6)
	                            {
	                        		String cellv = String.valueOf(cell.getNumericCellValue());
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E9", "");
	                            	if(cellv.length()<10){
	                            		int l = 10 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setMobno(cellv);
	                            	System.out.print("3  "+obj.getMobno()+ "\t");
	                            }
	                        	if(k==7)
	                            {
	                        		String cellv = String.valueOf(cell.getNumericCellValue());
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E9", "");
	                            	if(cellv.length()<10){
	                            		int l = 10 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setMobno2(cellv);
	                            	System.out.print("3  "+obj.getMobno2()+ "\t");
	                            }
	                        	if(k==8)
	                            {
	                        		String cellv = String.valueOf(cell.getNumericCellValue());
	                        		cellv = cellv.replace("U", "");
	                            	obj.setSimno(cellv);
	                            	System.out.print("4  "+obj.getSimno()+ "\t");
	                            }
	                            if(k==3)
		                         {
	                            	obj.setVehicleno(String.valueOf(cell.getNumericCellValue()));
	                            	System.out.print("5  "+obj.getVehicleno()+ "\n");
		                         }
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                        	if(k==0)
	                            {
	                            	String did = String.valueOf(cell.getStringCellValue());
	                            	did = did.replace(".0", "");
		                            obj.setSno(did);
		                            System.out.print("0  "+obj.getSno()+ "\t");
	                            }
	                        	if(k==1)
	                            {	 
	                        		String did = cell.getStringCellValue();
	                            	did = did.replace("N", "");
	                            	obj.setDeviceid(did);
	                            	System.out.print("1  "+did+ "\t");
	                            }
	                        	if(k==2)
	                            {
	                            	String cellv = cell.getStringCellValue();
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E14", "");
	                            	if(cellv.length()<15){
	                            		int l = 15 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setImeino(cellv);
	                            	System.out.print("2  "+obj.getImeino()+ "\t");
	                            }
	                        	if(k==6)
	                            {
	                        		String cellv = cell.getStringCellValue();
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E9", "");
	                            	if(cellv.length()<10){
	                            		int l = 10 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setMobno(cellv);
	                            	System.out.print("3  "+obj.getMobno()+ "\t");
	                            }
	                        	if(k==7)
	                            {
	                        		String cellv = cell.getStringCellValue();
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E9", "");
	                            	if(cellv.length()<10){
	                            		int l = 10 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setMobno2(cellv);
	                            	System.out.print("3  "+obj.getMobno2()+ "\t");
	                            }
	                        	if(k==8)
	                            {
	                        		String val = cell.getStringCellValue();
	                        		val = val.replace("U", "");
	                            	obj.setSimno(val);
	                            	System.out.print("4  "+obj.getSimno()+ "\t");
	                            }
	                            if(k==3)
		                         {
	                            	obj.setVehicleno(cell.getStringCellValue());
	                            	System.out.print("5  "+obj.getVehicleno()+ "\n");
		                         }
	                            if(k==4)
		                         {
	                            	obj.setChasis(cell.getStringCellValue());
	                            	System.out.print("6  "+obj.getChasis()+ "\n");
		                         }
	                            if(k==5)
		                         {
	                            	obj.setEngine(cell.getStringCellValue());
	                            	System.out.print("7  "+obj.getEngine()+ "\n");
		                         }
	                            break;
	                  
	                    }
	                    k++;
	                    
	                }
	                listpojo.add(obj);
	               // System.out.println("length = "+listpojo.size());
	            }
	            file.close();
	            deviceiddetails(listpojo, loginid, customerid, makeid, modelid);
//	            System.out.println("length = "+listpojo.size());
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	 
	 
//	 select * from dblocator.insertprocedure('insertVehicle', '0', '110013', '66015', '50006', 'TN20G1275', '03-Nov-2016', 'TN20G1275', 'TN20G1275', 'White',  '170001', '03-Nov-2016', '03-Nov-2016', '03-Nov-2016','80', '50003', '3 Nov, 2016 2:24:46 PM', 'ok', '0','HDFC', 'undefined', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');
	 public static void deviceiddetails(List<ExcelPojo> listpojo,  String loginid, String customerid, String makeid, String modelid) {
			Connection con = null;
//			long id = 110101;
			try {
//				int deviceid = 2000;
//				System.out.println("jdbc:postgresql://localhost:5472/dbOrissa");
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbOrissa", "postgres", "rsrtc@2017");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());
				for(int i=1;i<listpojo.size();i++){
					ExcelPojo obj= listpojo.get(i);
//					  
					
					
					try{
						if(obj.getDeviceid()!="0" && obj.getImeino()!=null){
							String sqlselect4 = "select * from dblocator.insertprocedure('insertBCUDevice'," + " '0', '"+makeid+"', "
									+ "'"+modelid+"', '" + obj.getImeino() + "', '" + loginid + "', '" + tt.toLocaleString() + "', " + "'"
									+ "OK', '0', '"+ obj.getDeviceid() +"',  " + "'', '', '', '',"
									+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	//						String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
							System.out.println(sqlselect4);
							PreparedStatement ps = con.prepareStatement(sqlselect4);
							boolean rs = ps.execute();
							if (rs) {
								System.out.println("executed");
							} else {
								System.out.println("Not executed");
							}
						}
					}catch(Exception e){
						System.out.println(e);
					}
					
					
					try{
						if(obj.getMobno()!=null && obj.getSimno()!=null){
							String sqlselect4 = "select * from dblocator.insertprocedure('insertsim'," + " '0', '1000001', "
									+ "'40002', '" + obj.getSimno() + "', '" + obj.getMobno() + "', '5002', " + "'"
									+ "22-Dec-2016', '" + loginid + "', '" + tt.toLocaleString() + "',  " + "'OK"
									+ "', '0', '" + obj.getMobno2() + "', '',"
									+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	//						String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
							System.out.println(sqlselect4);
							PreparedStatement ps = con.prepareStatement(sqlselect4);
							boolean rs = ps.execute();
							if (rs) {
								System.out.println("executed");
							} else {
								System.out.println("Not executed");
							}
						}
					}catch(Exception e){
						System.out.println(e);
					}
					
					
					try{
						
						String simid = null;
						
						String sqlselect4 = "select simid from dblocator.msttblsim where mobilenumber = '"+obj.getMobno()+"'";
//					    List<String> ll = new ArrayList<String>();
//						String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
						System.out.println(sqlselect4);
						Statement st4 = con.createStatement();
						ResultSet rs4 = st4.executeQuery(sqlselect4);
						while(rs4.next()){
							simid = rs4.getString(1);
							System.out.println(simid);
						}
						 sqlselect4 = "select * from dblocator.insertprocedure('insertsimassign'," + " '0', '" + obj.getDeviceid() + "', "
								+ "'" + simid + "', '" + loginid + "', '" + tt.toLocaleString() + "', 'Assigned', " + "'"
								+ "0', '" + loginid + "', '" + tt.toLocaleString() + "',  " + "'OK"
								+ "', '0', '', '',"
								+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//						if(obj.getMobno()!=null && obj.getSimno()!=null){
//							 sqlselect4 = "select * from insertprocedure('insertsimassign', '0', '" + obj.getDeviceid() + "', "
//									+ "'" + simid + "', '" + loginid + "', '" + tt.toLocaleString() + "', 'Assigned', "
//									+ "'0', '', '',  " + "'', '', '', '',"
//									+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
	//						String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
							System.out.println(sqlselect4);
							PreparedStatement ps = con.prepareStatement(sqlselect4);
							boolean rs = ps.execute();
							if (rs) {
								System.out.println("executed");
							} else {
								System.out.println("Not executed");
							}
//						}
					}catch(Exception e){
						System.out.println(e);
					}
					
					
					try{
						if(obj.getVehicleno()!=null){
							String cloginid = null;
	
							String sqlselect4 = "select loginid from dblocator.msttbluserlogin where ownersid = '"+customerid+"'";
	//					    List<String> ll = new ArrayList<String>();
	//						String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
							System.out.println(sqlselect4);
							Statement st4 = con.createStatement();
							ResultSet rs4 = st4.executeQuery(sqlselect4);
							while(rs4.next()){
								cloginid = rs4.getString(1);
								System.out.println(cloginid);
							}
							
							
							String deviceid = null;
							try{
								
								 sqlselect4 = "select deviceid from dblocator.msttbldevice where uniqueid = '"+obj.getImeino()+"'";
			//				    List<String> ll = new ArrayList<String>();
			//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
								System.out.println(sqlselect4);
								 st4 = con.createStatement();
								 rs4 = st4.executeQuery(sqlselect4);
								while(rs4.next()){
									deviceid = rs4.getString(1);
									System.out.println(deviceid);
								}
							}catch(Exception e){
								
							}
							
							try{
								 sqlselect4 = "select * from dblocator.insertprocedure('insertVehicle', '0', '110003', '66003', '50006', "
										+ "'"+((obj.getVehicleno().toUpperCase()).replace("-", "")).replace(" ", "")+"', '03-Nov-2016', '"+obj.getChasis()+"', '"+obj.getEngine()+"', 'White',  '170001', '22-Dec-2016', '22-Dec-2016', "
										+ "'22-Dec-2016','80', '"+cloginid+"', '22 Dec, 2016 2:24:46 PM', 'ok', '0','HDFC', '"+customerid+"', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
			//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
								System.out.println(sqlselect4);
								PreparedStatement ps = con.prepareStatement(sqlselect4);
								boolean rs = ps.execute();
								if (rs) {
									System.out.println("executed");
								} else {
									System.out.println("Not executed");
								}
							}catch(Exception e){
								System.out.println(e);
							}
							
							String dealerid = "";
							try{
								 sqlselect4 = "SELECT md.dealerid "+
									  " FROM  dblocator.msttblcustomer as mco "+
									  " left join dblocator.msttbluserlogin as mcp on mcp.loginid = mco.loginid "+
									  " left join dblocator.msttbldealer as md on md.dealerid = mcp.ownersid "+
									  " where mco.customerid = '"+customerid+"'";
									st4 = con.createStatement();
									rs4 = st4.executeQuery(sqlselect4);
									while(rs4.next()){
										dealerid = rs4.getString(1);
										System.out.println(dealerid);
									}
								
									sqlselect4 = "select * from dblocator.saveimei('{"+deviceid+"}', '"+dealerid+"', '0', '"+tt.toLocaleString()+"', '"+loginid+"')";
									System.out.println("sqlselect = "+sqlselect4);
									PreparedStatement ps = con.prepareStatement(sqlselect4);
							  
									boolean rs = ps.execute();
							}catch(Exception e){
								System.out.println(e);
							}
							
							
							try{
								sqlselect4 = "select * from dblocator.salecustomer('{"+deviceid+"}', '"+customerid+"', '0', '"+tt.toLocaleString()+"', '"+loginid+"')";
								System.out.println("sqlselect = "+sqlselect4);
								PreparedStatement ps = con.prepareStatement(sqlselect4);
							  
								boolean rs = ps.execute();
								 
							}catch(Exception e){
								
							}
						}
					}catch(Exception e){
						System.out.println(e);
					}
					
					
					
					
					try{
						if(obj.getVehicleno()!=null && obj.getDeviceid()!="0"){
							String vehicleid = null;
		
							String sqlselect4 = "select vehicleid from dblocator.msttblvehicle where vehicleregno = '"+obj.getVehicleno()+"'";
		//				    List<String> ll = new ArrayList<String>();
		//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
							Statement st4 = con.createStatement();
							ResultSet rs4 = st4.executeQuery(sqlselect4);
							while(rs4.next()){
								vehicleid = rs4.getString(1);
								System.out.println(vehicleid);
							}
							
							String deviceid = null;
							sqlselect4 = "select deviceid from dblocator.msttbldevice where uniqueid = '"+obj.getImeino()+"'";
		//				    List<String> ll = new ArrayList<String>();
		//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
							st4 = con.createStatement();
							rs4 = st4.executeQuery(sqlselect4);
							while(rs4.next()){
								deviceid = rs4.getString(1);
								System.out.println(deviceid);
							}
							
							String cloginid = null;
		
							sqlselect4 = "select loginid from dblocator.msttbluserlogin where ownersid = '"+customerid+"'";
		//				    List<String> ll = new ArrayList<String>();
		//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
							st4 = con.createStatement();
						    rs4 = st4.executeQuery(sqlselect4);
							while(rs4.next()){
								cloginid = rs4.getString(1);
								System.out.println(cloginid);
							}
							
							
							
							sqlselect4 = "select * from dblocator.insertprocedure('insertVehicleToDevice', "
									+ "'0', '"+vehicleid+"',  '"+deviceid+"',  '1', '22-Dec-2016', '"+cloginid+"', 'ok',  "
									+ "'22 Dec, 2016 5:48:41 PM', '"+customerid+"', '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
		//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
							System.out.println(sqlselect4);
							PreparedStatement ps = con.prepareStatement(sqlselect4);
							boolean rs = ps.execute();
							if (rs) {
								System.out.println("executed");
							} else {
								System.out.println("Not executed");
							}
					
					}
//					sqlselect4 = "select * from insertprocedure('insertsimassign', '0', '" + obj.getDeviceid() + "', "
//							+ "'" + simid + "', '" + loginid + "', '" + tt.toLocaleString() + "', 'Assigned', "
//							+ "'0', '', '',  " + "'', '', '', '',"
//							+ "'', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";

					}catch(Exception e){
						System.out.println(e);
					}
//					}
			}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					con.close(); // System.out.println("connection closed");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	 

}