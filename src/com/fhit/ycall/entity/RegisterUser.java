package com.fhit.ycall.entity;

/**
 * ×¢²áÊµÌå
 * @author liubaoxing
 *
 */
public class RegisterUser extends BaseEntity{

	private String Cellphone;
	private String Password;
	private String VerifyCode;
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
	public String getVerifyCode() {
		return VerifyCode;
	}
	public void setVerifyCode(String verifyCode) {
		VerifyCode = verifyCode;
	}
}
