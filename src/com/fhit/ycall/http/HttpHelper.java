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
			LogUtil.i("ycall", "���ɵ����� authorization = "+authorization);
			ConfigUtil.getInstance().setConfigString("authorization", authorization);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			LogUtil.eSave("ycall", "HttpHelper-setAuthorization()�쳣��", e1.fillInStackTrace());
		}
		
	}
	private static  HttpClient getHttpClient() {   
		Protocol https = new Protocol("https", new HTTPSSecureProtocolSocketFactory(), 443);
		Protocol.registerProtocol("https", https);
		HttpClient mHttpClient = new HttpClient();
//	// ���� HttpClient ���� Cookie,���������һ���Ĳ���
//	httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);
//    // ���� Ĭ�ϵĳ�ʱ���Դ������
		mHttpClient.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
//	// ���� ���ӳ�ʱʱ��
		mHttpClient.getHttpConnectionManager().getParams().setConnectionTimeout(TIMEOUT_CONNECTION);
//	// ���� �����ݳ�ʱʱ�� 
		mHttpClient.getHttpConnectionManager().getParams().setSoTimeout(TIMEOUT_SOCKET);
//	// ���� �ַ���
		mHttpClient.getParams().setContentCharset(UTF_8);
	 
		return mHttpClient;
	}	
	private static GetMethod getHttpGet(String url, String cookie, String userAgent) {
		GetMethod httpGet = new GetMethod();
		httpGet.setPath(url);
		// ���� ����ʱʱ��
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
				//����URLEncoder����
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
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_get ǰ : url = "+url);
				int statusCode = httpClient.executeMethod(httpGet); 
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_get ��  �������� = "+statusCode+" / url = "+url);
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
				// �����������쳣��������Э�鲻�Ի��߷��ص�����������
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// ���������쳣
				throw AppException.network(e);
			} finally {
				// �ͷ�����
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
			    LogUtil.e("ycall", Thread.currentThread()+"<-> http_delete ǰ : url = "+url);
				int statusCode = httpClient.executeMethod(httpDelete);
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_delete ��  �������� = "+statusCode+" / url = "+url);
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
				// �����������쳣��������Э�鲻�Ի��߷��ص�����������
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// ���������쳣
				throw AppException.network(e);
			} finally {
				// �ͷ�����
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
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_post ǰ : url = "+url);
				int statusCode = httpClient.executeMethod(httpPost);
				LogUtil.e("ycall", Thread.currentThread()+"<-> http_post ��  �������� = "+statusCode+" / url = "+url);
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
				// �����������쳣��������Э�鲻�Ի��߷��ص�����������
				throw AppException.http(e);
			} catch (IOException e) {
				time++;
				if(time < RETRY_TIME) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {} 
					continue;
				}
				// ���������쳣
 				throw AppException.network(e);
			} finally {
				// �ͷ�����
				httpPost.releaseConnection();
				httpClient = null;
			}
		}while(time < RETRY_TIME);
		return null;
	}
}
