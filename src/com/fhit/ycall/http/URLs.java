package com.fhit.ycall.http;

public class URLs {

	private static String HOST = "http://yuehu.fhit.com.cn/api/";
	/**
	 * 登录 注册
	 */
	public static String User;
	/**
	 * 验证码
	 */
	public static String VerifyCode;
	/**
	 * 企业
	 */
	public static String Enterprise;
	
	public static void initUrls(){
		if(!HOST.endsWith("/")){
			HOST = HOST+"/";
		}
		VerifyCode = HOST + "v1/VerifyCode";
		User = HOST + "v1/User";
		Enterprise = HOST + "v1/Enterprise";
	}
	
	
	
	
}
