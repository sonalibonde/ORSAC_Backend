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

public class Atlantexcel {
	 public static void main(String[] args) 
	    {
		 String loginid=null, customerid=null, makeid=null, modelid=null;
	        try
	        {
	            FileInputStream file = new FileInputStream(new File("Upload 24-02-2017_49.xlsx"));
	            
	            //Create Workbook instance holding reference to .xlsx file
	            org.apache.poi.ss.usermodel.Workbook workbook = WorkbookFactory.create(file);
	            
	            //Get first/desired sheet from the workbook
	            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
	 
	            //Iterate through each rows one by one
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            List<ExcelPojo> listpojo=new ArrayList<ExcelPojo>();
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
//	                        	if(k==1){
//	                        		String cellv = String.valueOf(cell.getNumericCellValue());
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E9", "");
//	                            	if(cellv.length()<10){
//	                            		int l = 10 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setMobno(cellv);
//	                            	System.out.print("Mobileno  "+obj.getMobno()+ "\n");
//	                        	 }
//	                        	if(k==1)
//	                            {	 
//	                        		String did = String.valueOf(cell.getNumericCellValue());
//	                            	did = did.replace("N", "");
//	                            	Double dd = Double.parseDouble(did);
//	                            	obj.setDeviceid(String.valueOf(dd.intValue()));
//	                            	System.out.print("setDeviceid  "+obj.getDeviceid()+ "\t");
//	                            }
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
	                            	System.out.print("setImeino  "+obj.getImeino()+ "\t");
	                            }
	                        	 if(k==3){
	                        		 String cellv = String.valueOf(cell.getNumericCellValue());
		                            	cellv = cellv.replace(".", "");
		                            	cellv = cellv.replace("E9", "");
		                            	if(cellv.length()<10){
		                            		int l = 10 - cellv.length();
		                            		for(int o = 0; o<l ;o++){
		                            			cellv = cellv + "0";
		                            		}
		                            	}
	                        		 obj.setMobno(String.valueOf(cellv));
	                        		 System.out.print("setmobno  "+obj.getMobno()+ "\t");
	                        	 }
//	                        	if(k==7)
//	                            {
//	                        		String cellv = String.valueOf(cell.getNumericCellValue());
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E9", "");
//	                            	if(cellv.length()<10){
//	                            		int l = 10 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setMobile1(cellv);
//	                            	System.out.print("setMobile1  "+obj.getMobile1()+ "\t");
//	                            }
//	                        	if(k==8)
//	                            {
//	                        		String cellv = String.valueOf(cell.getNumericCellValue());
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E9", "");
//	                            	if(cellv.length()<10){
//	                            		int l = 10 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setMobile2(cellv);
//	                            	System.out.print("setMobile2  "+obj.getMobile2()+ "\n");
//	                            }
	                            break;
	                        case Cell.CELL_TYPE_STRING:
	                        	
//	                        	 if(k==5)
//		                         {
//	                            	obj.setCustomer(cell.getStringCellValue());
//	                            	System.out.print("setCustomer  "+obj.getCustomer()+ "\t");
//		                         }
	                        	 if(k==4){
	                        		 obj.setSimno(cell.getStringCellValue());
	                        		 System.out.print("setmobno  "+obj.getSimno()+ "\n");
	                        	 }
//	                        	
//	                        	if(k==4)
//	                            {
//	                            	String cellv = cell.getStringCellValue();
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E14", "");
//	                            	if(cellv.length()<15){
//	                            		int l = 15 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setImeino(cellv);
//	                            	System.out.print("setImeino  "+obj.getImeino()+ "\n");
//	                            }

//	                            if(k==3)
//		                         {
//	                            	obj.setVehicleno(cell.getStringCellValue());
//	                            	System.out.print("setVehicleno  "+obj.getVehicleno()+ "\t");
//		                         }
//	                            if(k==1)
//	                            {	 
//	                        		String did = String.valueOf(cell.getStringCellValue());
////	                            	did = did.replace("N", "");
////	                            	Double dd = Double.parseDouble(did);
//	                            	obj.setDeviceid(did);
//	                            	System.out.print("setDeviceid  "+obj.getDeviceid()+ "\t");
//	                            }
	                        	if(k==2)
	                            {
	                            	String cellv = String.valueOf(cell.getStringCellValue());
	                            	cellv = cellv.replace(".", "");
	                            	cellv = cellv.replace("E14", "");
	                            	if(cellv.length()<15){
	                            		int l = 15 - cellv.length();
	                            		for(int o = 0; o<l ;o++){
	                            			cellv = cellv + "0";
	                            		}
	                            	}
	                            	obj.setImeino(cellv);
	                            	System.out.print("setImeino  "+obj.getImeino()+ "\t");
	                            }
//	                        	if(k==7)
//	                            {
//	                        		String cellv = String.valueOf(cell.getStringCellValue());
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E9", "");
//	                            	if(cellv.length()<10){
//	                            		int l = 10 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setMobile1(cellv);
//	                            	System.out.print("setMobile1  "+obj.getMobile1()+ "\t");
//	                            }
//	                        	if(k==8)
//	                            {
//	                        		String cellv = String.valueOf(cell.getStringCellValue());
//	                            	cellv = cellv.replace(".", "");
//	                            	cellv = cellv.replace("E9", "");
//	                            	if(cellv.length()<10){
//	                            		int l = 10 - cellv.length();
//	                            		for(int o = 0; o<l ;o++){
//	                            			cellv = cellv + "0";
//	                            		}
//	                            	}
//	                            	obj.setMobile2(cellv);
//	                            	System.out.print("setMobile2  "+obj.getMobile2()+ "\n");
//	                            }
	                            break;
	                  
	                    }
	                    k++;
	                    
	                }
	                listpojo.add(obj);
	            }
	            file.close();
	            makeid = "110014";
	            modelid = "66010";
	            loginid = "10012";
	            deviceiddetails(listpojo, loginid, "610014", makeid, modelid);
