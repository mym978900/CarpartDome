package com.xiupeilian.carpart.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.xiupeilian.carpart.constant.SysConstant;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private ThreadPoolTaskExecutor executor;
    @Autowired
    private CityService cityService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private RedisTemplate jedis;
    /**
     * 去往登录页
     * @return
     */
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login/login";
    }
    /**
     * 登录
     */
    @RequestMapping("/login")
    public void login(LoginVo vo, HttpServletRequest request, HttpServletResponse response)throws Exception{
        //判断验证码是否正确
        String code = (String) request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
        if (vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            //验证码正确
            //创建subject
            Subject subject= SecurityUtils.getSubject();
            //获取token
            UsernamePasswordToken token=new UsernamePasswordToken(vo.getLoginName(),vo.getPassword());
            //前往登录认证
            //try catch看是否认证失败
            try {
                subject.login(token);
            }catch (Exception es){
                //用户名密码错误
                response.getWriter().write(es.getMessage());
                return;
            }
            //获取存入的用户信息
            SysUser user=(SysUser) SecurityUtils.getSubject().getPrincipal();
            //存入spring-session
            request.getSession().setAttribute("user",user);
            response.getWriter().write("3");
        }else {
            response.getWriter().write("1");
        }

    }

    @RequestMapping("/noauth")
    public String noauth(){
        return "exception/noauth";
    }

    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "login/forgetPassword";
    }

    @RequestMapping("/getPassword")
    public void getPassword(HttpServletResponse response,LoginVo vo) throws Exception {
        //查询sys_user,看看邮箱以及账号是否匹配
        SysUser user=userService.findUserByLoginNameAndEmail(vo);
        if(null==user){
            response.getWriter().write("1");
        }else{
            //生成新密码 0.01s
            String password=new Random().nextInt(899999)+100000+"";

            //修改数据库  0.5s
            user.setPassword(SHA1Util.encode(password));
            userService.updateUser(user);

            //发送邮件到用户邮箱  1s  将同步流程异步化 可以采用多线程
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom("mengzeng123@sina.com");
            message.setTo(user.getEmail());
            message.setSubject("修配连汽配市场密码找回功能:");
            message.setText("您的新密码是"+password);
            //创建一个任务，交给线程池
            MailTask mailTask=new MailTask(mailSender,message);
            executor.execute(mailTask);

            response.getWriter().write("2");

        }
    }

    @RequestMapping("/toRegister")
    public String toRegister(HttpServletRequest request){
        //初始化数据  汽车品牌、配件种类、精品种类
        List<Brand> brandList=brandService.findBrandAll();
        List<Parts> partsList=brandService.findPartsAll();
        List<Prime> primelist=brandService.findPrimeAll();
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("primeList",primelist);
        return "login/register";
    }

    /**
     * 登录账号验证
     * @return
     */
    @RequestMapping("/checkLoginName")
    public void checkLoginName(String loginName,HttpServletResponse response)throws Exception{
        SysUser user=userService.findUserByLoginName(loginName);
        if(null==user){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("2");
        }
    }

    /**
     * 注册电话验证
     * @param telnum
     * @param response
     * @throws Exception
     */
    @RequestMapping("/checkPhone")
    public void checkPhone(String telnum,HttpServletResponse response) throws Exception {
        //根据手机号去数据库查询
        SysUser user=userService.findUserByPhone(telnum);
        if(null==user){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("2");
        }
    }

    /**
     * 注册邮箱验证
     * @param email
     * @param response
     * @throws Exception
     */
    @RequestMapping("/checkEmail")
    public void checkEmail(String email,HttpServletResponse response) throws Exception {
        //根据手机号去数据库查询
        SysUser user=userService.findUserByEmail(email);
        if(null==user){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("2");
        }
    }

    /**
     * 企业名称校验
     */
    @RequestMapping("/checkCompanyname")
    public void checkCompanyname(String companyname,HttpServletResponse response) throws Exception {
        //校验企业名称是否注册过
        Company company=userService.findCompanyByName(companyname);
        if(null==company){
            response.getWriter().write("1");
        }else{
            response.getWriter().write("2");
        }
    }
    /**
     * 省市县三级联动
     */
    @RequestMapping("/getCity")
    public @ResponseBody
    List<City> getCity(Integer parentId){
        parentId=parentId==null?SysConstant.CITY_CHINA_ID:parentId;
        List<City> cityList=cityService.findCitiesByParentId(parentId);
        return cityList;
    }

    /**
     * 注册
     */
    @RequestMapping("/register")
    public String register(RegisterVo vo){
        //参数
        //先插入企业表，再插入用户表,需要在一个事务进行控制
        userService.addRegsiter(vo);
        return "redirect:toLogin";
    }

    /**
     * 阿里雲sms
     */
    @RequestMapping("/toSms")
    public String toSms(){
        return "sms/sms";
    }
    /**
     * 发送短信
     */
    @RequestMapping("/smsControllter")
    public void smsControllter(String phone,HttpServletResponse resp){
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIIxKfL09legx7", "fbsGtBZaAxDTLM1nwOSpPWDrlZJ1dm");
        IAcsClient client = new DefaultAcsClient(profile);

        String password=new Random().nextInt(899999)+100000+"";
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "default");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "\u4fee\u914d\u8fde");
        request.putQueryParameter("TemplateCode", "SMS_172884080");
        request.putQueryParameter("TemplateParam", "{\"num\":\""+password+"\"}");
        String json = JSONUtils.toJSONString(password);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            System.out.println(password);
            jedis.boundValueOps(phone).set(password);
            //失效时间
            jedis.expire(phone,1, TimeUnit.MINUTES);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    /**
     * 验证
     */
    @RequestMapping("/smsQuery")
    public void smsQuery(String code,String phone,HttpServletResponse response)throws Exception{
        String cod = (String) jedis.boundValueOps(phone).get();
        if (null==cod){
            response.getWriter().write("1");
            return;
        }
        if (code.equals(cod)){
            response.getWriter().write("2");
        }else {
            response.getWriter().write("3");
        }

    }
    @RequestMapping("/test")
    public void test(int id){
        cityService.deleteCityById(id);
    }
}
