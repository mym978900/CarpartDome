package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Brand implements Serializable {
    private Integer id;

    private String chineseName;

    private Integer parentId;

    private Integer seq;

    private Date createTime;

    private String picUrl;

    private String englishName;


}