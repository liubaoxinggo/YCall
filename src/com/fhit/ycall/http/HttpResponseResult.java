package com.fhit.ycall.http;

import java.io.Serializable;

import com.google.gson.Gson;

public class HttpResponseResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6712390493340799947L;
	private int statusCode;
	private String responseBody;
	public HttpResponseResult(int statusCode, String result) {
		super();
		this.statusCode = statusCode;
		this.responseBody = result;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	 
	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public String getResponseBody() {
		return responseBody;
	}
	/**
	 * 返回的错误信息
	 * @return
	 */
	public String getErrorMsg(){
		try {
			ErrorMessage error = new Gson().fromJson(this.responseBody, ErrorMessage.class);
			return error.getMessage();
		} catch (Exception e) {
			return null;
		}
	}
	class ErrorMessage{
		private String Message;
		public ErrorMessage() {
			super();
		}
		public String getMessage() {
			return Message;
		}
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return new Gson().toJson(this);
	}
}
