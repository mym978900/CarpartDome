package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    /**
     * session过滤及权限控制
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*//获取目标资源路径
        String path=request.getRequestURI();
        //判断路径是否需要登录才能访问
        if (path.contains("login")||path.contains("upLoad")){
            return true;
        }else {
            //进行session过滤
            HttpSession session=request.getSession(false);
            if (null==session){
                //没有登录
                response.sendRedirect(request.getContextPath()+"/login/toLogin");
                return false;
            }else {
                //session不为null
                if (null==session.getAttribute("user")){
                    //没有登录session不为null，但是user对象不存在
                    response.sendRedirect(request.getContextPath()+"/login/toLogin");
                    return false;
                }else {
                    //已登录，开始权限过滤
                    SysUser user=(SysUser)session.getAttribute("user");
                    //查询全部用户对应的权限
                    List<Menu> menuList=userService.findMenusById(user.getId());
                    boolean check=false;
                    //每个导航菜单，关键字
                    for (Menu menu:menuList){
                        if (path.contains(menu.getMenuKey())){
                            check=true;
                        }
                    }
                    //判断是否有权限访问
                    if (check){
                        return true;
                    }else {
                        response.sendRedirect(request.getContextPath()+"/login/noauth");
                        return false;
                    }
                }
            }

        }*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.setAttribute("ctx",request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
