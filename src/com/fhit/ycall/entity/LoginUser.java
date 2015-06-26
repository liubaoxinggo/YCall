package com.fhit.ycall.entity;

public class LoginUser extends BaseEntity{

	private String Cellphone;
	private String Password;
	public LoginUser() {
		super();
	}
	public String getCellphone() {
		return Cellphone;
	}
	public void setCellphone(String cellphone) {
		Cellphone = cellphone;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	
}