//	            System.out.println("length = "+listpojo.size());
	        } 
	        catch (Exception e) 
	        {
	            e.printStackTrace();
	        }
	    }
	 
	 
//	 select * from dblocator.insertprocedure('insertVehicle', '0', '110013', '66015', '50006', 'TN20G1275', '03-Nov-2016', 'TN20G1275', 'TN20G1275', 'White',  '170001', '03-Nov-2016', '03-Nov-2016', '03-Nov-2016','80', '50003', '3 Nov, 2016 2:24:46 PM', 'ok', '0','HDFC', 'undefined', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');
	 public static void deviceiddetails(List<ExcelPojo> listpojo,  String loginid, String dealerid, String makeid, String modelid) {
			Connection con = null;
//			long id = 110101;
			try {
				int deviceid = 83954;
				
				Class.forName("org.postgresql.Driver");
				con = DriverManager.getConnection("jdbc:postgresql://209.190.15.18:5432/dbOrissa", "postgres", "takshak11");
				java.util.Date dd = new Date();
				Timestamp tt = new Timestamp(dd.getTime());
				for(int i=1;i<listpojo.size();i++){
					ExcelPojo obj= listpojo.get(i);
//					if(obj.getCustomer()!=null){  
//					if(obj.getMobno()==null){
//						obj.setMobno("0");
//					}
//					try{
//						String customer_id = null;
//						
//						String sqlselect4 = "select customerid from dblocator.msttblcustomer where customername = '"+obj.getCustomer()+"'";
//	//				    List<String> ll = new ArrayList<String>();
//	//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//						Statement st4 = con.createStatement();
//						ResultSet rs4 = st4.executeQuery(sqlselect4);
//						if(!rs4.next()){
////							customer_id = rs4.getString(1);
////							System.out.println(customer_id);
//
//						sqlselect4 = "select * from dblocator.insertprocedure('insertCustomer', '0', '"+obj.getCustomer()+"', 'Individual', '"+obj.getMobno()+"', '"+obj.getCustomer()+"@gmail.com', '"+obj.getMobno()+"', '"+obj.getMobno()+"', 'Orissa', 'Bhuwneshwar', '880002', 'No', '', '', 'By Cash', '', '', '', '', '', '"+loginid+"', '17 Feb, 2017 7:27:03 PM', '', '0', '"+dealerid+"', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '','')";
//	//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
//						System.out.println(sqlselect4);
//						PreparedStatement ps = con.prepareStatement(sqlselect4);
//						boolean rs = ps.execute();
//						if (rs) {
//							System.out.println("executed");
//						} else {
//							System.out.println("Not executed");
//						}
//						 sqlselect4 = "select customerid from dblocator.msttblcustomer where customername = '"+obj.getCustomer()+"'";
//						//				    List<String> ll = new ArrayList<String>();
//						//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//											 st4 = con.createStatement();
//											 rs4 = st4.executeQuery(sqlselect4);
//											while(rs4.next()){
//												customer_id = rs4.getString(1);
//												System.out.println(customer_id);
//											}
//						sqlselect4 = "select * from dblocator.insertprocedure('insertUser', '0', '"+obj.getCustomer().replace(" ", "")+"', '"+obj.getCustomer().replace(" ", "")+"987', '1003', '17 Feb, 2017 7:27:08 PM', '0', '"+dealerid+"','"+customer_id+"', '30008',  '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//						//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
//											System.out.println(sqlselect4);
//											 ps = con.prepareStatement(sqlselect4);
//											 rs = ps.execute();
//											if (rs) {
//												System.out.println("executed");
//											} else {
//												System.out.println("Not executed");
//											}
//						}				
//					}catch(Exception e){
//						System.out.println(e);
//					}
					
					try{
//						if(obj.getDeviceid()!="0" && obj.getImeino()!=null){
							String sqlselect4 = "select * from dblocator.insertprocedure('insertBCUDevice'," + " '0', '"+makeid+"', "
									+ "'"+modelid+"', '" + obj.getImeino() + "', '" + loginid + "', '" + tt.toLocaleString() + "', " + "'"
									+ "OK', '0', '"+ deviceid +"',  " + "'', '', '', '',"
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
//						}
					}catch(Exception e){
						System.out.println(e);
					}
					
					
					try{
//						if(obj.getMobno()!=null && obj.getSimno()!=null){
							String sqlselect4 = "select * from dblocator.insertprocedure('insertsim'," + " '0', '1000001', "
									+ "'40002', '" + obj.getSimno() + "', '" + obj.getMobno() + "', '5002', " + "'"
									+ "22-Dec-2016', '" + loginid + "', '" + tt.toLocaleString() + "',  " + "'OK"
									+ "', '0', '" + obj.getMobno() + "', '',"
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
//						}
					}catch(Exception e){
						System.out.println(e);
					}
////					
////					
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
						 sqlselect4 = "select * from dblocator.insertprocedure('insertsimassign'," + " '0', '" + deviceid + "', "
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
					
					
					deviceid++;
					
//					try{
////						if(obj.getVehicleno()!=null){
//						String customer_id = null;
//							String cloginid = null;
//							String sqlselect4 = "select customerid from dblocator.msttblcustomer where customername = '"+obj.getCustomer()+"'";
//			//				    List<String> ll = new ArrayList<String>();
//			//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//								Statement st4 = con.createStatement();
//								ResultSet rs4 = st4.executeQuery(sqlselect4);
//								while(rs4.next()){
//									customer_id = rs4.getString(1);
//									System.out.println(customer_id);
//									sqlselect4 = "select loginid from dblocator.msttbluserlogin where ownersid = '"+customer_id+"'";
//			//					    List<String> ll = new ArrayList<String>();
//			//						String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//									System.out.println(sqlselect4);
//									 st4 = con.createStatement();
//									 rs4 = st4.executeQuery(sqlselect4);
//									while(rs4.next()){
//										cloginid = rs4.getString(1);
//										System.out.println(cloginid);
//									}
//							
//									sqlselect4 = "select * from dblocator.insertprocedure('insertVehicle', '0', '110003', '66003', '50006', "
//											+ "'"+obj.getVehicleno()+"', '03-Nov-2016', '"+obj.getVehicleno().toUpperCase().replace(" ", "")+"', '"+obj.getVehicleno()+"', 'White',  '170001', '22-Dec-2016', '22-Dec-2016', "
//											+ "'22-Dec-2016','80', '"+cloginid+"', '22 Dec, 2016 2:24:46 PM', 'ok', '0','HDFC', '"+customer_id+"', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//				//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
//									System.out.println(sqlselect4);
//									PreparedStatement ps = con.prepareStatement(sqlselect4);
//									boolean rs = ps.execute();
//									if (rs) {
//										System.out.println("executed");
//									} else {
//										System.out.println("Not executed");
//									}
//						}
//					}catch(Exception e){
//						System.out.println(e);
//					}
//					
//					
//					
//					
//					try{
////						if(obj.getVehicleno()!=null && obj.getDeviceid()!="0"){
//							String vehicleid = null;
//							String customer_id = null;
//							String sqlselect4 = "select vehicleid from dblocator.msttblvehicle where vehicleregno = '"+obj.getVehicleno()+"'";
//		//				    List<String> ll = new ArrayList<String>();
//		//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//							Statement st4 = con.createStatement();
//							ResultSet rs4 = st4.executeQuery(sqlselect4);
//							while(rs4.next()){
//								vehicleid = rs4.getString(1);
//								System.out.println(vehicleid);
//							}
//							
//							 sqlselect4 = "select customerid from dblocator.msttblcustomer where customername = '"+obj.getCustomer()+"'";
//							//				    List<String> ll = new ArrayList<String>();
//							//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//												 st4 = con.createStatement();
//												 rs4 = st4.executeQuery(sqlselect4);
//												while(rs4.next()){
//													customer_id = rs4.getString(1);
//													System.out.println(customer_id);
//							
//							String cloginid = null;
//		
//							sqlselect4 = "select loginid from dblocator.msttbluserlogin where ownersid = '"+customer_id+"'";
//		//				    List<String> ll = new ArrayList<String>();
//		//					String sqlselect4= "SELECT deviceid FROM dblocator.msttblmappingdevices_toall where datetimestamp::date=current_date and loginid = 10001";
//							st4 = con.createStatement();
//						    rs4 = st4.executeQuery(sqlselect4);
//							while(rs4.next()){
//								cloginid = rs4.getString(1);
//								System.out.println(cloginid);
//							}
//							
//							
//							
//							sqlselect4 = "select * from dblocator.insertprocedure('insertVehicleToDevice', "
//									+ "'0', '"+vehicleid+"',  '"+obj.getDeviceid()+"',  '1', '22-Dec-2016', '"+cloginid+"', 'ok',  "
//									+ "'22 Dec, 2016 5:48:41 PM', '"+customer_id+"', '', '', '', '','', '', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '','', '', '', '', '', '', '', '','', '', '', '','', '', '', '');";
//		//					String sqlselect4 = "delete from dblocator.msttblmappingdevices_toall where deviceid = "+obj.getDeviceid();
//							System.out.println(sqlselect4);
//							PreparedStatement ps = con.prepareStatement(sqlselect4);
//							boolean rs = ps.execute();
//							if (rs) {
//								System.out.println("executed");
//							} else {
//								System.out.println("Not executed");
//							}
////					
//					}
////					
////
//					}catch(Exception e){
//						System.out.println(e);
//					}
					}
//			}
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