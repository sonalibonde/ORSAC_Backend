package com.pakt.pojos;

//mstvehicle.vehicleid, mstvehicle.vehicletypeid, mstvehicle.vehicleregno,
//mstass.deviceid,mstass.assigndate,
//deviceparse.imeino,deviceparse.latitude,deviceparse.longitude,deviceparse.datatimestamp


public class daywisevehiclesReport {
	public String getVehicleid() {
		return vehicleid;
	}
	public void setVehicleid(String vehicleid) {
		this.vehicleid = vehicleid;
	}
	public String getVehicletypeid() {
		return vehicletypeid;
	}
	public void setVehicletypeid(String vehicletypeid) {
		this.vehicletypeid = vehicletypeid;
	}
	public String getVehicleregno() {
		return vehicleregno;
	}
	public void setVehicleregno(String vehicleregno) {
		this.vehicleregno = vehicleregno;
	}
	public String getDatetimestamp() {
		return datetimestamp;
	}
	public void setDatetimestamp(String datetimestamp) {
		this.datetimestamp = datetimestamp;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getAssigndate() {
		return assigndate;
	}
	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}
	String  vehicleid;
	String vehicletypeid;
	String vehicleregno;
	String datetimestamp;
	String  uniqueid;
	String latitude;
	String  longitude;
	String  assigndate;
	String  deviceid;
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

}
