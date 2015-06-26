package com.fhit.ycall.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import android.util.Base64;

import com.fhit.ycall.util.ConfigUtil;
import com.fhit.ycall.util.LogUtil;

public class HttpHelper {

	public static final String UTF_8 = "UTF-8";
	private final static int TIMEOUT_CONNECTION = 20000;
	private final static int TIMEOUT_SOCKET = 20000;
	private final static int RETRY_TIME = 3;
	public static void setAuthorization(final String username,final String password){
		try {
			String auto = username+":"+password;
			String authorization = "Basic "+Base64.encodeToString(auto.getBytes("utf-8"), Base64.URL_SAFE);
			authorization = authorization.substring(0,authorization.length()-1);
			LogUtil.i("ycall", "生成的令牌 authorization = "+authorization);
			ConfigUtil.getInstance().setConfigString("authorization", authorization);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			LogUtil.eSave("ycall", "HttpHelper-setAuthorization()异常：", e1.fillInStackTrace());
		}
		
	}
	private static  HttpClient getHttpClient() {   
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		HttpClient mHttpClient = new HttpClient();
//	// 设置 HttpClient 接收 Cookie,用与浏览器一样的策略
//	httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//    // 设置 默认的超时重试处理策略
		mHttpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//	// 设置 连接超时时间
		mHttpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
//	// 设置 读数据超时时间 
		mHttpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
//	// 设置 字符集
		mHttpClient.getParams().setContentCharset(UTF_8);
	 
		return mHttpClient;
	}	
	private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod();
		httpGet.setPath(url);
		// 设置 请求超时时间
		httpGet.getParams().setSoTimeout(TIMEOUT_SOCKET);
		String Authorization = ConfigUtil.getInstance().getConfigString("authorization");
		if(!"".equals("Authorization")){
			httpGet.addRequestHeader("Authorization", Authorization);
		}
		httpGet.setRequestHeader("content-type", "application/json");
		return httpGet;
	}
	private static PostMethod getHttpPost(String url, String cookie, String userAgent) {
		PostMethod httpPost = new PostMethod(url);
		String Authorization = ConfigUtil.getInstance().getConfigString("authorization");
		if(!"".equals("Authorization")){
			httpPost.addRequestHeader("Authorization", Authorization);
		}
		httpPost.setRequestHeader("Content-Type", "application/json");
		return httpPost;
	}
	private static DeleteMethod getHttpDelete(String url, String cookie, String userAgent){
		DeleteMethod httpDelete = new DeleteMethod(url);
		String Authorization = ConfigUtil.getInstance().getConfigString("authorization");
		if(!"".equals("Authorization")){
			httpDelete.addRequestHeader("Authorization", Authorization);
		}
		httpDelete.setRequestHeader("Content-Type", "application/json");
		return httpDelete;
	}
	private static PutMethod getHttpPut(String url, String cookie, String userAgent){
		PutMethod httpDelete = new PutMethod(url);
		String Authorization = ConfigUtil.getInstance().getConfigString("authorization");
		if(!"".equals("Authorization")){
			httpDelete.addRequestHeader("Authorization", Authorization);
		}
		httpDelete.setRequestHeader("Content-Type", "application/json");
		return httpDelete;
	}
	public static String makeURL(String p_url, Map<String, Object> params) {
		if(params != null){
			StringBuilder url = new StringBuilder(p_url);
			if(url.indexOf("?")<0)
				url.append('?');
			
			for(String name : params.keySet()){
				url.append('&');
				url.append(name);
				url.append('=');
				url.append(String.valueOf(params.get(name)));
				//不做URLEncoder处理
				//url.append(URLEncoder.encode(String.valueOf(params.get(name)), UTF_8));
			}
			return url.toString().replace("?&", "?");
		}else{
			return p_url;
		}
	}
	public static HttpResponseResult http_get(String url) throws Exception {	
	    HttpClient httpClient = null;
		GetMethod httpGet = null;
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
				httpGet = getHttpGet(url, null, null);	
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_get 前 : url = "+url);
				int statusCode = httpClient.executeMethod(httpGet); 
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_get 后  ：返回码 = "+statusCode+" / url = "+url);
				String responseBody = httpGet.getResponseBodyAsString();
				return new HttpResponseResult(statusCode, responseBody);
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpGet.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		return null;//new ByteArrayInputStream(responseBody.getBytes());
	}
	public static HttpResponseResult http_delete(String url) throws Exception  {	
		HttpClient httpClient = null;
		DeleteMethod httpDelete = null;
		int time = 0;
		do{
			try 
			{
				httpClient = getHttpClient();
			    httpDelete = getHttpDelete(url, null, null);	
			    LogUtil.e("ycall", Thread.currentThread()+"<-> http_delete 前 : url = "+url);
				int statusCode = httpClient.executeMethod(httpDelete);
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_delete 后  ：返回码 = "+statusCode+" / url = "+url);
				String responseBody = httpDelete.getResponseBodyAsString();
				return new HttpResponseResult(statusCode, responseBody);
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
				throw AppException.network(e);
			} finally {
				// 释放连接
				httpDelete.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		return null;//new ByteArrayInputStream(responseBody.getBytes());
	}
	public static HttpResponseResult http_post( String url ,String postInfo) throws AppException {
		HttpClient httpClient = null;
		PostMethod httpPost = null;
		int time = 0;
		do{
			try {
				httpClient = getHttpClient();
				httpPost = getHttpPost(url, null, null);	        
				httpPost.setRequestBody(postInfo);	 
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_post 前 : url = "+url);
				int statusCode = httpClient.executeMethod(httpPost);
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_post 后  ：返回码 = "+statusCode+" / url = "+url);
				String responseBody = httpPost.getResponseBodyAsString();
				return new HttpResponseResult(statusCode, responseBody);
			} catch (HttpException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生致命的异常，可能是协议不对或者返回的内容有问题
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// 发生网络异常
 				throw AppException.network(e);
			} finally {
				// 释放连接
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		return null;
	}
}
