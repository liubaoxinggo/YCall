package com.fhit.ycall.util;

import java.util.regex.Pattern;

public class StringUtil {

	public StringUtil() {
		 throw new AssertionError("StringUtil����ʵ�����������涼�Ǿ�̬����");
	}
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	/**
	 * �ֻ���������ʽ
	 */
	public final static String MobileRegex = "^((\\+86)?1[3-9]\\d{9})$";
	/**
	 * ������������ʽ���ɴ��ֻ��š��ֻ���ǰ����ӡ�-����<br>
	 * �磺010-62144530-101��01062144530-101<br>
	 *    010-62144530 ��01062144530
	 */
	public final static String LandlineNumberRegex = "^((0\\d{2,3}(-)?)?[2-9]{1}\\d{6,7}(-\\d{3,5})?)$";
}
