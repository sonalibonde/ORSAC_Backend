package com.pakt.pojos;

public class network_details {
	 String networkid; 
	 String networkname; 
	 String networkapn; 
	 String loginid; 
	 String datetimestamp; 
	 String remarks;
	 String companyname;
	 String flag;
	 
	 
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getNetworkid() {
		return networkid;
	}
	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}
	public String getNetworkname() {
		return networkname;
	}
	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}
	public String getNetworkapn() {
		return networkapn;
	}
	public void setNetworkapn(String networkapn) {
		this.networkapn = networkapn;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
	public String getDatetimestamp() {
		return datetimestamp;
	}
	public void setDatetimestamp(String datetimestamp) {
		this.datetimestamp = datetimestamp;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	 
	 
	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}


	private int rowno;
}
