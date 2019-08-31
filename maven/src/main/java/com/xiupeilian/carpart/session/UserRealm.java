package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:登录认证授权
 * @Author: Tu Xu
 * @CreateDate: 2019/8/29 14:38
 * @Version: 1.0
 **/
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
 * @Description: 授权的方法
 * @Author:      孟
 * @Param:       [principalCollection]
 * @Return       org.apache.shiro.authz.AuthorizationInfo
  **/
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection Collection) {
        //用户信息
        SysUser user=(SysUser) Collection.getPrimaryPrincipal();
        //查询出该用户所有的角色信息和权限信息  角色
        Role role=userService.findRoleByRoleId(user.getRoleId());
        List<String> roleList = new ArrayList<>();
        roleList.add(role.getRoleEnglishName());
        //权限信息
        List<Menu> menuList=userService.findMenusById(user.getId());
        List<String> permisstionList=new ArrayList<>();
        for (Menu menu:menuList){
            permisstionList.add(menu.getMenuKey());
        }

        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roleList);
        info.addStringPermissions(permisstionList);

        return info;
    }
/**
 * @Description: 登录认证的方法
 * @Author:      孟
 * @Param:       [authenticationToken]
 * @Return       org.apache.shiro.authc.AuthenticationInfo
  **/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //强转
        UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
        //查询数据库判断用户是否存在
        LoginVo vo = new LoginVo();
        vo.setLoginName(token.getUsername());
        vo.setPassword(SHA1Util.encode(new String(token.getPassword())));
        SysUser user=userService.findUserByLoginNameAndPassword(vo);
        //判断
        if (user==null){
            throw new AccountException("2");
        }else {
            AuthenticationInfo info=new SimpleAuthenticationInfo(user,token.getPassword(),getName());
            return info;
        }

    }
}
