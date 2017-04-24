package com.pakt.pojos;

import java.util.List;

public class subdealersold {
	
	public String getDealerid() {
		return dealerid;
	}
	public void setDealerid(String dealerid) {
		this.dealerid = dealerid;
	}
	public String getNo_devices() {
		return no_devices;
	}
	public void setNo_devices(String no_devices) {
		this.no_devices = no_devices;
	}
	public String getDealername() {
		return dealername;
	}
	public void setDealername(String dealername) {
		this.dealername = dealername;
	}
	public String getSubdealername() {
		return subdealername;
	}
	public void setSubdealername(String subdealername) {
		this.subdealername = subdealername;
	}
	public List<String> getPurchaseorder() {
		return purchaseorder;
	}
	public void setPurchaseorder(List<String> purchaseorder) {
		this.purchaseorder = purchaseorder;
	}



	List<String> purchaseorder;
	String no_devices;
	String dealername;
	String subdealername;
	String dealerid;
}
