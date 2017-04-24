package com.pakt.pojos;

public class livevehicle {
	private String recordid;
	public String getRecordid() {
		return recordid;
	}
	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}
	public String getDatatimestamp() {
		return datatimestamp;
	}
	public void setDatatimestamp(String datatimestamp) {
		this.datatimestamp = datatimestamp;
	}
	public int getPackettype() {
		return packettype;
	}
	public void setPackettype(int packettype) {
		this.packettype = packettype;
	}
	public String getImeino() {
		return imeino;
	}
	public void setImeino(String imeino) {
		this.imeino = imeino;
	}
	public int getSequenceno() {
		return sequenceno;
	}
	public void setSequenceno(int sequenceno) {
		this.sequenceno = sequenceno;
	}
	public String getGpsdatetime() {
		return gpsdatetime;
	}
	public void setGpsdatetime(String gpsdatetime) {
		this.gpsdatetime = gpsdatetime;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getDevicespeed() {
		return devicespeed;
	}
	public void setDevicespeed(double devicespeed) {
		this.devicespeed = devicespeed;
	}
	public double getCoarseoverground() {
		return coarseoverground;
	}
	public void setCoarseoverground(double coarseoverground) {
		this.coarseoverground = coarseoverground;
	}
	public double getTracksatellite() {
		return tracksatellite;
	}
	public void setTracksatellite(double tracksatellite) {
		this.tracksatellite = tracksatellite;
	}
	private String datatimestamp;
	private int packettype;
	private String imeino;
	private int sequenceno;
	private String gpsdatetime;
	private double latitude;
	private double longitude;
	private double devicespeed;
	private double coarseoverground;
	private double tracksatellite;
	public String getMakename() {
		return makename;
	}
	public void setMakename(String makename) {
		this.makename = makename;
	}
	private String makename;
	
	
	
	private int rowno;
	public int getRowno() {
		return rowno;
	}
	public void setRowno(int rowno) {
		this.rowno = rowno;
	}
}
