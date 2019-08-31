package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private UserService userService;

    @RequestMapping("/staffList")
    public String staffList(LoginVo vo, Integer pageNo, Integer pageSize, HttpServletResponse response, HttpServletRequest request) throws Exception {
        pageSize=pageSize==null?10:pageSize;
        pageNo=pageNo==null?1:pageNo;
        PageHelper.startPage(pageNo,pageSize);
        //查询全部
        List<SysUser> userList=userService.findUserByLimit(vo);
        PageInfo<SysUser> page=new PageInfo<SysUser>(userList);
        request.setAttribute("page",page);
        request.setAttribute("pageNo",pageNo);
        request.setAttribute("username",vo.getFindName());
        return "index/staffList";
    }
}
