package com.xiupeilian.carpart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/9/1 21:36
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/private")
public class PrivateController {

    @RequestMapping("/myCommodity")
    public String myCommodity(){
        return "private/myCommodity";
    }
}
