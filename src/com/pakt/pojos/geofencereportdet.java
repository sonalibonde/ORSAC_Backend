package com.pakt.pojos;

import java.sql.Timestamp;

public class geofencereportdet {
	String vehicleno;
	String geofencename;
	String intime;
	String outtime;
	String stoppage;
	
	public String getVehicleno() {
		return vehicleno;
	}
	public void setVehicleno(String vehicleno) {
		this.vehicleno = vehicleno;
	}
	public String getGeofencename() {
		return geofencename;
	}
	public void setGeofencename(String geofencename) {
		this.geofencename = geofencename;
	}
	public String getIntime() {
		return intime;
	}
	public void setIntime(String intime) {
		this.intime = intime;
	}
	public String getOuttime() {
		return outtime;
	}
	public void setOuttime(String outtime) {
		this.outtime = outtime;
	}
	public String getStoppage() {
		return stoppage;
	}
	public void setStoppage(String stoppage) {
		this.stoppage = stoppage;
	}
	

	
}
