package com.fashion.spider.model;

public class HrmResource {
	private Integer ID;
	private String LOGINID;
	private String PASSWORD;
	private String LASTNAME;
	private String WORKCODE;
	public String getLOGINID() {
		return LOGINID;
	}
	public void setLOGINID(String lOGINID) {
		LOGINID = lOGINID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getWORKCODE() {
		return WORKCODE;
	}
	public void setWORKCODE(String wORKCODE) {
		WORKCODE = wORKCODE;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getLASTNAME() {
		return LASTNAME;
	}
	public void setLASTNAME(String lASTNAME) {
		LASTNAME = lASTNAME;
	}
}
