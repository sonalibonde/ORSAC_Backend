package com.pakt.pojos;

import java.sql.Timestamp;

public class company_details {
	  Long companyid ;
	  String companyname ;
	  String shortname ;
	  String companyaddress ;
	  String contactpersonname ;
	  Long lanldlinenumber1 ;
	  Long lanldlinenumber2 ;
	  Long faxnumber ;
	  String emailid ;
	  String registeredaddress ;
	  Long loginid ;
	  String datetimestamp ;
	  String remarks ;
	  String parentcompid;
	  String flag;
	  
	  
	  
	  
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getParentcompid() {
		return parentcompid;
	}
	public void setParentcompid(String parentcompid) {
		this.parentcompid = parentcompid;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getShortname() {
		return shortname;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public String getCompanyaddress() {
		return companyaddress;
	}
	public void setCompanyaddress(String companyaddress) {
		this.companyaddress = companyaddress;
	}
	public String getContactpersonname() {
		return contactpersonname;
	}
	public void setContactpersonname(String contactpersonname) {
		this.contactpersonname = contactpersonname;
	}
	public Long getLanldlinenumber1() {
		return lanldlinenumber1;
	}
	public void setLanldlinenumber1(Long lanldlinenumber1) {
		this.lanldlinenumber1 = lanldlinenumber1;
	}
	public Long getLanldlinenumber2() {
		return lanldlinenumber2;
	}
	public void setLanldlinenumber2(Long lanldlinenumber2) {
		this.lanldlinenumber2 = lanldlinenumber2;
	}
	public Long getFaxnumber() {
		return faxnumber;
	}
	public void setFaxnumber(Long faxnumber) {
		this.faxnumber = faxnumber;
	}
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public String getRegisteredaddress() {
		return registeredaddress;
	}
	public void setRegisteredaddress(String registeredaddress) {
		this.registeredaddress = registeredaddress;
	}
	public Long getLoginid() {
		return loginid;
	}
	public void setLoginid(Long loginid) {
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
	  
	  public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private String city;
	  private String state;
	  private String zip;
	  private String status;
	  public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}

	private int rowno;
	  
	  
}
