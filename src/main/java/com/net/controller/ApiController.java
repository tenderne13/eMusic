package com.net.controller;

import com.net.util.AES;
import com.net.util.Constant;
import com.net.util.httpclient.MusicUtil;
import com.net.util.httpclient.PostUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

        String params= AES.getAllParams(realId);
        logger.info("参数为:"+params);
        boolean isSuccess= MusicUtil.download(realId,songName,params);
        if(isSuccess)
            return "success";
        return "error";
    }

}
