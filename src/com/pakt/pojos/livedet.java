package com.pakt.pojos;

import java.util.List;

public class livedet {
	int total_count;
	List<vts_Live_Vehicle> data;
	String exc;
	
	
	public String getExc() {
		return exc;
	}
	public void setExc(String exc) {
		this.exc = exc;
	}
	public int getTotal_count() {
		return total_count;
	}
	public void setTotal_count(int total_count) {
		this.total_count = total_count;
	}
	public List<vts_Live_Vehicle> getData() {
		return data;
	}
	public void setData(List<vts_Live_Vehicle> data) {
		this.data = data;
	}
	
	
}
