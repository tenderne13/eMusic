package com.net.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("route")
public class RouteController {


    @RequestMapping(value = "index",method = RequestMethod.GET)
    public String index(){
        return "playList";
    }
}
