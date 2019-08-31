package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicItemsController {
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private BrandService brandService;
    /**
     * 公共货架展示
     * @param items
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping("/publicItems")
    public String publicItems(Items items, Integer pageNo, Integer pageSize, HttpServletRequest request,String brandName){
        //初始化页数，条数
        pageSize=pageSize==null?8:pageSize;
        pageNo=pageNo==null?1:pageNo;
        //开始分页
        PageHelper.startPage(pageNo,pageSize);
        //查询满足条件的所有数据
        List<Items> itemsList=itemsService.findItemsByQueryVo(items);
        //封装，底层动态代理
        PageInfo<Items> page=new PageInfo<>(itemsList);
        //存入request域page对象 搜索条件回填
        request.setAttribute("page",page);
        request.setAttribute("items",items);
        //初始化品牌，配件类别
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partsList=brandService.findPartsAll();
        //品牌列表，配件类别，brandName
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("brandName",brandName);
        return "public/publicItems";
    }
}
