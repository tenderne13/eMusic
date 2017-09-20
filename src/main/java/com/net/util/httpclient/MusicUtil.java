package com.net.util.httpclient;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.net.util.AES;
import com.net.util.Constant;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.*;

public class MusicUtil {

    private static Log log = LogFactory.getLog(MusicUtil.class);

    //下载音乐工具类
    public static boolean download(String song_id,String songName,String params){
        //String realPath="E:/netMusic/";//E:/data/image
        //String realPath="E:/IDEAspace/eMusic/target/netMusic/static/music1/";//E:/data/image
        //String realPath="E:/IdeaProject/netMusic/target/netMusic/static/music1/";//E:/data/image
        String realPath=Constant.MUSIC_PATH;
        String target_url= Constant.TARGERT_SONG_URL;

        //1.第一步先判断是否有这个id的文件夹，如果有就不用下载
        File directory=new File(realPath+song_id);
        boolean isExist = directory.exists();
        if(isExist){
            return true;
        }
        //2.如果没有httpclient请求url下载视频并下载

        JSONObject dataObj = getSong(params, target_url);
        if(null!=dataObj) {
            JSONArray data = dataObj.getJSONArray("data");
            JSONObject obj = data.getJSONObject(0);
            String url = obj.getString("url");
            String fileType=obj.getString("type");
            boolean isDown = downMusic(url,fileType,songName,realPath+song_id,directory);
            return  isDown;
        }

        return false;
    }

    public static void downloadService(String song_id,String songName,String params,OutputStream outputStream) throws FileNotFoundException {

        String realPath=Constant.MUSIC_PATH;
        String target_url= Constant.TARGERT_SONG_URL;

        //1.第一步先判断是否有这个id的文件夹，如果有就不用下载
        File directory=new File(realPath+song_id);
        boolean isExist = directory.exists();
        if(!isExist){
            JSONObject dataObj = getSong(params, target_url);
            if(null!=dataObj) {
                JSONArray data = dataObj.getJSONArray("data");
                JSONObject obj = data.getJSONObject(0);
                String url = obj.getString("url");
                String fileType=obj.getString("type");
                downMusic(url,fileType,songName,realPath+song_id,directory);
            }
        }

        try {
            InputStream inputStream= new FileInputStream(new File(realPath+song_id+"/"+songName+".mp3"));
            downloadByInputStream(inputStream,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private static JSONObject getSong(String params, String target_url) {
        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost(target_url);
        httpPost.setHeader("Host", "music.163.com");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:49.0) Gecko/20100101 Firefox/49.0");
        httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");

        String result;
        try {

            StringEntity entity=new StringEntity(params);
            entity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(entity);

            HttpResponse response = httpClient.execute(httpPost);
            result= EntityUtils.toString(response.getEntity(),"UTF-8");
            log.info("响应的数据为:"+result);
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
                log.info("-----------开始下载歌曲:["+songName+"]----------");
                FileUtils.copyInputStreamToFile(entity.getContent(),new File(directory, song));
                log.info("-----------["+songName+"]下载完毕----------");
                return true;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            dir.delete();
            log.error("io异常:"+e);
        }finally{
            httpClient.close();
        }
        return false;
    }

    //获取音乐输入流
    public static InputStream getMusicInputStream(String song_id,String songName) throws Exception {


        String params= AES.getAllParams(song_id);
        JSONObject dataObj = getSong(params, Constant.TARGERT_SONG_URL);
        String url=null;
        if(null!=dataObj) {
            JSONArray data = dataObj.getJSONArray("data");
            JSONObject obj = data.getJSONObject(0);
            url = obj.getString("url");

        }
        if(url==null){
            log.error("--------获得url失败----------");
            return null;
        }

        DefaultHttpClient httpClient=new DefaultHttpClient();
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse response=httpClient.execute(httpGet);
            HttpEntity entity=response.getEntity();
            if(entity!=null){
                log.info("-----------获取歌曲:["+songName+"]输入流----------");
                return entity.getContent();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            log.error("io异常:"+e);
        }finally{
            httpClient.close();
        }
        return null;
    }

    public static void downloadByInputStream(InputStream in, OutputStream out) throws IOException {
        int len=0;
        byte[] buffer = new byte[2048];
        try {
            while ((len = in.read(buffer))>0) {
                out.write(buffer,0,len);//将缓冲区的数据输出到客户端浏览器
            }
            in.close();
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            log.info("---------歌曲传输完毕----------");
        }

    }




    public static void main(String[] ar){
        log.info("测试陈宫");
    }
}
