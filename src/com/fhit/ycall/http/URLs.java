package com.fhit.ycall.http;

public class URLs {

	private static String HOST = "http://yuehu.fhit.com.cn/api/";
	/**
	 * ��¼ ע��
	 */
	public static String User;
	/**
	 * ��֤��
	 */
	public static String VerifyCode;
	/**
	 * ��ҵ
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
