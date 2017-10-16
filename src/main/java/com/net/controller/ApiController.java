package com.net.controller;

import com.alibaba.fastjson.JSON;
import com.net.Entity.Album;
import com.net.util.AES;
import com.net.util.Constant;
import com.net.util.httpclient.BusUtil;
import com.net.util.httpclient.MusicUtil;
import com.net.util.httpclient.PostUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api")
public class ApiController {

    private static Log logger = LogFactory.getLog(ApiController.class);

    @RequestMapping("playList")
    @ResponseBody
    public String playList(@RequestParam(value ="offset",defaultValue = "0")String offset,@RequestParam(value ="order",defaultValue = "hot") String order){
        String target_url= Constant.URL+"?order="+order+"&offset="+offset;
        String data = PostUtil.doGetStr(target_url);
        return data;
    }

    @RequestMapping("albumList")
    @ResponseBody
    public String albumList(@RequestParam(value ="offset",defaultValue = "0")String offset,@RequestParam(value ="order",defaultValue = "hot") String order){
        String target_url= Constant.URL+"?order="+order+"&offset="+offset;
        String data = PostUtil.doGetStr(target_url);
        Document document = Jsoup.parse(data);
        Elements lis = document.select(".m-cvrlst li");
        List<Album> albumList=new ArrayList<Album>(35);
        Album album = null;
        /*
        * default_playlist.cover_img_url = $(this).find('img')[0].src;
            default_playlist.title = $(this).find('div a')[0].title;
            var url = $(this).find('div a')[0].href;
            var author=$(this).find('p a')[1].title;
            var list_id = getParameterByName('id', url);
            default_playlist.id = 'neplaylist_' + list_id;
            default_playlist.source_url = 'http://music.163.com/#/playlist?id=' + list_id;
            default_playlist.author=author;
            musicList.push(default_playlist);
        * */
        for(Element element : lis){
            String cover_img_url = element.select("img").get(0).attr("src");
            String author=element.select("p a").get(1).attr("title");
            String title = element.select("div a").get(0).attr("title");
            String url = element.select("div a").get(0).attr("href");
            String list_id = url.split("=")[1];
            String source_url="http://music.163.com/#/"+url;

            album=new Album("neplaylist_"+list_id,title,cover_img_url,source_url,author);
            albumList.add(album);
            //System.out.println(album);
        }


        Map<String,Object> map = new HashMap<String, Object>();


        map.put("msg","success");
        map.put("albumList",albumList);
        return JSON.toJSONString(map);
    }

    @RequestMapping("orderList")
    @ResponseBody
    public String orderList(String id){
        String target_url=Constant.ORDER_URL+id;
        return  PostUtil.doGetStr(target_url);
    }


    @RequestMapping("getSongUrl")
    @ResponseBody
    public String getSongUrl(String song_id) throws Exception {
        Map<String,Object> map=new HashMap<String, Object>();

        String songUrl = MusicUtil.getSongUrl(song_id);

        if(null != songUrl && !"".equals(songUrl)){
            map.put("msg","success");
            map.put("url",songUrl);
            return JSON.toJSONString(map);
        }else{
            map.put("msg","error");
            return JSON.toJSONString(map);
        }

    }

    /*该接口已弃用*/
    @RequestMapping("downloadMusic")
    @ResponseBody
    public String downloadMusic(String song_id,String songName) throws Exception {
        String params= AES.getAllParams(song_id);
        logger.info("参数为:"+params);
        boolean isSuccess= MusicUtil.download(song_id,songName,params);
        if(isSuccess)
            return "success";
        return "error";
    }


    @RequestMapping("download")
    public void download(String song_id, String songName, HttpServletResponse response) throws Exception {

        response.setContentType("audio/x-mpeg;charset=ISO8859_1");
        String song=songName+".mp3";
        String filename=new String(song.getBytes(),"ISO8859_1");
        response.addHeader("Content-Disposition","attachment;filename=\""+filename+"\"");

        //拼装请求参数
        String params= AES.getAllParams(song_id);

        byte[] buff = MusicUtil.getMusicBytes(song_id,songName);
        OutputStream outputStream = response.getOutputStream();

        /*---(已废弃的service,受限于需配置服务器地址)---*/
        //MusicUtil.downloadService(song_id,songName,params,outputStream);

        MusicUtil.downloadByBytes(buff,outputStream);

    }


    @RequestMapping("songSearch")
    @ResponseBody
    public String songSearch(String keyword,String offset) throws Exception {
        String params="limit=20&offset="+offset+"&type=1&s="+keyword;
        String result = MusicUtil.songSearch(params);
        return result;
    }

    //获得北京所有的公交线路
    @RequestMapping("getLines")
    @ResponseBody
    public String getLines(){
        return BusUtil.getLines();
    }

    //获取方向和站点的公共方法
    @RequestMapping("busData")
    @ResponseBody
    public String busData(String act,String selBLine,String selBDir){
        String result=null;
        if("getLineDir".equals(act)){
            return BusUtil.getLineDir(Constant.BASE_BUSURL+"act="+act+"&selBLine="+selBLine);
        }else if("getDirStation".equals(act)){
            return BusUtil.getDirStation(Constant.BASE_BUSURL+"act="+act+"&selBLine="+selBLine+"&selBDir="+selBDir);
        }else if("busTime".equals(act)){
            //return BusUtil.get
        }
        return "";
    }



}
