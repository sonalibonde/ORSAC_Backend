package com.pakt.pojos;

public class parentcomp_details {
	String companyid; 
	String parentcompid;
	String companyname; 
	String loginid;
	int orderno;
	
	
	public int getOrderno() {
		return orderno;
	}
	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getParentcompid() {
		return parentcompid;
	}
	public void setParentcompid(String parentcompid) {
		this.parentcompid = parentcompid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getLoginid() {
		return loginid;
	}
	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}
}
