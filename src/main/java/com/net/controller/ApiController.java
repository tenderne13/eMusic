package com.net.controller;

import com.net.util.Constant;
import com.net.util.httpclient.PostUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("api")
public class ApiController {

    @RequestMapping("playList")
    @ResponseBody
    public String playList(String pageNo,@RequestParam(value ="type",defaultValue = "hot") String type){
        String target_url= Constant.URL+"?order="+type;
        String data = PostUtil.doGetStr(target_url);
        return data;
    }

}
