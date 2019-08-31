package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Notice implements Serializable {
    private Integer id;

    private String title;

    private String url;

    private Date createTime;

    private Integer deleteStatus;

}