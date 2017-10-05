package com.net.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("route")
public class RouteController {


    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        return "playList";
    }

    //手机端页面显示
    @RequestMapping(value = "wxIndex",method = RequestMethod.GET)
    public String wxIndex(){
        return "wxPlayList";
    }

    @RequestMapping(value = "orderList",method = RequestMethod.GET)
    public String orderList(String id, Model model){
        model.addAttribute("id",id);
        return "orderList";
    }
    //手机歌单页面
    @RequestMapping(value="wxOrderList",method = RequestMethod.GET)
    public String wxOrderList(String id, Model model){
        model.addAttribute("id",id);
        return "wxOrderList";
    }
}
