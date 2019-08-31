package com.xiupeilian.carpart.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVo implements Serializable {
    private String loginName;
    private String password;
    private String validate;
    private String email;
    private String findName;

}
