package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.ItemsService;
import com.xiupeilian.carpart.util.AliyunOSSClientUtil;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * @Description:
 * @Author: Tu Xu
 * @CreateDate: 2019/9/1 21:36
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/private")
public class PrivateController {
    @Autowired
    private ItemsService itemsService;

    @RequestMapping("/myCommodity")
    public String myCommodity(HttpServletRequest request){
        //获取当前用户
        SysUser user=(SysUser) request.getSession().getAttribute("user");
        //查询用户货架
        List<Items> itemsList=itemsService.findItemsByUserId(user.getId());
        int num=itemsList.size();
        request.setAttribute("itemsList",itemsList);
        request.setAttribute("userFlag",user.getId());
        request.setAttribute("num",num);
        return "private/myCommodity";
    }
    /**
     * @Description: 添加我的货架
     * @Author:      孟
     * @Param:       [request]
     * @Return       java.lang.String
      **/
    @RequestMapping("/addCommodity")
    public void addCommodity(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception {
        CommonsMultipartFile cf = (CommonsMultipartFile) file;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File f = fi.getStoreLocation();
        System.err.println(AliyunOSSClientUtil.uploadObject2OSS(AliyunOSSClientUtil.getOSSClient(), f, SysConstant.BACKET_NAME, SysConstant.FOLDER));

        System.out.println(AliyunOSSClientUtil.getUrl((SysConstant.FOLDER + f.getName())));
        String imgUrl = "https://" + SysConstant.BACKET_NAME + "." + SysConstant.ENDPOINT + "/" + SysConstant.FOLDER + f.getName();
        response.getWriter().write(imgUrl);
        System.out.println(imgUrl);

    }

    @RequestMapping("/deleteCommodity")
    public String deleteCommodity(String id,String url){
        itemsService.updateDeleteStatusById(Integer.parseInt(id));
        System.out.println("------------------------"+id);
        return "redirect:myCommodity";
    }

}
