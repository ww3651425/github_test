package com.fashion.spider.model;

import java.util.Date;



public class User implements java.io.Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userId;//雇员编号
	private String name;//雇员姓名
	private String nativePlace;//籍贯
	private Integer gender;//性别
	private Integer age;//年龄
	private String degree;//学位
	private String idNumber;//身份证号码
	private String residenceAddr;//居住地址
	private String emain;//邮箱
	private String phone;//公司电话
	private String mobile;//移动电话
	private Integer userType;//雇员类型  
	private Integer status;//雇员状态
	private String password;//用户登录密码
	private Date birthday;//员工生日
	
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getDegree() {
		return degree;
	}
	public void setDegree(String degree) {
		this.degree = degree;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getResidenceAddr() {
		return residenceAddr;
	}
	public void setResidenceAddr(String residenceAddr) {
		this.residenceAddr = residenceAddr;
	}
	public String getEmain() {
		return emain;
	}
	public void setEmain(String emain) {
		this.emain = emain;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}