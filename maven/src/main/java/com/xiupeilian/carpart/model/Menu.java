package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Menu implements Serializable {
    private Integer id;

    private Integer parentId;

    private String menuName;

    private String url;

    private String logo;

    private Integer seq;

    private Integer isShow;

    private String menuKey;


}