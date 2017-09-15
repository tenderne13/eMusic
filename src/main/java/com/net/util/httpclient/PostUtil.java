package com.net.util.httpclient;

import java.io.FileReader;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class PostUtil {
	 /** 
     * 重写验证方法，取消检测ssl 
     */  
    private static TrustManager truseAllManager = new X509TrustManager(){  
  
        public void checkClientTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
            // TODO Auto-generated method stub  
              
        }  
  
        public void checkServerTrusted(  
                java.security.cert.X509Certificate[] arg0, String arg1)  
                throws CertificateException {  
            // TODO Auto-generated method stub  
              
        }  
  
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {  
            // TODO Auto-generated method stub  
            return null;  
        }  
          
    }; 
	/** 
     * 访问https的网站 
     * @param httpclient 
     */  
    private static void enableSSL(DefaultHttpClient httpclient){  
        //调用ssl  
         try {  
                SSLContext sslcontext = SSLContext.getInstance("TLS");  
                sslcontext.init(null, new TrustManager[] { truseAllManager }, null);  
                SSLSocketFactory sf = new SSLSocketFactory(sslcontext);  
                sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);  
                Scheme https = new Scheme("https", sf, 443);  
                httpclient.getConnectionManager().getSchemeRegistry().register(https);  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
    }  
    
    
	
	
	public static JSONObject doPostStr(String url,String outStr){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		enableSSL(httpClient);
		HttpPost httpPost=new HttpPost(url);
		JSONObject jsonObject=null;
		try {
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response=httpClient.execute(httpPost);
			String result=EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject=JSON.parseObject(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	
	
	
	

	
	public static String doGetStr(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		enableSSL(httpClient);
		HttpGet httpGet=new HttpGet(url);
		//httpGet.setHeader("Host", "www.toutiao.com");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
//		httpGet.setHeader("Accept","text/javascript, text/html, application/xml, text/xml, */*");
//		httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//		httpGet.setHeader("X-Requested-With","XMLHttpRequest");
//		httpGet.setHeader("Content-Type","text/*, application/xml");
//		httpGet.setHeader("Connection","keep-alive");
//		httpGet.setHeader("Cookie", "csrftoken=e82b5b2cb174f60a24bcf2ee7708abe2; tt_webid=6448069943489480205; WEATHER_CITY=%E5%8C%97%E4%BA\n" +
//				"%AC; UM_distinctid=15d8cf2512841f-07f4b2f42a9d15-4d584131-13c680-15d8cf25129375; CNZZDATA1259612802=507456306-1501305400-null\n" +
//				"%7C1501305400; __tasessionId=rswfipxi31501308277796; uuid=\"w:650c3c7a16584a4caf64127d7b5c18c2\"; sso_login_status\n" +
//				"=1; login_flag=faf433809996809beb20c2f4416ff375; sessionid=f5953d1045b4d52a3d7347b5d82dba4b; uid_tt=0383d0d6b9bc3897e2d3a2db5edc72ca\n" +
//				"; sid_tt=f5953d1045b4d52a3d7347b5d82dba4b; sid_guard=\"f5953d1045b4d52a3d7347b5d82dba4b|1501308416|2591999\n" +
//				"|Mon\\054 28-Aug-2017 06:06:55 GMT\"");
		try {
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result =EntityUtils.toString(entity,"UTF-8");
				return result;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			httpClient.close();
		}
		return "";
		
	}
	
	
	public static JSONObject doGetJson(String url){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		//HttpClient httpClient=HttpClientManager.getHttpClient("111.155.116.201", 8123);
		//enableSSL(httpClient);
		HttpGet httpGet=new HttpGet(url);
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
		try {
			HttpResponse response=httpClient.execute(httpGet);
			HttpEntity entity=response.getEntity();
			if(entity!=null){
				String result =EntityUtils.toString(entity,"UTF-8");
				return JSON.parseObject(result);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(">>>>>>>>>出现异常开始重试<<<<<<"+e);
			doGetJson(url);
		}
		return null;
		
	}
	
	/**
	 * doPost请求方法
	 * @param url
	 * @param
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String doPostStr(String url,Map<String, String> params){
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpPost httpPost=new HttpPost(url);
		httpPost.setHeader("Host", "www.toutiao.com");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
		httpPost.setHeader("Accept","text/javascript, text/html, application/xml, text/xml, */*");
		httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		httpPost.setHeader("Content-Type","text/*, application/xml");
		httpPost.setHeader("Connection","keep-alive");
		httpPost.setHeader("Cookie", "csrftoken=e82b5b2cb174f60a24bcf2ee7708abe2; tt_webid=6448069943489480205; WEATHER_CITY=%E5%8C%97%E4%BA\n" +
				"%AC; UM_distinctid=15d8cf2512841f-07f4b2f42a9d15-4d584131-13c680-15d8cf25129375; CNZZDATA1259612802=507456306-1501305400-null\n" +
				"%7C1501305400; __tasessionId=rswfipxi31501308277796; uuid=\"w:650c3c7a16584a4caf64127d7b5c18c2\"; sso_login_status\n" +
				"=1; login_flag=faf433809996809beb20c2f4416ff375; sessionid=f5953d1045b4d52a3d7347b5d82dba4b; uid_tt=0383d0d6b9bc3897e2d3a2db5edc72ca\n" +
				"; sid_tt=f5953d1045b4d52a3d7347b5d82dba4b; sid_guard=\"f5953d1045b4d52a3d7347b5d82dba4b|1501308416|2591999\n" +
				"|Mon\\054 28-Aug-2017 06:06:55 GMT\"");
		JSONObject jsonObject=null;
		String result=null;
		try {
			if(params!=null){
				List<NameValuePair> list=new ArrayList<NameValuePair>();
				Set<String> keySet=params.keySet();
				for(String key:keySet){
					list.add(new BasicNameValuePair(key, params.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			}
			HttpResponse response=httpClient.execute(httpPost);
			result=EntityUtils.toString(response.getEntity(),"UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			httpClient.close();
		}
		return result;
	}
	
	

	
	public static void main(String[] args) {
		
	}
}
