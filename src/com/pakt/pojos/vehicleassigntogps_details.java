package com.pakt.pojos;

public class vehicleassigntogps_details {
	String id;
	String vehicleid;
	String vehicleregno;
	String deviceid;
	String assigndate;
	String companyname;
	String companyid;
	String uniqueid;
	String remarks;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	public String getVehicleregno() {
		return vehicleregno;
	}
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getAssigndate() {
		return assigndate;
	}
	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	
	private int rowno;


	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}
}

