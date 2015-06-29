package com.android.internal.telephony;
//这个声明是为了能调用系统挂断电话的功能
interface ITelephony {
	//结束呼叫
	boolean endCall();

	//电话打进
	void answerRingingCall();
}