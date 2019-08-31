package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class SysUser implements Serializable {
    private Integer id;

    private String loginName;

    private String username;

    private String password;

    private Integer roleId;

    private Integer userStatus;

    private String email;

    private Integer companyId;

    private Integer manageLevel;

    private String phone;

    private Date createTime;

    private String leader;

    private String remark;


}