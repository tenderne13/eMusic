package com.net.controller;

import com.net.util.AES;
import com.net.util.Constant;
import com.net.util.EncryptUtils;
import com.net.util.httpclient.MusicUtil;
import com.net.util.httpclient.PostUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("api")
public class ApiController {

    @RequestMapping("playList")
    @ResponseBody
    public String playList(@RequestParam(value ="offset",defaultValue = "0")String offset,@RequestParam(value ="order",defaultValue = "hot") String order){
        String target_url= Constant.URL+"?order="+order+"&offset="+offset;
        String data = PostUtil.doGetStr(target_url);
        return data;
    }

    @RequestMapping("orderList")
    @ResponseBody
    public String orderList(String id){
        String target_url=Constant.ORDER_URL+id;
        return  PostUtil.doGetStr(target_url);
    }

    @RequestMapping("downloadMusic")
    @ResponseBody
    public String downloadMusic(String song_id,String songName) throws Exception {
        String realId=song_id.split("_")[1];
        //String data="{\"id\":["+realId+"],\"br\": 128000,\"csrf_token\": ''}";
        //String data="{\"ids\":["+realId+"],\"br\": 128000,\"csrf_token\": ''}";
        //Map<String, String> paramap = EncryptUtils.encrypt(data);
        //String params = "params="+paramap.get("params")+"&"+"encSecKey="+paramap.get("encSecKey");


        String params= AES.getAllParams(realId);
        System.out.println("参数为:"+params);
        boolean isSuccess= MusicUtil.download(realId,songName,params);
        if(isSuccess)
            return "success";
        return "error";
    }

}
