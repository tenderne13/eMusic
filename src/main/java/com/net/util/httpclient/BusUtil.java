package com.net.util.httpclient;

import com.alibaba.fastjson.JSON;
import com.net.Entity.Option;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusUtil {
    public static String doGetStr(String url){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
                HttpEntity entity = httpResponse.getEntity();
                Header ceheader = entity.getContentEncoding();
                if (ceheader != null) {
                    HeaderElement[] codecs = ceheader.getElements();
                    for (int i = 0; i < codecs.length; i++) {
                        if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                            httpResponse.setEntity(
                                    new GzipDecompressingEntity(httpResponse.getEntity()));
                            return;
                        }
                    }
                }
            }
        });



        String cookies = getCookie();
        HttpGet httpGet=new HttpGet(url);
        httpGet.setHeader("Accept","text/plain, */*; q=0.01");
        httpGet.setHeader("Accept-Encoding","gzip, deflate");
        httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Cookie", cookies);
        httpGet.setHeader("Host", "www.bjbus.com");
        //httpGet.setHeader("Referer","http://www.bjbus.com/home/fun_rtbus.php?uSec=00000160&uSub=00000162&sBl=351&sBd=4728370121774235792&sBs=3");
        httpGet.setHeader("Referer","http://www.bjbus.com/home/fun_rtbus.php?uSec=00000160&uSub=00000162&sBl=351&sBd=5157920195005691727&sBs=4");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
		httpGet.setHeader("X-Requested-With","XMLHttpRequest");
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            Header[] allHeaders = response.getAllHeaders();
            for(Header header : allHeaders){
                System.out.println(header.getName()+":"+header.getValue());
            }
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
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

    //从首页获得cookie
    public static String getCookie(){
        String url="http://www.bjbus.com/home/index.php";
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response=httpClient.execute(httpGet);
            Header[] headers = response.getHeaders("Set-Cookie");
            String cookies="";
            for(Header header:headers){
                cookies+=header.getValue().split(";")[0]+";";
            }
            return  cookies;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            httpClient.close();
        }
        return "";
    }

    //从首页获得公交车线路
    public static String getLines(){
        String url="http://www.bjbus.com/home/index.php";
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                String result = EntityUtils.toString(entity,"UTF-8");
                List<Option> list = new ArrayList<Option>(1100);
                Document document = Jsoup.parse(result);
                Elements elements = document.select("#selBLine");
                if(elements!=null){
                    Element element = elements.get(0);
                    Elements hrefs = element.select("a");
                    Option option =null;
                    for(Element ele : hrefs){
                        option=new Option(ele.text(),ele.text());
                        list.add(option);
                    }
                    System.out.println(list);
                    return JSON.toJSONString(list);
                }

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


   public static String getLineDir(String url){
       String result=doGetStr(url);
       List<Option> list = new ArrayList<Option>();
       Document document = Jsoup.parseBodyFragment(result);
       Elements elements = document.select("a");
       Option option = null;
       if(elements!=null){
           for(Element ele : elements){
               option=new Option(ele.attr("data-uuid"),ele.text());
               list.add(option);
           }
           return JSON.toJSONString(list);
       }
       return "";
   }

   public static  String getDirStation(String url){
       String result=doGetStr(url);
       System.out.println(result);
       List<Option> list = new ArrayList<Option>();
       Document document = Jsoup.parseBodyFragment(result);
       Elements elements = document.select("a");
       Option option = null;
       if(elements!=null){
           for(Element ele : elements){
               option=new Option(ele.attr("data-seq"),ele.text());
               list.add(option);
           }
           return JSON.toJSONString(list);
       }
       return "";
   }

   public static String getBusTime(String url){
       //fun_rtbus.php?uSec=00000160&uSub=00000162&sBl="+encodeURI(selBLine)+"&sBd="+encodeURI(selBDir)+"&sBs="+encodeURI(selBStop);
       DefaultHttpClient httpClient=new DefaultHttpClient();
       httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
           public void process(HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
               HttpEntity entity = httpResponse.getEntity();
               Header ceheader = entity.getContentEncoding();
               if (ceheader != null) {
                   HeaderElement[] codecs = ceheader.getElements();
                   for (int i = 0; i < codecs.length; i++) {
                       if (codecs[i].getName().equalsIgnoreCase("gzip")) {
                           httpResponse.setEntity(
                                   new GzipDecompressingEntity(httpResponse.getEntity()));
                           return;
                       }
                   }
               }
           }
       });



       String cookies = getCookie();
       HttpGet httpGet=new HttpGet(url);
       httpGet.setHeader("Accept","text/plain, */*; q=0.01");
       httpGet.setHeader("Accept-Encoding","gzip, deflate");
       httpGet.setHeader("Accept-Language","zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
       httpGet.setHeader("Cookie", cookies);
       httpGet.setHeader("Host", "www.bjbus.com");
       //httpGet.setHeader("Referer","http://www.bjbus.com/home/fun_rtbus.php?uSec=00000160&uSub=00000162&sBl=351&sBd=4728370121774235792&sBs=3");
       httpGet.setHeader("Referer","http://www.bjbus.com/home/fun_rtbus.php?uSec=00000160&uSub=00000162&sBl=351&sBd=5157920195005691727&sBs=4");
       httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
       httpGet.setHeader("X-Requested-With","XMLHttpRequest");
       try {
           HttpResponse response=httpClient.execute(httpGet);
           HttpEntity entity=response.getEntity();
           Header[] allHeaders = response.getAllHeaders();
           for(Header header : allHeaders){
               System.out.println(header.getName()+":"+header.getValue());
           }
           if(entity!=null){
               String result = EntityUtils.toString(entity,"UTF-8");
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






    public static void main(String[] arcgs) throws UnsupportedEncodingException {
        String targetUrl="http://www.bjbus.com/home/ajax_rtbus_data.php?act=getDirStation&selBLine=通勤苹果园&selBDir=5089582594970265217";
        String targetUrl2="http://www.bjbus.com/home/ajax_rtbus_data.php?act=getLineDir&selBLine=351";
        String targetUrl3="http://www.bjbus.com/home/ajax_rtbus_data.php?act=busTime&selBLine=351&selBDir=5157920195005691727&selBStop=4";
        //String result=getLineDir(targetUrl2);
        //String result=getDirStation(targetUrl);
        String targetUrl4="http://www.bjbus.com/home/fun_rtbus.php?uSec=00000160&uSub=00000162&sBl=351&sBd=4728370121774235792&sBs=4";
        String result=getBusTime(targetUrl4);
        System.out.println(result);
    }
}
