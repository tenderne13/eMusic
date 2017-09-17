package com.net.util.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.net.util.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicUtil {

    //下载音乐工具类
    public static boolean download(String song_id,String songName,String params){
        //String realPath="E:/netMusic/";//E:/data/image
        String realPath="E:/IDEAspace/eMusic/target/netMusic/static/music1/";//E:/data/image
        String target_url= Constant.TARGERT_SONG_URL;

        //1.第一步先判断是否有这个id的文件夹，如果有就不用下载
        File directory=new File(realPath+song_id);
        boolean isExist = directory.exists();
        if(isExist){
            return true;
        }
        System.out.println("文件夹是否存在:"+isExist);

        //2.如果没有httpclient请求url下载视频并下载

        JSONObject dataObj = getSong(params, target_url);
        if(null!=dataObj) {
            JSONArray data = dataObj.getJSONArray("data");
            JSONObject obj = data.getJSONObject(0);
            String url = obj.getString("url");
            String fileType=obj.getString("type");
            System.out.println("url为:"+url);

            boolean isDown = downMusic(url,fileType,songName,realPath+song_id,directory);
            return  isDown;
        }

        return false;
    }

    private static JSONObject getSong(String params, String target_url) {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(target_url);
        httpPost.setHeader("Host", "music.163.com");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        String result;
        try {

            StringEntity entity=new StringEntity(params,HTTP.UTF_8);
            entity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            result= EntityUtils.toString(response.getEntity(),"UTF-8");
            System.out.println(params);
            System.out.println("响应的数据为:"+result);
            return JSON.parseObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            httpClient.close();
        }
        return null;
    }

    private static boolean downMusic(String url,String type,String songName,String directory,File dir){
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                dir.mkdir();
                String song=songName+"."+type;
                FileUtils.copyInputStreamToFile(entity.getContent(),new File(directory, song));
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            httpClient.close();
        }
        return false;
    }




    public static void main(String[] ar){
        String params="encSecKey=8533a6cbe40fa182cb6694f3effc0100af7578e45ffc138510327199664c0bd90f05819feedd630980844e1558f261760358e921eb8cb076b3683ab2a26e351d6ffcce530fd4d6e055de8fdd2b0e6b84bffb6791956471e4d6d536773c98d04be97d923ba09f64fdfcc4622ad134c8491650908e4c57c1632dbb6bfc93e2c877&params=aAPzNRqwC4Y7saKZdEc%2BBsEJblrT%2Fk16NjYcIatc14WUgfvSthEK1BPpMO4nxassSv2PoP%2BtFGZSoZWtFOdnp6oa48pAWDrblVP%2BZcjvPPS%2FpdqoW1i2vIwBiZiZV%2Bpf";
        download("496869520", "xo", params);
    }
}
