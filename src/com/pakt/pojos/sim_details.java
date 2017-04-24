package com.pakt.pojos;

import java.util.Date;
import java.sql.Timestamp;

public class sim_details {
	  Long simid ;
	  Long networkid ;
	  Long assettypeid ;
	  String simnumber ;
	  Long mobilenumber ;
	  Long vendorid ;
	  String receiptdt ;
	  Long loginid ;
	  String datetimestamp ;
	  String remarks ;
	  String flag ;
	  Long companyid ;
	  String dateofactive ;
	  Long available ;
	  String assetname;
	  String status;
	  String mobilenumber2;
	  String sim_status;
	  
	 
	  
	  
	  

	public String getMobilenumber2() {
		return mobilenumber2;
	}
	public void setMobilenumber2(String mobilenumber2) {
		this.mobilenumber2 = mobilenumber2;
	}
	public String getSim_status() {
		return sim_status;
	}
	public void setSim_status(String sim_status) {
		this.sim_status = sim_status;
	}
	public String getSimnumber() {
		return simnumber;
	}
	public void setSimnumber(String simnumber) {
		this.simnumber = simnumber;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssetname() {
		return assetname;
	}
	public void setAssetname(String assetname) {
		this.assetname = assetname;
	}
	public Long getSimid() {
		return simid;
	}
	public void setSimid(Long simid) {
		this.simid = simid;
	}
	public Long getNetworkid() {
		return networkid;
	}
	public void setNetworkid(Long networkid) {
		this.networkid = networkid;
	}
	public Long getAssettypeid() {
		return assettypeid;
	}
	public void setAssettypeid(Long assettypeid) {
		this.assettypeid = assettypeid;
	}
	
	public Long getMobilenumber() {
		return mobilenumber;
	}
	public void setMobilenumber(Long mobilenumber) {
		this.mobilenumber = mobilenumber;
	}
	public Long getVendorid() {
		return vendorid;
	}
	public void setVendorid(Long vendorid) {
		this.vendorid = vendorid;
	}
	public String getReceiptdt() {
		return receiptdt;
	}
	public void setReceiptdt(String receiptdt) {
		this.receiptdt = receiptdt;
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
		this. datetimestamp=datetimestamp;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public Long getCompanyid() {
		return companyid;
	}
	public void setCompanyid(Long companyid) {
		this.companyid = companyid;
	}
	public String getDateofactive() {
		return dateofactive;
	}
	public void setDateofactive(String dateofactive) {
		this.dateofactive = dateofactive;
	}
	public Long getAvailable() {
		return available;
	}
	public void setAvailable(Long available) {
		this.available = available;
	}
	  
	  private String networkname;
	  public String getNetworkname() {
		return networkname;
	}
	public void setNetworkname(String networkname) {
		this.networkname = networkname;
	}
	public String getVendorname() {
		return vendorname;
	}
	public void setVendorname(String vendorname) {
		this.vendorname = vendorname;
	}

	private String vendorname;
	private String companyname;
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
