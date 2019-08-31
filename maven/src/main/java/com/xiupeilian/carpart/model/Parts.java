package com.xiupeilian.carpart.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Parts implements Serializable {
    private Integer id;

    private String type;

    private String name;

    private Date createTime;

    private Integer seq;


}