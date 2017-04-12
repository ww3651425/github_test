package com.fashion.spider.model;

public class HrmDepartment {
	// hr中间部门表
	private Integer ID;
	private String DEPARTMENTMARK;
	private String DEPARTMENTNAME;
	private Integer SUBCOMPANYID1;
	private Integer SUPDEPID;
	private Integer ALLSUPDEPID;

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getDEPARTMENTMARK() {
		return DEPARTMENTMARK;
	}

	public void setDEPARTMENTMARK(String dEPARTMENTMARK) {
		DEPARTMENTMARK = dEPARTMENTMARK;
	}

	public String getDEPARTMENTNAME() {
		return DEPARTMENTNAME;
	}

	public void setDEPARTMENTNAME(String dEPARTMENTNAME) {
		DEPARTMENTNAME = dEPARTMENTNAME;
	}

	public Integer getSUBCOMPANYID1() {
		return SUBCOMPANYID1;
	}

	public void setSUBCOMPANYID1(Integer sUBCOMPANYID1) {
		SUBCOMPANYID1 = sUBCOMPANYID1;
	}

	public Integer getSUPDEPID() {
		return SUPDEPID;
	}

	public void setSUPDEPID(Integer sUPDEPID) {
		SUPDEPID = sUPDEPID;
	}

	public Integer getALLSUPDEPID() {
		return ALLSUPDEPID;
	}

	public void setALLSUPDEPID(Integer aLLSUPDEPID) {
		ALLSUPDEPID = aLLSUPDEPID;
	}

	public Integer getSHOWORDER() {
		return SHOWORDER;
	}

	public void setSHOWORDER(Integer sHOWORDER) {
		SHOWORDER = sHOWORDER;
	}

	public String getDEPARTMENTCODE() {
		return DEPARTMENTCODE;
	}

	public void setDEPARTMENTCODE(String dEPARTMENTCODE) {
		DEPARTMENTCODE = dEPARTMENTCODE;
	}

	private Integer SHOWORDER;
	private String DEPARTMENTCODE;
}
