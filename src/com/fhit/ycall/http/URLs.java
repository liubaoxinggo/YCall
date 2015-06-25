package com.fhit.ycall.http;

public class URLs {

	private static String HOST = "http://yuehu.fhit.com.cn/api/";
	
	public static String User;
	public static String VerifyCode;
	
	public static void initUrls(){
		if(!HOST.endsWith("/")){
			HOST = HOST+"/";
		}
		VerifyCode = HOST + "v1/VerifyCode";
		User = HOST + "v1/User";
	}
	
	
	
	
}
