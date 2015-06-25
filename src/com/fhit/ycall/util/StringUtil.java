package com.fhit.ycall.util;

import java.util.regex.Pattern;

public class StringUtil {

	public StringUtil() {
		 throw new AssertionError("StringUtil不能实例化，它下面都是静态方法");
	}
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	/**
	 * 手机的正则表达式
	 */
	public final static String MobileRegex = "^((\\+86)?1[3-9]\\d{9})$";
	/**
	 * 座机的正则表达式，可带分机号【分机号前必须加‘-’】<br>
	 * 如：010-62144530-101；01062144530-101<br>
	 *    010-62144530 ；01062144530
	 */
	public final static String LandlineNumberRegex = "^((0\\d{2,3}(-)?)?[2-9]{1}\\d{6,7}(-\\d{3,5})?)$";
}
