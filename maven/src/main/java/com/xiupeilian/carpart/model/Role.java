package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Role implements Serializable {
    private Integer id;

    private String roleName;

    private String roleEnglishName;

    private Integer isAvailable;

    private Date createTime;

    private Integer deleteStatus;


}