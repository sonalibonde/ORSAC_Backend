package com.pakt.pojos;

public class menu_details {
	 String menutext;
	 String navigateurl;
	 String parentid;
	 String mid;
	 String orderno;
	 String menuid;
	 String description;
	 String rolename;
	 String parentname;
	 
	 
	public String getParentname() {
		return parentname;
	}
	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMenutext() {
		return menutext;
	}
	public void setMenutext(String menutext) {
		this.menutext = menutext;
	}
	public String getNavigateurl() {
		return navigateurl;
	}
	public void setNavigateurl(String navigateurl) {
		this.navigateurl = navigateurl;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getOrderno() {
		return orderno;
	}
	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	 
	 private int rowno;


	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}
}
