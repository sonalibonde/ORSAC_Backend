package com.pakt.pojos;

import java.util.List;

public class loginresponse {
	List<login_details> data;
	String success;
	
	
	public List<login_details> getData() {
		return data;
	}
	public void setData(List<login_details> data) {
		this.data = data;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	
	
}
