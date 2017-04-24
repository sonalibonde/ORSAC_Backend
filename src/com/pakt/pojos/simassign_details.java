package com.pakt.pojos;

public class simassign_details {
	String deviceid;
	String uniqueid;
	String simid;
	String simnumber;
	String networkname;
	String companyid;
	String companyname;
	String networkid;
	String assigndate;
	String devicesimid;
	String remarks;
	String simassign_status;
	
	
	public String getSimassign_status() {
		return simassign_status;
	}
	public void setSimassign_status(String simassign_status) {
		this.simassign_status = simassign_status;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	String mobileno;
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	String flag;
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDevicesimid() {
		return devicesimid;
	}
	public void setDevicesimid(String devicesimid) {
		this.devicesimid = devicesimid;
	}
	public String getDeviceid() {
		return deviceid;
	}
	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}
	public String getUniqueid() {
		return uniqueid;
	}
	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}
	public String getSimid() {
		return simid;
	}
	public void setSimid(String simid) {
		this.simid = simid;
	}
	public String getSimnumber() {
		return simnumber;
	}
	public void setSimnumber(String simnumber) {
		this.simnumber = simnumber;
	}
	public String getNetworkname() {
		return networkname;
	}
	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getNetworkid() {
		return networkid;
	}
	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}
	public String getAssigndate() {
		return assigndate;
	}
	public void setAssigndate(String assigndate) {
		this.assigndate = assigndate;
	}
	
	private int rowno;
	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}
}
