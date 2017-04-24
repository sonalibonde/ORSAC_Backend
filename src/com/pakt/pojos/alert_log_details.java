package com.pakt.pojos;

public class alert_log_details {

	
String vehicleregno;
public String getVehicleregno() {
	return vehicleregno;
}
public void setVehicleregno(String vehicleregno) {
	this.vehicleregno = vehicleregno;
}
public String getDatatimestamp() {
	return datatimestamp;
}
public void setDatatimestamp(String datatimestamp) {
	this.datatimestamp = datatimestamp;
}
public String getType_of_alerts() {
	return type_of_alerts;
}
public void setType_of_alerts(String type_of_alerts) {
	this.type_of_alerts = type_of_alerts;
}
String datatimestamp;
String type_of_alerts;
String location;
String latitute;
public String getLatitute() {
	return latitute;
}
public void setLatitute(String latitute) {
	this.latitute = latitute;
}
public String getLongitute() {
	return longitute;
}
public void setLongitute(String longitute) {
	this.longitute = longitute;
}
public String getLocation() {
	return location;
}
String longitute;

public void setLocation(String location) {
	this.location = location;
}

}
